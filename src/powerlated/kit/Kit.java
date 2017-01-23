package powerlated.kit;

import org.bukkit.entity.Player;

public abstract class Kit {
	public abstract String getName();
	public abstract void give(Player p);
	public abstract Kits getType();
}
