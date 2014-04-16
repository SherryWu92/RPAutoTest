package cn.edu.nju.software.rp.model;

import java.util.ArrayList;

public class Device {
	private String id;
	private String physicalIp;
	private String password;
	private ArrayList<Connection> connections;
	
	public Device() {
		
	}
	
	public Device(String id, String physicalIp, String password, ArrayList<Connection> connections) {
		this.id = id;
		this.physicalIp = physicalIp;
		this.password = password;
		this.connections = connections;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhysicalIp() {
		return physicalIp;
	}

	public void setPhysicalIp(String physicalIp) {
		this.physicalIp = physicalIp;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public ArrayList<Connection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<Connection> connections) {
		this.connections = connections;
	}

}
