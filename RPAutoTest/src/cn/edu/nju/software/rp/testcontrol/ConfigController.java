package cn.edu.nju.software.rp.testcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.sf.json.JSONArray;

import cn.edu.nju.software.rp.model.Protocal;
import cn.edu.nju.software.rp.xmlmodel.TestCases;

public class ConfigController {

	public JSONArray config(Protocal p){
		DeviceMap deviceMap = new DeviceMap();
		deviceMap.setMap(p);
		XMLDataMapping xmlMap = new XMLDataMapping();
		TestCases cases = xmlMap.mapping(p);
		try {
			this.marshal(cases, "c:\\test\\test.xml");
			DeviceMediator dm = new DeviceMediator();
			JSONArray runlog = dm.config("c:\\test\\test.xml");
			return runlog;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public String getXMLString(Protocal p){
		XMLDataMapping xmlMap = new XMLDataMapping();
		TestCases cases = xmlMap.mapping(p);
		String tempFilePath = "c:\\test\\temp.xml";
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
	
	public Protocal parseXML(String xmlcontent){
		XMLDataMapping mapper = new XMLDataMapping();
		DeviceMediator dm = new DeviceMediator();
		try {
			TestCases t = dm.unmarshal(xmlcontent);
			Protocal p = mapper.mapping(t);
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
	    
	    jaxbMarshaller.marshal(root, System.out);
	    jaxbMarshaller.marshal(root, new File(saveFilePath));
	}
}
