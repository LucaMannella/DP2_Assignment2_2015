package it.polito.dp2.WF.sol2;

import java.io.File;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class WFInfoSerializer {

	public static void main(String[] args) {
		try {
			JAXBContext jc = JAXBContext.newInstance("it.polito.WF");
			JAXBElement<Object> root = null;
			
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(root, System.out);
			m.marshal(root, new File("xsd/output.xml"));
			
		} catch (JAXBException e) {
			System.err.println("Error creating the new instance of the JAXBContent");
			e.printStackTrace();
		} 
		
	}

}
