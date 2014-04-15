package cn.edu.nju.software.rp.xmlmodel;

import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="testcase")
public class TestCase {

	private Equipments equipments;
	private Tests tests;
	public Equipments getEquipments() {
		return equipments;
	}
	@XmlElement(name="equipments")
	public void setEquipments(Equipments equipments) {
		this.equipments = equipments;
	}
	public Tests getTests() {
		return tests;
	}
	@XmlElement(name="tests")
	public void setTests(Tests tests) {
		this.tests = tests;
	}
	
}
