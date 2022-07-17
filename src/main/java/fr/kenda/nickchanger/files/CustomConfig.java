package fr.kenda.nickchanger.files;

import fr.kenda.nickchanger.Nick;
import fr.kenda.nickchanger.utils.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.Level;

public class CustomConfig {

    private final ArrayList<File> configFiles;

    public CustomConfig() {
        configFiles = new ArrayList<>();
    }

    public void createFile(String name) {
        if (!Nick.getInstance().getDataFolder().exists()) {
            Nick.getInstance().getDataFolder().mkdir();
        }
        File file = new File(Nick.getInstance().getDataFolder(), name + ".yml");
        configFiles.add(file);
        if (!file.exists()) {
            try {
                file.createNewFile();
                Nick.getInstance().getServer().getConsoleSender().sendMessage(Configuration.getConfigString("messages.prefix") + ChatColor.AQUA + "Création du fichier de configuration " + name + ".yml");
                Thread.sleep(1000L);
                loadConfig(file);
            } catch (IOException e) {
                Nick.getInstance().getLogger().log(Level.CONFIG, Nick.getPrefix() + ChatColor.RED + "Une erreur est survenu à la création du fichier " + name + ".yml");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadConfig(File file) throws IOException {
        final YamlConfiguration configFile = YamlConfiguration.loadConfiguration(file);
        if (file.getName().equals("grades.yml")) {
            configFile.set("grades.fondateur.displayName", "&cFondateur");
            configFile.set("grades.fondateur.power", 999);
            configFile.set("grades.fondateur.permission", "nick.fondateur");

            configFile.set("grades.admin.displayName", "&4Administrateur");
            configFile.set("grades.admin.power", 50);
            configFile.set("grades.admin.permission", "nick.admin");

            configFile.set("grades.protecteur.displayName", "&eProtecteur");
            configFile.set("grades.protecteur.power", 49);
            configFile.set("grades.protecteur.permission", "nick.protecteur");
        }
        configFile.save(file);
    }

    public File getFile(String name) {
        return configFiles.stream().filter(file -> file.getName().equals(name + ".yml")).findFirst().orElse(null);
    }

    public int getSizeInventory() {
        final YamlConfiguration configFile = YamlConfiguration.loadConfiguration(getFile("grades"));
        int count = configFile.getConfigurationSection("grades").getKeys(false).size();
        int i = 0;
        while (i < count) {
            i += 9;
        }
        return i;
    }

    public HashMap<String, HashMap<String, String>> getRankInfos() {
        LinkedHashMap<String, HashMap<String, String>> ranks = new LinkedHashMap<>();
        final YamlConfiguration configFile = YamlConfiguration.loadConfiguration(getFile("grades"));
        for (String rank : configFile.getConfigurationSection("grades").getKeys(false)) {
            LinkedHashMap<String, String> sections = new LinkedHashMap<>();
            for (String section : configFile.getConfigurationSection("grades." + rank).getKeys(false)) {
                sections.put(section, configFile.getConfigurationSection("grades." + rank).getString(section));
            }
            ranks.put(rank, sections);
        }
        return ranks;
    }

    public YamlConfiguration getConfig(String filename) {
        return YamlConfiguration.loadConfiguration(getFile(filename));
    }

    public int getPower(Player player) {
        int power = 0;
        for (String rank : getConfig("grades").getConfigurationSection("grades").getKeys(false)) {
            if (player.hasPermission(getConfig("grades").getConfigurationSection("grades").getString(rank + ".permission"))) {
                if (getConfig("grades").getConfigurationSection("grades").getInt(rank + ".power") > power) {
                    power = getConfig("grades").getConfigurationSection("grades").getInt(rank + ".power");
                }
            }
        }
        return power;
    }
}
