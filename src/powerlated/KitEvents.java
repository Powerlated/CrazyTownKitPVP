package powerlated;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

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
//replace air 31:1,38,37,38:8,31:1,31:1,31:1,31:1,31:1,31:1,31:1,31:1,31:1,31:1,31:1,31:1,31:1,31:1,
	static class RobinHood implements Listener {
		int counter = 0;

		@EventHandler
		public void rapidFire(EntityShootBowEvent event) {
			if (event.getEntity() instanceof Player) {
				ItemStack itemInHand = ((Player) event.getEntity()).getInventory().getItemInMainHand();
				ItemMeta itemInHandMeta = itemInHand.getItemMeta();
				if (itemInHandMeta.getDisplayName() == null) {
					return;
				}
				if (itemInHand.getType() == Material.BOW
						&& itemInHandMeta.getDisplayName().equals(ChatColor.AQUA + "Epic Bow")) {
					Player p = (Player) event.getEntity();
					Projectile arrow = (Projectile) event.getProjectile();

					new BukkitRunnable() {

						@Override
						public void run() {
							Vector speed = arrow.getVelocity();
							Projectile spawned = p.launchProjectile(Arrow.class, speed);
							p.playSound(p.getLocation(), Sound.ENTITY_ARROW_SHOOT, 1, 1);
							spawned.setVelocity(speed);
							spawned.setShooter(p);
							counter++;
							if (counter > 10) {
								this.cancel();
								counter = 0;
							}
						}

					}.runTaskTimer(cb, 0, 1);
					arrow.remove();
				}
			}
		}
		@EventHandler
		public void onArrowHit(ProjectileHitEvent event){
			  if(event.getEntity() instanceof Arrow){
			    Arrow arrow = (Arrow) event.getEntity();
			    arrow.remove();
			  }
			}
	}
}
