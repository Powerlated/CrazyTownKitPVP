package powerlated.kit;

import java.util.HashSet;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import powerlated.CrazyBucket;

public class Pyrotechnic implements Listener {
	private HashSet<Player> noFireball = new HashSet<Player>();
	static BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
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
			scheduler.scheduleSyncDelayedTask(CrazyBucket.cb, new Runnable() {
				@Override
				public void run() {
					noFireball.remove(player);
				}
			}, 5L);
		}

	}
}
