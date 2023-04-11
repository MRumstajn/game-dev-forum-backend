package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.CreateForumThreadRequest;
import com.mrumstajn.gamedevforum.dto.request.CreatePostRequest;
import com.mrumstajn.gamedevforum.entity.Category;
import com.mrumstajn.gamedevforum.entity.ForumThread;
import com.mrumstajn.gamedevforum.entity.ForumUserRole;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.ForumThreadRepository;
import com.mrumstajn.gamedevforum.service.command.ForumThreadCommandService;
import com.mrumstajn.gamedevforum.service.command.PostCommandService;
import com.mrumstajn.gamedevforum.service.query.CategoryQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class ForumThreadCommandServiceImpl implements ForumThreadCommandService {
    private final ForumThreadRepository forumThreadRepository;

    private final PostCommandService postCommandService;

    private final CategoryQueryService categoryQueryService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public ForumThread create(CreateForumThreadRequest request) {
        // only allow admins to make threads in news section categories
        Category targetCategory = categoryQueryService.getById(request.getCategoryIdentifier());
        if (targetCategory.getSection().getTitle().equalsIgnoreCase("news") &&
                UserUtil.getCurrentUser().getRole() != ForumUserRole.ADMIN){
            throw new UnauthorizedActionException("Only ADMIN users can create threads in this category");
        }

        ForumThread newThread = modelMapper.map(request, ForumThread.class);
        newThread.setAuthor(UserUtil.getCurrentUser());
        newThread.setCreationDateTime(LocalDateTime.now());
        newThread.setCategory(targetCategory);

        forumThreadRepository.save(newThread);

        CreatePostRequest postRequest = new CreatePostRequest();
        postRequest.setThreadIdentifier(newThread.getId());
        postRequest.setContent(request.getFirstPostContent());

        postCommandService.create(postRequest);

        return newThread;
    }
}
