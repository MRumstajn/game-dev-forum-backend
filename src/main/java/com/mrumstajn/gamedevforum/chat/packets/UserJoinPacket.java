package com.mrumstajn.gamedevforum.chat.packets;

import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinPacket extends Packet{
    private ForumUserResponse user;

    public UserJoinPacket(){
        setType(PacketType.USER_JOIN);
    }

    public UserJoinPacket(ForumUserResponse user){
        setType(PacketType.USER_JOIN);
        this.user = user;
    }
}
