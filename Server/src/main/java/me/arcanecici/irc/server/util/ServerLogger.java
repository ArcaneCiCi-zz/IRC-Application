package me.arcanecici.irc.server.util;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

public class ServerLogger {

    public static void log(String msg) {
        System.out.println("[WS] " + msg);
    }

    public static void logWithSuffix(String suffix, String msg) {
        System.out.println("[WS (" + suffix + ")] " + msg);
    }

    public static void error(String errorMsg) {
        System.err.println("[WS] " + errorMsg);
    }

    public static void errorWithSuffix(String suffix, String errorMsg) {
        System.err.println("[WS (" + suffix + ")] " + errorMsg);
    }

}
