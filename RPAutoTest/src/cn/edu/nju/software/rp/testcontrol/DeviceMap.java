package cn.edu.nju.software.rp.testcontrol;

import java.util.HashMap;

public class DeviceMap {
	// Map<DeviceId, DeviceInfo>
	// DeviceInfo class has Phy IP and password
	
	public static HashMap<String, DeviceInfo> getMap(){
		DeviceInfo d1 = new DeviceInfo();
		d1.setIP("12.0.0.2");
		d1.setPassword("1234");
		DeviceInfo d2 = new DeviceInfo();
		d2.setIP("12.0.0.3");
		d2.setPassword("1234");
		DeviceInfo d3 = new DeviceInfo();
		d3.setIP("12.0.0.4");
		d3.setPassword("1234");
		HashMap<String, DeviceInfo> map = new HashMap<String, DeviceInfo>();
		map.put("R1", d1);
		map.put("R2", d2);
		map.put("R3", d3);
		return map;
	}

}
