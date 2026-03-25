package me.anas.customweapons;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

public class GUICommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p = (Player) sender;

        Inventory gui = Bukkit.createInventory(null, 27, "§8Custom Weapons");

        gui.setItem(10, ItemManager.meteor());
        gui.setItem(11, ItemManager.wardenSword());
        gui.setItem(12, ItemManager.frost());
        gui.setItem(13, ItemManager.vampire());
        gui.setItem(14, ItemManager.combo());
        gui.setItem(15, ItemManager.cobweb());

        gui.setItem(16, ItemManager.dashMace());
        gui.setItem(17, ItemManager.heartMace());
        gui.setItem(18, ItemManager.freezeMace());

        p.openInventory(gui);
        return true;
    }
}
