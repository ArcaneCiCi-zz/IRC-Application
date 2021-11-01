package me.arcanecici.irc.client.feature;

import lombok.Getter;
import lombok.Setter;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

@Getter
@Setter
public abstract class Feature {

    private boolean experimental;
    private int id;
    private String name;

}
