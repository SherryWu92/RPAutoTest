package cn.edu.nju.software.rp.factory;

import cn.edu.nju.software.rp.testcontrol.JSONDataMapper;

public class JSONDataMapperFactory {
	
	public static JSONDataMapper getJSONDataMapper() {
		return JSONDataMapper.getInstance();
	}
}
