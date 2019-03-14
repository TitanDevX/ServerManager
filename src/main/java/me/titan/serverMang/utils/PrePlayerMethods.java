package me.titan.serverMang.utils;

import org.bukkit.entity.Player;

import me.titan.lib.Common;
import me.titan.serverMang.config.Config;
import me.titan.serverMang.enums.Perms;
import me.titan.serverMang.enums.TaskType;
import net.milkbowl.vault.economy.EconomyResponse;

public class PrePlayerMethods {
	public static void RequestBan(final Player p, final Player target, final boolean ask, final String defR) {

		if (!p.hasPermission(Perms.BAN.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else //===========================
		if (ask) {

			if (methods.BanAsk.containsKey(p.getUniqueId()))
				if (methods.BanAsk.containsValue(target.getUniqueId())) {

					Common.tell(p,
							"&4You already asked to ban this player, &8Please type 'YES' to ban him.");
					return;
				}
			methods.BanAsk.put(p.getUniqueId(), target.getUniqueId());
			methods.tellLeagendryMsg(p, "Ban", target, "reason");

			methods.removeRequest(p, target, "BAN");
		} else
			PlayerMethods.Ban(target, defR, p);
	}

	public static void RequestKick(final Player p, final Player target) {

		if (!p.hasPermission(Perms.KICK.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else if (Config.ASK) {

			if (target.isOnline()) {
				if (!methods.KickAsk.containsKey(p.getUniqueId())
						&& !methods.KickAsk.containsKey(target.getUniqueId())) {

					methods.KickAsk.put(p.getUniqueId(), target.getUniqueId());
					methods.tellLeagendryMsg(p, "Kick", target, "reason");

					methods.removeRequest(p, target, "KICK");
				} else
					Common.tell(p, "&4You already request ");
			} else
				Common.tell(p, "&4" + target.getName() + " is not online.");

		} else {

			PlayerMethods.kick(target, Config.DEFAULT_REASON, p);
			Common.tell(p, "&7You have kicked &c" + target.getName() + " &7For " + Config.DEFAULT_REASON);

		}

	}

	public static void RequestTp(final Player p, final Player target) {

		if (!p.hasPermission(Perms.TP.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else if (target.isOnline())
			if (methods.TpAsk.containsKey(p.getUniqueId()) && methods.TpAsk.containsValue(target.getUniqueId()))
				Common.tell(p, "&4You already requested this, &8reply with &c" + "'YES [location]'.");
			else {

				methods.TpAsk.put(p.getUniqueId(), target.getUniqueId());
				Common.tell(p,
						"&8you have requested to teleport &c" + target.getName() + " &8to a location",
						"&8to specify this location please reply with &c" + "'YES [location]'.");

			}

	}

	public static void requestDeposit(final Player p, final Player target) {

		if (!p.hasPermission(Perms.TAKE.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else if (!Config.ASK) {

			final EconomyResponse r = methods.econ.depositPlayer(target, Config.DEFAULT_MONEY);
			if (r.transactionSuccess()) {
				methods.registerTask("Gave " + target.getName() + " &4" + Config.DEFAULT_MONEY,
						methods.getTaskMessage(
								"&c{target} &8has given &c" + Config.DEFAULT_MONEY + " &8by &c{player} &8on {date}", p,
								target),
						target, p, TaskType.GIVE, Config.DEFAULT_MONEY);

				Common.tell(p,
						"&8You gave &c" + target.getName() + Config.DEFAULT_MONEY + " &8Now he have &c"
								+ methods.econ.getBalance(target));

				Common.tell(target, "&8You were given &c" + Config.DEFAULT_MONEY + " &8By &c" + p.getName()
						+ " &8Now have &c" + methods.econ.getBalance(target));

			} else
				Common.tell(p, "&8An error occured: &c" + r.errorMessage);
		} else if (methods.GiveAsk.containsKey(p.getUniqueId()) && methods.GiveAsk.containsValue(target.getUniqueId()))
			Common.tell(p, "&4You already requested this, &8please reply with &c" + "'YES [amount]'.");
		else {

			methods.GiveAsk.put(p.getUniqueId(), target.getUniqueId());
			methods.tellLeagendryMsg(p, "Give", target, "amount");
			methods.removeRequest(p, target, "GIVE");
		}

	}

	public static void requestWithdraw(final Player p, final Player target) {

		if (!p.hasPermission(Perms.TAKE.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else if (!Config.ASK) {

			final EconomyResponse r = methods.econ.withdrawPlayer(target, Config.DEFAULT_MONEY);
			if (r.transactionSuccess()) {
				methods.registerTask("Take " + target.getName() + " &4" + Config.DEFAULT_MONEY,
						methods.getTaskMessage(
								"&c{player} &8has taken &c" + Config.DEFAULT_MONEY + "&8from &c{target}'s &8on {date} ",
								p, target),
						target, p, TaskType.TAKE, Config.DEFAULT_MONEY);

				Common.tell(p,
						"&8You take from &c" + target.getName() + Config.DEFAULT_MONEY + " &8Now he have &c"
								+ methods.econ.getBalance(target));

				Common.tell(target,
						"&8You were Taken &c" + Config.DEFAULT_MONEY + " &8from your balance By &c"
								+ p.getName()
								+ " &8Now have &c" + methods.econ.getBalance(target));

			} else
				Common.tell(p, "&8An error occured: &c" + r.errorMessage);
		} else if (methods.TakeAsk.containsKey(p.getUniqueId()) && methods.TakeAsk.containsValue(target.getUniqueId()))
			Common.tell(p, "&4You already requested this, &8please reply with &c" + "'YES [amount]'.");
		else {

			methods.TakeAsk.put(p.getUniqueId(), target.getUniqueId());
			methods.tellLeagendryMsg(p, "Take", target, "amount");
			methods.removeRequest(p, target, "TAKE");
		}

	}

	public static void requestPrefixChange(final Player p, final Player target) {
		if (!p.hasPermission(Perms.PREFIX.p()))

			Common.tell(p, "&4You lack the proper permission.");
		else {
			if (methods.PrefixAsk.containsKey(p.getUniqueId())
					|| methods.PrefixAsk.containsValue(target.getUniqueId())) {

				Common.tell(p, "&4You aleady requested this.");
				return;
			}
			methods.PrefixAsk.put(p.getUniqueId(), target.getUniqueId());
			Common.tell(p, "&8ok, now reply with &c" + "'YES [prefix]'");
			methods.removeRequest(p, target, "PREFIX");

		}

	}

	public static void RequestGroupChange(final Player p, final Player target) {
		if (!p.hasPermission(Perms.GROUP.p()))
			Common.tell(p, "&4You lack the proper permission.");
		else {

			if (methods.GroupAsk.containsKey(p.getUniqueId()) || methods.GroupAsk.containsValue(target.getUniqueId())) {

				Common.tell(p, "&4You aleady requested this.");
				return;
			}
			methods.GroupAsk.put(p.getUniqueId(), target.getUniqueId());
			Common.tell(p, "&8ok, now reply with &c" + "'YES [group]'");
			methods.removeRequest(p, target, "GROUP");
		}

	}
}
