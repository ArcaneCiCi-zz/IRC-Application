package me.arcanecici.irc.client.websocket.packet.impl.client;

import lombok.Setter;
import me.arcanecici.irc.client.feature.irc.IRCRoom;
import me.arcanecici.irc.client.websocket.WSClientOverride;
import me.arcanecici.irc.client.websocket.packet.ByteBufWrapper;
import me.arcanecici.irc.client.websocket.packet.Packet;

import java.io.IOException;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

@Setter
public class WSPacketAddRoom extends Packet {

    private String name;
    private int id;
    private int currentUsers;
    private int userLimit;
    private boolean privateRoom;

    public WSPacketAddRoom() {
    }

    @Override
    public void write(ByteBufWrapper buffer) throws IOException {
    }

    @Override
    public void read(ByteBufWrapper buffer) throws IOException {
        setName(buffer.readString(512));
        setId(buffer.readInt());
        setCurrentUsers(buffer.readInt());
        setUserLimit(buffer.readInt());
        setPrivateRoom(buffer.readBoolean());
    }

    @Override
    public void handle(WSClientOverride webSocket) throws IOException {
        webSocket.handleRoomAdd(new IRCRoom(
                this.name,
                this.id,
                this.currentUsers,
                this.userLimit,
                this.privateRoom
        ));
    }
}
