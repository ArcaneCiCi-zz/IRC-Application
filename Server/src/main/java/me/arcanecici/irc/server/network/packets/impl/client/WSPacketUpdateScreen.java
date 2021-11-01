package me.arcanecici.irc.server.network.packets.impl.client;

import io.javalin.websocket.WsContext;
import lombok.AllArgsConstructor;
import me.arcanecici.irc.server.network.PacketHandler;
import me.arcanecici.irc.server.network.packets.ByteBufWrapper;
import me.arcanecici.irc.server.network.packets.Packet;
import me.arcanecici.irc.server.network.packets.PacketInfo;

import java.io.IOException;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

@AllArgsConstructor
@PacketInfo(id = 2)
public class WSPacketUpdateScreen extends Packet {

    private String screen;

    public WSPacketUpdateScreen() {
    }

    @Override
    public void write(WsContext webSocket, ByteBufWrapper buf) throws IOException {
        buf.writeString(screen);
    }

    @Override
    public void read(WsContext webSocket, ByteBufWrapper buf) {
    }

    @Override
    public void process(WsContext webSocket, PacketHandler packetHandler) {
    }
}
