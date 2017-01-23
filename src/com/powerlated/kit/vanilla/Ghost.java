package com.powerlated.kit.vanilla;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.powerlated.CrazyBucket;
import com.powerlated.kit.Kit;

public class Ghost extends Kit {
	public static HashMap<UUID, BukkitTask> ghostMap = new HashMap<UUID, BukkitTask>();
	@Override
	public String getName() {
		return "Ghost";
	}

	@Override
	public void give(Player p) {
		PlayerInventory pi = p.getInventory();
		pi.setHelmet(new ItemStack(Material.SKULL, 1, (short) 1));
		pi.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 2));
		BukkitTask br = new BukkitRunnable() {
			@Override
			public void run() {
				ItemStack ps = new ItemStack(Material.SPLASH_POTION);
				PotionMeta pm = (PotionMeta) ps.getItemMeta();
				pm.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 0, 0), true);
				pm.setDisplayName(ChatColor.AQUA + "Splash Potion of Battle");
				ps.setItemMeta(pm);
				PlayerInventory pi = p.getInventory();
				if (!pi.contains(ps)) {
					pi.addItem(ps);
				}
			}

		}.runTaskTimer(CrazyBucket.cb, 0, 10);
		ghostMap.put(p.getUniqueId(), br);
	}

	@Override
	public void remove(Player p) {
		if (ghostMap.get(p.getUniqueId()) != null) {
			ghostMap.get(p.getUniqueId()).cancel();
			ghostMap.remove(p.getUniqueId());
		}
	}

}
