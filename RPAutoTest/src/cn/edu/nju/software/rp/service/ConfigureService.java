package cn.edu.nju.software.rp.service;

import java.util.ArrayList;


import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;


import cn.edu.nju.software.rp.model.Connection;
import cn.edu.nju.software.rp.model.Protocal;
import cn.edu.nju.software.rp.model.Router;
import cn.edu.nju.software.rp.model.Switch;

@Path("/configure")
public class ConfigureService {
	
	@POST
	@Path("/protocal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject configureProtocal(JSONObject protocalInfo) {
		JSONObject response = new JSONObject();
		try {
			String type = protocalInfo.getString("type");
			
			JSONArray routerArray = protocalInfo.getJSONArray("routers");
			ArrayList<Router> routers = new ArrayList<Router>();					
			for(int i = 0; i < routerArray.size(); i++) {
                JSONObject routerObject = routerArray.getJSONObject(i);   
                Router router = new Router();
                router.setId(routerObject.getString("id"));
                
                ArrayList<Connection> conns = new ArrayList<Connection>();
                JSONArray connArray = routerObject.getJSONArray("connections");
                for(int j = 0; j < connArray.size(); j++) {
                	JSONObject connObject = connArray.getJSONObject(i);
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
                _switch.setId(switchObject.getString("id"));
                
                ArrayList<Connection> conns = new ArrayList<Connection>();
                JSONArray connArray = switchObject.getJSONArray("connections");
                for(int j = 0; j < connArray.size(); j++) {
                	JSONObject connObject = connArray.getJSONObject(i);
                	Connection conn = new Connection(connObject.getString("port"), connObject.getString("ipAddress"), 
                			connObject.getString("submask"), connObject.getString("network"), connObject.getString("area"),
                			connObject.getString("target"));
                	conns.add(conn);
                }
                
                _switch.setConnections(conns);
                switches.add(_switch);
			}						
			
			Protocal protocal = new Protocal(type, routers, switches);
			System.out.println(JSONObject.fromObject(protocal));
			response.put("errCode", 0);
			response.put("result", "Success");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			response.put("errCode", 1);
			response.put("errDesc", "JSONException");
		}

		return response;
	}
}
