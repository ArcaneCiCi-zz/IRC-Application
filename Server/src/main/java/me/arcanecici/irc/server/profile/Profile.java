package me.arcanecici.irc.server.profile;

import io.javalin.websocket.WsContext;
import lombok.Getter;
import lombok.Setter;
import me.arcanecici.irc.server.Server;
import me.arcanecici.irc.server.feature.irc.AbstractIRCRoom;
import me.arcanecici.irc.server.network.packets.Packet;

/**
 * @author ArcaneCiCi - 10/30/2021
 * cheatbreakerplus.com & github.com/ArcaneCiCi
 */

@Getter
@Setter
public class Profile {

    private WsContext webSocket;

    private String username;

    private AbstractIRCRoom currentRoom;

    public Profile(String username) {
        this.username = username;
    }

    public void sendPacket(WsContext ws, Packet packet) {
        Server.getInstance().getPacketHandler().sendPacket(ws, packet);
    }

}
