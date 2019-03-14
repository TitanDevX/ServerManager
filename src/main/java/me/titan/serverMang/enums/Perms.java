package me.titan.serverMang.enums;

public enum Perms {

	PLAYER_MENU("sm.menus.playermenu"), MAIN_MENU("sm.menus.mainmenu"), PLAYERS_MENU("sm.menus.playersmenu"), TASKS_MENU("sm.menus.tasksmenu"), WORLD_MENU("sm.menus.worldmenu"), WORLDS_MENU("sm.menus.worldsmenu"), BAN("sm.ban"), KICK("sm.kick"), PARDON("sm.pardon"), GM_C("sm.gm.creative"), GM_S("sm.gm.survival"), GM_SP("sm.gm.spectator"), TPH("sm.tph"), TP("sm.tp"), GIVE("sm.deposit"), TAKE("sm.withdraw"), TPT("sm.tpt"), GROUP("sm.groupchange"), PREFIX("sm.prefixchange"), LOG("sm.chatlog"), BROADCAST("sm.world.broadcast"), RELOAD("sm.reload");

	String perm;

	Perms(String perm) {
		this.perm = perm;
	}

	public String p() {
		return perm;
	}

}
