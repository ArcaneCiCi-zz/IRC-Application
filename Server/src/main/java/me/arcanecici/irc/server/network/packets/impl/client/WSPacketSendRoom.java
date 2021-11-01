package me.arcanecici.irc.server.network.packets.impl.client;

import io.javalin.websocket.WsContext;
import lombok.AllArgsConstructor;
import lombok.Setter;
import me.arcanecici.irc.server.network.PacketHandler;
import me.arcanecici.irc.server.network.packets.ByteBufWrapper;
import me.arcanecici.irc.server.network.packets.Packet;
import me.arcanecici.irc.server.network.packets.PacketInfo;

import java.io.IOException;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

@Setter
@AllArgsConstructor
@PacketInfo(id = 1)
public class WSPacketSendRoom extends Packet {

    private String name;
    private int id;
    private int currentUsers;
    private int userLimit;
    private boolean privateRoom;

    public WSPacketSendRoom() {
    }

    @Override
    public void write(WsContext webSocket, ByteBufWrapper buf) throws IOException {
        buf.writeString(this.name);
        buf.writeInt(this.id);
        buf.writeInt(this.currentUsers);
        buf.writeInt(this.userLimit);
        buf.writeBoolean(this.privateRoom);
    }

    @Override
    public void read(WsContext webSocket, ByteBufWrapper buf) {
    }

    @Override
    public void process(WsContext webSocket, PacketHandler packetHandler) {
    }
}
