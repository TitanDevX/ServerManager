package me.titan.serverMang.menus;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.kangarko.compatbridge.model.CompMaterial;
import me.kangarko.ui.menu.Menu;
import me.kangarko.ui.menu.MenuButton;
import me.kangarko.ui.menu.menues.MenuStandard;
import me.kangarko.ui.model.ItemCreator;
import me.titan.lib.Common;
import me.titan.serverMang.enums.Perms;
import me.titan.serverMang.utils.methods;
import me.titan.serverMang.utils.methods.FillItem;

public class MainMenu extends MenuStandard {

	private final MenuButton Worlds;
	private final MenuButton Players;
	private final MenuButton empty;
	private final MenuButton Tasks;

	@Getter
	public static final String perm = Perms.MAIN_MENU.p();

	public MainMenu() {
		super(null);

		setSize(27);
		setTitle("What do you want to manage?");

		Worlds = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				if (!pl.hasPermission(WorldsMenu.getPerms())) {

					Common.tell(pl, "&4You lack the proper permission.");
					return;
				}
				new WorldsMenu().displayTo(pl);
				pl.playSound(pl.getLocation(), Sound.BLOCK_CHEST_OPEN, 30, 10);

				Common.tell(pl, "&5&lLET THE MAGIC HAPPEN!!");
			}

			@Override
			public ItemStack getItem() {
				// TODO Auto-generated method stub
				return ItemCreator.of(CompMaterial.COMMAND_BLOCK, "&5&lWorlds &b&lManager",
						"",
						"&8Opens the worlds manager menu.").build().make();
			}
		};
		Players = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				if (!pl.hasPermission(PlayersMenu.getPerms())) {

					Common.tell(pl, "&4You lack the proper permission.");
					return;
				}
				new PlayersMenu().displayTo(pl);
				pl.playSound(pl.getLocation(), Sound.BLOCK_CHEST_OPEN, 30, 10);
				Common.tell(pl, "&5&lLET THE MAGIC HAPPEN!!");

			}

			@Override
			public ItemStack getItem() {
				// TODO Auto-generated method stub
				return ItemCreator.of(CompMaterial.PLAYER_HEAD, "&c&lPlayers &b&lManager",
						"",
						"&8Opens the players manager menu.").data(3).build().make();
			}
		};
		empty = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {

				animateTitle("&cReview us!");

			}

			@Override
			public ItemStack getItem() {
				return methods.getFillItem(FillItem.BLACK_GLASS, "&7&lHuh?").build().make();
			}
		};
		Tasks = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				if (!pl.hasPermission(TasksMenu.getPerms())) {

					Common.tell(pl, "&4You lack the proper permission.");
					return;
				}
				new TasksMenu().displayTo(pl);

			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.BOOK, "&c&lTask &b&lManager",
						"",
						"&8Opens the tasks of this plugin manager menu.").build().make();
			}
		};

	}

	@Override
	protected ItemStack getItemAt(final int slot) {

		if (slot == 12)
			return Worlds.getItem();
		if (slot == 14)
			return Players.getItem();
		if (slot == 22)
			return Tasks.getItem();

		for (int i = 0; i < 28; i++)
			if (!this.formInventory().isSlotTaken(i))
				return empty.getItem();

		return null;

	}

	@Override
	protected String[] getInfo() {
		return new String[] {
				"",
				"This is the mainmenu of this plugin",
				"it Offers a Simple GUI", " to choose between", " &6&lPlayers &8and &c&lWorlds",
				"&8Managers."
		};
	}

}
