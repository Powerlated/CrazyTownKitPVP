package com.powerlated.kit;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public abstract class Kit implements Listener {
	public abstract String getName();

	public abstract void give(Player p);

	public abstract void remove(Player p);
}