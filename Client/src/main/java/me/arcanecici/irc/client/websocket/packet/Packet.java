package me.arcanecici.irc.client.websocket.packet;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.arcanecici.irc.client.websocket.WSClientOverride;
import me.arcanecici.irc.client.websocket.packet.impl.client.WSPacketAddRoom;
import me.arcanecici.irc.client.websocket.packet.impl.client.WSPacketRoomMessage;
import me.arcanecici.irc.client.websocket.packet.impl.client.WSPacketUpdateScreen;
import me.arcanecici.irc.client.websocket.packet.impl.server.WSPacketRoomChange;

import java.io.IOException;


public abstract class Packet {
    public static final BiMap<Object, Object> REGISTRY = HashBiMap.create();

    static {
        REGISTRY.put(WSPacketAddRoom.class, 1);
        REGISTRY.put(WSPacketUpdateScreen.class, 2);
        REGISTRY.put(WSPacketRoomMessage.class, 3);
        REGISTRY.put(WSPacketRoomChange.class, 4);
    }

    public abstract void write(ByteBufWrapper buffer) throws IOException;

    public abstract void read(ByteBufWrapper buffer) throws IOException;

    public abstract void handle(WSClientOverride webSocket) throws IOException;

}
