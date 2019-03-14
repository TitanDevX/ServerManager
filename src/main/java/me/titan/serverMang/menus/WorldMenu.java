package me.titan.serverMang.menus;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
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
import me.titan.serverMang.enums.Perms;
import me.titan.serverMang.utils.methods;
import me.titan.serverMang.utils.methods.FillItem;

public class WorldMenu extends MenuStandard {

	private final MenuButton Time, Diff, Players;

	private final MenuButton KillEntity, brodcast;

	// PLACES ITEMS
	private final MenuButton RedPlace, GreenPlace, BlackPlace;

	@Getter
	public static final String perm = Perms.WORLD_MENU.p();

	public WorldMenu(final World world) {
		super(new WorldsMenu());
		setSize(54);
		setTitle(world.getName() + "'s Control Panel");

		Time = new MenuButton() {

			int timeInt = (int) (world.getTime() / 1000);
			String time = timeInt > 12 ? timeInt + "pm" : timeInt + "am";

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle(time);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.CLOCK, "&6" + time,
						"",
						"&8" + world.getName() + " time.").build().make();
			}
		};
		Diff = new MenuButton() {
			String diff = world.getDifficulty().toString();

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle(diff);
			}

			@Override
			public ItemStack getItem() {
				if (diff.equals(Difficulty.HARD.toString()))
					return ItemCreator.of(CompMaterial.RED_WOOL, "&6" + diff,
							"",
							"&8" + world.getName() + " Difficulty.").color(CompDye.RED).build().make();
				if (diff.equals(Difficulty.EASY.toString()))
					return ItemCreator.of(CompMaterial.BLUE_WOOL, "&6" + diff,
							"",
							"&8" + world.getName() + " Difficulty.").color(CompDye.BLUE).build().make();

				if (diff.equals(Difficulty.NORMAL.toString()))
					return ItemCreator.of(CompMaterial.GRAY_WOOL, "&6" + diff,
							"",
							"&8" + world.getName() + " Difficulty.").color(CompDye.GRAY).build().make();

				if (diff.equals(Difficulty.PEACEFUL.toString()))
					return ItemCreator.of(CompMaterial.LIGHT_GRAY_WOOL, "&6" + diff,
							"",
							"&8" + world.getName() + " Difficulty.").color(CompDye.LIGHT_BLUE).build().make();

				return null;
			}

		};
		Players = new MenuButton() {
			String players = world.getPlayers().size() + "";

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle(players);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.PLAYER_HEAD, "&6" + players,

						"",
						"&8" + world.getName() + " players.").data(5).build().make();
			}
		};
		KillEntity = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {

				for (final Entity e : world.getEntities())
					if (e.getType() != EntityType.PLAYER)
						e.remove();

			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.DIAMOND_SWORD, "&cKill entities",
						"",
						"&8Kill entities in " + world.getName()).build().make();
			}
		};
		brodcast = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				methods.putInBrodcast(pl, world);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.PAPER, "&6&lBrodcast Message",
						"",
						"&8Brodcast a message ONLY in " + world.getName()).build().make();
			}
		};
		RedPlace = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle("DONT CLICK THEM!");
			}

			@Override
			public ItemStack getItem() {
				return methods.getFillItem(FillItem.RED_GLASS, "&4&lNIGATIVE SECTION").build().make();

			}
		};
		BlackPlace = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle("NVM");
			}

			@Override
			public ItemStack getItem() {
				return methods.getFillItem(FillItem.BLACK_GLASS, "&8&lNORMAL SECTION").build().make();
			}
		};
		GreenPlace = new MenuButton() {

			@Override
			public void onClickedInMenu(final Player pl, final Menu menu, final ClickType click) {
				animateTitle("CLICK ON IT (:");
			}

			@Override
			public ItemStack getItem() {
				return methods.getFillItem(FillItem.GREEN_GLASS, "&2&lPOSITIVE SECTION").build().make();
			}
		};
	}

	@Override
	public ItemStack getItemAt(final int slot) {

		if (slot == 45)
			return Time.getItem();
		if (slot == 46)
			return Diff.getItem();
		if (slot == 47)
			return Players.getItem();
		if (slot == 8)
			return KillEntity.getItem();
		if (slot == 4)
			return brodcast.getItem();

		if (slot == 44)
			return RedPlace.getItem();
		for (int i = 7; i < 52; i = i + 9)
			if (slot == i)
				return RedPlace.getItem();
		//=-=--=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-
		for (int i = 2; i < 47; i = i + 9)
			if (slot == i)
				return BlackPlace.getItem();
		for (int i = 6; i < 51; i += 9)
			if (slot == i)
				return BlackPlace.getItem();
		for (int i = 38; i < 43; i++)
			if (slot == i)
				return BlackPlace.getItem();
		//=-=--=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-
		if (slot == 36)
			return GreenPlace.getItem();
		for (int i = 1; i < 46; i = i + 9)
			if (slot == i)
				return GreenPlace.getItem();

		return null;

	}

	@Override
	protected String[] getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

}
