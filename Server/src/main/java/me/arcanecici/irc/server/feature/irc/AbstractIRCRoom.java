package me.arcanecici.irc.server.feature.irc;

import lombok.Data;
import me.arcanecici.irc.server.profile.Profile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

@Data
public abstract class AbstractIRCRoom {
    private List<Profile> users = new ArrayList<>();

    private String name;

    private int id;
    private int limit;

    private boolean joinable;

    public AbstractIRCRoom(String name, int id, int limit, boolean joinable) {
        this.name = name;
        this.id = id;
        this.limit = limit;
        this.joinable = joinable;
    }

    public void addUser(Profile user) {
        this.users.add(user);
    }

}
