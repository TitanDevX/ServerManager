package me.titan.serverMang.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.titan.lib.Common;
import me.titan.lib.enums.LogType;
import me.titan.serverMang.ServerMang;
import me.titan.serverMang.enums.Message;
import me.titan.serverMang.enums.Perms;
import me.titan.serverMang.enums.TaskType;
import me.titan.serverMang.messages.Messages;
import me.titan.serverMang.messages.PlaceHolder;
import net.milkbowl.vault.economy.EconomyResponse;

public class PlayerMethods {
	public static void Ban(final Player target, final String reason, final Player p) {

		Bukkit.getServer().getBanList(Type.IP).addBan(target.getAddress().getHostName(), reason, null, null);
		kick(target, reason, p);
		methods.BanAsk.remove(p.getUniqueId(), target.getUniqueId());
		Common.tell(p, Messages.get(Message.BAN, Arrays.asList(new PlaceHolder("admin", p.getName()),
				new PlaceHolder("target", target.getName()), new PlaceHolder("reason", reason))));

		methods.registerTask("&4Banned &6" + target.getName(),
				methods.getTaskMessage("&c{target} &8Bannned by &c{player} &8on {date}.", target, p), target,
				p, TaskType.BAN, reason);

		methods.bans++;
	}

	public static void kick(final Player target, final String reason, final Player p) {

		new BukkitRunnable() {

			@Override
			public void run() {

				if (target.isOnline()) {

					target.kickPlayer(reason);
					methods.KickAsk.remove(p.getUniqueId());
					Common.tell(p, Messages.get(Message.KICK, Arrays.asList(new PlaceHolder("admin", p.getName()),
							new PlaceHolder("target", target.getName()), new PlaceHolder("reason", reason))));
					methods.kicks++;
				}

			}
		}.runTask(ServerMang.getInstance());
	}

	@SuppressWarnings("deprecation")
	public static void TakeMoney(final Player target, final int amount, final Player p) {
		if (methods.econ.getBalance(target.getName()) >= amount) {

			final EconomyResponse r = methods.econ.withdrawPlayer(target, amount);
			if (r.transactionSuccess()) {
				methods.registerTask("&6Take from  &c" + target.getName() + " &5" + amount,
						methods.getTaskMessage("&c{player} &8has taken &c" + amount + " &8from &c{target} &8 on {date}",
								target,
								p),
						target, p, TaskType.TAKE, amount);

				List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
						new PlaceHolder("target", target.getName()),
						new PlaceHolder("amount", amount + ""),
						new PlaceHolder("balance", methods.econ.getBalance(target.getName()) + ""));
				Common.tell(p, Messages.get(Message.TAKE_P, pcs));
				Common.tell(target, Messages.get(Message.TAKE_TARGET, pcs));

			} else
				Common.tell(p, "&8An error occured: &c" + r.errorMessage);

		} else {
			Common.tell(p, "&4" + target.getName() + " don't have " + amount + "!");
			return;
		}

	}

	public static void pardon(final Player p, final Player target) {

		if (!p.hasPermission(Perms.PARDON.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else if (Bukkit.getBanList(Type.IP).isBanned(target.getAddress().getHostName())) {

			Bukkit.getServer().getBanList(Type.IP).pardon(target.getAddress().getHostName());
			List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
					new PlaceHolder("target", target.getName()));

			Common.tell(p, Messages.get(Message.PARDON, pcs));
			methods.ubans++;
			methods.registerTask("&6Unbanned &5" + target.getName(),
					methods.getTaskMessage("&c{player} &8has Unbanned &c{target} &8on {date}.", p, target),
					target, p, TaskType.PARDON, 0);
		} else
			Common.tell(p, "&4This player is not banned.");

	}

	public static void setCreative(final Player p, final Player target) {

		if (!p.hasPermission(Perms.GM_C.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else if (target.getGameMode() != GameMode.CREATIVE) {
			target.setGameMode(GameMode.CREATIVE);
			methods.registerTask("&6Set &c" + target.getName() + " &6to Creative",
					methods.getTaskMessage(
							"&c{player} &8has changed &c{target}'s &8GameMode to &cCreative &8on {date}.", p,
							target),
					target, p,
					TaskType.CREATIVE, 0);
			List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
					new PlaceHolder("target", target.getName()));
			Common.tell(p, Messages.get(Message.CREATIVE_P, pcs));

			Common.tell(target, Messages.get(Message.CREATIVE_TARGET, pcs));
		} else
			Common.tell(p, "&4" + target.getName() + " is already in Creative Gamemode.");
	}

	public static void setSurvival(final Player p, final Player target) {

		if (!p.hasPermission(Perms.GM_S.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else if (target.getGameMode() != GameMode.SURVIVAL) {
			target.setGameMode(GameMode.SURVIVAL);
			methods.registerTask("&6Set &c" + target.getName() + " &6to Survial",
					methods.getTaskMessage(
							"&c{player} &8has changed &c{target}'s &8GameMode to &cSurvival &8on {date}.", p,
							target),
					target, p,
					TaskType.SURVIVAL, 0);

			List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
					new PlaceHolder("target", target.getName()));
			Common.tell(p, Messages.get(Message.SURVIVAL_P, pcs));

			Common.tell(target, Messages.get(Message.SURVIVAL_TARGET, pcs));

		} else
			Common.tell(p, "&4" + target.getName() + " is already in Survival Gamemode.");

	}

	public static void setSpec(final Player p, final Player target) {

		if (!p.hasPermission(Perms.GM_SP.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else if (target.getGameMode() != GameMode.SPECTATOR) {
			target.setGameMode(GameMode.SPECTATOR);
			methods.registerTask("&6Set &c" + target.getName() + " &6to Spectator",
					methods.getTaskMessage(
							"&c{player} &8has changed &c{target}'s &8GameMode to &cSpectator &8on {date}.", p,
							target),
					target, p,
					TaskType.SPECTETOR, 0);

			List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
					new PlaceHolder("target", target.getName()));
			Common.tell(p, Messages.get(Message.SPEC_P, pcs));

			Common.tell(target, Messages.get(Message.SPEC_TARGET, pcs));

		} else
			Common.tell(p, "&4" + target.getName() + " is already in Spectator Gamemode.");

	}

	public static void tpHere(final Player p, final Player target) {
		if (!p.hasPermission(Perms.TPH.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else if (target.isOnline()) {

			target.teleport(p.getLocation());
			List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
					new PlaceHolder("target", target.getName()));
			Common.tell(p, Messages.get(Message.TPH_P, pcs));

			Common.tell(target, Messages.get(Message.TP_TARGET, pcs));
			methods.tps++;
		} else
			Common.tell(p, "&4this player is not online.");

	}

	public static void tp(final Player p, final Player target, final String Text) {
		// YES 120,10,100,world
		final String[] args = Text.split(",");
		final int x = Integer.parseInt(args[0]);
		final int y = Integer.parseInt(args[1]);
		final int z = Integer.parseInt(args[2]);
		final World w = Bukkit.getWorld(args[3]);
		target.teleport(new Location(w, x, y, z));
		List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
				new PlaceHolder("target", target.getName()),
				new PlaceHolder("location", x + ", " + y + ", " + z + ", " + w.getName()));
		Common.tell(p, Messages.get(Message.TP_P, pcs));

		Common.tell(target, Messages.get(Message.TP_TARGET, pcs));
		methods.TpAsk.remove(p.getUniqueId());
		methods.tps++;
	}

	public static void giveMoney(final Player p, final Player target, final String Text) {

		final int amount = Integer.parseInt(Text);

		final EconomyResponse r = methods.econ.depositPlayer(target, amount);
		if (r.transactionSuccess()) {
			methods.registerTask("&6Gave &c" + target.getName() + " &5" + amount,
					methods.getTaskMessage(
							"&c{target} &8has given &c" + amount + " &8by &c{player} &8on {date}", p,
							target),
					target, p, TaskType.GIVE, amount);

			List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
					new PlaceHolder("target", target.getName()),
					new PlaceHolder("amount", amount + ""),
					new PlaceHolder("balance", methods.econ.getBalance(target.getName()) + ""));
			Common.tell(p, Messages.get(Message.GIVE_P, pcs));
			Common.tell(target, Messages.get(Message.GIVE_TARGET, pcs));

			methods.gives++;
		} else
			Common.tell(p, "&8An error occured: &c" + r.errorMessage);
		methods.GiveAsk.remove(p.getUniqueId());
	}

	public static void takeMoney(final Player p, final Player target, final String Text) {

		final int amount = Integer.parseInt(Text);
		TakeMoney(target, amount, p);
		methods.TakeAsk.remove(p.getUniqueId());
	}

	public static void tpTo(final Player p, final Player target) {
		if (!p.hasPermission(Perms.TPT.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else {

			p.teleport(target.getLocation());
			List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
					new PlaceHolder("target", target.getName()));
			Common.tell(p, Messages.get(Message.TP_P, pcs));
			Common.tell(target, Messages.get(Message.TP_TARGET, pcs));
			methods.tps++;
		}
	}

	public static void setPrefix(final Player p, final Player target, String prefix) {
		prefix = Common.colorize(prefix);
		methods.registerTask("&c" + target.getName() + " &6prefix's changed to " + prefix,
				methods.getTaskMessage("&c{player} &8changed &c{target}'s &8prefix to &c" + prefix + " &8on {date}",
						p, target),
				target, p, TaskType.PREFIX,
				methods.chat.getPlayerPrefix(target) + ";" + prefix);

		methods.chat.setPlayerPrefix(target, prefix);
		List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
				new PlaceHolder("target", target.getName()), new PlaceHolder("prefix", prefix));
		Common.tell(p, Messages.get(Message.PREFIX_P, pcs));
		Common.tell(target, Messages.get(Message.PREFIX_TARGET, pcs));
		methods.PrefixAsk.containsKey(p.getUniqueId() != null ? methods.PrefixAsk.remove(p.getUniqueId()) : null);

	}

	public static void sendChatLog(final Player p, final Player target) {
		if (!p.hasPermission(Perms.LOG.p())) {
			Common.tell(p, "&4You lack the proper permission.");
			return;
		}

		final int from = 1440 * 60 * 1000;
		final int to = 0;

		final String fromDate = ChatUtils.getDate(System.currentTimeMillis() - from);
		final String toDate = ChatUtils.getDate(System.currentTimeMillis() - to);

		Common.tell(p, "&8&m-------------------------------------------");
		Common.tell(p, "&7chat history for &c" + target.getName());
		Common.tell(p, "&8&m-------------------------------------------");

		ChatUtils.readLogs("chat.json").forEach((lm) -> {
			final long diff = System.currentTimeMillis() - lm.getTime();

			if (diff < from && diff > to)
				if (lm.getPlayer().equals(target.getName()))
					Common.tell(p, String.format("&8%s &7<&f%s&7> &f%s", ChatUtils.getDate(lm.getTime()),
							lm.getPlayer(), lm.getMessage()));
		});

		Common.tell(p, "&8&m-------------------------------------------");

	}

	public static void ChangeGroup(final Player p, final Player target, final String GROUP_NAME) {
		if (methods.perm == null) {
			Common.log("&4Group change task has failed due to no permission plugin found.", LogType.WARNING);
			return;
		}

		for (final String group : methods.perm.getGroups())
			if (group.equals(GROUP_NAME)) {
				methods.registerTask("&c" + target.getName() + " &6group's changed to " + GROUP_NAME,
						methods.getTaskMessage(
								"&c{player} &8changed &c{target}'s &8group to &c" + GROUP_NAME + " &8on {date}",
								p, target),
						target, p, TaskType.GROUP,
						methods.perm.getPrimaryGroup(target) + ";" + GROUP_NAME);
				methods.perm.playerAddGroup(target, GROUP_NAME);

				List<PlaceHolder> pcs = PlaceHolder.makeList(new PlaceHolder("admin", p.getName()),
						new PlaceHolder("target", target.getName()), new PlaceHolder("group", GROUP_NAME));
				Common.tell(p, Messages.get(Message.GROUP_P, pcs));
				Common.tell(target, Messages.get(Message.GROUP_TARGET, pcs));
				methods.GroupAsk.remove(p.getUniqueId());
				break;
			}

	}
}
