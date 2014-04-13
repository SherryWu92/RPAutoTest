package cn.edu.nju.software.rp.testcontrol;

import java.util.ArrayList;

import cn.edu.nju.software.rp.model.*;
import cn.edu.nju.software.rp.xmlmodel.*;

public class XMLDataMapping {

	public TestCases mapping(Protocal p){
		ArrayList<Router> routers = p.getRouters();
		ArrayList<Switch> switches = p.getSwitches();
		ArrayList<Equipment> equips = new ArrayList<Equipment>();
		for (int i_router=0; i_router < routers.size(); i_router++){
			Router r = routers.get(i_router);
			Equipment equip = new Equipment();
			equip.setId(r.getId());
			equip.setCommands(this.makeCommands(r.getConnections(), p.getType()));
			equips.add(equip);
		}
		for (int i_switches=0; i_switches < switches.size(); i_switches++){}
		
		Equipments equipments = new Equipments();
		equipments.setEquipment(equips);
		TestCase t = new TestCase();
		t.setEquipments(equipments);
		ArrayList<TestCase> ts = new ArrayList<TestCase>();
		ts.add(t);
		TestCases testcases = new TestCases();
		testcases.setTestcase(ts);
		return testcases;
	}
	
	private ArrayList<String> makeCommands(ArrayList<Connection> ports, String protocalType){
		ArrayList<String> commands = new ArrayList<String>();
		ArrayList<String> networks = new ArrayList<String>();
		commands.add("conf t");
		for (int i_port=0; i_port < ports.size(); i_port++){
			Connection port = ports.get(i_port);
			commands.add("int "+port.getPort());
			commands.add("ip add "+port.getIpAddress()+" "+port.getSubmask());
			commands.add("no sh");
			if(!networks.contains(port.getNetwork())){
				networks.add(port.getNetwork());
			}
		}
		if (protocalType == "rip"){
			commands.add("router rip");
			for(int i_net=0; i_net < networks.size(); i_net++){
				commands.add("net "+ networks.get(i_net));
			}
		}
		commands.add("exit");
		commands.add("exit");
		for (int i=0; i < commands.size(); i++) System.out.println(commands.get(i));
		return commands;
	}
}
