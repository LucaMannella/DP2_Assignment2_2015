package it.polito.dp2.WF.sol2;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

import it.polito.dp2.WF.Actor;
import it.polito.dp2.WF.FactoryConfigurationError;
import it.polito.dp2.WF.ProcessReader;
import it.polito.dp2.WF.WorkflowMonitor;
import it.polito.dp2.WF.WorkflowReader;
import it.polito.dp2.WF.sol2.jaxb.Actors;
import it.polito.dp2.WF.sol2.jaxb.Process;
import it.polito.dp2.WF.sol2.jaxb.Workflow;
import it.polito.dp2.WF.sol2.jaxb.WorkflowManager;

public class ConcreteWorkflowMonitor implements WorkflowMonitor {
	
	public static final String XSD_NAME = "xsd/WFInfo.xsd";
	public static final String PACKAGE = "it.polito.dp2.WF.sol2.jaxb";

	private HashMap<String, ProcessReader> processes;
	private HashMap<String, WorkflowReader> workflows;
	private HashMap<String, Actor> actors;
	
	public ConcreteWorkflowMonitor() throws SAXException, JAXBException {
		
		JAXBContext jc = JAXBContext.newInstance(PACKAGE);
		String fileName = System.getProperty("it.polito.dp2.WF.sol2.WorkflowInfo.file");
		WorkflowManager root = null;
		
		try {
			root = unmarshallDocument(jc, new File(fileName));
		} catch (SAXException | JAXBException e) {
			System.err.println("Error during the unmarshalling of the XML document...");
			throw e;
		}
		
		// - Workflows - //
		System.out.println("DEBUG - In the document there are "+root.getWorkflow().size()+" workflows");
		
		workflows = new HashMap<String, WorkflowReader>();
		for( Workflow wf : root.getWorkflow() ) {
			WorkflowReader wfReader = new ConcreteWorkflowReader(wf, root);
			workflows.put(wfReader.getName(), wfReader);
		}
		// This loop is to managing the ProcessActions
		for( WorkflowReader wf : workflows.values() ) {
			if(wf instanceof ConcreteWorkflowReader)
				((ConcreteWorkflowReader)wf).setWfsInsideProcessActions(workflows);
		}
		System.out.println("DEBUG - "+workflows.size()+" workflows were created.");
		
		// - Processes - //
		System.out.println("DEBUG - In the document there are "+root.getProcess().size()+" processes");
		
		
		int code = 1;
		processes = new HashMap<String, ProcessReader>();
		for( Process p : root.getProcess() ) {
			WorkflowReader myWF = workflows.get(p.getWorkflow());
			//I should have already the workflow inside the hashmap (document should be valid)
			
			ProcessReader procReader = new ConcreteProcessReader(p, myWF);		    	
	    	processes.put("p"+code, procReader);
	    	
	    	code++;
		}
		System.out.println("DEBUG - "+processes.size()+" processes were created.");
		
		// - Actors - //	TODO: update this part if you want to manage more departments	
		System.out.println("DEBUG - In the document there are "+root.getActors().size()+" departments");
		actors = new HashMap<String, Actor> ();
		
		// this loop is executed just one time in this particular application
		for( Actors dep : root.getActors() ) {
			System.out.println("DEBUG - In the department there are "+dep.getActor().size()+" actors");
			for( Actors.Actor xmlActor : dep.getActor() ) {
				String actorName = xmlActor.getName().replaceAll("_", " ");
				Actor a = new Actor(actorName, xmlActor.getRole());
				actors.put(a.getName(), a);
			}
			System.out.println("DEBUG - "+actors.size()+" actors were created.");
		}
		
	}
	
	@Override
	public Set<ProcessReader> getProcesses() {
		return new TreeSet<ProcessReader>(processes.values());
	}

	@Override
	public WorkflowReader getWorkflow(String name) {
		return workflows.get(name);
	}

	@Override
	public Set<WorkflowReader> getWorkflows() {
		return new TreeSet<WorkflowReader>(workflows.values());
	}
	
	public String toString(){
		StringBuffer buf = new StringBuffer("Inside this WorkflowMonitor there are:\n");
		
		if((workflows==null) || (workflows.isEmpty()))
			buf.append("\tNo Workflows\n");
		else {
			buf.append("Workflows:\n");
			for(WorkflowReader wfr : workflows.values())
				buf.append("\t\t"+wfr.toString()+"\n");
		}
		
		if((processes==null) || (processes.isEmpty()))
			buf.append("\tNo Processes\n");
		else {
			buf.append("Processes:\n");
			for(ProcessReader pr : processes.values())
				buf.append("\t"+pr.toString()+"\n");
		}
				
		if((actors==null) || (actors.isEmpty()))
			buf.append("\tNo Actors\n");
		else {
			buf.append("Actors:\n");
			for(Actor a : actors.values())
				buf.append("\t"+a.toString()+"\n");
		}
		
		return buf.toString();
	}
	
	
	/**
	 * This method converts a valid XML file into a {@link WorkflowManager} object.
	 * 
	 * @param jc - The {@link JAXBContext} for the unmarshaller.
	 * @param inputFile - A stream related to the file that you want to read.
	 * 
	 * @return The root element of the document (a {@link WorkflowManager} object).
	 * 
	 * @throws JAXBException If it is not possible to create the XML file.
	 * @throws SAXException If a SAX error occurs during parsing the XML Schema.
	 */
	private WorkflowManager unmarshallDocument(JAXBContext jc, File inputFile) throws JAXBException, SAXException {
		Schema schema;
		try {	//creating the XML schema to validate the XML file
			schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(new File(XSD_NAME));
		}
		catch(IllegalArgumentException e) {
			System.err.println("Error! No implementation of the schema language is available");
			throw new FactoryConfigurationError("No implementation of the schema language is available.");
		}
		catch(NullPointerException e) {
			System.err.println("Error! The instance of the schema or the file of the schema is not well created!\n");
			throw new SAXException("The schema file is null!");
		}		
		
		Unmarshaller u = jc.createUnmarshaller();
		u.setSchema(schema);
		return (WorkflowManager) u.unmarshal(inputFile);
	}

}
