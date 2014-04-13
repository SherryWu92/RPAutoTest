package cn.edu.nju.software.rp.testcontrol;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

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

	private TestCases unmarshal(String xmlpath) throws JAXBException{
		JAXBContext jaxbContext = JAXBContext.newInstance(TestCases.class);
		Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
	    TestCases t = (TestCases)unMarshaller.unmarshal(new File(xmlpath));
	    return t;
	}
	
	public void config(String xmlpath){
		PyObject pyconfig = this.getPythonClassInstance("C:\\Users\\njusoftware\\git\\RPAutoTest\\AutoLib\\config.py", "Config");
		try {
			TestCases t = this.unmarshal(xmlpath);
			ArrayList<TestCase> cases = t.getTestcase();
			for(int i_case=0; i_case < cases.size(); i_case++){
				ArrayList<Equipment> equips = cases.get(i_case).getEquipments().getEquipment();
				for(int i_equip=0; i_equip < equips.size(); i_equip++){
					String id = equips.get(i_equip).getId();
					DeviceInfo info = DeviceMap.getMap().get(id);
					ArrayList<String> commands = equips.get(i_equip).getCommands();
					PyObject[] paras = new PyObject[3];
					paras[0] = new PyString(info.getIP());
					paras[1] = new PyString(info.getPassword());
					paras[2] = new PyList(commands);
					pyconfig.invoke("config_router", paras);
				}
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	public static void main(String[] args){
		DeviceMediator m = new DeviceMediator();
		m.config("c:\\test\\testcases.xml");
	}
}
