package powerlated.kit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import powerlated.CrazyBucket;

public class RobinHood implements Listener {
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

				}.runTaskTimer(CrazyBucket.cb, 0, 1);
				arrow.remove();
			}
		}
	}

	@EventHandler
	public void onArrowHit(ProjectileHitEvent event) {
		if (event.getEntity() instanceof Arrow) {
			Arrow arrow = (Arrow) event.getEntity();
			arrow.remove();
		}
	}
}