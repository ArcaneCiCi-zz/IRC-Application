package me.arcanecici.irc.client.feature;

import lombok.Getter;
import me.arcanecici.irc.client.feature.irc.IRCRoom;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ArcaneCiCi ~ 10/7/2021
 */

@Getter
public class FeatureManager {

    public List<IRCRoom> ircRooms = new ArrayList<>();

}
