package me.titan.serverMang.cache;

import java.util.UUID;

import me.titan.lib.TitanConfig;

public class PlayerCache {

	private boolean isOnline;

	private final TitanConfig cfg;

	public PlayerCache(final UUID id) {
		cfg = new TitanConfig("Cache.dat", false);
		cfg.setPathPrefix(id.toString());
		onLoad();

	}

	private void onLoad() {
		isOnline = cfg.getBoolean("is-online", false);

	}

	public void save() {

		cfg.set("is-online", isOnline);
		cfg.saveConfig();
	}

	public boolean isOnline() {
		return isOnline;
	}
}
