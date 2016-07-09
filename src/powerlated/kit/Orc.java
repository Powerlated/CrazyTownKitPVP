package powerlated.kit;

import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import powerlated.KitHandler;
import powerlated.Kits;

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
}