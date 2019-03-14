package me.titan.serverMang.menus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import me.kangarko.compatbridge.model.CompMaterial;
import me.kangarko.ui.menu.menues.MenuPagged;
import me.kangarko.ui.model.ItemCreator;
import me.titan.serverMang.Tasks.Task;
import me.titan.serverMang.enums.Perms;
import me.titan.serverMang.utils.methods;

public class TasksMenu extends MenuPagged<Task> {

	@Getter
	public static final String perms = Perms.TASKS_MENU.p();

	protected TasksMenu() {
		super(27, new MainMenu(), true, getTasks());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String getMenuTitle() {
		// TODO Auto-generated method stub
		return "Latest Tasks";
	}

	@Override
	protected ItemStack convertToItemStack(Task task) {
		// TODO Auto-generated method stub
		return ItemCreator.of(CompMaterial.PAPER, task.getTitle(), task.getMessage()).build().make();
	}

	@Override
	protected void onMenuClickPaged(Player pl, Task task, ClickType click) {

		methods.cancelTask(task);
	}

	@Override
	protected String[] getInfo() {
		// TODO Auto-generated method stub
		return new String[] {
				"Click on a task",
				"to undo it...",
				"&4&lWARNING: Please Go easy on this",
				"&8menu as it's not very stable for now."

		};
	}

	private final static List<Task> getTasks() {
		List<Task> tasks = new ArrayList<>();
		tasks.addAll(Task.Tasks.values());
		return tasks;

	}

}
