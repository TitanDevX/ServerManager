package me.titan.serverMang.config;

import me.titan.lib.Common;
import me.titan.lib.TitanConfig;

public class Config extends TitanConfig {

	public Config(final String fileName) {
		super(fileName);

		setHeader(new String[] {
				"----------------------------------------------------",
				"Your configuration file got automaticly updated",
				" ",
				"due to bukkit how saves .yml file",
				"all comments in your file were lost, Please open",
				"" + fileName + " dierctly to browse the default values.",
				"----------------------------------------------------"
		});
	}

	public static boolean ASK;
	public static String DEFAULT_REASON;
	public static int DEFAULT_MONEY;

	public void onLoad() {

		ASK = getBoolean("ask-before-red");
		DEFAULT_REASON = Common.colorize(getString("defult-reason"));
		DEFAULT_MONEY = getInt("default-money");

	}

	public static void init() {

		new Config("config.yml").onLoad();
	}

}
