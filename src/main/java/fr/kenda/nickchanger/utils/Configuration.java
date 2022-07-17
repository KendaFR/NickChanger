package fr.kenda.nickchanger.utils;

import fr.kenda.nickchanger.Nick;

import java.util.HashMap;

public class Configuration {

    public static String getConfigString(String path) {
        return Nick.getInstance().getConfig().getString(path).replace("&", "§");
    }

    public static String getConfigString(String path, HashMap<String, String> charReplace) {
        final String[] message = {getConfigString("messages.nick_changed")};
        charReplace.forEach((oldChar, newChar) -> {
            message[0] = message[0].replace(oldChar, newChar);
        });
        return message[0];
    }

    public static boolean getConfigBoolean(String path) {
        return Nick.getInstance().getConfig().getBoolean(path);
    }
}
