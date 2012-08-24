package com.worksapplications.neil.framework;

import java.util.HashMap;

public class URLMappingService {

	static HashMap<String,String> map = new HashMap<String,String>();

	public HashMap<String,String> getMap() {
		return map;
	}

	public void setMap(HashMap<String,String> map) {
		URLMappingService.map = map;
	}
	
	/*
	 * if map contains the key, return corresponding value
	 * if map does not contain the key, returns the default value.
	 */
	public String getURLValue(String key){		
		return map.get(key);	
	}
	
	
	
	
}
