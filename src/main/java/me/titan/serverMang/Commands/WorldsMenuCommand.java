package me.titan.serverMang.Commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.kangarko.compatbridge.model.CompSound;
import me.titan.lib.Common;
import me.titan.lib.CommandsLib.ChildCommand;
import me.titan.serverMang.menus.WorldsMenu;
import me.titan.serverMang.utils.methods;

public class WorldsMenuCommand extends ChildCommand {

	public WorldsMenuCommand() {
		super("worldsmenu", false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(String[] args, Player pl, ConsoleCommandSender console) {
		if (!pl.hasPermission(WorldsMenu.getPerms())) {

			Common.tell(pl, "&4You lack the proper permission.");
			return;
		}
		Common.tell(pl, "&8[&5&lServer&6lManager&8] &4Make sure to donate and/or review us!");
		methods.playSoundFor(pl, CompSound.BLOCK_NOTE_HARP);
		new WorldsMenu().displayTo(pl);

	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("wsm", "worldsm", "wsmenu");
	}

}
