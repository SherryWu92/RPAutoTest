package cn.edu.nju.software.rp.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Switch extends Device {
	
	public Switch() {
		
	}
	
	public Switch(String id, String physicalIp, String password, ArrayList<Connection> connections) {
		 super(id, physicalIp, password, connections);
	}

}
