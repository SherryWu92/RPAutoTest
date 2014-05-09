package cn.edu.nju.software.rp.testcontrol;

import java.util.ArrayList;

import cn.edu.nju.software.rp.model.Connection;
import cn.edu.nju.software.rp.model.Protocol;
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
	
	public Protocol mapToProtocol(JSONObject protocolInfo) {
		try {
			String type = protocolInfo.getString("type");
			
			JSONArray routerArray = protocolInfo.getJSONArray("routers");
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
			
			
			JSONArray switchArray = protocolInfo.getJSONArray("switches");
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
			
			Protocol protocol = new Protocol(type, routers, switches);
			return protocol;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public JSONObject mapToJSON(Protocol protocol) {
		JSONObject protocolInfo = new JSONObject(); 
		protocolInfo.put("type", protocol.getType());
		
		JSONArray routers = new JSONArray();		
		
		ArrayList<Router> routerArr = protocol.getRouters();
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
		protocolInfo.put("routers", routers);
		
		
		JSONArray switches = new JSONArray();		
		
		ArrayList<Switch> switchArr = protocol.getSwitches();
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
		protocolInfo.put("switches", switches);
		return protocolInfo;
	}
}
