package com.mrumstajn.gamedevforum.chat.packets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnlineUsersRequestPacket extends Packet{

    public OnlineUsersRequestPacket(){
        setType(PacketType.ONLINE_USERS_REQUEST);
    }
}
