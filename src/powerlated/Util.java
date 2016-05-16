
package powerlated;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Openable;
import org.bukkit.scheduler.BukkitRunnable;

public class Util {

	static final class CloseDoor extends BukkitRunnable {

		Block b;

		public CloseDoor(Block b) {
			this.b = b;
		}

		@Override
		public void run() {
			if (b.getState() instanceof Openable) {
				Bukkit.broadcastMessage("Close!");
				Openable o = (Openable) b.getState();
				o.setOpen(false);
			}
		}
	}

	// This is not my code!
	// Thanks to kreashenz on the Bukkit forums for this!
	public static List<Location> sphere(Location loc, int radius, int height, boolean hollow, boolean sphere,
			int plusY) {
		List<Location> circleblocks = new ArrayList<Location>();
		int cx = loc.getBlockX();
		int cy = loc.getBlockY();
		int cz = loc.getBlockZ();

		for (int x = cx - radius; x <= cx + radius; x++) {
			for (int z = cz - radius; z <= cz + radius; z++) {
				for (int y = (sphere ? cy - radius : cy); y < (sphere ? cy + radius : cy + height); y++) {
					double dist = (cx - x) * (cx - x) + (cz - z) * (cz - z) + (sphere ? (cy - y) * (cy - y) : 0);

					if (dist < radius * radius && !(hollow && dist < (radius - 1) * (radius - 1))) {
						Location l = new Location(loc.getWorld(), x, y + plusY, z);
						circleblocks.add(l);
					}
				}
			}
		}

		return circleblocks;
	}

	static final class OpenDoor extends BukkitRunnable {

		Block b;

		public OpenDoor(Block b) {
			this.b = b;
		}

		@Override
		public void run() {
			if (b.getState() instanceof Openable) {
				Bukkit.broadcastMessage("Openable!!!!");
				Bukkit.broadcastMessage("Open!");
				Openable o = (Openable) b.getState();
				o.setOpen(true);
			}
		}
	}

	static final class ToggleDoor extends BukkitRunnable {

		Block b;

		public ToggleDoor(Block b) {
			this.b = b;
		}

		@Override
		public void run() {

			if (b.getState() instanceof Openable) {
				Openable o = (Openable) b.getState();
				if (o.isOpen()) {
					o.setOpen(false);
				} else {
					o.setOpen(true);
				}
				b.getState().update();
			}
		}
	}
}
