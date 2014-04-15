package cn.edu.nju.software.rp.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Connection {
	private String port;
	private String ipAddress;
	private String submask;
	private String network;
	private String area;
	private String target;
	
	public Connection() {
		
	}
	
	public Connection(String port, String ipAddress, String submask, String network, String area, String target) {
		this.port = port;
		this.ipAddress = ipAddress;
		this.submask = submask;
		this.network = network;
		this.area = area;
		this.target = target;
	}
	
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getSubmask() {
		return submask;
	}
	public void setSubmask(String submask) {
		this.submask = submask;
	}
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	
		
}
