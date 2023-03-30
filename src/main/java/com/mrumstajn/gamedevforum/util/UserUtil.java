package com.mrumstajn.gamedevforum.util;

import com.mrumstajn.gamedevforum.entity.ForumUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static ForumUser getCurrentUser(){
        return (ForumUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
