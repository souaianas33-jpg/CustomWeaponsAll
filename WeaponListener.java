package me.anas.customweapons;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.*;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.*;

public class WeaponListener implements Listener {

    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();
    private final Map<UUID, Integer> combo = new HashMap<>();
    private final Set<UUID> frozen = new HashSet<>();

    private boolean hasName(ItemStack item, String name) {
        return item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equals(name);
    }

    private int getCD(String key) {
        return Main.getInstance().getConfig().getInt("cooldowns." + key);
    }

    private boolean cooldown(Player p, String key, int sec) {
        cooldowns.putIfAbsent(p.getUniqueId(), new HashMap<>());
        long now = System.currentTimeMillis();

        if (cooldowns.get(p.getUniqueId()).containsKey(key)) {
            long last = cooldowns.get(p.getUniqueId()).get(key);
            long left = sec - ((now - last) / 1000);

            if (left > 0) {
                p.sendMessage("§cCooldown: " + left + "s");
                return true;
            }
        }

        cooldowns.get(p.getUniqueId()).put(key, now);
        return false;
    }

    private void freeze(LivingEntity ent, int ticks) {
        frozen.add(ent.getUniqueId());
        ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, ticks, 255));

        new BukkitRunnable() {
            public void run() {
                frozen.remove(ent.getUniqueId());
            }
        }.runTaskLater(Main.getInstance(), ticks);
    }

    @EventHandler
    public void move(PlayerMoveEvent e) {
        if (frozen.contains(e.getPlayer().getUniqueId())) {
            e.setTo(e.getFrom());
        }
    }

    private void shootMeteor(Player p) {
        ArmorStand meteor = p.getWorld().spawn(p.getEyeLocation(), ArmorStand.class);
        meteor.setInvisible(true);
        meteor.setGravity(false);

        Vector dir = p.getLocation().getDirection();

        new BukkitRunnable() {
            int t = 0;

            public void run() {
                if (t > 40) { meteor.remove(); cancel(); return; }

                Location loc = meteor.getLocation().add(dir);
                meteor.teleport(loc);

                loc.getWorld().spawnParticle(Particle.FLAME, loc, 10);

                if (loc.getBlock().getType().isSolid()) {
                    loc.getWorld().createExplosion(loc, 4);
                    meteor.remove();
                    cancel();
                }

                for (Entity ent : loc.getWorld().getNearbyEntities(loc, 2,2,2)) {
                    if (ent instanceof LivingEntity && ent != p) {
                        loc.getWorld().createExplosion(loc, 4);
                        meteor.remove();
                        cancel();
                    }
                }

                t++;
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    @EventHandler
    public void use(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();

        if (!p.isSneaking()) return;

        if (hasName(item, "§cMeteor Sword")) {
            if (cooldown(p, "meteor", getCD("meteor"))) return;
            shootMeteor(p);
        }

        if (hasName(item, "§eDash Mace")) {
            if (cooldown(p, "dash", getCD("dash"))) return;
            p.setVelocity(p.getLocation().getDirection().multiply(3));
        }

        if (hasName(item, "§cHeart Mace")) {
            if (cooldown(p, "heart", getCD("heart"))) return;
            p.setHealth(p.getMaxHealth());
        }

        if (hasName(item, "§bFreeze Mace")) {
            if (cooldown(p, "freeze", getCD("freeze"))) return;
            for (Entity ent : p.getNearbyEntities(4,4,4)) {
                if (ent instanceof LivingEntity && ent != p) {
                    freeze((LivingEntity) ent, 100);
                }
            }
        }
    }

    @EventHandler
    public void hit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;

        Player p = (Player) e.getDamager();
        ItemStack item = p.getInventory().getItemInMainHand();

        if (hasName(item, "§bFrost Sword")) {
            if (Math.random() < 0.3) freeze((LivingEntity) e.getEntity(), 100);
        }

        if (hasName(item, "§4Vampire Sword")) {
            p.setHealth(Math.min(p.getHealth() + 4, p.getMaxHealth()));
        }

        if (hasName(item, "§7Cobweb Axe")) {
            if (Math.random() < 0.3) {
                e.getEntity().getLocation().getBlock().setType(Material.COBWEB);
            }
        }
    }
}
