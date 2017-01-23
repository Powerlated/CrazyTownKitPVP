
package powerlated.kit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import powerlated.KitNameConflictException;
import powerlated.kit.vanilla.Ghost;
import powerlated.kit.vanilla.Orc;
import powerlated.kit.vanilla.Pyrotechnic;
import powerlated.kit.vanilla.RobinHood;

public final class KitHandler {
	private PluginManager pm = cb.getServer().getPluginManager();
	public static HashMap<UUID, Kit> kitMap = new HashMap<UUID, Kit>();
	protected static HashSet<Kit> registeredKits = new HashSet<Kit>();
	static JavaPlugin cb;

	public static void registerKit(Kit kit) throws KitNameConflictException {
		for (Kit k : registeredKits) {
			if (k.getName() == kit.getName()) {
				throw new KitNameConflictException();
			}
		}
	}
	
	public static void registerDefaultKits() throws KitNameConflictException {
		registerKit(new Orc());
		registerKit(new Ghost());
		registerKit(new Pyrotechnic());
		registerKit(new RobinHood());
	}

	public static Kit getKit(String input) {
		for (Kit k : registeredKits) {
			if (k.getName() == input) {
				return k;
			}
		}
		return null;
	}
}
