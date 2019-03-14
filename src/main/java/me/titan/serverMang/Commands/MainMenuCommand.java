package me.titan.serverMang.Commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.titan.lib.Common;
import me.titan.lib.CommandsLib.ChildCommand;
import me.titan.serverMang.menus.MainMenu;

public class MainMenuCommand extends ChildCommand {

	public MainMenuCommand() {
		super("MainMenu", false);

	}

	@Override
	public void run(String[] args, Player pl, ConsoleCommandSender console) {

		if (!pl.hasPermission(MainMenu.getPerm())) {
			Common.tell(pl, "&4You lack the proper permission");
			return;

		}

		new MainMenu().displayTo(pl);
	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("mm", "mainm", "smmenu", "servermanagermenu", "servermmenu");
	}

}
