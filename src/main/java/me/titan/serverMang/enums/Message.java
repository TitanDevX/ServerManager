package me.titan.serverMang.enums;

public enum Message {

	BAN("&7You have banned &c{target} &7For {reason}"),
	KICK("&7You have kicked &c{target} &7For {reason}"),
	GIVE_P("&8You gave &c{taget} {amount} &8Now he have &c{balance}"),
	GIVE_TARGET( "&8You were given &c{amount} &8By &c{admin} &8Now have &c{balance}"),
	PARDON("&c{target} &8Has been unBanned!"),
	CREATIVE_P("&8you have changed &c{target}&8's Gamemode to &cCreative."),
	CREATIVE_TARGET("&8Your game mode has changed to &cCreative &8By &c{admin}"),
	SURVIVAL_P("&8You have changed &c{target}&8's Gamemode to &cSurvival."),
	SURVIVAL_TARGET( "&8Your game mode has changed to &cSurvival &8By &c{admin}"),
	SPEC_P("&8You have changed &c{target}&8's Gamemode to &cSpectator."),
	SPEC_TARGET("&8Your game mode has changed to &cSpectator &8By &c{admin}"),
	TPH_P("&8Say welcome for &c{target}&8!"),
	TPH_TARGET("&c{admin} &8Has teleported you to his location."),
	TP_P("&8You teleported &c{target} &8To &c{location}"),
	TP_TARGET("&c{admin} &8has teleported you to this location, enjoy it!"),
	TPT_P("&8You have teleported to &c{target}&8's location."),
	TPT_TARGET("&c{admin} &8has teleported to your location, say welcome!"),
	TAKE_P("&8You take &c{amount} &8from &c{target}, &8Now he have &c{balance}"),
	TAKE_TARGET("&8You were Taken &c{amount} &8By &c{admin} &8Now have &c{balance}"), 
	GROUP_P("&8You added &c{target} &8to group &c{group}"), 
	GROUP_TARGET("&c{admin} &8has added you to group &c{group}"),
	PREFIX_P("&8You changed &c{target}&8's group prefix to &c{prefix}"),
	PREFIX_TARGET("&8Your group prefix has got changed to &c{prefix} &8By &c{prefix}");
	
	public String msg;
	
	Message(String msg) {
		
		this.msg = msg;
	}
	
	public String get() {
		return this.msg;
	}

}
