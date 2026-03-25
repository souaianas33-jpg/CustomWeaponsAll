package me.anas.customweapons;

import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class GiveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Player p = (Player) sender;

        p.getInventory().addItem(
                ItemManager.meteor(),
                ItemManager.wardenSword(),
                ItemManager.frost(),
                ItemManager.vampire(),
                ItemManager.combo(),
                ItemManager.cobweb(),
                ItemManager.dashMace(),
                ItemManager.heartMace(),
                ItemManager.freezeMace()
        );

        p.sendMessage("§aAll weapons received!");
        return true;
    }
}
