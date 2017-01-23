package powerlated.kit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import powerlated.Util;

public class Orc extends Kit implements Listener {
	@EventHandler
	public void healthStealer(EntityDamageByEntityEvent event) {
		if (KitHandler.kitMap.get(event.getDamager().getUniqueId()) == null) {
			return;
		}
		if (KitHandler.kitMap.get(event.getDamager().getUniqueId()).equals(Kits.ORC)) {
			Bukkit.broadcastMessage(KitHandler.kitMap.get(event.getDamager().getUniqueId()).toString());
			if (event.getDamager() instanceof Player) {
				Damageable damager = (Damageable) event.getDamager();
				double damage = event.getDamage();
				damager.setHealth((damage / 2) + damager.getHealth());
			}
		}
	}

	@Override
	public String getName() {
		return "Orc";
	}

	@Override
	public void give(Player p) {
		Util.clearInventory(p);
		Util.removeEffects(p);
		PlayerInventory pi = p.getInventory();
		pi.setHelmet(new ItemStack(Material.CHAINMAIL_HELMET));
		pi.setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE));
		pi.setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS));
		pi.setBoots(new ItemStack(Material.CHAINMAIL_BOOTS));
		ItemStack sword = new ItemStack(Material.WOOD_SWORD);
		Util.unbreakable(sword);
		pi.addItem(sword);
	}

	@Override
	public Kits getType() {
		return Kits.ORC;
	}

}