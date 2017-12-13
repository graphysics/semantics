package semantics.java.utils;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtility {
	private static ObjectMapper mapper = new ObjectMapper();
	public static String toJson(Object o) throws JsonGenerationException, JsonMappingException, IOException{
		return mapper.writeValueAsString(o);
	}
}
