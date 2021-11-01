package me.arcanecici.irc.server.network.packets;

import io.javalin.websocket.WsContext;
import me.arcanecici.irc.server.network.PacketHandler;

import java.io.IOException;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

public abstract class Packet {

    public abstract void write(WsContext webSocket, ByteBufWrapper buf) throws IOException;

    public abstract void read(WsContext webSocket, ByteBufWrapper buf) throws IOException;

    public abstract void process(WsContext webSocket, PacketHandler packetHandler) throws IOException;

}