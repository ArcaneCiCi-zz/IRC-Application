package me.arcanecici.irc.client.feature.irc;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

@Getter
@Setter
public class IRCRoom {
    private final String name;

    private boolean privateRoom;

    private int currentUsers;
    private int limit;
    private int id;


    public IRCRoom(String name, int id, int currentUsers, int userLimit, boolean privateRoom) {
        this.name = name;
        this.id = id;
        this.currentUsers = currentUsers;
        this.limit = userLimit;
        this.privateRoom = privateRoom;
    }
}
