package cn.edu.nju.software.rp.xmlmodel;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="equipment")
public class Equipment {
	private String id;
	private ArrayList<String> commands = new ArrayList<String>();
	public String getId() {
		return id;
	}
	@XmlElement(name="id")
	public void setId(String id) {
		this.id = id;
	}
	public ArrayList<String> getCommands() {
		return commands;
	}
	@XmlElement(name="command")
	public void setCommands(ArrayList<String> commands) {
		this.commands = commands;
	}
	

}
