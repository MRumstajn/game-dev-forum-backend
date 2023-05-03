package com.mrumstajn.gamedevforum.service.query;

import com.mrumstajn.gamedevforum.entity.ForumUser;

import java.util.List;

public interface ForumUserQueryService {

    ForumUser getById(Long id);

    List<ForumUser> getAllById(List<Long> ids);

    ForumUser getByUsernameExact(String username);

    Long getTotalCount();

    Boolean isUsernameTaken(String username);
}
