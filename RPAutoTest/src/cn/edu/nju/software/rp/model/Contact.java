package cn.edu.nju.software.rp.model;

import java.util.List;

import javax.xml.bind.annotation.*;

@XmlRootElement
public class Contact {
	private String id;
	private String name;
	private List<String> addresses;
	
	public Contact() {}
	
	public Contact(String id, String name, List<String> addresses) {
		this.id = id;
		this.name = name;
		this.addresses = addresses;
	}

	@XmlElement(name="address")
	public List<String> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<String> addresses) {
		this.addresses = addresses;
	}
}
