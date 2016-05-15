
package powerlated;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.Material;

import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import powerlated.CBScoreboard;

public final class CrazyBucket extends JavaPlugin {

	protected Set<UUID> invincible = Collections
			.synchronizedSet(new HashSet<UUID>());
	protected Map<UUID, Scoreboard> sidebarMap = new HashMap<UUID, Scoreboard>();
	protected Map<UUID, CBScoreboard> cbsMap = new HashMap<UUID, CBScoreboard>();
	protected Objective sidebarObjective;
	protected Score kills;
	protected Score killStreak;
	protected Score killsNumber;
	protected Score killStreakNumber;
	protected int killsNumberInt;
	protected int killStreakNumberInt;
	Events events = new Events(invincible, sidebarMap, cbsMap,
			sidebarObjective, kills, killStreak, killsNumber, killStreakNumber,
			killsNumberInt, killStreakNumberInt, this);

	@Override
	public void onEnable() {
		events.ghastWatcher = events.getDefaultWatcher(getServer().getWorlds().get(0),
				EntityType.GHAST);

		for (WrappedWatchableObject object : events.ghastWatcher)
			System.out.println(object);
		for (Player p : this.getServer().getOnlinePlayers()) {
			p.setAllowFlight(true);
		}

		getServer().getPluginManager().registerEvents(events, this);
		getServer().getPluginManager().registerEvents(
				new KitEvents.Pyrotechnic(), this);
		getServer().getPluginManager().registerEvents(
				new KitEvents.Orc(), this);
		getServer().getPluginManager().registerEvents(
				new KitEvents.Ghost(), this);
		Events.plugin = this;
		KitEvents.cb = this;
	}

	@Override
	public void onDisable() {
		invincible = null;
		sidebarMap = null;
		cbsMap = null;
		sidebarObjective = null;
		kills = null;
		killStreak = null;
		killsNumber = null;
		Bukkit.broadcastMessage(ChatColor.RED
				+ "Please note that CrazyBucketKitPVP will break if players do not relog.");
		HandlerList.unregisterAll((Plugin) this);
	}

}
