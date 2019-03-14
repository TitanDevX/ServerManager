package me.titan.serverMang.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import me.titan.serverMang.LogMessage;
import me.titan.serverMang.ServerMang;

@SuppressWarnings("unchecked")
public class ChatUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm" /*dd.MM.YYYY*/);
	private static JSONParser parser = new JSONParser();

	public static String getDate(final long time) {
		return sdf.format(new Date(time));
	}

	public static void writeLog(final String path, final String playerName, final String message) {
		final File file = new File(ServerMang.getInstance().getDataFolder(), path);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

			final LogMessage lm = new LogMessage(playerName, System.currentTimeMillis(), message);
			final JSONObject json = new JSONObject(lm.serialize());

			bw.write(json.toJSONString());
			bw.write(System.lineSeparator());

		} catch (final IOException ex) {
			ex.printStackTrace();
		}
	}

	public static List<LogMessage> readLogs(final String path) {
		final List<String> rawLines = new ArrayList<>();
		final List<LogMessage> logs = new ArrayList<>();
		final File file = new File(ServerMang.getInstance().getDataFolder(), path);

		if (file.exists())
			try {
				rawLines.addAll(Files.readAllLines(Paths.get(file.toURI())));

			} catch (final IOException e) {
				e.printStackTrace();
			}

		for (final String line : rawLines)
			try {
				final JSONObject json = (JSONObject) parser.parse(line);

				logs.add(LogMessage.deserialize(json));

			} catch (final ParseException e) {
				e.printStackTrace();
			}

		return logs;
	}

	public static void write(final String path, final String playerName, final String message) {
		final File file = new File(ServerMang.getInstance().getDataFolder(), path);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
			bw.write("[" + sdf.format(new Date()) + "] <" + playerName + "> " + message);
			bw.write(System.lineSeparator());

		} catch (final IOException ex) {
			ex.printStackTrace();
		}
	}
}
