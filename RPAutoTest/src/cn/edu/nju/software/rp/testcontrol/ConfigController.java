package cn.edu.nju.software.rp.testcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.edu.nju.software.rp.model.Protocol;
import cn.edu.nju.software.rp.xmlmodel.TestCases;

public class ConfigController {

	private static ConfigController configController = new ConfigController();
	
	private ConfigController() {
		
	}
	
	public static ConfigController getInstance(){
		return configController;
	}
	
	public JSONArray config(Protocol p){
		DeviceMap deviceMap = DeviceMap.getInstance();
		deviceMap.setMap(p);
		XMLDataMapper xmlMap = new XMLDataMapper();
		TestCases cases = xmlMap.mapping(p);
		try {
			this.marshal(cases, GlobalVariables.TEMP_XML_PATH_FOR_CONFIG);
			DeviceMediator dm = new DeviceMediator();
			JSONArray runlog = dm.config(GlobalVariables.TEMP_XML_PATH_FOR_CONFIG);
			return runlog;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String test(JSONObject testObject){
		DeviceMap deviceMap = DeviceMap.getInstance();
		String id = (String) testObject.get("id");
		String host = deviceMap.getMap().get(id).getIP();
		String password = deviceMap.getMap().get(id).getPassword();
		JSONArray json_commands = (JSONArray) testObject.get("testCmds");
		ArrayList<String> list_commands = new ArrayList<String>();
		for (int i_command=0; i_command < json_commands.size(); i_command++){
			list_commands.add(json_commands.getString(i_command));
		}
		
		DeviceMediator dm = new DeviceMediator();
		return dm.test(host, password, list_commands);
	}
	
	public String getXMLString(Protocol p){
		XMLDataMapper xmlMap = new XMLDataMapper();
		TestCases cases = xmlMap.mapping(p);
		String tempFilePath = GlobalVariables.TEMP_XML_PATH_FOR_EXPORT;
		try{
			this.marshal(cases, tempFilePath);
		}catch (JAXBException e){
			e.printStackTrace();
		}
		BufferedReader reader;
		try {
			reader = new BufferedReader( new FileReader(tempFilePath));
			String         line = null;
		    StringBuilder  stringBuilder = new StringBuilder();
		    String         ls = System.getProperty("line.separator");
		    while( ( line = reader.readLine() ) != null ) {
		        stringBuilder.append( line );
		        stringBuilder.append( ls );
		    }
		    return stringBuilder.toString();
		    
		}catch (IOException e) {
			e.printStackTrace();
		}	    
		return null;
	}
	
	public Protocol parseXML(String xmlcontent){
		XMLDataMapper mapper = new XMLDataMapper();
		DeviceMediator dm = new DeviceMediator();
		try {
			TestCases t = dm.unmarshal(xmlcontent);
			Protocol p = mapper.mapping(t);
			return p;
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void marshal(TestCases root, String saveFilePath) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(TestCases.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//	    jaxbMarshaller.marshal(root, System.out);
	    jaxbMarshaller.marshal(root, new File(saveFilePath));
	}
}
