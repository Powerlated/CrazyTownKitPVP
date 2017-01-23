
package powerlated.kit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import powerlated.KitConflictException;

public final class KitHandler {
	private PluginManager pm = cb.getServer().getPluginManager();
	public static HashMap<UUID, Kits> kitMap = new HashMap<UUID, Kits>();
	protected static HashSet<Kit> registeredKits = new HashSet<Kit>();
	static JavaPlugin cb;

	public static void registerKit(Kit kit) throws KitConflictException {
		for (Kit k : registeredKits) {
			if (k.getType() == kit.getType()) {
				throw new KitConflictException();
			}
		}
	}
	
	public static void registerDefaultKits() throws KitConflictException {
		registerKit(new Orc());
		registerKit(new Ghost());
		registerKit(new Pyrotechnic());
		registerKit(new RobinHood());
	}

	


	// Remove a kit from a player
	public static void removeKit(Player p) {
		kitMap.remove(p.getUniqueId());
		
	}

	public static void setKit(Player p, Kits kits) {
		removeKit(p);
		kitMap.put(p.getUniqueId(), kits);

	}
}
