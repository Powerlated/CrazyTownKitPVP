
package com.powerlated;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Openable;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class Util {
	// This is not my code!
	// Thanks to kreashenz on the Bukkit forums for this!
	public static List<Location> sphere(Location loc, int radius, int height, boolean hollow, boolean sphere,
			int plusY) {
		List<Location> circleblocks = new ArrayList<Location>();
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();

		for (int x = cx - radius; x <= cx + radius; x++) {
			for (int z = cz - radius; z <= cz + radius; z++) {
				for (int y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);

					if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
						Location l = new Location(loc.getWorld(), x, y + plusY, z);
						circleblocks.add(l);
					}
				}
			}
		}

		return circleblocks;
	}

	public static void closeDoor(Block b) {
		if (b.getState() instanceof Openable) {
			Openable o = (Openable) b.getState();
			o.setOpen(false);
		}
	}

	public static void openDoor(Block b) {

		if (b.getState() instanceof Openable) {
			Openable o = (Openable) b.getState();
			o.setOpen(true);
		}

	}

	public static void toggleDoor(Block b) {
		if (b.getState() instanceof Openable) {
			Openable o = (Openable) b.getState();
			if (o.isOpen()) {
				o.setOpen(false);
			} else {
				o.setOpen(true);
			}
			b.getState().update();
		}
	}

	public static void clearInventory(Player player) {
		PlayerInventory i = player.getInventory();
		i.setArmorContents(new ItemStack[i.getArmorContents().length]);
		i.clear();
	}

	public static void addEffects(Player player) {
		player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 2));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 2));
		player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1000000, 0));
	}
	
	// Removes all potion effects from a player
	public static void removeEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
	}

	public static void unbreakable(ItemStack item) {
		ItemMeta im = item.getItemMeta();
		im.setUnbreakable(true);
		item.setItemMeta(im);
	}

	public static void launch(double vel, Player p) {
		p.setVelocity(p.getVelocity().add(new Vector(0.0, vel, 0.0)));
	}
	
}
