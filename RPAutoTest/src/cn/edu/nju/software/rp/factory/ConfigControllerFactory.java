package cn.edu.nju.software.rp.factory;

import cn.edu.nju.software.rp.testcontrol.ConfigController;

public class ConfigControllerFactory {
	public static ConfigController getJSONDataMapper() {
		return ConfigController.getInstance();
	}
}
