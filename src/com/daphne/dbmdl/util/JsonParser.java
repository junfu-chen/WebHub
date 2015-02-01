package com.daphne.dbmdl.util;

import java.io.IOException;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonParser {
	private static JsonParser instance=null;
	private ObjectMapper mapper = new ObjectMapper();
	public static JsonParser getInstance() {
		if (instance == null) {
			instance = new JsonParser();
		}
		return instance;
	}
	/**
	 * 此方法描述的是： map 转 json
	 * 
	 * @author: junfu.chen@mail.daphne.com.cn
	 * @version: 2014年12月23日 下午2:28:25
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */

	public String toJson(Map<String, Object> o) throws JsonGenerationException, JsonMappingException, IOException {

		String resultStr = "";

		resultStr = mapper.writeValueAsString(o);

		return resultStr;
	}
	
}
