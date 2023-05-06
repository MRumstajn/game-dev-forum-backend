package com.mrumstajn.gamedevforum.chat.packets;

import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLeavePacket extends Packet{
    private ForumUserResponse user;

    public UserLeavePacket(){
        setType(PacketType.USER_LEAVE);
    }

    public UserLeavePacket(ForumUserResponse user){
        setType(PacketType.USER_LEAVE);
        this.user = user;
    }
}
