package me.anas.customweapons;

import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GUIListener implements Listener {

    @EventHandler
    public void click(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals("§8Custom Weapons")) return;

        e.setCancelled(true);

        Player p = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (item == null) return;

        p.getInventory().addItem(item.clone());
        p.sendMessage("§aWeapon received!");
    }
}
