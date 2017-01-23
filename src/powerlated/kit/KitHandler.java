
package powerlated.kit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import powerlated.Util;

public final class KitHandler {

	public static HashMap<UUID, Kits> kitMap = new HashMap<UUID, Kits>();
	public static HashMap<Kits, Kit> kitEnumMap = new HashMap<Kits, Kit>();
	public static HashMap<UUID, BukkitTask> ghostMap = new HashMap<UUID, BukkitTask>();
	static JavaPlugin cb;

	@EventHandler(priority = EventPriority.MONITOR)
	// Pyromaniac blaze rod
	public final static Kits toEnum(String kit) {
		switch (kit.toLowerCase()) {
		case "pyromaniac":
			return Kits.PYROMANIAC;
		case "huntsman":
			return Kits.HUNTSMAN;
		case "robin hood":
			return Kits.ROBIN_HOOD;
		case "ghost":
			return Kits.GHOST;
		case "thorn man":
			return Kits.THORN_MAN;
		case "armored knight":
			return Kits.ARMORED_KNIGHT;
		case "orc":
			return Kits.ORC;
		case "the king":
			return Kits.THE_KING;
		case "knight":
			return Kits.KNIGHT;
		case "ninja":
			return Kits.NINJA;
		default:
			return Kits.UNSPECIFIED;
		}
	}

	public final static void select(Kits kits, Player player, PlayerInteractEvent event) {
		switch (kits) {
		case PYROMANIAC:
			Give.pyromaniac(player);
			return;
		case HUNTSMAN:
			Give.huntsman(player);
			return;
		case ROBIN_HOOD:
			Give.robinHood(player);
			return;
		case GHOST:
			Give.ghost(player);
			return;
		case THORN_MAN:
			Give.thornMan(player);
			return;
		case ARMORED_KNIGHT:
			Give.armouredKnight(player);
			return;
		case ORC:
			Give.orc(player);
			return;
		case THE_KING:
			Give.theKing(player);
			return;
		case KNIGHT:
			Give.knight(player);
			return;
		case NINJA:
			Give.ninja(player);
			return;
		default:
			player.getWorld().setGameRuleValue("doTileDrops", "false");
			event.getClickedBlock().breakNaturally();
			player.getWorld().setGameRuleValue("doTileDrops", "true");
			player.sendMessage(ChatColor.RED + "Error: That kit does not exist.");
		}

	}

	// Remove a kit from a player
	public static void removeKit(Player p) {
		kitMap.remove(p.getUniqueId());
		Util.removeEffects(p);
		if (ghostMap.get(p.getUniqueId()) != null) {
			ghostMap.get(p.getUniqueId()).cancel();
			ghostMap.remove(p.getUniqueId());
		}
	}

	public static void setKit(Player p, Kits kits) {
		removeKit(p);
		kitMap.put(p.getUniqueId(), kits);

	}

	static class Give {

		private static void pyromaniac(Player player) {
			
		}

		private static void huntsman(Player player) {
			addEffects(player);
		}

		private static void robinHood(Player player) {
			
		}

	

		private static void ghost(Player player) {
			player.sendMessage(ChatColor.GRAY + "Equipped Ghost");
			setKit(player, Kits.ORC);
			Util.clearInventory(player);
			Util.removeEffects(player);
			PlayerInventory pi = player.getInventory();
			pi.setHelmet(new ItemStack(Material.SKULL, 1, (short) 1));
			pi.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			pi.setLeggings(new ItemStack(Material.IRON_LEGGINGS));

			player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 1000000, 2));
			BukkitTask br = new BukkitRunnable() {

				@Override
				public void run() {
					ItemStack ps = new ItemStack(Material.SPLASH_POTION);
					PotionMeta pm = (PotionMeta) ps.getItemMeta();
					pm.addCustomEffect(new PotionEffect(PotionEffectType.HARM, 0, 0), true);
					pm.setDisplayName(ChatColor.AQUA + "Splash Potion of Battle");
					ps.setItemMeta(pm);
					PlayerInventory pi = player.getInventory();
					if (!pi.contains(ps)) {
						pi.addItem(ps);
					}
				}

			}.runTaskTimer(cb, 0, 10);
			ghostMap.put(player.getUniqueId(), br);
			addEffects(player);
		}

		private static void thornMan(Player player) {
			addEffects(player);
		}

		private static void armouredKnight(Player player) {
			addEffects(player);
		}

		private static void orc(Player player) {
			
		}

		private static void theKing(Player player) {

		}

		private static void knight(Player player) {

		}

		private static void ninja(Player player) {
			addEffects(player);
		}

		private static void addEffects(Player player) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, 2));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 1000000, 2));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 1000000, 0));
		}
	}

}
