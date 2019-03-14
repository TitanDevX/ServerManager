package me.titan.serverMang.listeners;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.titan.serverMang.ServerMang;
import me.titan.serverMang.utils.ChatUtils;
import me.titan.serverMang.utils.PlayerMethods;
import me.titan.serverMang.utils.methods;

public class PlayerListener implements Listener {

	@EventHandler
	public void onJoin(final AsyncPlayerPreLoginEvent e) {
		final UUID id = e.getUniqueId();
		methods.Players.put(id, Bukkit.getServer().getOnlinePlayers().size());
		ServerMang.getCache(id);
	}

	@EventHandler
	public void onQuit(final PlayerQuitEvent e) {
		final UUID id = e.getPlayer().getUniqueId();
		methods.Players.remove(id);
	}

	// MESSAGE REGISTER
	@EventHandler
	public void onChat(final AsyncPlayerChatEvent e) {
		final Player p = e.getPlayer();
		final String message = e.getMessage();

		ChatUtils.write("chat-log.log", p.getName(), message);
		ChatUtils.writeLog("chat.json", p.getName(), message);

	}

	@EventHandler
	public void onTalk(final AsyncPlayerChatEvent e) {
		final Player p = e.getPlayer();
		final String msg = e.getMessage();
		Player target;
		World w;
		String reason;

		if (!msg.contains("YES"))
			return;
		if (methods.BanAsk.containsKey(p.getUniqueId())) {
			methods.check(p, methods.BanAsk, null);
			target = Bukkit.getPlayer(methods.BanAsk.get(p.getUniqueId()));
			reason = msg.replace("YES ", "");
			e.setCancelled(true);
			PlayerMethods.Ban(target, reason, p);
			methods.BanAsk.remove(p.getUniqueId());
		}
		if (methods.KickAsk.containsKey(p.getUniqueId())) {
			methods.check(p, methods.KickAsk, null);
			target = Bukkit.getPlayer(methods.KickAsk.get(p.getUniqueId()));
			reason = msg.replace("YES ", "");
			e.setCancelled(true);
			PlayerMethods.kick(target, reason, p);
			methods.KickAsk.remove(p.getUniqueId());
		}
		if (methods.GiveAsk.containsKey(p.getUniqueId())) {
			methods.check(p, methods.GiveAsk, null);
			target = Bukkit.getPlayer(methods.GiveAsk.get(p.getUniqueId()));
			reason = msg.replace("YES ", "");
			e.setCancelled(true);
			PlayerMethods.giveMoney(p, target, reason);
			methods.GiveAsk.remove(p.getUniqueId());
		}
		if (methods.TakeAsk.containsKey(p.getUniqueId())) {
			methods.check(p, methods.TakeAsk, null);
			target = Bukkit.getPlayer(methods.TakeAsk.get(p.getUniqueId()));
			reason = msg.replace("YES ", "");
			e.setCancelled(true);
			PlayerMethods.takeMoney(p, target, reason);
			methods.TakeAsk.remove(p.getUniqueId());
		}
		if (methods.TpAsk.containsKey(p.getUniqueId())) {
			methods.check(p, methods.TpAsk, null);
			target = Bukkit.getPlayer(methods.TpAsk.get(p.getUniqueId()));
			reason = msg.replace("YES ", "");
			e.setCancelled(true);
			PlayerMethods.tp(p, target, reason);
			methods.TpAsk.remove(p.getUniqueId());
		}
		if (methods.GroupAsk.containsKey(p.getUniqueId())) {
			methods.check(p, methods.GroupAsk, null);
			target = Bukkit.getPlayer(methods.GroupAsk.get(p.getUniqueId()));
			reason = msg.replace("YES ", "");
			e.setCancelled(true);
			PlayerMethods.ChangeGroup(p, target, reason);
			methods.GroupAsk.remove(p.getUniqueId());
		}
		if (methods.PrefixAsk.containsKey(p.getUniqueId())) {
			methods.check(p, methods.PrefixAsk, null);
			target = Bukkit.getPlayer(methods.PrefixAsk.get(p.getUniqueId()));
			reason = msg.replace("YES ", "");
			e.setCancelled(true);
			PlayerMethods.setPrefix(p, target, reason);
			methods.PrefixAsk.remove(p.getUniqueId());
		}
		if (methods.BrodAsk.containsKey(p.getUniqueId())) {
			methods.check(p, null, methods.BrodAsk);
			w = methods.BrodAsk.get(p.getUniqueId());
			reason = msg.replace("YES ", "");
			e.setCancelled(true);
			methods.brodcastMsg(p, w, reason);
			methods.BrodAsk.remove(p.getUniqueId());
		}

	}

}
