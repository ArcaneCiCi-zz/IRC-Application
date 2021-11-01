package me.arcanecici.irc.client.websocket.packet.impl.server;

import me.arcanecici.irc.client.websocket.WSClientOverride;
import me.arcanecici.irc.client.websocket.packet.ByteBufWrapper;
import me.arcanecici.irc.client.websocket.packet.Packet;

public class WSPacketRoomChange extends Packet {
    private final int roomId;

    public WSPacketRoomChange(int id) {
        this.roomId = id;
    }

    @Override
    public void write(ByteBufWrapper buffer) {
        buffer.writeInt(roomId);
    }

    @Override
    public void read(ByteBufWrapper buffer) {
    }

    @Override
    public void handle(WSClientOverride webSocket) {
    }
}
