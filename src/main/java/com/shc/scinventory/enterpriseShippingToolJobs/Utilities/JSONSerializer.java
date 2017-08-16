package com.shc.scinventory.enterpriseShippingToolJobs.Utilities;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JSONSerializer {
	private static Gson gson = null;
	
	private static Gson getGson() {
		if(gson == null) {
			gson = new Gson();
		}
		return gson;
	}
	
	public static <T> String serialize(T t) {
		return getGson().toJson(t);
	}
	
	public static <T> T deserialize (String json, Class<T> cls) {
		return getGson().fromJson(json, cls);
	}

	public static <T> T deserialize (String json, Type typeToken) {
		return getGson().fromJson(json, typeToken);
	}

}
