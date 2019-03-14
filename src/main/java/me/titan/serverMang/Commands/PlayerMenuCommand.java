package me.titan.serverMang.Commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.titan.lib.Common;
import me.titan.lib.CommandsLib.ChildCommand;
import me.titan.serverMang.menus.ControlMenu;

public class PlayerMenuCommand extends ChildCommand {

	public PlayerMenuCommand() {
		super("playermenu", false);
	}

	@Override
	public void run(String[] args, Player pl, ConsoleCommandSender console) {

		if (!pl.hasPermission(ControlMenu.getPerms())) {
			Common.tell(pl, "&4You lack the proper permission.");
			return;
		}
		if (args.length < 2) {
			Common.tell(pl, "&4You must specify the player.");
			return;

		}
		Player p = ServerManagerCommand.getPlayer(args[1]);
		new ControlMenu(p).displayTo(pl);

	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("pm", "pmenu", "plmenu", "playermanager", "pmang");
	}

}
