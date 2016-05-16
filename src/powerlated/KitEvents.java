package powerlated;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.Bukkit;

public class KitEvents {
	public static Plugin cb;
	static BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
	static class Pyrotechnic implements Listener {
		private HashSet<Player> noFireball = new HashSet<Player>();

		@EventHandler
		public void shootFireball(PlayerInteractEvent event) {
			Player player = event.getPlayer();
			if (!noFireball.contains(player)) {		
				if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					ItemStack itemInHand = player.getInventory().getItemInMainHand();
					ItemMeta itemInHandMeta = itemInHand.getItemMeta();
					if (itemInHand.getType() == Material.BLAZE_ROD && itemInHandMeta.getDisplayName()
							.equals(ChatColor.YELLOW + "" + ChatColor.BOLD + "THE EPIC STICK OF EPICNESS")) {
						Location lookingAt = player.getEyeLocation().toVector()
								.add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(),
										player.getLocation().getYaw(), player.getLocation().getPitch());
						Fireball fb = player.getWorld().spawn(lookingAt, Fireball.class);
						fb.setShooter(player);
						fb.setIsIncendiary(false);
						fb.setYield(0);
						fb.setVelocity(player.getEyeLocation().getDirection().multiply(5));
					}
				}
				
				noFireball.add(player);
				scheduler.scheduleSyncDelayedTask(cb, new Runnable() {
					@Override
					public void run() {
						noFireball.remove(player);
					}
				}, 5L);
			}
			
		}
	}

	static class Orc implements Listener {
		@EventHandler
		public void healthStealer(EntityDamageByEntityEvent event) {
			if (KitHandler.kitMap.get(event.getDamager().getUniqueId()) == null) {
				return;
			}
			if (KitHandler.kitMap.get(event.getDamager().getUniqueId()).equals(Kit.ORC)) {
				Bukkit.broadcastMessage(KitHandler.kitMap.get(event.getDamager().getUniqueId()).toString());
				if (event.getDamager() instanceof Player) {
					Damageable damager = (Damageable) event.getDamager();
					double damage = event.getDamage();
					damager.setHealth((damage / 2) + damager.getHealth());
				}
			}
		}
	}
	
	static class Ghost implements Listener {
		
	}
}
