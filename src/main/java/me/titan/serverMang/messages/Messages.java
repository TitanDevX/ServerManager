package me.titan.serverMang.messages;

import java.util.List;

import me.titan.lib.TitanConfig;
import me.titan.serverMang.enums.Message;

public class Messages extends TitanConfig {

	public Messages() {
		super("messages.yml");
		// TODO Auto-generated constructor stub
	}

	public static String BAN;
	public static String KICK;
	public static String GIVE_P;
	public static String GIVE_TARGET;
	public static String PARDON;
	public static String CREATIVE_P;
	public static String CREATIVE_TARGET;
	public static String SURVIVAL_P;
	public static String SURVIVAL_TARGET;
	public static String SPEC_P;
	public static String SPEC_TARGET;
	public static String TPH_P;
	public static String TPH_TARGET;
	public static String TP_P;
	public static String TP_TARGET;
	public static String TPT_P;
	public static String TPT_TARGET;
	public static String TAKE_P;
	public static String TAKE_TARGET;
	public static String GROUP_P;
	public static String GROUP_TARGET;
	public static String PREFIX_P;
	public static String PREFIX_TARGET;

	private final void onLoad() {

		GIVE_P = getString("give-message-to-admin");
		GIVE_TARGET = getString("give-message-to-target");
		PARDON = getString("pardon");
		CREATIVE_P = getString("set-creative-to-admin");
		CREATIVE_TARGET = getString("set-creative-to-target");
		SURVIVAL_P = getString("set-survival-to-admin");
		SURVIVAL_TARGET = getString("set-survival-to-target");
		SPEC_P = getString("set-spectator-to-admin");
		SPEC_TARGET = getString("set-sepctator-to-target");
		TPH_P = getString("tp-here-to-admin");
		TPH_TARGET = getString("tp-here-to-target");
		TP_P = getString("tp-to-admin");
		TP_TARGET = getString("tp-to-target");
		TPT_P = getString("tpto-to-admin");
		TPT_TARGET = getString("tpto-to-target");
		TAKE_P = getString("take-to-admin");
		TAKE_TARGET = getString("take-to-target");
		GROUP_P = getString("groupchange-to-admin");
		GROUP_TARGET = getString("groupchange-to-target");
		PREFIX_P = getString("prefixchange-to-admin");
		PREFIX_TARGET = getString("prefixchange-to-target");
		BAN = getString("ban");
		KICK = getString("kick");

	}

	public static void init() {
		new Messages().onLoad();
	}

	public static String get(Message msg, List<PlaceHolder> pcs) {
		String result = null;

		switch (msg) {
			case BAN:
				result = BAN;
				break;
			case KICK:
				result = KICK;
				break;
			case CREATIVE_P:
				result = CREATIVE_P;
				break;
			case CREATIVE_TARGET:
				result = CREATIVE_TARGET;
				break;
			case SURVIVAL_P:
				result = SURVIVAL_P;
				break;
			case SURVIVAL_TARGET:
				result = SURVIVAL_TARGET;
				break;
			case GIVE_P:
				result = GIVE_P;
				break;
			case GIVE_TARGET:
				result = GIVE_TARGET;
				break;
			case PREFIX_TARGET:
				result = PREFIX_TARGET;
				break;
			case PARDON:
				result = PARDON;
				break;
			case PREFIX_P:
				result = PREFIX_P;
				break;
			case GROUP_P:
				result = GROUP_P;
				break;
			case GROUP_TARGET:
				result = GROUP_TARGET;
				break;
			case SPEC_P:
				result = SPEC_P;
				break;
			case SPEC_TARGET:
				result = SPEC_TARGET;
				break;

			case TAKE_P:
				result = TAKE_P;
				break;

			case TAKE_TARGET:
				result = TAKE_TARGET;
				break;
			case TP_P:
				result = TP_P;
				break;

			case TP_TARGET:
				result = TP_TARGET;
				break;
			case TPH_P:
				result = TPH_P;
				break;
			case TPH_TARGET:
				result = TP_TARGET;
				break;
			case TPT_P:
				result = TPT_P;
				break;
			case TPT_TARGET:
				result = TP_TARGET;
				break;
			default:
				result = "&4Unknown Message.";
				break;
		}

		for (PlaceHolder pc : pcs)
			if (result.contains(pc.getKey()))
				result = result.replace(pc.getKey(), pc.getValue());
		return result;

	}

}
