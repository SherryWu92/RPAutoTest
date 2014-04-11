package cn.edu.nju.software.rp.model;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Switch {
	private String id;
	private ArrayList<Connection> connections;
	
	public Switch() {
		
	}
	
	public Switch(String id, ArrayList<Connection> connections) {
		this.id = id;
		this.connections = connections;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}
}
