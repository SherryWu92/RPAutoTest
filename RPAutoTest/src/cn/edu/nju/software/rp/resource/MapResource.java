package cn.edu.nju.software.rp.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONObject;
import cn.edu.nju.software.rp.factory.ConfigControllerFactory;
import cn.edu.nju.software.rp.factory.JSONDataMapperFactory;
import cn.edu.nju.software.rp.model.Protocol;
import cn.edu.nju.software.rp.testcontrol.ConfigController;
import cn.edu.nju.software.rp.testcontrol.JSONDataMapper;

@Path("/map")
public class MapResource {

	
	private ConfigController configController;
	private JSONDataMapper jsonDataMapper;
	
	public MapResource() {
		configController = ConfigControllerFactory.getConfigController();
		jsonDataMapper = JSONDataMapperFactory.getJSONDataMapper();
	}
	
	@POST
	@Path("/protocolInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject parseXML(JSONObject xmlInfo) {
		JSONObject response = new JSONObject();
		
		String xmlContent = xmlInfo.getString("content");
		System.out.println(xmlContent);
		Protocol protocol = configController.parseXML(xmlContent);
		JSONObject protocolInfo = jsonDataMapper.mapToJSON(protocol); 		
		
		response.put("errCode", 0);
		response.put("result", protocolInfo);
		return response;  
	}
	
	@POST
	@Path("/xmlInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject saveXML(JSONObject protocolInfo) {
		JSONObject response = new JSONObject();

		Protocol protocol = jsonDataMapper.mapToProtocol(protocolInfo);
		if(protocol != null) {
			JSONObject xmlInfo = new JSONObject(); 	
			
			String xmlContent = configController.getXMLString(protocol);	
			xmlInfo.put("content", xmlContent);
			
			response.put("errCode", 0);
			response.put("result", xmlInfo);
		}
		else {
			response.put("errCode", 1);
			response.put("result", "Error");
		}				

		return response; 
	}
}
