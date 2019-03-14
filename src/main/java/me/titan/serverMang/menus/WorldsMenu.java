package me.titan.serverMang.menus;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.kangarko.compatbridge.model.CompMaterial;
import me.kangarko.ui.menu.menues.MenuPagged;
import me.kangarko.ui.model.ItemCreator;
import me.titan.lib.Common;
import me.titan.serverMang.enums.Perms;

public class WorldsMenu extends MenuPagged<World> {

	@Getter
	public static final String perms = Perms.WORLDS_MENU.p();

	public WorldsMenu() {
		super(9 * 2, new MainMenu(), false, Bukkit.getWorlds());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getMenuTitle() {
		// TODO Auto-generated method stub
		return "Worlds In This Server";
	}

	@Override
	protected ItemStack convertToItemStack(final org.bukkit.World world) {
		// TODO Auto-generated method stub
		return ItemCreator.of(CompMaterial.MAP, "&6" + world.getName(), "",
				"&b&lOpens this world menu.").build().make();
	}

	@Override
	protected void onMenuClickPaged(final Player pl, final org.bukkit.World object, final ClickType click) {
		if (!pl.hasPermission(WorldMenu.getPerm())) {
			Common.tell(pl, "&4You lack the proper permission");
			return;
		}
		new WorldMenu(object).displayTo(pl);

	}

	@Override
	protected String[] getInfo() {
		// TODO Auto-generated method stub
		return new String[] {

				"",
				"This menu displays the worlds",
				"in this server",
				"&c&lRight click to open cotrol panel menu for this world."
		};
	}

}
