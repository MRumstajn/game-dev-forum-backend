package com.mrumstajn.gamedevforum.chat.server;

import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import lombok.Getter;
import lombok.Setter;

import java.net.InetSocketAddress;

@Getter
@Setter
public class WebSocketClient {
    private ForumUser user;

    private InetSocketAddress socketAddress;

    private Boolean authenticated;
}
