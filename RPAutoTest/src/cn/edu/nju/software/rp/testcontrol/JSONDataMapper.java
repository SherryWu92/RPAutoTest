package cn.edu.nju.software.rp.testcontrol;

import java.util.ArrayList;

import cn.edu.nju.software.rp.model.Connection;
import cn.edu.nju.software.rp.model.Protocal;
import cn.edu.nju.software.rp.model.Router;
import cn.edu.nju.software.rp.model.Switch;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JSONDataMapper {
	
	private static JSONDataMapper jsonDataMapper = new JSONDataMapper();
	
	private JSONDataMapper() {
		
	}
	
	public static JSONDataMapper getInstance(){
		return jsonDataMapper;
	}
	
	public Protocal mapToProtocal(JSONObject protocalInfo) {
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
	
	public JSONObject mapToJSON(Protocal protocal) {
		JSONObject protocalInfo = new JSONObject(); 
		protocalInfo.put("type", protocal.getType());
		
		JSONArray routers = new JSONArray();		
		
		ArrayList<Router> routerArr = protocal.getRouters();
		for(int i = 0; i < routerArr.size(); i++) {
			Router r = routerArr.get(i);
			JSONObject router = new JSONObject();
			router.put("id", r.getId());
			String physicalIp = r.getPhysicalIp() != null ? r.getPhysicalIp() : "";
			router.put("physicalIp", physicalIp);
			String password = r.getPassword() != null ? r.getPassword() : "";
			router.put("password", password);
			
			ArrayList<Connection> conns = r.getConnections();
			JSONArray connections = new JSONArray();
			for(int j = 0; j < conns.size(); j++) {
				Connection conn = conns.get(j);
				JSONObject connection = new JSONObject();
				connection.put("port", conn.getPort());
				connection.put("ipAddress", conn.getIpAddress());
				connection.put("submask", conn.getSubmask());
				connection.put("network", conn.getNetwork());
				String area = conn.getArea() != null ? conn.getArea() : "";
				connection.put("area", area);
				connection.put("target", conn.getTarget());	
				
				connections.add(connection);
			}
			router.put("connections", connections);			
			routers.add(router);
		}
		protocalInfo.put("routers", routers);
		
		
		JSONArray switches = new JSONArray();		
		
		ArrayList<Switch> switchArr = protocal.getSwitches();
		for(int i = 0; i < switchArr.size(); i++) {
			Switch sw = switchArr.get(i);
			JSONObject _switch = new JSONObject();
			_switch.put("id", sw.getId());
			String physicalIp = sw.getPhysicalIp() != null ? sw.getPhysicalIp() : "";
			_switch.put("physicalIp", physicalIp);
			String password = sw.getPassword() != null ? sw.getPassword() : "";
			_switch.put("password", password);
			
			ArrayList<Connection> conns = sw.getConnections();
			JSONArray connections = new JSONArray();
			for(int j = 0; j < conns.size(); j++) {
				Connection conn = conns.get(j);
				JSONObject connection = new JSONObject();
				connection.put("port", conn.getPort());
				connection.put("ipAddress", conn.getIpAddress());
				connection.put("submask", conn.getSubmask());
				connection.put("network", conn.getNetwork());
				String area = conn.getArea() != null ? conn.getArea() : "";
				connection.put("area", area);
				connection.put("target", conn.getTarget());	
				
				connections.add(connection);
			}
			_switch.put("connections", connections);			
			switches.add(_switch);
		}
		protocalInfo.put("switches", switches);
		return protocalInfo;
	}
}
