
package powerlated;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public final class KitHandler {

	public static HashMap<UUID, Kit> kitMap = new HashMap<UUID, Kit>();

	@EventHandler(priority = EventPriority.MONITOR)
	// Pyromaniac blaze rod
	public final static Kit toEnum(String kit) {
		switch (kit.toLowerCase()) {
			case "pyromaniac":
				return Kit.PYROMANIAC;
			case "huntsman":
				return Kit.HUNTSMAN;
			case "robin hood":
				return Kit.ROBIN_HOOD;
			case "ghost":
				return Kit.GHOST;
			case "thorn man":
				return Kit.THORN_MAN;
			case "armored knight":
				return Kit.ARMORED_KNIGHT;
			case "orc":
				return Kit.ORC;
			case "the king":
				return Kit.THE_KING;
			case "knight":
				return Kit.KNIGHT;
			case "ninja":
				return Kit.NINJA;
			default:
				return null; 
		}
	}

	public final static void select(Kit kit, Player player,
			PlayerInteractEvent event) {
		switch (kit) {
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
				player.sendMessage(ChatColor.RED
						+ "Error: That kit does not exist.");
		}

	}
	//Removes all potion effects from a player
	public static void removeEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects())
			player.removePotionEffect(effect.getType());
	}
	//Remove a kit from a player
	public static void removeKit(Player p) {
		kitMap.remove(p.getUniqueId());
	}

	public static void setKit(Player p, Kit kit) {
		kitMap.remove(p.getUniqueId());
		kitMap.put(p.getUniqueId(), kit);
		Bukkit.broadcastMessage(p.getUniqueId().toString());
		p.addPotionEffect(new PotionEffect(
				PotionEffectType.SATURATION, 1000000, 1));
	}

	static class Give {
		private static void clearInventory(Player player) {
			PlayerInventory i = player.getInventory();
			i.setArmorContents(new ItemStack[i.getArmorContents().length]);
			i.clear();
		}

		private static void pyromaniac(Player player) {
			player.sendMessage(ChatColor.GRAY + "Equipped Pyromaniac");
			setKit(player, Kit.PYROMANIAC);
			clearInventory(player);
			KitHandler.removeEffects(player);
			Map<Enchantment, Integer> enchantments = new HashMap<Enchantment, Integer>();
			enchantments.put(Enchantment.FIRE_ASPECT, 2);
			enchantments.put(Enchantment.DAMAGE_ALL, 2);
			ItemStack[] is = { new ItemStack(Material.BLAZE_ROD, 1),
					new ItemStack(Material.LEATHER_CHESTPLATE, 1) };
			is[0].addUnsafeEnchantments(enchantments);
			ItemMeta im = (ItemMeta) is[0].getItemMeta();
			LeatherArmorMeta lam = (LeatherArmorMeta) is[1].getItemMeta();
			im.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD
					+ "THE EPIC STICK OF EPICNESS");
			lam.setColor(Color.RED);
			is[0].setItemMeta(im);
			is[1].setItemMeta(lam);
			player.getInventory().addItem(is[0]);
			player.getInventory().setChestplate(is[1]);
			
			player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP,
					1000000, 2));
			player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,
					1000000, 2));

		}

		private static void huntsman(Player player) {

		}

		private static void robinHood(Player player) {

		}

		private static void ghost(Player player) {

		}

		private static void thornMan(Player player) {

		}

		private static void armouredKnight(Player player) {

		}

		private static void orc(Player player) {
			player.sendMessage(ChatColor.GRAY + "Equipped Orc");
			setKit(player, Kit.ORC);
			clearInventory(player);
			KitHandler.removeEffects(player);
			PlayerInventory pi = player.getInventory();
			pi.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
			pi.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
			pi.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
			pi.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
			ItemStack sword = new ItemStack(Material.WOOD_SWORD);
			ItemMeta swordIm = sword.getItemMeta();
			swordIm.spigot().setUnbreakable(true);
			pi.addItem(sword);
			sword.setItemMeta(swordIm);
		}

		private static void theKing(Player player) {

		}

		private static void knight(Player player) {

		}

		private static void ninja(Player player) {

		}
	}

}
