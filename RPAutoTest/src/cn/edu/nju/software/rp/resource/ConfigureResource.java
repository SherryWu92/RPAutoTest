package cn.edu.nju.software.rp.resource;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.edu.nju.software.rp.factory.JSONDataMapperFactory;
import cn.edu.nju.software.rp.model.Protocal;
import cn.edu.nju.software.rp.testcontrol.ConfigController;
import cn.edu.nju.software.rp.testcontrol.JSONDataMapper;

@Path("/configure")
public class ConfigureResource {
	
	private ConfigController configureController;
	private JSONDataMapper jsonDataMapping;
	
	public ConfigureResource() {
		configureController = ConfigController.getInstance();
		jsonDataMapping = JSONDataMapperFactory.getJSONDataMapper();
	}
	
	@POST
	@Path("/runResults")
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
	@Path("/protocalInfo")
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
	@Path("/xmlInfo")
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
	
	@POST
	@Path("/testResults")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject testProtocal(JSONObject testInfo) {
		JSONObject response = new JSONObject();
		if(testInfo != null) {
			String id = testInfo.getString("id");
			JSONArray testCmds = testInfo.getJSONArray("testCmds");
			if(id !=null && testCmds != null) {
				JSONObject testLog = new JSONObject(); 	
					
				String logStr = configureController.test(testInfo);
				
				testLog.put("id", id);
				testLog.put("log", logStr);
				
				response.put("errCode", 0);
				response.put("result", testLog);
			}
			else {
				response.put("errCode", 1);
				response.put("result", "Error");
			}				
		}
		else {
			response.put("errCode", 1);
			response.put("result", "Error");
		}	

		return response; 
	}
}
