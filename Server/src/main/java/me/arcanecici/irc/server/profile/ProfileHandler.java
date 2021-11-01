package me.arcanecici.irc.server.profile;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ArcaneCiCi - 10/30/2021
 * cheatbreakerplus.com & github.com/ArcaneCiCi
 */

@Getter
public class ProfileHandler {

    public Map<String, Profile> profileMap = new HashMap<>();

    public Profile getProfile(String username) {
        return this.profileMap.get(username);
    }

}
