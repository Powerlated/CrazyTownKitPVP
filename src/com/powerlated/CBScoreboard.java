package com.powerlated;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public final class CBScoreboard {
	Scoreboard sidebar;

	protected static Map<UUID, CBScoreboard> cbsMap = new HashMap<UUID, CBScoreboard>();
	protected Objective sidebarObjective;
	protected Score kills;
	protected Score killStreak;
	protected Score killsNumber;
	protected Score killStreakNumber;
	protected int killsNumberInt;
	protected int killStreakNumberInt;

	// Gets scoreboard manager
	ScoreboardManager manager = Bukkit.getScoreboardManager();
	protected Map<UUID, Scoreboard> sidebarMap = new HashMap<UUID, Scoreboard>();

	public void setup(PlayerJoinEvent event) {

		// Gets a new scoreboard and puts it in the player's scoreboard.
		Scoreboard sidebar = manager.getNewScoreboard();
		sidebarMap.put(event.getPlayer().getUniqueId(), sidebar);

		// Creates scoreboard objective and sets it to the
		sidebarObjective = sidebar.registerNewObjective("sidebarObjective", "dummy");
		sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

		// Sets up the scoreboard
		sidebarObjective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "CRAZY BUCKET");
		kills = sidebarObjective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Kills:");
		killsNumber = sidebarObjective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + Integer.toString(0));
		/*
		 * killStreak = sidebarObjective.getScore(ChatColor.RED + "" +
		 * ChatColor.BOLD + "Killstreak:"); killStreakNumber =
		 * sidebarObjective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD +
		 * Integer.toString(0) + " ");
		 */
		kills.setScore(3);
		killsNumber.setScore(2);
		// killStreak.setScore(1);
		// killStreakNumber.setScore(0);
		event.getPlayer().setScoreboard(sidebar);
		this.sidebar = sidebar;
	}

	public void addKills(Objective sidebarObjective) {
		sidebar.getObjective("sidebarObjective").unregister();
		sidebarObjective = sidebar.registerNewObjective("sidebarObjective", "dummy");
		sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		sidebarObjective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "CRAZY BUCKET");
		kills = sidebarObjective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Kills:");
		killsNumber = sidebarObjective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + killsNumberInt);
		/*
		 * killStreak = sidebarObjective.getScore(ChatColor.RED + "" +
		 * ChatColor.BOLD + "Killstreak:"); killStreakNumber =
		 * sidebarObjective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD +
		 * Integer.toString(0) + " ");
		 */
		kills.setScore(3);
		killsNumber.setScore(2);
		// killStreak.setScore(1);
		// killStreakNumber.setScore(0);
		this.killsNumberInt++;
	}

	public void setKills(Objective sidebarObjective, int set) {
		sidebar.getObjective("sidebarObjective").unregister();
		sidebarObjective = sidebar.registerNewObjective("sidebarObjective", "dummy");
		sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
		sidebarObjective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "CRAZY BUCKET");
		kills = sidebarObjective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Kills:");
		killsNumber = sidebarObjective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + killsNumberInt);
		/*
		 * killStreak = sidebarObjective.getScore(ChatColor.RED + "" +
		 * ChatColor.BOLD + "Killstreak:"); killStreakNumber =
		 * sidebarObjective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD +
		 * Integer.toString(0) + " ");
		 */
		kills.setScore(3);
		killsNumber.setScore(2);
		// killStreak.setScore(1);
		// killStreakNumber.setScore(0);
		this.killsNumberInt = set;
	}

}
