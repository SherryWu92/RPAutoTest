package cn.edu.nju.software.rp.testcontrol;

import java.util.ArrayList;
import java.util.HashMap;

import cn.edu.nju.software.rp.model.Protocal;
import cn.edu.nju.software.rp.model.Router;
import cn.edu.nju.software.rp.model.Switch;

public class DeviceMap {
	// Map<DeviceId, DeviceInfo>
	// DeviceInfo class has Phy IP and password
	
	HashMap<String, DeviceInfo> map = new HashMap<String, DeviceInfo>();
	
	public void setMap(Protocal p){
		ArrayList<Router> routers = p.getRouters();
		ArrayList<Switch> switches = p.getSwitches();
		for (int i_router=0; i_router < routers.size(); i_router++){
			String ip = routers.get(i_router).getPhysicalIp();
			String password = routers.get(i_router).getPassword();
			DeviceInfo info = new DeviceInfo();
			info.setIP(ip);
			info.setPassword(password);
			this.map.put(routers.get(i_router).getId(), info);
		}
		for (int i_switch=0; i_switch < switches.size(); i_switch++){
			String ip = switches.get(i_switch).getPhysicalIp();
			String password = switches.get(i_switch).getPassword();
			DeviceInfo info = new DeviceInfo();
			info.setIP(ip);
			info.setPassword(password);
			this.map.put(switches.get(i_switch).getId(), info);
		}
	}
	
	public HashMap<String, DeviceInfo> getMap(){
		return this.map;
	}

}
