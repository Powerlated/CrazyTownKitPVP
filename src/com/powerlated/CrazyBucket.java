
package com.powerlated;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.powerlated.kit.Kit;
import com.powerlated.kit.KitHandler;

public final class CrazyBucket extends JavaPlugin {
	private PluginManager pm = getServer().getPluginManager();
	protected Set<UUID> invincible = Collections.synchronizedSet(new HashSet<UUID>());

	// Sends all scoreboard values into Events
	Events events = new Events(invincible, this);
	public static CrazyBucket cb;

	@Override
	public void onEnable() {
		// Registers events for Events class
		pm.registerEvents(events, this);
		// Gets instance of plugin
		cb = this;
		// Puts instance of self and PluginManager into KitHandler.
		KitHandler.cb = this;
		KitHandler.pm = pm;
		// Registers default kits
		try {
			KitHandler.registerDefaultKits();
		} catch (KitNameConflictException e) {
			e.printStackTrace();
		}
	}

	// Unregisters events and resets objectives.
	@Override
	public void onDisable() {
		invincible = null;
		Bukkit.broadcastMessage(
				ChatColor.RED + "Please note that CrazyBucketKitPVP will break if players do not relog.");
		HandlerList.unregisterAll((Plugin) this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// Lists all kits registered
		if (cmd.getName().equalsIgnoreCase("cbkits")) {
			sender.sendMessage("Kits available:");
			for (Kit kit : KitHandler.registeredKits) {
				sender.sendMessage(kit.getName());
			}
			return true;
		}
		return false;
	}
}
