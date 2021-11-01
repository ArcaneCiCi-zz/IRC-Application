package me.arcanecici.irc.server.profile;

import io.javalin.websocket.WsContext;
import me.arcanecici.irc.server.Server;
import me.arcanecici.irc.server.network.packets.Packet;

import java.util.Map;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

public class Handshake {
    private final Map<String, String> headerMap;
    private WsContext wsContext;

    public Handshake(Map<String, String> headerMap) {
        this.headerMap = headerMap;
    }

    public Profile getProfile() {
        return Server.getInstance().getProfileHandler().getProfile(this.getUsername());
    }

    public String getUsername() {
        return headerMap.get("username");
    }

    public String getKey() {
        return headerMap.get("clientKey");
    }

    public void sendPacket(Packet packet) {
        Server.getInstance().getPacketHandler().sendPacket(wsContext, packet);
    }
}
