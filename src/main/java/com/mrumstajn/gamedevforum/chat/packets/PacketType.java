package com.mrumstajn.gamedevforum.chat.packets;

public enum PacketType {
    LOGIN_REQUEST,

    LOGIN_RESPONSE,

    ERROR,

    MESSAGE_REQUEST,

    MESSAGE_RESPONSE,

    SYSTEM_MESSAGE,

    USER_JOIN,

    USER_LEAVE,

    ONLINE_USERS_REQUEST,

    ONLINE_USERS_RESPONSE,
}
