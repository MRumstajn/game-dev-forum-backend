package com.mrumstajn.gamedevforum.chat.packets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemMessagePacket extends Packet{
    private String message;

    public SystemMessagePacket(){
        setType(PacketType.SYSTEM_MESSAGE);
    }

    public SystemMessagePacket(String message){
        setType(PacketType.SYSTEM_MESSAGE);
        this.message = message;
    }
}
