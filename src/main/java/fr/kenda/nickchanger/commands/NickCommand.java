package fr.kenda.nickchanger.commands;

import fr.kenda.nickchanger.Nick;
import fr.kenda.nickchanger.files.CustomConfig;
import fr.kenda.nickchanger.nick.NickManager;
import fr.kenda.nickchanger.utils.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;

public class NickCommand implements CommandExecutor, Listener {

    private final String prefix = Nick.getPrefix();
    private final CustomConfig customConfig = Nick.getInstance().getCustomConfig();
    HashMap<String, String> arguments = new HashMap<>();
    private NickManager nickManager = Nick.getInstance().getNickManager();
    private String nick;
    private Inventory inv;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(prefix + Configuration.getConfigString("messages.noperms"));
            return false;
        }
        Player player = (Player) sender;
        if (!player.hasPermission(Configuration.getConfigString("permissions.use"))) {
            player.sendMessage(prefix + Configuration.getConfigString("messages.noperms"));
            return false;
        }
        if (args.length == 0 && nickManager.isNick(player)) {
            System.out.println("Le nom réel est " + nickManager.getRealName(player));
            player.setDisplayName(null);
            player.setPlayerListName(null);
            nickManager.removeNick(player);
            player.sendMessage(prefix + Configuration.getConfigString("messages.nick_removed"));
            return false;
        }
        if (args.length != 1 && !nickManager.isNick(player)) {
            player.sendMessage(prefix + Configuration.getConfigString("messages.not_enough_arguments"));
            return false;
        }
        if(args[0].length() < 3 || args[0].length() > 16){
            player.sendMessage(prefix + Configuration.getConfigString("messages."));
        }
        nick = args[0];
        if (!Configuration.getConfigBoolean("permissions.username_connected")) {
            Player nickUser = Bukkit.getPlayer(nick);
            if (nickUser != null) {
                player.sendMessage(prefix + Configuration.getConfigString("messages.nick_already_used"));
                return false;
            }
        }
        inv = Bukkit.createInventory(null, customConfig.getSizeInventory(), "§7Menu Grade");
        customConfig.getRankInfos().forEach((rank, section) -> {
            if (customConfig.getPower(player) >= Integer.parseInt(section.get("power"))) {
                inv.addItem(getSkullByName(player.getName(), section.get("displayName") + " " + nick));
            }
        });
        nickManager.addTemporary(player, nick);
        player.openInventory(inv);
        return false;
    }

    /**
     * Permet de récupéré la tête d'un joueur
     *
     * @param playerName  Le nom du joueur à récupérer
     * @param displayName Le texte à afficher sur l'item (peut être null)
     * @return la tête du joueur avec un displayName custom
     */
    private ItemStack getSkullByName(String playerName, String displayName) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(playerName);
        meta.setDisplayName(displayName.replace("&", "§"));
        skull.setItemMeta(meta);
        return skull;
    }

    @EventHandler
    public void onClickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack current = e.getCurrentItem();
        if (e.getInventory().getName().equals("§7Menu Grade")) {
            if (current != null && current.hasItemMeta()) {
                nickManager.add(player, nickManager.getTemporary(player));
                HashMap<String, String> arguments = new HashMap<>();
                arguments.put("{playername}", player.getName());
                arguments.put("{nickname}", nickManager.getNick(player));
                player.setDisplayName(current.getItemMeta().getDisplayName());
                player.setPlayerListName(current.getItemMeta().getDisplayName());
                player.sendMessage(prefix + Configuration.getConfigString("messages.nick_changed", arguments));
                player.closeInventory();
            }
        }
    }
}
