package me.arcanecici.irc.client.websocket.packet.impl.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.arcanecici.irc.client.Client;
import me.arcanecici.irc.client.ui.menu.impl.GuiNewChat;
import me.arcanecici.irc.client.websocket.WSClientOverride;
import me.arcanecici.irc.client.websocket.packet.ByteBufWrapper;
import me.arcanecici.irc.client.websocket.packet.Packet;

import java.io.IOException;

/**
 * @author ArcaneCiCi ~ 10/8/2021
 */

@AllArgsConstructor
@Getter
public class WSPacketRoomMessage extends Packet {

    private String messageIn;
    private String messageOut;

    public WSPacketRoomMessage() {
    }

    public WSPacketRoomMessage(String messageOut) {
        this.messageOut = messageOut;
    }

    @Override
    public void write(ByteBufWrapper buffer) {
        buffer.writeString(this.messageOut);
    }

    @Override
    public void read(ByteBufWrapper buffer) throws IOException {
        this.messageIn = buffer.readString(512);
    }

    @Override
    public void handle(WSClientOverride webSocket) {
        System.out.println(this.messageIn);

        // Since the GuiNewChat is odd, we do this temporarily, shit way of doing it but idc.
        if (this.messageIn.equalsIgnoreCase("[JOIN] '" + Client.alias + "' has joined the room.")) {
            Client.getInstance().displayScreen(new GuiNewChat());
            System.out.println("To leave this room, type '/leave'.");
        }
    }
}
