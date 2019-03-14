package me.titan.serverMang.messages;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlaceHolder {

	final String key;
	@Getter
	final String value;

	public String getKey() {
		return "{" + key + "}";

	}

	public static List<PlaceHolder> makeList(PlaceHolder... pcs) {
		return Arrays.asList(pcs);
	}

}
