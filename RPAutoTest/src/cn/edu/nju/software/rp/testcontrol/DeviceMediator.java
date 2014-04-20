package cn.edu.nju.software.rp.testcontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

import cn.edu.nju.software.rp.model.Protocal;
import cn.edu.nju.software.rp.xmlmodel.*;

public class DeviceMediator {
	
	private PythonInterpreter pythonInterp;
	private PyObject configInstance;
	
	public PythonInterpreter getPythonInterpreter(){
		if (this.pythonInterp == null){
			this.pythonInterp = new PythonInterpreter();
		}	
	    return this.pythonInterp;
	}
	
	public PyObject getPythonClassInstance(String pyfilePath, String classname){
		if (this.configInstance == null){
			PythonInterpreter interp = getPythonInterpreter();
			interp.execfile(pyfilePath);
		    String className = classname;
		    String instanceName = className.toLowerCase();
		    String objectDef = "=" + className + "()";
		    interp.exec(instanceName + objectDef);
		    configInstance = interp.get(instanceName);
		}
		
	    return this.configInstance;
	}

	public TestCases unmarshal(File xmlfile) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(TestCases.class);
		Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
	    TestCases t = (TestCases)unMarshaller.unmarshal(xmlfile);
	    return t;
	}
	
	public TestCases unmarshal(String xmlcontent) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(TestCases.class);
		Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
		StringBuffer xmlStr = new StringBuffer(xmlcontent);
		TestCases t = (TestCases)unMarshaller.unmarshal(new StreamSource(new StringReader(xmlStr.toString())));
	    return t;
	}
	
	public JSONArray config(String xmlpath){
		JSONArray runlog = new JSONArray();
		PyObject pyconfig = this.getPythonClassInstance(GlobalVariables.PYCONFIG_PATH, "Config");
		try {
			TestCases t = this.unmarshal(new File(xmlpath));
			ArrayList<TestCase> cases = t.getTestcase();
			DeviceMap deviceMap = DeviceMap.getInstance();
			for(int i_case=0; i_case < cases.size(); i_case++){
				ArrayList<Equipment> equips = cases.get(i_case).getEquipments().getEquipment();
				for(int i_equip=0; i_equip < equips.size(); i_equip++){
					String id = equips.get(i_equip).getId();
					DeviceInfo info = deviceMap.getMap().get(id);
					ArrayList<String> commands = equips.get(i_equip).getCommands();
					PyObject[] paras = new PyObject[3];
					paras[0] = new PyString(info.getIP());
					paras[1] = new PyString(info.getPassword());
					paras[2] = new PyList(commands);
					JSONObject a_log = new JSONObject();
					a_log.put("id", id);
					PyObject equip_log = pyconfig.invoke("config_router", paras);
					String log = (String) equip_log.__tojava__(String.class);
					System.out.println("******"+log);
					a_log.put("log", log);
					runlog.add(a_log);
				}
			}
			return runlog;
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
	public String test(String host, String password, ArrayList<String> testCommands){
		PyObject pyTest = this.getPythonClassInstance(GlobalVariables.PYCONFIG_PATH, "Config");
		PyObject[] paras = new PyObject[3];
		paras[0] = new PyString(host);
		paras[1] = new PyString(password);
		paras[2] = new PyList(testCommands);
		PyObject result = pyTest.invoke("test_router", paras);
		String resultString = (String) result.__tojava__(String.class);
		return resultString;
	}
	
	public static void main(String[] args){
//		DeviceMap map = DeviceMap.getInstance();
//		XMLDataMapping mapper = new XMLDataMapping();
//		DeviceMediator dm = new DeviceMediator();
//		BufferedReader reader;
//		StringBuilder  stringBuilder = null;
//		Protocal p = null;
//		try {
//			reader = new BufferedReader( new FileReader("c:\\test\\test.xml"));
//			String         line = null;
//		    stringBuilder = new StringBuilder();
//		    String         ls = System.getProperty("line.separator");
//		    while( ( line = reader.readLine() ) != null ) {
//		        stringBuilder.append( line );
//		        stringBuilder.append( ls );
//		    } 
//		}catch (IOException e) {
//			e.printStackTrace();
//		}	    
//		try {
//			TestCases t = dm.unmarshal(stringBuilder.toString());
//			p = mapper.mapping(t);
//
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//		map.setMap(p);
//		dm.config("c:\\test\\test.xml");
	}
}
