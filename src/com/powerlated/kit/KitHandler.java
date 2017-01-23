
package com.powerlated.kit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.powerlated.KitNameConflictException;
import com.powerlated.Util;
import com.powerlated.kit.vanilla.Ghost;
import com.powerlated.kit.vanilla.Orc;
import com.powerlated.kit.vanilla.Pyrotechnic;
import com.powerlated.kit.vanilla.RobinHood;

public final class KitHandler {
	public static JavaPlugin cb;
	public static PluginManager pm;
	public static HashMap<UUID, Kit> kitMap = new HashMap<UUID, Kit>();
	public static HashSet<Kit> registeredKits = new HashSet<Kit>();
	

	public static void registerKit(Kit kit) throws KitNameConflictException {
		for (Kit k : registeredKits) {
			if (k.getName() == kit.getName()) {
				throw new KitNameConflictException();
			}
		}
		registeredKits.add(kit);
		pm.registerEvents(kit, cb);
	}
	
	public static void registerDefaultKits() throws KitNameConflictException {
		registerKit(new Orc());
		registerKit(new Ghost());
		registerKit(new Pyrotechnic());
		registerKit(new RobinHood());
	}

	public static Kit getKit(String input) {
		for (Kit k : registeredKits) {
			if (k.getName().equalsIgnoreCase(input)) {
				return k;
			}
		}
		return null;
	}
	
	public static void setPlayerKit(Player p, Kit k) {
		if (kitMap.get(p.getUniqueId()) != null) {
			kitMap.get(p.getUniqueId()).remove(p);
		}
		Util.clearInventory(p);
		kitMap.put(p.getUniqueId(), k);
		p.sendMessage("Equipped " + k.getName() + " Kit");
		Util.removeEffects(p);
		Util.addEffects(p);
		k.give(p);
	}
}
