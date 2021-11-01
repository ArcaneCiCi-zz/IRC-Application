package me.arcanecici.irc.server.network.packets.impl.shared;

import io.javalin.websocket.WsContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import me.arcanecici.irc.server.Server;
import me.arcanecici.irc.server.feature.irc.AbstractIRCRoom;
import me.arcanecici.irc.server.network.PacketHandler;
import me.arcanecici.irc.server.network.packets.ByteBufWrapper;
import me.arcanecici.irc.server.network.packets.Packet;
import me.arcanecici.irc.server.network.packets.PacketInfo;
import me.arcanecici.irc.server.profile.Handshake;
import me.arcanecici.irc.server.profile.Profile;

import java.io.IOException;

@Getter
@AllArgsConstructor

@PacketInfo(id = 3)
public class WSPacketMessage extends Packet {
    private String message;

    public WSPacketMessage() {
    }

    @Override
    public void write(WsContext webSocket, ByteBufWrapper buf) throws IOException {
        buf.writeString(message);
    }

    @Override
    public void read(WsContext webSocket, ByteBufWrapper buf) throws IOException {
        this.message = buf.readString(512);
    }

    @Override
    public void process(WsContext webSocket, PacketHandler packetHandler) {
        Handshake handshake = webSocket.attribute("handshake");
        Profile profile = Server.getInstance().getProfileHandler().getProfile(handshake.getUsername());

        if (handshake == null) {
            webSocket.session.close();
            return;
        }

        AbstractIRCRoom room = Server.getInstance().getRoomManager().getRoomById(profile.getCurrentRoom().getId());

        profile.sendPacket(webSocket, new WSPacketMessage(profile.getUsername() + ": " + this.message));
    }
}
