package me.arcanecici.irc.server.feature.irc.rooms;

import me.arcanecici.irc.server.feature.irc.AbstractIRCRoom;

public class TestRoom extends AbstractIRCRoom {

    public TestRoom() {
        super("Room 1", 1, 10, false);
    }
}
