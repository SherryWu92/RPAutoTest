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
	
	public Protocal mapping(TestCases t){
		ArrayList<Equipment> equips = t.getTestcase().get(0).getEquipments().getEquipment();
		ArrayList<Router> routers = new ArrayList<Router>();
		ArrayList<Switch> switches = new ArrayList<Switch>();
		for (int i_equip=0; i_equip < equips.size(); i_equip++){
			Equipment equip = equips.get(i_equip);
			if (equip.getId().charAt(0) == 'R'){
				Router r = new Router();
				r.setId(equip.getId());
				ArrayList<Connection> c = this.parseCommands(equip.getCommands());
				r.setConnections(c);
				routers.add(r);
			}else if (equip.getId().charAt(0) == 'S'){
				Switch s = new Switch();
				s.setId(equip.getId());
				s.setConnections(this.parseCommands(equip.getCommands()));
				switches.add(s);
			}
		}
		for (int i_router=0; i_router < routers.size(); i_router++){
			ArrayList<Connection> cons = routers.get(i_router).getConnections();
			for (int i_con=0; i_con < cons.size(); i_con++){
				Connection c = cons.get(i_con);
				if (c.getTarget() != null) continue;
				String net = c.getNetwork();
				for (int i_compare=i_router+1; i_compare < routers.size(); i_compare++){
					ArrayList<Connection> cons_compare = routers.get(i_compare).getConnections();
					for (int k_con=0; k_con < cons_compare.size(); k_con++){
						Connection c_compare = cons_compare.get(k_con);
						String net_compare = c_compare.getNetwork();
						if (net.equals(net_compare)){
							c.setTarget(routers.get(i_compare).getId());
							c_compare.setTarget(routers.get(i_router).getId());
						}
					}
				}
			}
		}
		Protocal p = new Protocal();
		p.setRouters(routers);
		p.setSwitches(switches);
		p.setType(this.parseProtocalType(equips.get(0).getCommands()));
		return p;
	}
	
	private String parseProtocalType(ArrayList<String> commands){
		for (int i_command=0; i_command < commands.size(); i_command++){
			String command = commands.get(i_command);
			if (command.startsWith("router")){
				if (command.contains("rip")){
					return "rip";
				}
				if (command.contains("ospf")){
					return "ospf";
				}
			}
		}
		return null;
	}
	
	private ArrayList<Connection> parseCommands(ArrayList<String> commands){
		ArrayList<Connection> cons = new ArrayList<Connection>();
		// boolean a_new_connection = false;
		Connection c = null;

		for (int i_command=0; i_command < commands.size(); i_command++){
			String command = commands.get(i_command);
			
			if (command.startsWith("int")){
				// a_new_connection = true;
				String port = command.split(" ")[1];
				c = new Connection();	
				c.setPort(port);
			}
			if (command.startsWith("ip add")){
				// a_new_connection = false;
				String ip = command.split(" ")[2];
				c.setIpAddress(ip);
				String submask = command.split(" ")[3];
				c.setSubmask(submask);
				c.setNetwork(this.computeNetwork(ip, submask));
			}
			if (command.startsWith("no sh")){
				// a_new_connection = false;
				cons.add(c);
			}
			
		}
		
		return cons;
	}
	
	private String computeNetwork(String ip, String submask){
		String[] ips = ip.split("\\.");
		String[] subs = submask.split("\\.");
		int ip_1 = Integer.parseInt(ips[0]);
		int ip_2 = Integer.parseInt(ips[1]);
		int ip_3 = Integer.parseInt(ips[2]);
		int ip_4 = Integer.parseInt(ips[3]);
		int sub_1 = Integer.parseInt(subs[0]);
		int sub_2 = Integer.parseInt(subs[1]);
		int sub_3 = Integer.parseInt(subs[2]);
		int sub_4 = Integer.parseInt(subs[3]);
		int net_1 = ip_1 & sub_1;
		int net_2 = ip_2 & sub_2;
		int net_3 = ip_3 & sub_3;
		int net_4 = ip_4 & sub_4;
		
		String network = String.valueOf(net_1)+"."+String.valueOf(net_2)+"."+String.valueOf(net_3)+"."+String.valueOf(net_4);
		return network;
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
		if (protocalType.equals("rip")){
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
