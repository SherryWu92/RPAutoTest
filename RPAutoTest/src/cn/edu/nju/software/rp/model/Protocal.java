package cn.edu.nju.software.rp.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Protocal {
	private String type;
	private ArrayList<Router> routers;
	private ArrayList<Switch> switches;
	
	public Protocal() {
		
	}
	
	public Protocal(String type, ArrayList<Router> routers, ArrayList<Switch> switches) {
		this.type = type;
		this.routers = routers;
		this.switches = switches;
	}	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public ArrayList<Router> getRouters() {
		return routers;
	}
	public void setRouters(ArrayList<Router> routers) {
		this.routers = routers;
	}
	
	public ArrayList<Switch> getSwitches() {
		return switches;
	}
	public void setSwitches(ArrayList<Switch> switches) {
		this.switches = switches;
	}

	
}
