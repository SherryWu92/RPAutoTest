package cn.edu.nju.software.rp.service;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.edu.nju.software.rp.model.Protocal;
import cn.edu.nju.software.rp.testcontrol.ConfigController;
import cn.edu.nju.software.rp.testcontrol.JSONDataMapping;

@Path("/configure")
public class ConfigureService {
	
	private ConfigController configureController;
	private JSONDataMapping jsonDataMapping;
	
	public ConfigureService() {
		configureController = new ConfigController();
		jsonDataMapping = new JSONDataMapping();
	}
	
	@POST
	@Path("/protocal")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject configureProtocal(JSONObject protocalInfo) {
		JSONObject response = new JSONObject();
		Protocal protocal = jsonDataMapping.mapToProtocal(protocalInfo);
		if(protocal != null) {
			JSONArray result=configureController.config(protocal);
			System.out.println(JSONObject.fromObject(protocal));
			response.put("errCode", 0);
			response.put("result", result);
		}
		else {
			response.put("errCode", 1);
			response.put("result", "Error");
		}				
		return response;
	}
	
	
	@POST
	@Path("/parseXML")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject parseXML(JSONObject xmlInfo) {
		JSONObject response = new JSONObject();
		
		String xmlContent = xmlInfo.getString("content");
		System.out.println(xmlContent);
		Protocal protocal = configureController.parseXML(xmlContent);
		JSONObject protocalInfo = jsonDataMapping.mapToJSON(protocal); 		
		
		response.put("errCode", 0);
		response.put("result", protocalInfo);
		return response;  
	}
	
	@POST
	@Path("/saveXml")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject saveXml(JSONObject protocalInfo) {
		JSONObject response = new JSONObject();

		Protocal protocal = jsonDataMapping.mapToProtocal(protocalInfo);
		if(protocal != null) {
			JSONObject xmlInfo = new JSONObject(); 	
			
			String xmlContent = configureController.getXMLString(protocal);	
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
