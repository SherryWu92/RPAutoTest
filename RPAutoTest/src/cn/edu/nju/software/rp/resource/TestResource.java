package cn.edu.nju.software.rp.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.edu.nju.software.rp.testcontrol.ConfigController;

@Path("/test")
public class TestResource {
	private ConfigController configureController;
	
	public TestResource() {
		configureController = ConfigController.getInstance();
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
