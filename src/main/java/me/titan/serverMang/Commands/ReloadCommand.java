package me.titan.serverMang.Commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.titan.lib.Common;
import me.titan.lib.CommandsLib.ChildCommand;
import me.titan.serverMang.ServerMang;
import me.titan.serverMang.config.Config;
import me.titan.serverMang.enums.Perms;
import me.titan.serverMang.messages.Messages;

public class ReloadCommand extends ChildCommand {

	public ReloadCommand() {
		super("reload", false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(String[] args, Player p, ConsoleCommandSender console) {
		if (!p.hasPermission(Perms.RELOAD.p())) {
			Common.tell(p, "&4You lack the proper permission.");
			return;
		}
		Config.init();
		Messages.init();
		ServerMang.getInstance().loadTasks();
		Common.tell(p, "&2Your configuration files have been reloaded!");

	}

	@Override
	public List<String> getAliases() {
		// TODO Auto-generated method stub
		return Arrays.asList("rl", "rel");
	}

}
