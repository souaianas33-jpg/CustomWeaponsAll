package me.anas.customweapons;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new WeaponListener(), this);
        getServer().getPluginManager().registerEvents(new GUIListener(), this);

        getCommand("givecustom").setExecutor(new GiveCommand());
        getCommand("weapons").setExecutor(new GUICommand());
    }

    public static Main getInstance() {
        return instance;
    }
}
