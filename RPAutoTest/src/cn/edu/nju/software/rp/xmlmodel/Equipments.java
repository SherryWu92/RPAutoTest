package cn.edu.nju.software.rp.xmlmodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="equipments")
public class Equipments {

	private ArrayList<Equipment> equipment = new ArrayList<Equipment>();

	public ArrayList<Equipment> getEquipment() {
		return equipment;
	}
	
	@XmlElement(name="equipment")
	public void setEquipment(ArrayList<Equipment> equipment) {
		this.equipment = equipment;
	}
	
}
