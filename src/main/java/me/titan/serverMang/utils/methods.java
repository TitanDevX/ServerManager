package me.titan.serverMang.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.kangarko.compatbridge.model.CompMaterial;
import me.kangarko.compatbridge.model.CompSound;
import me.kangarko.compatbridge.utils.VersionResolver;
import me.kangarko.ui.model.ItemCreator;
import me.kangarko.ui.model.ItemCreator.ItemCreatorBuilder;
import me.titan.lib.Common;
import me.titan.serverMang.ServerMang;
import me.titan.serverMang.Tasks.Task;
import me.titan.serverMang.enums.Perms;
import me.titan.serverMang.enums.TaskType;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class methods {

	// MAPS -=-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-

	public static Map<UUID, UUID> BanAsk = new HashMap<>();
	public static Map<UUID, UUID> KickAsk = new HashMap<>();
	public static Map<UUID, UUID> TpAsk = new HashMap<>();
	public static Map<UUID, UUID> GiveAsk = new HashMap<>();
	public static Map<UUID, UUID> TakeAsk = new HashMap<>();
	public static Map<UUID, UUID> GroupAsk = new HashMap<>();
	public static Map<UUID, UUID> PrefixAsk = new HashMap<>();
	public static Map<UUID, World> BrodAsk = new HashMap<>();

	public static Map<UUID, Integer> Players = new HashMap<>();

	public static int bans;
	public static int kicks;
	public static int tps;
	public static int gives;
	public static int ubans;
	// MAPS =-=-=-=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=
	static Economy econ = ServerMang.getEconomy();
	static Permission perm = ServerMang.getPermissions();
	static Chat chat = ServerMang.getChat();

	public static void removeRequest(final Player p, final Player target, final String reason) {
		Map m = null;
		if (target != null)
			switch (reason) {
				case "BAN":
					m = BanAsk;
					break;
				case "GIVE":

					m = GiveAsk;
					break;
				case "TAKE":

					m = TakeAsk;
					break;
				case "KICK":

					m = KickAsk;
					break;
				case "TP":

					m = TpAsk;
					break;
				case "GROUP":

					m = GroupAsk;
					break;
				case "PREFIX":
					m = PrefixAsk;
					break;
				default:
					m = null;
					break;
			}
		if (m == null)
			return;
		final Map<UUID, UUID> mp = m;
		new BukkitRunnable() {

			@Override
			public void run() {
				if (mp.containsKey(p.getUniqueId())
						&& mp.containsValue(target.getUniqueId())) {
					mp.remove(p.getUniqueId(), target.getUniqueId());
					Common.tell(p,
							"&8we have automaticly removed the " + reason.toLowerCase() + " request for &c"
									+ target.getName());
				}
			}
		}.runTaskLater(ServerMang.getInstance(), 60 * 20);

	}

	public static void WorldRequestRemove(final Player p, final World w, final String reason) {
		Map<UUID, World> m;
		switch (reason) {
			case "BRODCAST":
				m = BrodAsk;
				break;
			default:
				m = null;
				break;
		}
		final Map<UUID, World> mp = m;
		new BukkitRunnable() {

			@Override
			public void run() {
				if (mp.containsKey(p.getUniqueId())
						&& mp.containsValue(w)) {
					mp.remove(p.getUniqueId(), w);
					Common.tell(p,
							"&8we have automaticly removed the " + reason.toLowerCase() + " request for &c"
									+ w.getName());

				}
			}
		}.runTaskLater(ServerMang.getInstance(), 60 * 20);
	}

	public static void tellLeagendryMsg(final Player p, final String reason, final Player target, final String idk) {
		Common.tell(p, "&8Are you sure you want to " + reason + " &c" + target.getName() + " &8?",
				"&8if you want so, please reply with: &c" + "'YES <" + idk + ">'.",
				"&8or your request will be removed in a minute.");

	}

	public static void runTaskS(final BukkitRunnable bn) {
		bn.runTask(ServerMang.getInstance());

	}

	public static List<Player> getNearPlayers(final Player p) {
		final List<Player> ps = new ArrayList<>();
		final Location loc = p.getLocation();
		final List<Entity> en = p.getNearbyEntities(p.getLocation().getX(), p.getLocation().getY(),
				p.getLocation().getZ());
		for (final Entity e : en)
			if (e instanceof Player)
				ps.add((Player) e);
		return ps;
	}

	public static void sendMenu(final Player p) {
		Common.tell(p, "&6&lNEARBY PLAYERS: RIGHT CLICK TO OPEN MENU");
		Common.tell(p, "&m&8-------------------------------------------");
		getNearPlayers(p).forEach((pl) -> {

			final TextComponent message = new TextComponent(pl.getName());
			message.setColor(ChatColor.RED);
			message.setBold(true);
			final ClickEvent ce = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/player " + pl.getName());
			final HoverEvent he = new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					new ComponentBuilder(Common.colorize("Right Click to open the menu for this player.")).create());

			message.setClickEvent(ce);
			message.setHoverEvent(he);
			p.spigot().sendMessage(message);

		});

		Common.tell(p, "&m&8-------------------------------------------");
	}

	// CHECK IF PLAYER IS NOT PUT IN A MAP WHILE TRYING TO JOIN ANOTHER ONE!
	// WITHOUT THIS METHOD MILLION OF BUGS WILL HEAD UP!!!
	// IT SAVES THIS PLUGINS LIFE!!!!!
	public static void check(final Player p, final Map<UUID, UUID> m, final Map<UUID, World> m2) {
		if (m2 == null && m != null) {
			if (!m.containsKey(p.getUniqueId()))
				return;
			if (m != BanAsk && BanAsk.containsKey(p.getUniqueId()))
				BanAsk.remove(p.getUniqueId());

			if (m != KickAsk && KickAsk.containsKey(p.getUniqueId()))
				KickAsk.remove(p.getUniqueId());

			if (m != GiveAsk && GiveAsk.containsKey(p.getUniqueId()))
				GiveAsk.remove(p.getUniqueId());

			if (m != TakeAsk && TakeAsk.containsKey(p.getUniqueId()))
				TakeAsk.remove(p.getUniqueId());

			if (m != TpAsk && TpAsk.containsKey(p.getUniqueId()))
				TpAsk.remove(p.getUniqueId());

			if (m != GroupAsk && GroupAsk.containsKey(p.getUniqueId()))
				GroupAsk.remove(p.getUniqueId());

			if (m != PrefixAsk && PrefixAsk.containsKey(p.getUniqueId()))
				PrefixAsk.remove(p.getUniqueId());
		} else if (m2 != null && m == null)
			if (m2 != BrodAsk && BrodAsk.containsKey(p.getUniqueId()))
				BrodAsk.remove(p.getUniqueId());

	}

	public static void putInBrodcast(final Player p, final World w) {

		if (!p.hasPermission(Perms.BROADCAST.p())) {
			Common.tell(p, "&4You lack the proper permission.");
			return;
		}
		p.closeInventory();
		BrodAsk.put(p.getUniqueId(), w);
		Common.tell(p, "&2We successfully added you to the list,",
				"&cNow reply with&6 " + "'YES <Message>'" + " &c(Use '&' to color).");
		WorldRequestRemove(p, w, "BRODCAST");
	}

	public static void brodcastMsg(final Player p, final World w, final String message) {

		BrodcastMessageIn(w, message);

		Common.tell(p, "&8You have brodcasted a message in &c" + w.getName());

	}

	public static void BrodcastMessageIn(final World w, final String message) {
		for (final Player p : w.getPlayers()) {
			Common.tell(p, message);

			playSoundFor(p, "BLOCK_NOTE_BLOCK_PLING", "BLOCK_NOTE_PLING");
		}

	}

	public static void playSoundFor(final Player pl, final String newName, final String oldName) {

		pl.playSound(pl.getLocation(), returnSound(newName, oldName), 100, 10);

	}

	public static void playSoundFor(final Player pl, CompSound sound) {

		pl.playSound(pl.getLocation(), sound.getSound(), 100, 10);

	}

	public static Sound returnSound(final String newName, final String oldName) {

		return Sound.valueOf(VersionResolver.isAtLeast1_13() ? newName : oldName);
	}

	public static ItemCreatorBuilder getFillItem(final FillItem item, final String name) {

		switch (item) {
			case BLACK_GLASS:
				return ItemCreator.of(CompMaterial.BLACK_STAINED_GLASS_PANE, name);
			case RED_GLASS:
				return ItemCreator.of(CompMaterial.RED_STAINED_GLASS_PANE, name);
			case GREEN_GLASS:
				return ItemCreator.of(CompMaterial.GREEN_STAINED_GLASS_PANE, name);
			case SOON:
				return ItemCreator.of(CompMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, "&7soon");
			default:
				return null;
		}

	}

	public static ItemStack getHead(final Player player) {

		return ItemCreator.of(CompMaterial.PLAYER_HEAD, player.getName(),
				"",
				"&7Right Click to open this player's",
				"&6Control panel.").data(3).build().setSkull(player.getName()).make();
	}

	public static String getTaskMessage(String msg, Player target, Player player) {

		return msg.replace("{target}", target.getName()).replace("{player}", player.getName())
				.replace("{date}", " &8[&c"
						+ ChatUtils.getDate(System.currentTimeMillis()) + "&8]");

	}

	public static void registerTask(String Title, String message, Player target, Player p, TaskType type, int ExtraInt,
			String ExtraText) {

		TaskRegister(Title, message, target, p, type, ExtraInt, ExtraText);

	}

	public static void registerTask(String Title, String message, Player target, Player p, TaskType type,
			int ExtraInt) {

		TaskRegister(Title, message, target, p, type, ExtraInt, "");

	}

	public static void registerTask(String Title, String message, Player target, Player p, TaskType type,
			String ExtraText) {

		TaskRegister(Title, message, target, p, type, 0, ExtraText);
	}

	public static void cancelTask(Task task) {
		task.remove();
		switch (task.getType()) {
			case BAN:

				Player target = Bukkit.getPlayer(task.getTarget());
				PlayerMethods.pardon(Bukkit.getPlayer(task.getPlayer()), target != null ? target : null);
				break;
			case PARDON:

				Player target1 = Bukkit.getPlayer(task.getTarget());
				PlayerMethods.Ban(Bukkit.getPlayer(task.getPlayer()), "", target1);
				break;
			case GIVE:
				int amount = task.getExtraInt();
				Player target2 = Bukkit.getPlayer(task.getTarget());

				PlayerMethods.TakeMoney(target2, amount, Bukkit.getPlayer(task.getPlayer()));
				break;
			case CREATIVE:
				Player target3 = Bukkit.getPlayer(task.getTarget());

				PlayerMethods.setSurvival(Bukkit.getPlayer(task.getPlayer()), target3);
				break;
			case SPECTETOR:
				Player target4 = Bukkit.getPlayer(task.getTarget());

				PlayerMethods.setSurvival(Bukkit.getPlayer(task.getPlayer()), target4);
				break;
			case SURVIVAL:
				Player target5 = Bukkit.getPlayer(task.getTarget());

				PlayerMethods.setCreative(Bukkit.getPlayer(task.getPlayer()), target5);

				break;
			case GROUP:
				String oldGroup = task.getExtraText().split(";")[0];

				Player target6 = Bukkit.getPlayer(task.getTarget());
				PlayerMethods.ChangeGroup(Bukkit.getPlayer(task.getPlayer()), target6, oldGroup);
				break;
			case PREFIX:
				String oldPrefix = task.getExtraText().split(";")[0];

				Player target7 = Bukkit.getPlayer(task.getTarget());
				PlayerMethods.setPrefix(Bukkit.getPlayer(task.getPlayer()), target7, oldPrefix);
				break;
			case TAKE:
				int amount1 = task.getExtraInt();
				Player target8 = Bukkit.getPlayer(task.getTarget());

				PlayerMethods.giveMoney(target8, Bukkit.getPlayer(task.getPlayer()), amount1 + "");
				break;

			default:
				break;
		}

	}

	public static enum FillItem {
		BLACK_GLASS, GREEN_GLASS, RED_GLASS, SOON;

	}

	private static void TaskRegister(String Title, String message, Player target, Player p, TaskType type, int ExtraInt,
			String ExtraText) {
		UUID Target = target.getUniqueId();
		UUID Player = p.getUniqueId();
		ServerMang.getInstance().registerTask(Title, message, type, Target, Player, ExtraInt, ExtraText);

	}

}
