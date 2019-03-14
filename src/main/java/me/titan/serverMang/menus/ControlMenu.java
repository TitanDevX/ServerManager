package me.titan.serverMang.menus;

import org.bukkit.BanList.Type;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.kangarko.compatbridge.model.CompDye;
import me.kangarko.compatbridge.model.CompMaterial;
import me.kangarko.ui.menu.Menu;
import me.kangarko.ui.menu.MenuButton;
import me.kangarko.ui.menu.menues.MenuStandard;
import me.kangarko.ui.model.ItemCreator;
import me.titan.serverMang.ServerMang;
import me.titan.serverMang.config.Config;
import me.titan.serverMang.enums.Perms;
import me.titan.serverMang.utils.PlayerMethods;
import me.titan.serverMang.utils.PrePlayerMethods;
import me.titan.serverMang.utils.methods;
import me.titan.serverMang.utils.methods.FillItem;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class ControlMenu extends MenuStandard {

	private final Economy econ;
	private final Permission perm;
	private final Chat chat;

	@Getter
	public static final String perms = Perms.PLAYER_MENU.p();

	Player target;

	// INFO ITEMS
	private final MenuButton Name, GM, Bal, Rank, loc, prefix, soon;
	// SETTINGS ITEMS
	private final MenuButton Ban, unBan, Kick, Creative, Survival, Spec, tph, tp, tpt;
	private final MenuButton Deposit, Withdraw, GROUP_CHANGE, PREFIX_CHANGE, log;

	// PLACES ITEMS
	private final MenuButton BlackPlace;

	public ControlMenu(final Player target) {
		super(new PlayersMenu());

		econ = ServerMang.getEconomy();
		perm = ServerMang.getPermissions();
		chat = ServerMang.getChat();

		this.target = target;

		setTitle("Contorl Panel of &6&l" + target.getName());
		setSize(54);

		Name = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle(target.getName());
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.STRING, "&6" + target.getName(),
						"",
						"&8Player's Name.").build().make();
			}
		};
		GM = new MenuButton() {
			String gm = target.getGameMode().toString().toLowerCase();

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle(gm);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.CHEST, "&6" + gm,
						"",
						"&8Player's game mode.").build().make();
			}
		};
		Bal = new MenuButton() {
			String bal = econ.getBalance(target) + "";

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle(bal);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.GOLD_INGOT, "&6" + bal,
						"",
						"&8Player's balance.").build().make();
			}
		};

		Rank = new MenuButton() {
			String Rank = perm.getPrimaryGroup(target);

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle(Rank);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.SNOWBALL, "&6" + Rank,
						"",
						"&8Player's rank.").build().make();
			}
		};

		loc = new MenuButton() {
			//=-=-=--==-=-=-=-=-=----="UNKNOWN CODE"=--=-=-=-=-=-==-=-==-==-=-=-=-=-=-=--=-
			int x = (int) target.getLocation().getX();
			int y = (int) target.getLocation().getY();
			int z = (int) target.getLocation().getZ();
			String w = target.getWorld().getName();
			//=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-
			String Loc = x + ", " + y + ", " + z + ", in World " + w;

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle(Loc);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.COMPASS, "&6" + Loc,
						"",
						"&8Player's location.").build().make();
			}
		};

		prefix = new MenuButton() {
			String PREFIX = chat.getPlayerPrefix(target);

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle(PREFIX);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.CLAY_BALL, "" + PREFIX,
						"",
						"&8Player's perfix.").build().make();
			}
		};
		soon = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle("&6&lMake sure to suggest us!!");
			}

			@Override
			public ItemStack getItem() {
				return methods.getFillItem(FillItem.SOON, "&7soon").build().make();
			}
		};

		Ban = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PrePlayerMethods.RequestBan(pl, target, Config.ASK, Config.DEFAULT_REASON);

			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.RED_WOOL, "&4&lBan", "", "&8Bans the player.",
						LEGEND_MESSAGES("reason")[0],
						LEGEND_MESSAGES("reason")[1],
						LEGEND_MESSAGES("reason")[2]).color(CompDye.RED).build()
						.make();
			}
		};
		unBan = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PlayerMethods.pardon(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.BLUE_WOOL, "&4&lPardon", "", "&8Unbans the player.")
						.color(CompDye.BLUE)
						.build().make();
			}
		};

		Kick = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PrePlayerMethods.RequestKick(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.GRAY_WOOL, "&7&lKick", "", "&8Kicks the player.",
						LEGEND_MESSAGES("reason")[0],
						LEGEND_MESSAGES("reason")[1],
						LEGEND_MESSAGES("reason")[2]).color(CompDye.GRAY).build()
						.make();
			}
		};

		Creative = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PlayerMethods.setCreative(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.EMERALD_BLOCK, "&6&lGameMode &c&lCreative", "",
						"&8Change this player GameMode to &cCreative.").build().make();
			}
		};
		Survival = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PlayerMethods.setSurvival(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.MAGENTA_WOOL, "&6&lGameMode &c&lSurvival", "",
						"&8Change this player GameMode to &cSurvival.")
						.color(CompDye.MAGENTA).build().make();
			}
		};
		Spec = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PlayerMethods.setSpec(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.ENDER_EYE, "&6&lGameMode &c&lSpectator", "",
						"&8Change this player GameMode to &cSpectator.").build().make();
			}
		};
		tph = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PlayerMethods.tpHere(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.YELLOW_WOOL, "&e&lTeleport to you", "",
						"&8Teleports this player to you location.")
						.color(CompDye.YELLOW).build().make();
			}
		};
		tpt = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PrePlayerMethods.RequestTp(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.PINK_WOOL, "&d&lTeleport to", "",
						"&8Teleports you tothis player location.",
						LEGEND_MESSAGES("location")[0],
						LEGEND_MESSAGES("location")[1],
						LEGEND_MESSAGES("location")[2]).color(CompDye.PINK).build().make();
			}
		};
		tp = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PlayerMethods.tpTo(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.CYAN_WOOL, "&c&lTeleport to a location", "",
						"&8Teleports this player to a location.")
						.color(CompDye.CYAN).build().make();
			}
		};
		Deposit = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PrePlayerMethods.requestDeposit(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.GOLD_BLOCK, "&6&lGive Some Money", "",
						"&8Gives this player some money.",
						LEGEND_MESSAGES("given amount")[0],
						LEGEND_MESSAGES("given amount")[1],
						LEGEND_MESSAGES("given amount")[2]).build().make();
			}
		};
		Withdraw = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PrePlayerMethods.requestWithdraw(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.BLACK_WOOL, "&6&lTake Some Money", "",
						"&8Takes some money from this player's balance",
						LEGEND_MESSAGES("taken amount")[0],
						LEGEND_MESSAGES("taken amount")[1],
						LEGEND_MESSAGES("taken amount")[2]).color(CompDye.BLACK).build().make();
			}
		};
		GROUP_CHANGE = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PrePlayerMethods.RequestGroupChange(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.NETHER_BRICK, "&6&lChange group", "",
						"&8Chnages this player's group",
						LEGEND_MESSAGES("group")[0],
						LEGEND_MESSAGES("group")[1],
						LEGEND_MESSAGES("group")[2]).build().make();
			}
		};
		PREFIX_CHANGE = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PrePlayerMethods.requestPrefixChange(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.ANVIL, "&c&lChange prefix", "",
						"&8Chnages this player's prefix",
						LEGEND_MESSAGES("prefix")[0],
						LEGEND_MESSAGES("prefix")[2]).build().make();
			}
		};
		log = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				PlayerMethods.sendChatLog(pl, target);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.PAPER, "&5&lChat Log", "",
						"&8Display chat log for &c" + target.getName() + " in the latest &bday")
						.build().make();
			}
		};

		BlackPlace = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle("Info about the player (:");
			}

			@Override
			public ItemStack getItem() {
				return methods.getFillItem(FillItem.BLACK_GLASS, "&8&lINFORMATION").build().make();
			}
		};

	}

	@Override
	protected ItemStack getItemAt(final int slot) {
		if (slot == 45)
			return Name.getItem();

		if (slot == 46)
			return GM.getItem();

		if (slot == 47)
			return Bal.getItem();
		if (slot == 48 && perm != null)
			return Rank.getItem();

		if (slot == 49)
			return loc.getItem();

		if (slot == 50)
			return prefix.getItem();

		if (slot == 8 && !Bukkit.getBanList(Type.IP).isBanned(target.getAddress().getHostName()))
			return Ban.getItem();

		if (slot == 27 && Bukkit.getBanList(Type.IP).isBanned(target.getAddress().getHostName()))
			return unBan.getItem();

		if (slot == 17 && target.isOnline())
			return Kick.getItem();

		if (slot == 18)
			return Creative.getItem();
		if (slot == 26)
			return Survival.getItem();
		if (slot == 13)
			return Spec.getItem();

		if (slot == 3)
			return tph.getItem();

		if (slot == 5)
			return tp.getItem();
		if (slot == 4)
			return tpt.getItem();
		if (slot == 9)
			return Deposit.getItem();
		if (slot == 35)
			return Withdraw.getItem();
		if (slot == 2)
			return GROUP_CHANGE.getItem();
		if (slot == 6 && chat != null)
			return PREFIX_CHANGE.getItem();
		if (slot == 22)
			return log.getItem();

		//=-=--=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-
		for (int i = 36; i <= 44; i++)
			if (slot == i)
				return BlackPlace.getItem();
		//=-=--=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-
		return null;
	}

	@Override
	protected String[] getInfo() {
		return null;
	}

	public String[] LEGEND_MESSAGES(final String key) {

		return new String[] {
				"&4You might need to specify the",
				"&4" + key + " in the chat after clicking.",
				"&cTo disable that go to config file->'ask-before-red'",
		};
	}

}
