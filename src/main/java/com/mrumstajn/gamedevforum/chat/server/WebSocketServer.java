package com.mrumstajn.gamedevforum.chat.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrumstajn.gamedevforum.auth.service.query.AuthorizationQueryService;
import com.mrumstajn.gamedevforum.chat.packets.*;
import com.mrumstajn.gamedevforum.user.dto.response.ForumUserResponse;
import com.mrumstajn.gamedevforum.user.entity.ForumUser;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.net.InetSocketAddress;
import java.util.HashMap;



@Slf4j
public class WebSocketServer extends org.java_websocket.server.WebSocketServer {
    private final Logger logger = LoggerFactory.getLogger(getClass().getSimpleName());

    private final HashMap<InetSocketAddress, WebSocketClient> clientMap;

    private AuthorizationQueryService authorizationQueryService;

    private ObjectMapper objectMapper;

    private ModelMapper modelMapper;

    public WebSocketServer(int webSocketServerPort) {
        super(new InetSocketAddress(webSocketServerPort));

        clientMap = new HashMap<>();
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {

    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        WebSocketClient client = clientMap.get(webSocket.getRemoteSocketAddress());

        broadcast(packetToJSON(new SystemMessagePacket(client.getUser().getUsername() + " has left the chat")));

        disconnectClient(webSocket);
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        Packet packet;
        try {
            packet = JSONToPacket(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            packet = null;
        }

        if (packet == null || !isPacketValid(packet)) {
            webSocket.send(packetToJSON(new ErrorPacket("Invalid data packet")));
            return;
        }

        if (!isClientAuthenticated(webSocket) && packet.getType() != PacketType.LOGIN_REQUEST) {
            webSocket.send(packetToJSON(new ErrorPacket("Client is not authenticated, access denied")));
            return;
        }

        switch (packet.getType()) {
            case MESSAGE_REQUEST -> {
                ForumUser author = clientMap.get(webSocket.getRemoteSocketAddress()).getUser();
                ForumUserResponse authorResponse = modelMapper.map(author, ForumUserResponse.class);
                broadcast(packetToJSON(new MessageResponsePacket(((MessageRequestPacket) packet).getMessage(), authorResponse)));
            }
            case LOGIN_REQUEST -> {
                WebSocketClient client = handleLogin(webSocket, packet);
                if (client != null) {
                    webSocket.send(packetToJSON(new LoginResponsePacket(LoginResponseStatus.OK)));
                    broadcast(packetToJSON(new SystemMessagePacket(client.getUser().getUsername() + " has joined the chat")));
                } else {
                    webSocket.send(packetToJSON(new LoginResponsePacket(LoginResponseStatus.INVALID_TOKEN)));
                }
            }
            default -> webSocket.send(packetToJSON(new ErrorPacket("Invalid packet type")));
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        disconnectClient(webSocket);
    }

    @Override
    public void onStart() {
        logger.info("Web socket server started!");
    }

    private void disconnectClient(WebSocket webSocket) {
        if (!webSocket.isClosed()) {
            webSocket.close();
        }

        clientMap.remove(webSocket.getRemoteSocketAddress());
    }

    private boolean isClientAuthenticated(WebSocket webSocket) {
        if (!clientMap.containsKey(webSocket.getRemoteSocketAddress())) {
            return false;
        }

        return clientMap.get(webSocket.getRemoteSocketAddress()).getAuthenticated();
    }

    private String packetToJSON(Packet packet) {
        try {
            return objectMapper.writeValueAsString(packet);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Packet JSONToPacket(String json) throws JsonProcessingException {
        Packet packet = objectMapper.readValue(json, Packet.class);

        switch (packet.getType()){
            case LOGIN_REQUEST -> {
                return objectMapper.readValue(json, LoginRequestPacket.class);
            }
            case LOGIN_RESPONSE -> {
                return objectMapper.readValue(json, LoginResponsePacket.class);
            }
            case MESSAGE_REQUEST -> {
                return objectMapper.readValue(json, MessageRequestPacket.class);
            }
            case MESSAGE_RESPONSE -> {
                return objectMapper.readValue(json, MessageResponsePacket.class);
            }
            case SYSTEM_MESSAGE -> {
                return objectMapper.readValue(json, SystemMessagePacket.class);
            }
            case ERROR -> {
                return objectMapper.readValue(json, ErrorPacket.class);
            }

        }

        return packet;
    }

    private WebSocketClient handleLogin(WebSocket webSocket, Packet packet) {
        String jwtToken = ((LoginRequestPacket) packet).getToken();
        UsernamePasswordAuthenticationToken authenticationToken = authorizationQueryService.getAuthenticationFromJWTToken(jwtToken);
        if (authenticationToken == null) {
            return null;
        }

        WebSocketClient webSocketClient = new WebSocketClient();
        webSocketClient.setSocketAddress(webSocket.getRemoteSocketAddress());
        webSocketClient.setUser((ForumUser) authenticationToken.getPrincipal());
        webSocketClient.setAuthenticated(true);

        clientMap.put(webSocket.getRemoteSocketAddress(), webSocketClient);

        return webSocketClient;
    }

    private boolean isPacketValid(Packet packet){
        switch (packet.getType()){
            case LOGIN_REQUEST -> {
                LoginRequestPacket loginRequestPacketPayload = (LoginRequestPacket) packet;
                return loginRequestPacketPayload.getToken().length() > 0;
            }
            case MESSAGE_REQUEST -> {
                return ((MessageRequestPacket) packet).getMessage().length() > 0;
            }
            case MESSAGE_RESPONSE -> {
                return ((MessageResponsePacket) packet).getMessage().length() > 0;
            }
            case SYSTEM_MESSAGE -> {
                return ((SystemMessagePacket) packet).getMessage().length() > 0;
            }
            default -> {
                return false;
            }
        }
    }

    public void setAuthorizationQueryService(AuthorizationQueryService authorizationQueryService) {
        this.authorizationQueryService = authorizationQueryService;
    }

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
