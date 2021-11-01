package me.arcanecici.irc.server.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import io.javalin.websocket.WsContext;
import io.netty.buffer.Unpooled;
import me.arcanecici.irc.server.network.packets.ByteBufWrapper;
import me.arcanecici.irc.server.network.packets.Packet;
import me.arcanecici.irc.server.network.packets.PacketInfo;
import me.arcanecici.irc.server.util.ClassUtils;

import java.io.IOException;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

public class PacketHandler {

    private final BiMap<Class<?>, Integer> PACKETS = HashBiMap.create();

    public PacketHandler() {
        try {
            ClassUtils.getClassesForPackage("me.arcanecici.irc.server.network.packets.impl").forEach(this::register);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void register(Class<?> packetClass) {
        if (!Packet.class.isAssignableFrom(packetClass)) return;

        PacketInfo packetInfo = packetClass.getAnnotation(PacketInfo.class);
        if (packetInfo == null) {
            System.err.println("[Packet: " + packetClass.getSimpleName() + "] doesn't have the @PacketInfo annotation.");
            return;
        }

        if (!packetInfo.enabled()) {
            return;
        }

        this.PACKETS.put(packetClass, packetInfo.id());
    }

    public void handlePacket(WsContext wsContext, ByteBufWrapper buf) {
        int packetId = buf.readVarInt();
        Class<?> packetClass = this.PACKETS.inverse().get(packetId);
        if (packetClass == null) return;

        try {
            Packet packet = (Packet) packetClass.newInstance();

            packet.read(wsContext, buf);
            packet.process(wsContext, this);
        } catch (InstantiationException | IllegalAccessException | IOException ex) {
            ex.printStackTrace();
        }
    }

    public void sendPacket(WsContext wsContext, Packet packet) {
        if (!PACKETS.containsKey(packet.getClass())) return;

        if (wsContext != null && wsContext.session.isOpen()) {
            ByteBufWrapper wrapper = new ByteBufWrapper(Unpooled.buffer());
            wrapper.writeVarInt(this.PACKETS.get(packet.getClass()));

            try {
                packet.write(wsContext, wrapper);
                wsContext.send(wrapper.nioBuffer());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
