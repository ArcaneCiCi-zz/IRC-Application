package me.arcanecici.irc.client.websocket.packet.impl.client;

import lombok.Setter;
import me.arcanecici.irc.client.Client;
import me.arcanecici.irc.client.websocket.WSClientOverride;
import me.arcanecici.irc.client.websocket.packet.ByteBufWrapper;
import me.arcanecici.irc.client.websocket.packet.Packet;

import java.io.IOException;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */
@Setter
public class WSPacketUpdateScreen extends Packet {
    private String screen;

    public WSPacketUpdateScreen() {
    }

    @Override
    public void write(ByteBufWrapper buffer) {
    }

    @Override
    public void read(ByteBufWrapper buffer) throws IOException {
        setScreen(buffer.readString(512));
    }

    @Override
    public void handle(WSClientOverride webSocket) {
        Client.getInstance().displayScreen(Client.getInstance().getMenuManager().getMenus().get(screen));
    }
}
