package me.titan.serverMang.Commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.titan.lib.Common;
import me.titan.lib.CommandsLib.ChildCommand;
import me.titan.serverMang.menus.WorldMenu;

public class WorldMenuCommand extends ChildCommand {

	public WorldMenuCommand() {
		super("worldmenu", false);
	}

	@Override
	public void run(String[] args, Player pl, ConsoleCommandSender console) {

		if (!pl.hasPermission(WorldMenu.getPerm())) {
			Common.tell(pl, "&4You lack the proper permission");
			return;
		}
		if (args.length < 2) {
			Common.tell(pl, "&4You must specify the world.");
			return;

		}
		World w = ServerManagerCommand.getWorld(args[1]);
		new WorldMenu(w).displayTo(pl);

	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("wm", "worldmanager", "wmanager", "wmenu", "worldm", "world");
	}

}
