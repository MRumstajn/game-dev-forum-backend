package com.mrumstajn.gamedevforum.chat.packets;

import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponsePacket extends Packet{
    private String message;

    private ForumUserResponse author;

    public MessageResponsePacket(){
        setType(PacketType.MESSAGE_RESPONSE);
    }

    public MessageResponsePacket(String message, ForumUserResponse authot){
        setType(PacketType.MESSAGE_RESPONSE);
        this.message = message;
        this.author = authot;
    }
}
