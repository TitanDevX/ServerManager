package me.titan.serverMang.Commands;

import java.util.Arrays;
import java.util.List;

import me.titan.lib.CommandsLib.ChildCommand;
import me.titan.lib.CommandsLib.ParentCommand;
import me.titan.lib.enums.CommandAccess;

public class ServerManagerCommand extends ParentCommand {

	public ServerManagerCommand() {
		super("ServerManager");
		setAliases(Arrays.asList("sm", "serverm", "servermang", "smang", "smanager"));
		setFlag(CommandAccess.PLAYERS);
	}

	@Override
	protected List<ChildCommand> getChilds() {
		// TODO Auto-generated method stub
		return Arrays.asList(new MainMenuCommand(), new PlayerMenuCommand(), new PlayersMenuCommand(),
				new WorldMenuCommand(), new WorldsMenuCommand(), new ReloadCommand());
	}

}
