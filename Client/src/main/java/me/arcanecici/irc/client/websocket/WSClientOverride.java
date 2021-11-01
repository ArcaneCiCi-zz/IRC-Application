package me.arcanecici.irc.client.websocket;

import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import me.arcanecici.irc.client.Client;
import me.arcanecici.irc.client.feature.irc.IRCRoom;
import me.arcanecici.irc.client.ui.menu.impl.IRCChatMenu;
import me.arcanecici.irc.client.websocket.packet.ByteBufWrapper;
import me.arcanecici.irc.client.websocket.packet.Packet;
import me.arcanecici.irc.client.websocket.packet.impl.server.WSPacketRoomChange;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

public class WSClientOverride extends WebSocketClient {

    public WSClientOverride(URI uri, Map<String, String> map) {
        super(uri, new Draft_6455(), map, 0);
    }

    @SneakyThrows
    public void sendToServer(Packet packet) {
        if (!this.isOpen()) {
            return;
        }
        final ByteBufWrapper buf = new ByteBufWrapper(Unpooled.buffer());
        buf.writeVarInt((Integer) Packet.REGISTRY.get(packet.getClass()));
        packet.write(buf);
        this.send(buf.array());
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
    }

    public void handle(ByteBufWrapper buf) {
        final int id = buf.readVarInt();

        final Class<?> packetClass = (Class<?>) Packet.REGISTRY.inverse().get(id);

        try {
            final Packet packet = packetClass == null ? null : (Packet) packetClass.newInstance();

            if (packet == null) return;
            packet.read(buf);
            packet.handle(this);

            if (id == 1) {
                Client.getInstance().displayScreen(new IRCChatMenu());
            }

        } catch (Exception exception) {
            System.out.println("Error from: " + packetClass);
            exception.printStackTrace();
        }
    }

    public void handleRoomAdd(IRCRoom room) {
        Client.getInstance().getFeatureManager().getIrcRooms().add(room);
    }

    public void handleRoomChange(int roomId) {
        sendToServer(new WSPacketRoomChange(roomId));
        System.out.println();
    }

    @Override
    public void send(String string) {
        if (!this.isOpen()) {
            return;
        }
        super.send(string);
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        this.handle(new ByteBufWrapper(Unpooled.wrappedBuffer(bytes.array())));
    }

    @Override
    public void onMessage(String s) {
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Close: " + s + "(" + i + ")");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("[Client (Websocket Error)] " + e.getMessage());
    }
}
