package co.microservicios.util;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

public class GSonUtils {
	

	public static String serialize(Object src) {
		Gson gson = new Gson();
		return gson.toJson(src);
	}
	
	public static <T> T toObject(String src, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(src,clazz);
	}

	public static <T> T toObject(Object src, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(serialize(src),clazz);
	}
	
	   public static <T> List<T>  toList(String src, Class<T> clazz) {
			Gson gson = new Gson();
			 Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
			return gson.fromJson(src,typeOfT);
		}
	
	/**
	* Convierte un objeto a json
	* @author oscaesgo
	*
	*/
	public static Map<String, String> deserializeFlat(String json) {
		
		GSonUtils gsonUtils = new GSonUtils();
		Type t = new TypeToken<Map<String, String>>(){}.getType();
		
		Gson gson = new GsonBuilder()
						.registerTypeAdapter(t , gsonUtils.new FlattenDeserializer())
						.setPrettyPrinting()
						.create();
		
		Map<String, String> map = gson.fromJson(json, t);
		
		return map;
		
	}
	
	
	/**
	* Transforma un json en un cojunto de claves y valores. Objetivo indexar valores
	* @author oscaesgo
	*
	*/
	public class FlattenDeserializer implements JsonDeserializer<Map<String, String>> {

		@Override
		public Map<String, String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			Map<String, String> map = new HashMap<>();
			if (json.isJsonArray()) {
				int i= 0;
				for (JsonElement e : json.getAsJsonArray()) {
					map.putAll(deserialize(e, typeOfT, context));
					i++;
				}
			} else if (json.isJsonObject()) {
				for (Map.Entry<String, JsonElement> entry : json.getAsJsonObject().entrySet()) {
					if (entry.getValue().isJsonPrimitive()) {
						map.put(entry.getKey(), entry.getValue().getAsString());
					} else {
						map.putAll(deserialize(entry.getValue(), typeOfT, context));
					}
				}
			}
			
			return map;
	
		}
	}
	

}



