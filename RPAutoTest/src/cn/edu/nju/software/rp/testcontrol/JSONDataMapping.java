package cn.edu.nju.software.rp.testcontrol;

import java.util.ArrayList;

import cn.edu.nju.software.rp.model.Connection;
import cn.edu.nju.software.rp.model.Protocal;
import cn.edu.nju.software.rp.model.Router;
import cn.edu.nju.software.rp.model.Switch;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JSONDataMapping {
	
	public Protocal mapProtocal(JSONObject protocalInfo) {
		try {
			String type = protocalInfo.getString("type");
			
			JSONArray routerArray = protocalInfo.getJSONArray("routers");
			ArrayList<Router> routers = new ArrayList<Router>();					
			for(int i = 0; i < routerArray.size(); i++) {
                JSONObject routerObject = routerArray.getJSONObject(i);   
                Router router = new Router();
                router.setId(routerObject.getString("id"));
                router.setPhysicalIp(routerObject.getString("physicalIp"));
                router.setPassword(routerObject.getString("password"));
                
                ArrayList<Connection> conns = new ArrayList<Connection>();
                JSONArray connArray = routerObject.getJSONArray("connections");
                for(int j = 0; j < connArray.size(); j++) {
                	JSONObject connObject = connArray.getJSONObject(j);
                	Connection conn = new Connection(connObject.getString("port"), connObject.getString("ipAddress"), 
                			connObject.getString("submask"), connObject.getString("network"), connObject.getString("area"),
                			connObject.getString("target"));
                	conns.add(conn);
                }
                
                router.setConnections(conns);
                routers.add(router);
			}
			
			
			JSONArray switchArray = protocalInfo.getJSONArray("switches");
			ArrayList<Switch> switches = new ArrayList<Switch>();					
			for(int i = 0; i < switchArray.size(); i++) {
                JSONObject switchObject = switchArray.getJSONObject(i);   
                Switch _switch = new Switch();
                _switch.setPhysicalIp(switchObject.getString("physicalIp"));
                _switch.setPassword(switchObject.getString("password"));
                
                _switch.setId(switchObject.getString("id"));
                
                ArrayList<Connection> conns = new ArrayList<Connection>();
                JSONArray connArray = switchObject.getJSONArray("connections");
                for(int j = 0; j < connArray.size(); j++) {
                	JSONObject connObject = connArray.getJSONObject(j);
                	Connection conn = new Connection(connObject.getString("port"), connObject.getString("ipAddress"), 
                			connObject.getString("submask"), connObject.getString("network"), connObject.getString("area"),
                			connObject.getString("target"));
                	conns.add(conn);
                }
                
                _switch.setConnections(conns);
                switches.add(_switch);
			}						
			
			Protocal protocal = new Protocal(type, routers, switches);
			return protocal;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
