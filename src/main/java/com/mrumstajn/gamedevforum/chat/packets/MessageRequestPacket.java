package com.mrumstajn.gamedevforum.chat.packets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequestPacket extends Packet{
    private String message;

    public MessageRequestPacket(){
        setType(PacketType.MESSAGE_REQUEST);
    }

    public MessageRequestPacket(String message){
        setType(PacketType.MESSAGE_REQUEST);
        this.message = message;
    }
}
