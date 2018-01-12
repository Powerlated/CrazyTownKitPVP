package com.powerlated;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
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
	static ScoreboardManager manager = Bukkit.getScoreboardManager();
	protected Map<UUID, Scoreboard> sidebarMap = new HashMap<UUID, Scoreboard>();

	public static void setup(Player p) {
		Bukkit.getLogger().info("[CrazyBucket] Setting up scoreboard for player " + p.getName());
		CBScoreboard cbs = new CBScoreboard();
		// Gets a new scoreboard and puts it in the player's scoreboard.
		Scoreboard sidebar = manager.getNewScoreboard();
		cbs.sidebarMap.put(p.getUniqueId(), sidebar);

		// Creates scoreboard objective and sets it to the
		cbs.sidebarObjective = sidebar.registerNewObjective("sidebarObjective", "dummy");
		cbs.sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

		// Sets up the scoreboard
		cbs.sidebarObjective.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "CRAZY BUCKET");
		cbs.kills = cbs.sidebarObjective.getScore(ChatColor.RED + "" + ChatColor.BOLD + "Kills:");
		cbs.killsNumber = cbs.sidebarObjective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD + Integer.toString(0));
		/*
		 * killStreak = sidebarObjective.getScore(ChatColor.RED + "" +
		 * ChatColor.BOLD + "Killstreak:"); killStreakNumber =
		 * sidebarObjective.getScore(ChatColor.AQUA + "" + ChatColor.BOLD +
		 * Integer.toString(0) + " ");
		 */
		cbs.kills.setScore(3);
		cbs.killsNumber.setScore(2);
		// killStreak.setScore(1);
		// killStreakNumber.setScore(0);
		p.setScoreboard(sidebar);
	}

	public void addKill(Player p) {
		
	}

	public void setKills(Player p) {
		
	}
	
	public void getKills(Player p) {
		
	}

}
