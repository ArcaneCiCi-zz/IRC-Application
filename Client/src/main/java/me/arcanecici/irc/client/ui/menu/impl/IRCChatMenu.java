package me.arcanecici.irc.client.ui.menu.impl;

import lombok.SneakyThrows;
import me.arcanecici.irc.client.Client;
import me.arcanecici.irc.client.feature.irc.IRCRoom;
import me.arcanecici.irc.client.ui.AbstractMenu;

import java.util.Date;
import java.util.Scanner;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

public class IRCChatMenu extends AbstractMenu {

    @SneakyThrows
    @Override
    public void init() {
        this.render();

        Scanner sc = new Scanner(System.in);
        int selectedRoomId;
        try {
            selectedRoomId = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException ignored) {
            System.out.println("That room is not available or is private, reloading.");
            System.out.println();
            Thread.sleep(1000);
            this.clearScreen();
            this.reload();
            return;
        }

        for (IRCRoom room : Client.getInstance().getFeatureManager().getIrcRooms()) {
            if (room.getId() == selectedRoomId) {
                Client.getInstance().getWebsocket().handleRoomChange(selectedRoomId);
                this.clearScreen();
            }
        }
    }

    @Override
    public void render() {
        System.out.println("----------------------------------------");
        System.out.println("           IRC Application\n    Welcome to complete anonymity.");
        System.out.println();
        System.out.println("Time: " + new Date().toLocaleString());
        System.out.println("----------------------------------------");
        System.out.println("\n--- Public Rooms ---");
        System.out.println();
        try {
            int index = 1;
            if (Client.getInstance().getFeatureManager().getIrcRooms().size() == 0) {
                System.out.println("There are currently no available chat rooms.");
                return;
            }

            for (IRCRoom room : Client.getInstance().getFeatureManager().getIrcRooms()) {
                if (!room.isPrivateRoom()) {
                    System.out.println("[" + index + "] - " + room.getName() + " (" + room.getCurrentUsers() + "/" + room.getLimit() + ")");
                }
                ++index;
            }

            System.out.println();
            System.out.println("Choose a room to enter:");
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
