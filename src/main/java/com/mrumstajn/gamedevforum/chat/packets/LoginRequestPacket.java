package com.mrumstajn.gamedevforum.chat.packets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestPacket extends Packet {
    private String token;

    public LoginRequestPacket(){
        setType(PacketType.LOGIN_REQUEST);
    }

    public LoginRequestPacket(String token){
        setType(PacketType.LOGIN_REQUEST);
        this.token = token;
    }
}
