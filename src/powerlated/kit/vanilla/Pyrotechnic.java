package powerlated.kit.vanilla;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitScheduler;

import powerlated.CrazyBucket;
import powerlated.Util;
import powerlated.kit.Kit;
import powerlated.kit.Kits;

public class Pyrotechnic extends Kit implements Listener {
	private HashSet<Player> noFireball = new HashSet<Player>();
	static BukkitScheduler scheduler = Bukkit.getServer().getScheduler();

	@EventHandler
    public void selfDamage(EntityDamageByEntityEvent event){
        if (event.getEntity() instanceof Player && event.getDamager() == event.getEntity()) {
            event.setDamage(event.getDamage() / 4);
        }
    }
	
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
					fb.setYield(2);
					fb.setVelocity(player.getEyeLocation().getDirection().multiply(2.5));
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

	@Override
	public String getName() {
		return "Pyrotechnic";
	}

	@Override
	public void give(Player p) {
		Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
		enchantments.put(Enchantment.FIRE_ASPECT, 2);
		enchantments.put(Enchantment.DAMAGE_ALL, 2);
		ItemStack[] is = { new ItemStack(Material.BLAZE_ROD, 1), new ItemStack(Material.LEATHER_CHESTPLATE, 1) };
		is[0].addUnsafeEnchantments(enchantments);
		// Makes the red chestplate unbreakable
		Util.unbreakable(is[1]);
		ItemMeta im = (ItemMeta) is[0].getItemMeta();
		LeatherArmorMeta lam = (LeatherArmorMeta) is[1].getItemMeta();
		im.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "THE EPIC STICK OF EPICNESS");
		lam.setColor(Color.RED);
		is[0].setItemMeta(im);
		is[1].setItemMeta(lam);
		p.getInventory().addItem(is[0]);
		p.getInventory().setChestplate(is[1]);
	}

	@Override
	public Kits getType() {
		return Kits.PYROMANIAC;
	}

	@Override
	public void remove(Player p) {

	}
}
