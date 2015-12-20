package it.polito.dp2.WF.sol2;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import com.sun.xml.internal.ws.runtime.config.ObjectFactory;

import it.polito.dp2.WF.FactoryConfigurationError;
import it.polito.dp2.WF.WorkflowMonitor;
import it.polito.dp2.WF.WorkflowMonitorException;
import it.polito.dp2.WF.WorkflowMonitorFactory;

public class WFInfoSerializer {

	public static void main(String[] args) {
		// This class should receive the name of the output file.
		if(args.length != 1) {
			System.err.println("Error! Usage: <program_name> <output.xml>");
			System.err.println("args.length is equal to "+args.length);
			return;
		}
		System.out.println("This program will serialize your WorkflowMonitor into an XML file!");
		
		
		try {
			// creating the XML schema to validate the XML file
			Schema schema = SchemaFactory.newInstance("").newSchema(new File("xsd/Wfinfo.xsd"));
			// creating the JAXB context to perform a validation
			JAXBContext jc = JAXBContext.newInstance("it.polito.WF");
			// creating the root element
			WorkflowMonitor workflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();
			
			/*---*/
			ObjectFactory asd = new ObjectFactory();
			JAXBElement<WorkflowMonitor> root = asd.createWorkflowMonitor(workflowMonitor);
			/*---*/
			
			Marshaller m = jc.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			//transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
			m.marshal(root, System.out);
			m.marshal(root, new File("xsd/output.xml"));

		} catch (FactoryConfigurationError e) {
			System.err.println("Could not create a DocumentBuilderFactory: "+e.getMessage());
			e.printStackTrace();
			System.exit(11);
		} catch (WorkflowMonitorException e) {
			System.err.println("Could not instantiate the manager class: "+e.getMessage());
			e.printStackTrace();
			System.exit(1);
		} catch (SAXException e) {
			System.err.println("Error creating the XML Schema object");
			e.printStackTrace();
		} catch (JAXBException e) {
			System.err.println("Error creating the new instance of the JAXBContent");
			e.printStackTrace();
		}

	}

}
