package cn.edu.nju.software.rp.resource;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.edu.nju.software.rp.factory.ConfigControllerFactory;
import cn.edu.nju.software.rp.factory.JSONDataMapperFactory;
import cn.edu.nju.software.rp.model.Protocol;
import cn.edu.nju.software.rp.testcontrol.ConfigController;
import cn.edu.nju.software.rp.testcontrol.JSONDataMapper;

@Path("/configure")
public class ConfigResource {
	
	private ConfigController configController;
	private JSONDataMapper jsonDataMapper;
	
	public ConfigResource() {
		configController = ConfigControllerFactory.getConfigController();
		jsonDataMapper = JSONDataMapperFactory.getJSONDataMapper();
	}
	
	@POST
	@Path("/runResults")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject configureProtocol(JSONObject protocolInfo) {
		JSONObject response = new JSONObject();
		Protocol protocol = jsonDataMapper.mapToProtocol(protocolInfo);
		if(protocol != null) {
			JSONArray result=configController.config(protocol);
			System.out.println(JSONObject.fromObject(protocol));
			response.put("errCode", 0);
			response.put("result", result);
		}
		else {
			response.put("errCode", 1);
			response.put("result", "Error");
		}				
		return response;
	}
	
}
