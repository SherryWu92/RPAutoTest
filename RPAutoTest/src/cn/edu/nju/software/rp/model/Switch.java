package cn.edu.nju.software.rp.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Switch extends Device {
	
	public Switch() {
		
	}
	
	public Switch(String id, ArrayList<Connection> connections) {
		 super(id, connections);
	}

}
