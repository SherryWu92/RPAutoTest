package cn.edu.nju.software.rp.xmlmodel;

import org.python.core.PyInstance;
import org.python.util.PythonInterpreter;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="testcases")
public class TestCases {

	
	private ArrayList<TestCase> testcase = new ArrayList<TestCase>();
	
	public ArrayList<TestCase> getTestcase() {
		return testcase;
	}

	@XmlElement(name="testcase")
	public void setTestcase(ArrayList<TestCase> testcases) {
		this.testcase = testcases;
	}

	public static void main(String[] args) throws JAXBException {
		ArrayList<String> c1 = new ArrayList<String>();
		c1.add("conf t");
		c1.add("int s1/1");
		c1.add("ip add 30.0.0.1 255.0.0.0");
		c1.add("no sh");
		c1.add("router rip");
		c1.add("net 30.0.0.0");
		c1.add("exit");
		c1.add("exit");
		ArrayList<String> c2 = new ArrayList<String>();
		c2.add("conf t");
		c2.add("int s1/0");
		c2.add("ip add 30.0.0.2 255.0.0.0");
		c2.add("no sh");
		c2.add("int s1/2");
		c2.add("ip add 20.0.0.2 255.0.0.0");
		c2.add("no sh");
		c2.add("router rip");
		c2.add("network 20.0.0.0");
		c2.add("net 30.0.0.0");
		c2.add("exit");
		c2.add("exit");
		ArrayList<String> c3 = new ArrayList<String>();
		c3.add("conf t");
		c3.add("int s1/0");
		c3.add("ip add 20.0.0.1 255.0.0.0");
		c3.add("no sh");
		c3.add("router rip");
		c3.add("network 20.0.0.0");
		c3.add("exit");
		c3.add("exit");
		Equipment e1 = new Equipment();
		Equipment e2 = new Equipment();
		Equipment e3 = new Equipment();
		
		e1.setId("1");
		e1.setCommands(c1);
		e2.setId("2");
		e2.setCommands(c2);
		e3.setId("3");
		e3.setCommands(c3);
		
		Equipments equips = new Equipments();
		equips.getEquipment().add(e1);
		equips.getEquipment().add(e2);
		equips.getEquipment().add(e3);
		
		Tests tests = new Tests();
		TestCase t1 = new TestCase();
		t1.setEquipments(equips);
		t1.setTests(tests);
		
		ArrayList<TestCase> cases = new ArrayList<TestCase>();
		cases.add(t1);
		
		TestCases a = new TestCases();
		a.setTestcase(cases);
		
		JAXBContext jaxbContext = JAXBContext.newInstance(TestCases.class);
	    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	    
	    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	    
	    jaxbMarshaller.marshal(a, System.out);
	    jaxbMarshaller.marshal(a, new File("c:\\test\\testcases.xml"));
	    
	    Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
	    TestCases t = (TestCases)unMarshaller.unmarshal(new File("c:\\test\\testcases.xml"));
	    System.out.print(t.testcase.get(0).getEquipments().getEquipment().get(0).getCommands().get(1));
	   
	    PythonInterpreter interp = new PythonInterpreter();
	    interp.execfile("C:\\Users\\njusoftware\\Desktop\\git-project\\RPAutoTest\\AutoLib\\config.py");
	    
	    String className = "Config";
	    String instanceName = className.toLowerCase();
	    String objectDef = "=" + className + "()";
	    interp.exec(instanceName + objectDef);
	    PyObject pyObject = interp.get(instanceName);
	    
	    
	    PyObject[] paras = new PyObject[3];
	    paras[0] = new PyString("12.0.0.2");
	    paras[1] = new PyString("1234");
	    paras[2] = new PyList(c1);
	    pyObject.invoke("config_router",paras);
	    
	}
}
