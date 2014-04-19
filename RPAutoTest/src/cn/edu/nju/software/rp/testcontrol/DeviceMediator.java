package cn.edu.nju.software.rp.testcontrol;

import java.io.File;
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

import cn.edu.nju.software.rp.xmlmodel.*;

public class DeviceMediator {
	
	private PythonInterpreter pythonInterp;
	
	public PythonInterpreter getPythonInterpreter(){
		if (this.pythonInterp == null){
			this.pythonInterp = new PythonInterpreter();
		    return this.pythonInterp;
		}else return this.pythonInterp;
	}
	
	public PyObject getPythonClassInstance(String pyfilePath, String classname){
		PythonInterpreter interp = getPythonInterpreter();
		interp.execfile(pyfilePath);	    
	    String className = classname;
	    String instanceName = className.toLowerCase();
	    String objectDef = "=" + className + "()";
	    interp.exec(instanceName + objectDef);
	    PyObject pyObject = interp.get(instanceName);
	    return pyObject;
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
		PyObject pyconfig = this.getPythonClassInstance("C:\\Users\\njusoftware\\git\\RPAutoTest\\AutoLib\\config.py", "Config");
		try {
			TestCases t = this.unmarshal(new File(xmlpath));
			ArrayList<TestCase> cases = t.getTestcase();
			DeviceMap deviceMap = new DeviceMap();
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
					PyString equip_log = (PyString) pyconfig.invoke("config_router", paras);
					a_log.put("log", equip_log.toString());
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
	
	public static void main(String[] args){
		DeviceMediator m = new DeviceMediator();
		m.config("c:\\test\\testcases.xml");
	}
}
