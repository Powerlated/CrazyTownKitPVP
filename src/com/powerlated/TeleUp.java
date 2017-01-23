
package com.powerlated;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleUp extends BukkitRunnable {

	Player p;

	public TeleUp(Player p) {
		this.p = p;
	}

	@Override
	public void run() {
		Location l = p.getLocation();
		l.setY(l.getY() + 1);
		p.teleport(l);
	}

}
