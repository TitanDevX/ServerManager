package me.titan.serverMang.menus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.kangarko.ui.menu.menues.MenuPagged;
import me.titan.lib.Common;
import me.titan.serverMang.ServerMang;
import me.titan.serverMang.enums.Perms;
import me.titan.serverMang.utils.methods;

public class PlayersMenu extends MenuPagged<Player> {

	@Getter
	public static final String perms = Perms.PLAYERS_MENU.p();

	public PlayersMenu() {
		super(2 * 9, new MainMenu(), generatePlayers());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getMenuTitle() {

		return "Players in this server";
	}

	@Override
	protected ItemStack convertToItemStack(final Player p) {

		return methods.getHead(p);
	}

	@Override
	protected void onMenuClickPaged(final Player pl, final Player target, final ClickType click) {
		if (!pl.hasPermission(ControlMenu.getPerms())) {
			Common.tell(pl, "&4You lack the proper permission.");
			return;
		}
		new ControlMenu(target).displayTo(pl);

	}

	@Override
	protected String[] getInfo() {
		return new String[] {
				"",
				"Displays the players in this server",
				"&b&lRight Click to open control panel for this player!"

		};
	}

	private final static List<Player> generatePlayers() {
		final List<Player> ps = new ArrayList<>();

		for (final UUID id : ServerMang.playerCache.asMap().keySet()) {
			final Player p = Bukkit.getPlayer(id);
			ps.add(p);
		}

		return ps;

	}
}
