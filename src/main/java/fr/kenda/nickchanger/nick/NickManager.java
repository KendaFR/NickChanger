package fr.kenda.nickchanger.nick;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class NickManager {

    private final HashMap<Player, String> playerNick;
    private final HashMap<Player, String> temporary;

    public NickManager() {
        playerNick = new HashMap<>();
        temporary = new HashMap<>();
    }

    public void add(Player player, String nick) {
        playerNick.put(player, nick);
        temporary.remove(player);

    }
    public String getNick(Player player){
        return playerNick.get(player);
    }

    public boolean isNick(Player player) {
        return playerNick.containsKey(player);
    }

    public void removeNick(Player player) {
        playerNick.remove(player);
    }

    public String getRealName(Player player) {
        for (Player p : playerNick.keySet()) {
            if (player == p) {
                return p.getDisplayName();
            }
        }
        return "";
    }

    public void addTemporary(Player player, String nick){
        temporary.put(player, nick);
    }

    public String getTemporary(Player player) {
        return temporary.get(player);
    }
}
