package me.arcanecici.irc.client.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author ArcaneCiCi ~ 10/5/2021
 */

public class DataUtil {

    public static String decodeBytes(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static String encodeAndDecodeKey(String stringIn) {
        byte[] clientKey = (Base64.getEncoder().encode(stringIn.getBytes()));

        return decodeBytes(clientKey);
    }

}
