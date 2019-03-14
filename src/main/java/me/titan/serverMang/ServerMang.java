package me.titan.serverMang;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.Getter;
import me.kangarko.ui.UIDesignerAPI;
import me.titan.lib.Common;
import me.titan.lib.TitanConfig;
import me.titan.lib.TitanLib;
import me.titan.serverMang.Commands.ServerManagerCommand;
import me.titan.serverMang.Tasks.Task;
import me.titan.serverMang.cache.PlayerCache;
import me.titan.serverMang.config.Config;
import me.titan.serverMang.enums.TaskType;
import me.titan.serverMang.listeners.PlayerListener;
import me.titan.serverMang.messages.Messages;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

@Getter
public class ServerMang extends JavaPlugin {

	/*
	 *
	 * SERVER MANAGER | v2.2
	 *
	 * this might not be the same version released on spigot page!
	 *
	 *  READ ME !!
	 *
	 * if you use any of my this plugin's code, please make sure to credit me!
	 * By putting a link in your plugin's page directly to my profile.
	 * Or by just putting my name in the page.
	 *
	 *  !!!!!!!!!!!!!!!!!
	 *
	 */

	static {
		ConfigurationSerialization.registerClass(Task.class);
	}

	private static ServerMang instance;
	TitanConfig cfg;
	private static Economy econ = null;
	private static Permission perms = null;
	private static Chat chat = null;
	private static final Logger log = Logger.getLogger("Minecraft");

	TitanConfig tasks;

	//public static List<Task> Tasks = new ArrayList<>();

	public static final Cache<UUID, PlayerCache> playerCache = CacheBuilder.newBuilder()
			.maximumSize(10_000)
			.expireAfterWrite(10, TimeUnit.MINUTES)
			.build();
	//	public static final Cache<String, TaskCache> taskCache = CacheBuilder.newBuilder()
	//			.maximumSize(10_000)
	//			.expireAfterWrite(10, TimeUnit.MINUTES)
	//			.build();

	@Override
	public void onEnable() {
		// ============== IMPORTANT =================================
		instance = this;
		TitanLib.setPlugin(this);
		TitanLib.setShowName(true);
		Common.log("gettings things ready...");

		Config.init();
		Messages.init();
		tasks = new TitanConfig("tasks.yml", false);

		if (!setupEconomy()) {
			log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		setupPermissions();
		setupChat();
		UIDesignerAPI.setPlugin(this);
		// ==================================================================

		//		for (String id : getTasksIds("tasksId.dat"))
		//			getTaskCache(id);

		for (Player p : Bukkit.getOnlinePlayers())
			getCache(p.getUniqueId());
		this.loadTasks();
		registerListeners();
		registerCommands();
		tellLogMessages();

	}

	@Override
	public void onDisable() {
		for (final PlayerCache cache : playerCache.asMap().values())
			cache.save();

		playerCache.invalidateAll();
		instance = null;

	}

	public static ServerMang getInstance() {

		return instance;
	}

	private final void registerConfig() {
		Config.init();
	}

	private final void registerCache() {

		for (final Player p : getServer().getOnlinePlayers())
			getCache(p.getUniqueId());

	}

	private final void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

	}

	private final void tellLogMessages() {

		Common.log("&2Loaded Events!");

		Common.log("&6IM READY TO USE!");
		Common.log("&6Making the dinner for you..");

	}

	private final void registerCommands() {
		Common.registerCommand(new ServerManagerCommand());

		Common.log("&2loaded Commands!");

	}

	private boolean setupEconomy() {
		if (getServer().getPluginManager().getPlugin("Vault") == null)
			return false;
		final RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null)
			return false;
		econ = rsp.getProvider();
		return econ != null;
	}

	private boolean setupPermissions() {
		final RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager()
				.getRegistration(Permission.class);
		perms = rsp.getProvider();
		return perms != null;
	}

	private boolean setupChat() {
		final RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
		chat = rsp.getProvider();
		return chat != null;
	}

	public static Economy getEconomy() {
		return econ;
	}

	public static Permission getPermissions() {
		return perms;
	}

	public static Chat getChat() {
		return chat;
	}

	public static PlayerCache getCache(final UUID id) {
		PlayerCache cache = playerCache.getIfPresent(id);
		if (cache == null) {

			cache = new PlayerCache(id);
			playerCache.put(id, cache);

		}
		return cache;
	}

	public void loadTasks() {
		if (tasks.isConfigurationSection("Tasks") && tasks.getConfigurationSection("Tasks") != null)
			for (String t : tasks.getConfigurationSection("Tasks").getKeys(false)) {

				Task task = (Task) tasks.get("Tasks." + t);
				this.registerTask(task.getTitle(), task.getMessage(), task.getType(), task.getTarget(),
						task.getPlayer(),
						task.getExtraInt(), task.getExtraText());

			}
	}

	public Task registerTask(String Title, String Message, TaskType Type, UUID Target, UUID Player, int ExtraInt,
			String ExtraText) {
		String id = Target.toString() + "/" + Player.toString() + Title;
		Task t = new Task(id,
				Title, Message, Player, Target, ExtraInt, ExtraText, System.currentTimeMillis(), Type);
		Task.Tasks.put(id, t);

		t.save();

		return t;
	}
}
