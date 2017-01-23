package com.powerlated;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public final class CBScoreboard {
	Scoreboard sidebar;
	Score kills;
	Score killStreak;
	Score killStreakNumber;
	Score killsNumber;
	int killsNumberInt = 1;

	public void setup(PlayerJoinEvent event, Set<UUID> invincible, Map<UUID, Scoreboard> sidebarMap,
			Objective sidebarObjective, Score kills, Score killsNumber, Score killStreak, Score killStreakNumber) {
		Scoreboard sidebar = Bukkit.getScoreboardManager().getNewScoreboard();
		sidebarMap.put(event.getPlayer().getUniqueId(), sidebar);
		sidebarObjective = sidebar.registerNewObjective("sidebarObjective", "dummy");
		sidebarObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
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
		this.kills = kills;
		this.killStreak = killStreak;
		this.killStreakNumber = killStreakNumber;
		this.killsNumber = killsNumber;
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
