package com.mrumstajn.gamedevforum.chat.packets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorPacket extends Packet{
    private String errorMsg;

    public ErrorPacket(){
        setType(PacketType.ERROR);
    }

    public ErrorPacket(String errorMsg){
        setType(PacketType.ERROR);
        this.errorMsg = errorMsg;
    }
}
