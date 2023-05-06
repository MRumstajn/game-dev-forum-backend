package com.mrumstajn.gamedevforum.chat.packets;

import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OnlineUsersResponsePacket extends Packet{
    private List<ForumUserResponse> onlineUsers;

    public OnlineUsersResponsePacket(){
        setType(PacketType.ONLINE_USERS_RESPONSE);
    }

    public OnlineUsersResponsePacket(List<ForumUserResponse> onlineUsers){
        setType(PacketType.ONLINE_USERS_RESPONSE);
        this.onlineUsers = onlineUsers;
    }
}
