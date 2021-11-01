package me.arcanecici.irc.client.ui.menu.impl;

import me.arcanecici.irc.client.Client;
import me.arcanecici.irc.client.ui.AbstractMenu;
import me.arcanecici.irc.client.websocket.packet.impl.client.WSPacketRoomMessage;
import me.arcanecici.irc.client.websocket.packet.impl.server.WSPacketRoomChange;

import java.util.Scanner;

/**
 * @author ArcaneCiCi - 10/31/2021
 * cheatbreakerplus.com & github.com/ArcaneCiCi
 */

public class GuiNewChat extends AbstractMenu {


    // No need for rendering since this is just for sending messages to everyone.
    @Override
    public void init() {
        Scanner messageListener = new Scanner(System.in);
        String msg = messageListener.nextLine();

        if (msg.equalsIgnoreCase("/leave")) {
            this.clearScreen();
            Client.getInstance().getWebsocket().sendToServer(new WSPacketRoomChange(0));
            return;
        }

        if (msg.length() > 0) {
            Client.getInstance().getWebsocket().sendToServer(new WSPacketRoomMessage(msg));
            this.reload();
        } else {
            System.out.println("You must have a valid message to send!");
            this.reload();
        }
    }
}
