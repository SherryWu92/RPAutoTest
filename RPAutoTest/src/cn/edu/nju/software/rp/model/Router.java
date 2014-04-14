package cn.edu.nju.software.rp.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Router extends Device {
	
	public Router() {
		
	}
	
	public Router(String id, ArrayList<Connection> connections) {
		 super(id, connections);
	}

}
