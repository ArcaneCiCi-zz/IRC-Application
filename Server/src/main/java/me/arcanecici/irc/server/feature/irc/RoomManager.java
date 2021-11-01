package me.arcanecici.irc.server.feature.irc;

import lombok.Getter;
import me.arcanecici.irc.server.feature.irc.rooms.TestRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

@Getter
public class RoomManager {
    private final List<AbstractIRCRoom> chatRooms = new ArrayList<>();

    private final TestRoom testRoom;

    public RoomManager() {
        this.chatRooms.add(this.testRoom = new TestRoom());
    }

    public AbstractIRCRoom getRoomById(int id) {
        return this.chatRooms.stream().filter(chatRoom -> chatRoom.getId() == id).findFirst().orElse(null);
    }
}
