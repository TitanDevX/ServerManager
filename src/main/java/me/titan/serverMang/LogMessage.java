package me.titan.serverMang;

import java.util.HashMap;
import java.util.Map;

public class LogMessage {

	private final String player;
	private final long time;
	private final String message;

	public LogMessage(final String player, final long time, final String message) {

		this.player = player;
		this.time = time;
		this.message = message;

	}

	public String getPlayer() {
		return player;
	}

	public long getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	public Map<Object, Object> serialize() {
		final Map<Object, Object> map = new HashMap<>();

		map.put("player", player);
		map.put("time", time);
		map.put("message", message);

		return map;

	}

	public static LogMessage deserialize(final Map<Object, Object> map) {
		final String player = (String) map.get("player");
		final long time = (long) map.get("time");
		final String message = (String) map.get("message");

		return new LogMessage(player, time, message);

	}
}
