package me.arcanecici.irc.server;

import io.javalin.Javalin;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import me.arcanecici.irc.server.config.Configuration;
import me.arcanecici.irc.server.feature.irc.AbstractIRCRoom;
import me.arcanecici.irc.server.feature.irc.RoomManager;
import me.arcanecici.irc.server.network.PacketHandler;
import me.arcanecici.irc.server.network.packets.ByteBufWrapper;
import me.arcanecici.irc.server.network.packets.impl.client.WSPacketSendRoom;
import me.arcanecici.irc.server.profile.Handshake;
import me.arcanecici.irc.server.profile.Profile;
import me.arcanecici.irc.server.profile.ProfileHandler;
import org.eclipse.jetty.websocket.api.Session;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

@Getter
@Setter
public class Server {

    @Getter
    private static Server instance;
    private final Javalin app;
    private final PacketHandler packetHandler;
    private final ProfileHandler profileHandler;
    private final RoomManager roomManager;

    public Server(Javalin app) {
        instance = this;
        this.app = app;

        this.packetHandler = new PacketHandler();
        this.profileHandler = new ProfileHandler();
        this.roomManager = new RoomManager();

        app.ws("/connect", ws -> {
            ws.onConnect(context -> {
                Session session = context.session;
                Handshake handshake = new Handshake(context.headerMap());

                // Authentication
                if (!handshake.getKey().equals(Configuration.clientKey)) {
                    session.close();
                    return;
                }

                context.attribute("handshake", handshake);
                this.getProfileHandler().profileMap.put(handshake.getUsername(), new Profile(handshake.getUsername()));

                // Send all the rooms on the server.
                if (Server.getInstance().getRoomManager().getChatRooms().size() > 0) {
                    for (AbstractIRCRoom room : Server.getInstance().getRoomManager().getChatRooms()) {
                        packetHandler.sendPacket(context, new WSPacketSendRoom(
                                room.getName(),
                                room.getId(),
                                room.getUsers().size(),
                                room.getLimit(),
                                room.isJoinable()
                        ));
                    }
                }
            });

            ws.onClose(context -> {
                Handshake handshake = context.attribute("handshake");
                assert handshake != null;
                Profile profile = Server.getInstance().getProfileHandler().getProfile(handshake.getUsername());

                // Remove their profile from the current room their in if they close the client.
                for (AbstractIRCRoom room : this.getRoomManager().getChatRooms()) {
                    room.getUsers().remove(profile);
                }

                // Remove them from the user count.
                profileHandler.getProfileMap().remove(handshake.getUsername());
            });

            ws.onBinaryMessage(context -> packetHandler.handlePacket(context, new ByteBufWrapper(Unpooled.wrappedBuffer(context.data()))));
        });
    }

    public static void main(String[] args) {
        if (args[1] == null) {
            new Server(Javalin.create().start("0.0.0.0", 9099));
        } else {
            new Server(Javalin.create().start("0.0.0.0", args[1]));
        }
    }
}
