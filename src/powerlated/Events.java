
package powerlated;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Hopper;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;

import powerlated.kit.Kit;
import powerlated.kit.KitHandler;

public final class Events implements Listener {
	WrappedDataWatcher ghastWatcher;
	protected static CrazyBucket plugin;
	protected Set<UUID> invincible = Collections.synchronizedSet(new HashSet<UUID>());
	protected Map<UUID, Scoreboard> sidebarMap = new HashMap<UUID, Scoreboard>();
	protected Map<UUID, CBScoreboard> cbsMap = new HashMap<UUID, CBScoreboard>();
	protected Objective sidebarObjective;
	protected Score kills;
	protected Score killStreak;
	protected Score killsNumber;
	protected Score killStreakNumber;
	protected int killsNumberInt;
	protected int killStreakNumberInt;
	protected CrazyBucket cb;
	Runtime r = Runtime.getRuntime();

	public Events() {

	}

	public Events(Set<UUID> invincible, Map<UUID, Scoreboard> sidebarMap, Map<UUID, CBScoreboard> cbsMap,
			Objective sidebarObjective, Score kills, Score killStreak, Score killStreakNumber, Score killsNumber,
			int killsNumberInt, int killStreakNumberInt, CrazyBucket cb) {
		this.invincible = invincible;
		this.sidebarMap = sidebarMap;
		this.cbsMap = cbsMap;
		this.sidebarObjective = sidebarObjective;
		this.kills = kills;
		this.killStreak = killStreak;
		this.killStreakNumber = killStreakNumber;
		this.killsNumberInt = killsNumberInt;
		this.killStreakNumberInt = killStreakNumberInt;
		this.cb = cb;
	}

	// Setup scoreboard
	@EventHandler
	public void login(PlayerJoinEvent event) {
		// Adds dust around player
		/*
		 * BukkitRunnable br = new BukkitRunnable() {
		 * 
		 * @Override public void run() { List<Location> l =
		 * Util.sphere(event.getPlayer().getLocation(), 5, 5, false, true, 0);
		 * for (Location loc : l) { Random rand = new Random(); double x =
		 * ThreadLocalRandom.current().nextDouble(-0.75, 0.75); double y =
		 * ThreadLocalRandom.current().nextDouble(-0.75, 0.75); double z =
		 * ThreadLocalRandom.current().nextDouble(-0.75, 0.75); loc.add(0.5 + x,
		 * 0.5 + y, 0.5 + z); event.getPlayer().playEffect(loc,
		 * Effect.COLOURED_DUST, null); } }
		 * 
		 * }; br.runTaskTimer(cb, 0, 5); event.getPlayer().setAllowFlight(true);
		 */
		CBScoreboard cbs = new CBScoreboard();
		cbsMap.put(event.getPlayer().getUniqueId(), cbs);
		cbs.setup(event, invincible, sidebarMap, sidebarObjective, kills, killsNumber, killStreak, killStreakNumber);
	}

	@EventHandler
	public void logOut(PlayerQuitEvent event) {
		cbsMap.remove(event.getPlayer());
	}

	// Scoreboard

	@EventHandler
	public void kitSignPlace(SignChangeEvent event) {
		if (event.getLine(0).equalsIgnoreCase("-Kit-")) {
			event.setLine(0, ChatColor.DARK_BLUE + "-Kit-");
		}
	}

	@EventHandler
	public void playerInteract(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		if (block == null) {
			return;
		}
		if (block.getState() instanceof Sign) {
			Player p = event.getPlayer();
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
				Sign sign = (Sign) block.getState();
				String line1 = sign.getLine(0);
				String line2 = sign.getLine(1);
				if (line1.equals(ChatColor.DARK_BLUE + "-Kit-")) {
					boolean foundKit = false;
					for (Kit kit : KitHandler.registeredKits) {
						if (kit.getName().equals(line2)) {
							KitHandler.setPlayerKit(p, KitHandler.getKit(line2));
							foundKit = true;
							break;
						}
					}
					if (foundKit == false) {
						event.getPlayer().sendMessage("Error: That is not a kit.");
					}
				}
				Location sl = sign.getLocation();
				if (sl.subtract(0.0, 2.0, 0.0).getBlock().getType() == Material.TORCH) {
					if (line1.equalsIgnoreCase("1999")) {
						p.setHealth(0);
						towerWrongAnswerDeath(p.getName());
					}
					if (line1.equalsIgnoreCase("2001")) {
						p.setHealth(0);
						towerWrongAnswerDeath(p.getName());
					}
					if (line1.equalsIgnoreCase("2009")) {
						Location l = p.getLocation().add(0.0, 6.0, 0.0);
						p.teleport(l);
						p.sendMessage(ChatColor.AQUA + "You are " + ChatColor.GREEN + "correct!");
					}

				}
			}
		}
		if (event.getAction() == Action.PHYSICAL) {
			Player p = event.getPlayer();
			Location l2 = event.getClickedBlock().getLocation().subtract(0.0, 2.0, 0.0);
			Location l3 = event.getClickedBlock().getLocation().subtract(0.0, 3.0, 0.0);
			if (l2.getBlock().getType() == Material.JUKEBOX) {
				if (l3.getBlock().getState() instanceof Hopper) {
					event.getClickedBlock().setType(Material.STONE_PLATE);
					Hopper h = (Hopper) l3.getBlock().getState();
					ItemStack contents[] = h.getInventory().getContents();
					// Slot 3 setting
					if (contents[2] != null && contents[2].getAmount() == 1) {
						double x = event.getClickedBlock().getLocation().getX() + 0.5;
						double z = event.getClickedBlock().getLocation().getZ() + 0.5;
						p.teleport(new Location(p.getWorld(), x, p.getLocation().getY(), z,
								event.getPlayer().getLocation().getYaw(), event.getPlayer().getLocation().getPitch()));

					} else {
						if (contents[2] != null && contents[2].getAmount() > 1) {
							p.sendMessage(ChatColor.RED
									+ "Error in pressure plate launcher: Slot 3 (center player on launch) must be less than 2");
						}
					}
					if (contents[3] != null && contents[3].getAmount() == 2) {
						new TeleUp(p).runTaskAsynchronously(cb);
					}
					if (contents[0] != null && contents[1] != null) {
						double launchAmount = contents[0].getAmount() + contents[1].getAmount();
						launch(launchAmount, p);
						return;
					} else if (contents[0] != null) {
						launch(contents[0].getAmount(), p);
						return;
					}
				}
			}

		}
	}

	public void towerWrongAnswerDeath(String s) {
		Bukkit.broadcastMessage(ChatColor.RED + s + ChatColor.WHITE + " chose the wrong answer in the " + ChatColor.AQUA
				+ "Tower of Challenges");
	}

	public void launch(double vel, Player p) {
		p.getPlayer().getVelocity().add(new Vector(p.getVelocity().getX(), vel, p.getVelocity().getZ()));
	}

	Location v;

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		/*
		 * if (v != null) { if
		 * (event.getPlayer().getLocation().toVector().getX() == v.getX()) { if
		 * (event.getPlayer().getLocation().toVector().getY() == v.getY()) { if
		 * (event.getPlayer().getLocation().toVector().getZ() == v.getZ()) {
		 * return; } } } } v =
		 * event.getPlayer().getLocation().toVector().toLocation(event.getPlayer
		 * ().getWorld());
		 * 
		 * 
		 * if (event.getPlayer().isFlying()) {
		 * event.getPlayer().playSound(event.getPlayer().getLocation(),
		 * Sound.GHAST_FIREBALL, 5, 1);
		 * event.getPlayer().setVelocity(event.getPlayer
		 * ().getLocation().getDirection().multiply(1.5));
		 * event.getPlayer().setFlying(false); }
		 */
		Player p = event.getPlayer();

		// Redstone launching
		if (p.getLocation().getBlock().getType() == Material.REDSTONE_WIRE
				&& !(p.getWorld().getName().equals("cwdearth"))) {
			Location l = p.getLocation();
			l.subtract(0.0, 1.0, 0.0);
			if (l.getBlock().getType() == Material.QUARTZ_BLOCK) {
				event.getPlayer().setVelocity(new Vector(10.0, 2.5, 0.0));
				if (!(invincible.contains(event.getPlayer().getUniqueId()))) {
					invincible.add(event.getPlayer().getUniqueId());
				}
				Bukkit.getServer().getScheduler().runTaskLater(cb,
						() -> invincible.remove(event.getPlayer().getUniqueId()), 140);

			}
		}

	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player p = (Player) event.getEntity();
			if (p.getWorld().getName().equalsIgnoreCase("world") && (p.getHealth() - event.getDamage()) < 1) {
			}

		}

	}

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		if ((event.getEntity().getKiller() instanceof Player) && event.getEntity().getKiller() != event.getEntity()) {
			Player p = (Player) event.getEntity().getKiller();
			CBScoreboard cbs = cbsMap.get(p.getUniqueId());
			cbs.addKills(sidebarObjective);
			DeathMessages.killMessage(event.getEntity().getKiller(), event.getEntity());
		} else {
			DeathMessages.message(event.getEntity());
		}
	}

	public WrappedDataWatcher getDefaultWatcher(World world, EntityType type) {
		Entity entity = world.spawnEntity(new Location(world, 0, 256, 0), type);
		WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();
		entity.remove();
		return watcher;
	}

}
