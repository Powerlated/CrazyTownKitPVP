package powerlated.kit.vanilla;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import powerlated.CrazyBucket;
import powerlated.Util;
import powerlated.kit.Kit;
import powerlated.kit.KitHandler;

public class RobinHood extends Kit implements Listener {
	int counter = 0;

	@EventHandler
	public void rapidFire(EntityShootBowEvent event) {
		if (event.getEntity() instanceof Player && KitHandler.kitMap.get(event.getEntity().getUniqueId()) instanceof RobinHood) {
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

	@Override
	public String getName() {
		return "Robin Hood";
	}

	@Override
	public void give(Player p) {
		PlayerInventory pi = p.getInventory();
		ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemStack pants = new ItemStack(Material.LEATHER_LEGGINGS);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		pants.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
		green(helmet);
		green(chestplate);
		green(pants);
		green(boots);
		pi.setHelmet(helmet);
		pi.setChestplate(chestplate);
		pi.setLeggings(pants);
		pi.setBoots(boots);
		ItemStack bow = new ItemStack(Material.BOW);
		ItemStack arrow = new ItemStack(Material.ARROW);
		Util.unbreakable(bow);
		ItemMeta bim = bow.getItemMeta();
		bim.setDisplayName(ChatColor.AQUA + "Epic Bow");
		bow.setItemMeta(bim);
		bow.addEnchantment(Enchantment.ARROW_DAMAGE, 2);
		bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
		bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
		pi.addItem(bow, arrow);
	}
	
	private static void green(ItemStack is) {
		ItemMeta im = is.getItemMeta();
		LeatherArmorMeta lam = (LeatherArmorMeta) im;
		lam.setColor(Color.GREEN);
		is.setItemMeta(lam);
	}

	@Override
	public void remove(Player p) {
		
	}
}