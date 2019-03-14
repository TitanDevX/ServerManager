package me.titan.serverMang.Tasks;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.titan.serverMang.ServerMang;
import me.titan.serverMang.enums.TaskType;

@Getter
@Setter
@RequiredArgsConstructor
public class Task implements ConfigurationSerializable {

	public static Map<String, Task> Tasks = new HashMap<>();

	final String Id;
	final String Title;
	final String Message;
	final UUID Player;
	final UUID Target;
	final int ExtraInt;
	final String ExtraText;
	@NonNull
	long Time;
	final TaskType Type;

	public Task(Map<String, Object> map) {
		String Id = (String) map.get("Id");
		String Title = (String) map.get("Title");
		String Message = (String) map.get("Message");
		UUID Player = UUID.fromString((String) map.get("Player"));
		UUID Target = UUID.fromString((String) map.get("Target"));
		int ExtraInt = (int) map.get("ExtraInt");
		String ExtraText = (String) map.get("ExtraText");
		long Time = (long) map.get("Time");
		String tn = (String) map.get("Type");
		TaskType Type = TaskType.valueOf(tn.toUpperCase());

		this.ExtraInt = ExtraInt;
		this.ExtraText = ExtraText;
		this.Id = Id;
		this.Message = Message;
		this.Player = Player;
		this.Target = Target;
		this.Title = Title;
		this.Type = Type;
		this.setTime(Time);

	}

	public void save() {
		if (!ServerMang.getInstance().getTasks().isConfigurationSection("Tasks")
				&& ServerMang.getInstance().getTasks().getConfigurationSection("Tasks") == null)
			return;
		if (ServerMang.getInstance().getTasks().getConfigurationSection("Tasks").contains(this.getId())
				&& ServerMang.getInstance().getTasks().getConfigurationSection("Tasks").get(this.getId(), null) == this)
			return;
		ServerMang.getInstance().getTasks().set("Tasks." + this.getId(), this);
		ServerMang.getInstance().getTasks().saveConfig();

	}

	public void remove() {

		Tasks.remove(generateId());

	}

	String generateId() {

		return Target.toString() + "/" + Player.toString() + Title;
	}

	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<>();
		map.put("Id", Id);
		map.put("Title", Title);
		map.put("Message", "Message");
		map.put("Player", Player.toString());
		map.put("Target", Target.toString());
		map.put("ExtraInt", ExtraInt);
		map.put("ExtraText", ExtraText);
		map.put("Time", Time);
		map.put("Type", Type.toString().toLowerCase());

		return map;
	}

	public static Task deserialize(Map<String, Object> map) {

		String Id = (String) map.get("Id");
		String Title = (String) map.get("Title");
		String Message = (String) map.get("Message");
		UUID Player = UUID.fromString((String) map.get("Player"));
		UUID Target = UUID.fromString((String) map.get("Target"));
		int ExtraInt = (int) map.get("ExtraInt");
		String ExtraText = (String) map.get("ExtraText");
		long Time = (long) map.get("Time");
		String tn = (String) map.get("Type");
		TaskType Type = TaskType.valueOf(tn.toUpperCase());

		Task t = ServerMang.getInstance().registerTask(Title, Message, Type, Target, Player, ExtraInt, ExtraText);
		t.setTime(Time);
		return t;
	}

	public static Task valueOf(Map<String, Object> map) {

		String Id = (String) map.get("Id");
		String Title = (String) map.get("Title");
		String Message = (String) map.get("Message");
		UUID Player = UUID.fromString((String) map.get("Player"));
		UUID Target = UUID.fromString((String) map.get("Target"));
		int ExtraInt = (int) map.get("ExtraInt");
		String ExtraText = (String) map.get("ExtraText");
		long Time = (long) map.get("Time");
		String tn = (String) map.get("Type");
		TaskType Type = TaskType.valueOf(tn.toUpperCase());

		Task t = ServerMang.getInstance().registerTask(Title, Message, Type, Target, Player, ExtraInt, ExtraText);
		t.setTime(Time);
		return t;
	}

}
