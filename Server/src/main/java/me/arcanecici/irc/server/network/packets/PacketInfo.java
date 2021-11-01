package me.arcanecici.irc.server.network.packets;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface PacketInfo {

    int id();

    boolean enabled() default true;

}
