package com.mrumstajn.gamedevforum.service.command.impl;

import com.mrumstajn.gamedevforum.dto.request.ChangeForumUserPasswordRequest;
import com.mrumstajn.gamedevforum.dto.request.CreateForumUserRequest;
import com.mrumstajn.gamedevforum.dto.request.EditForumUserRequest;
import com.mrumstajn.gamedevforum.entity.ForumUser;
import com.mrumstajn.gamedevforum.entity.ForumUserRole;
import com.mrumstajn.gamedevforum.exception.DuplicateResourceException;
import com.mrumstajn.gamedevforum.exception.LoginException;
import com.mrumstajn.gamedevforum.exception.UnauthorizedActionException;
import com.mrumstajn.gamedevforum.repository.ForumUserRepository;
import com.mrumstajn.gamedevforum.service.command.ForumUserCommandService;
import com.mrumstajn.gamedevforum.service.query.ForumUserQueryService;
import com.mrumstajn.gamedevforum.util.UserUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ForumUserCommandServiceImpl implements ForumUserCommandService {
    private final ForumUserRepository forumUserRepository;

    private final ForumUserQueryService forumUserQueryService;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ForumUser create(CreateForumUserRequest request) {
        if (forumUserQueryService.isUsernameTaken(request.getUsername())){
            throw new DuplicateResourceException("That username is taken");
        }

        ForumUser newUser = modelMapper.map(request, ForumUser.class);
        newUser.setJoinDate(LocalDate.now());
        newUser.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(ForumUserRole.USER);

        return forumUserRepository.save(newUser);
    }

    @Override
    @Transactional
    public ForumUser edit(Long id, EditForumUserRequest request) {
        if (!UserUtil.isUserAdmin() && !isCurrentUser(id)){
            throw new UnauthorizedActionException("User is not the owner of the specified account");
        }

        ForumUser existingUser = forumUserQueryService.getById(id);

        modelMapper.map(request, existingUser);

        return forumUserRepository.save(existingUser);
    }

    @Override
    @Transactional
    public ForumUser changePassword(Long id, ChangeForumUserPasswordRequest request) {
        if (!isCurrentUser(id)){
            throw new UnauthorizedActionException("User is not the owner of the specified account");
        }

        ForumUser existingUser = forumUserQueryService.getById(id);

        if (!passwordEncoder.matches(request.getCurrentPassword(), existingUser.getPasswordHash())){
            throw new LoginException("Invalid credentials");
        }

        existingUser.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));

        return forumUserRepository.save(existingUser);
    }

    @Override
    public ForumUser grantRole(Long id, ForumUserRole role) {
        ForumUser user = forumUserQueryService.getById(id);
        user.setRole(role);

        return forumUserRepository.save(user);
    }

    private boolean isCurrentUser(Long id){
        return Objects.equals(UserUtil.getCurrentUser().getId(), id);
    }
}
