package cn.edu.nju.software.rp.testcontrol;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import cn.edu.nju.software.rp.model.Protocal;
import cn.edu.nju.software.rp.xmlmodel.TestCases;

public class ConfigController {

	public boolean config(Protocal p){
		XMLDataMapping mapper = new XMLDataMapping();
		TestCases cases = mapper.mapping(p);
		try {
			this.marshal(cases, "c:\\test\\test.xml");
			DeviceMediator dm = new DeviceMediator();
			dm.config("c:\\test\\test.xml");
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	private void marshal(TestCases root, String saveFilePath) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(TestCases.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    
	    jaxbMarshaller.marshal(root, System.out);
	    jaxbMarshaller.marshal(root, new File(saveFilePath));
	}
}
