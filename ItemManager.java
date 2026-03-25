package me.anas.customweapons;

import org.bukkit.*;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

    private static ItemStack create(Material mat, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack meteor() { return create(Material.DIAMOND_SWORD, "§cMeteor Sword"); }
    public static ItemStack wardenSword() { return create(Material.DIAMOND_SWORD, "§9Warden Sword"); }
    public static ItemStack frost() { return create(Material.DIAMOND_SWORD, "§bFrost Sword"); }
    public static ItemStack vampire() { return create(Material.DIAMOND_SWORD, "§4Vampire Sword"); }
    public static ItemStack combo() { return create(Material.DIAMOND_SWORD, "§eCombo Sword"); }
    public static ItemStack cobweb() { return create(Material.NETHERITE_AXE, "§7Cobweb Axe"); }

    public static ItemStack dashMace() { return create(Material.MACE, "§eDash Mace"); }
    public static ItemStack heartMace() { return create(Material.MACE, "§cHeart Mace"); }
    public static ItemStack freezeMace() { return create(Material.MACE, "§bFreeze Mace"); }
}
