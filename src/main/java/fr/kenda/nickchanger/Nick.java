package fr.kenda.nickchanger;

import fr.kenda.nickchanger.commands.NickCommand;
import fr.kenda.nickchanger.files.CustomConfig;
import fr.kenda.nickchanger.nick.NickManager;
import fr.kenda.nickchanger.utils.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nick extends JavaPlugin {

    private static Nick instance;
    private static String prefix;

    private NickManager nickManager;
    private CustomConfig customConfig;

    public static String getPrefix() {
        return prefix;
    }

    public static Nick getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        prefix = Configuration.getConfigString("messages.prefix");
        customConfig = new CustomConfig();
        nickManager = new NickManager();
        customConfig.createFile("grades");
        getCommand("nick").setExecutor(new NickCommand());
        Bukkit.getPluginManager().registerEvents(new NickCommand(), this);
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + "§aPlugin started");

    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(prefix + "§cPlugin stopped");
    }

    public CustomConfig getCustomConfig() {
        return customConfig;
    }

    public NickManager getNickManager() {
        return nickManager;
    }
}
