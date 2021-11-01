package me.arcanecici.irc.server.network.packets.impl.server;

import io.javalin.websocket.WsContext;
import lombok.Getter;
import me.arcanecici.irc.server.Server;
import me.arcanecici.irc.server.feature.irc.AbstractIRCRoom;
import me.arcanecici.irc.server.network.PacketHandler;
import me.arcanecici.irc.server.network.packets.ByteBufWrapper;
import me.arcanecici.irc.server.network.packets.Packet;
import me.arcanecici.irc.server.network.packets.PacketInfo;
import me.arcanecici.irc.server.network.packets.impl.client.WSPacketUpdateScreen;
import me.arcanecici.irc.server.network.packets.impl.shared.WSPacketMessage;
import me.arcanecici.irc.server.profile.Handshake;
import me.arcanecici.irc.server.profile.Profile;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

@Getter
@PacketInfo(id = 4)
public class WSPacketRoomJoin extends Packet {

    private int roomId;

    public WSPacketRoomJoin() {
    }

    @Override
    public void write(WsContext webSocket, ByteBufWrapper buf) {
    }

    @Override
    public void read(WsContext webSocket, ByteBufWrapper buf) {
        this.roomId = buf.readInt();
    }

    @Override
    public void process(WsContext webSocket, PacketHandler packetHandler) {
        Handshake handshake = webSocket.attribute("handshake");
        Profile profile = Server.getInstance().getProfileHandler().getProfile(handshake.getUsername());

        if (handshake == null) {
            webSocket.session.close();
            return;
        }

        if (this.roomId == 0) {
            AbstractIRCRoom currentRoom = Server.getInstance().getRoomManager().getRoomById(profile.getCurrentRoom().getId());
            profile.sendPacket(webSocket, new WSPacketUpdateScreen("lobby"));
            currentRoom.getUsers().remove(profile);
            profile.setCurrentRoom(null);

            for (Profile user : currentRoom.getUsers()) {
                user.sendPacket(webSocket, new WSPacketMessage("[LEAVE] '" + handshake.getUsername() + "'" + " has left the room."));
            }
            return;
        }

        AbstractIRCRoom room = Server.getInstance().getRoomManager().getRoomById(this.roomId);

        if (!room.getUsers().contains(profile)) {
            room.addUser(Server.getInstance().getProfileHandler().getProfile(handshake.getUsername()));
        }

        profile.setCurrentRoom(room);

        for (Profile user : room.getUsers()) {
            user.sendPacket(webSocket, new WSPacketMessage("[JOIN] '" + profile.getUsername() + "' has joined the room."));
        }
    }
}
