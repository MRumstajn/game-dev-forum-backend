package com.mrumstajn.gamedevforum.chat.packets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponsePacket extends Packet {
    private LoginResponseStatus status;

    public LoginResponsePacket(){
        setType(PacketType.LOGIN_RESPONSE);
    }

    public LoginResponsePacket(LoginResponseStatus status){
        setType(PacketType.LOGIN_RESPONSE);
        this.status = status;
    }
}
