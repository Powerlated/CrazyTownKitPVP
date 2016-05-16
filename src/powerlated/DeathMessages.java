package powerlated;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DeathMessages implements Listener {
	public static void killMessage(Player k, Player d) {
		Random rand = new Random();
		int n = 1 + rand.nextInt(1 - 3 + 1);
		if (n == 1) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + k.getName() + ChatColor.GOLD + "has killed" + ChatColor.GREEN + "" + ChatColor.BOLD + d.getName() + ChatColor.GOLD + "!");
		} else if (n == 2) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + k.getName() + ChatColor.GOLD + "just rekt" + ChatColor.GREEN + "" + ChatColor.BOLD + d.getName() + ChatColor.GOLD + "!");
		}
		
	}
	
	public static void message(Player d) {
		EntityDamageEvent ede = d.getLastDamageCause();
		DamageCause dc = ede.getCause();
		if (dc == DamageCause.FALL) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + d.getName() + ChatColor.GOLD + "fell to their death!");
		} else if (dc == DamageCause.FIRE) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + d.getName() + ChatColor.GOLD + "burned to death!");
		} else if (dc == DamageCause.LAVA) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + d.getName() + ChatColor.GOLD + "burned to death in lava!");
		} else if (dc == DamageCause.DROWNING) {
			Bukkit.broadcastMessage(ChatColor.GREEN + "" + ChatColor.BOLD + d.getName() + ChatColor.GOLD + "forgot to breathe!");
		}
	}
	
	
}