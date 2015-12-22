package it.polito.dp2.WF.sol2;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.SchemaFactoryConfigurationError;

import org.xml.sax.SAXException;

import it.polito.dp2.WF.FactoryConfigurationError;
import it.polito.dp2.WF.ProcessReader;
import it.polito.dp2.WF.WorkflowMonitor;
import it.polito.dp2.WF.WorkflowMonitorException;
import it.polito.dp2.WF.WorkflowMonitorFactory;
import it.polito.dp2.WF.WorkflowReader;
import it.polito.dp2.WF.sol2.jaxb.Actors;
import it.polito.dp2.WF.sol2.jaxb.ObjectFactory;
import it.polito.dp2.WF.sol2.jaxb.Process;
import it.polito.dp2.WF.sol2.jaxb.Workflow;
import it.polito.dp2.WF.sol2.jaxb.WorkflowManager;

public class WFInfoSerializer {
	
	public static final String XSD_NAME = "xsd/Wfinfo.xsd";
	public static final String PACKAGE = "it.polito.dp2.WF.sol2.jaxb";
	
	private Schema schema;
	private JAXBContext jc;
	private ObjectFactory objFactory;
	private WorkflowMonitor workflowMonitor;
	
	private SimpleDateFormat dateFormat;

	/**
	 * This method create an object {@link WFInfoSerializer} and it transforms its content into an XML file.
	 * @param args [0] - The name fo the output file
	 */
	public static void main(String[] args) {
		// This class should receive the name of the output file.
		if(args.length != 1) {
			System.err.println("Error! Usage: <program_name> <output.xml>");
			System.err.println("args.length is equal to "+args.length);
			return;
		}
		System.out.println("This program will serialize your WorkflowMonitor into an XML file!");
		
		try {
			
			WFInfoSerializer serializer = new WFInfoSerializer();
			
			WorkflowManager root = serializer.createWorkflowManager();
			
			serializer.marshallDocument(root, System.out);
			
			PrintStream fpout = new PrintStream(new File(args[0]));
			serializer.marshallDocument(root, fpout);
			fpout.close();

		}
		catch (FactoryConfigurationError e) {
			System.err.println("Could not create a DocumentBuilderFactory: "+e.getMessage());
			e.printStackTrace();
			System.exit(11);
		}
		catch (WorkflowMonitorException e) {
			System.err.println("Could not instantiate the manager class: "+e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
		catch (SAXException e) {
			System.err.println("Error creating the XML Schema object");
			e.printStackTrace();
		}
		catch (JAXBException e) {
			System.err.println("Error creating the new instance of the JAXBContent");
			e.printStackTrace();
		}
		catch (FileNotFoundException e) {
			System.err.println("Error! The file: "+args[0]+" does not exists!");
			e.printStackTrace();
		}

	}
	
	
	/**
	 * This method create an instance of the WFInfoSerializer.<br>
	 * The serializer contains an XML {@link Schema} definition, a {@link JAXBContext} and a {@link WorkflowMonitor}.
	 * 
	 * @throws SAXException
	 * @throws JAXBException - If an error occurs during the creation of the {@link JAXBContext}
	 * @throws FactoryConfigurationError - If an error occurs during the creation of the XML {@link Schema} or the {@link WorkflowMonitor}
	 * @throws WorkflowMonitorException - If an error occurs during the creation of the {@link WorkflowMonitor}
	 */
	public WFInfoSerializer() throws  SAXException, JAXBException, FactoryConfigurationError, WorkflowMonitorException{
		// creating the XML schema to validate the XML file
		try {
			schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(new File(XSD_NAME));
		}
		catch(IllegalArgumentException e) {
			System.err.println("Error! No implementation of the schema language is available");
			throw new FactoryConfigurationError("No implementation of the schema language is available");
		}
		catch(SchemaFactoryConfigurationError e) {
			System.err.println("Error! A configuration error is encountered!");
			throw new FactoryConfigurationError("A configuration error is encountered");
		}
		catch(NullPointerException e) {
			System.err.println("Error! The instane of the schema or the file of the schema is not well created!\n");
			throw new SAXException("The schema file is null!");
		}
		
		// creating the JAXB context to perform a validation
		jc = JAXBContext.newInstance(PACKAGE);
		// creating the root element
		workflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();
		// creating the object factory
		objFactory = new ObjectFactory();
		
		// This element will help to managing the data format (e.g. 2015-10-20T16:12:15+01:00 )
		dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
	}

	/**
	 * This method create an instance of {@link WorkflowManager} taking the data from the {@link WorkflowMonitor}.
	 * 
	 * @return
	 */
	private WorkflowManager createWorkflowManager() {
		WorkflowManager root = objFactory.createWorkflowManager();
		
		System.out.println(workflowMonitor.getWorkflows());
		Set<Workflow> createdWorkflows = createWorkflows(workflowMonitor.getWorkflows());
		if(( createdWorkflows != null )&&( !createdWorkflows.isEmpty() ))
			 root.getWorkflow().addAll(createdWorkflows);
		else
			System.out.println("DEBUG - The return value of createWorkflows() is null or empty!");
		
		System.out.println(workflowMonitor.getProcesses());
		Set<Process> createdProcesses = createProcesses(workflowMonitor.getProcesses());
		if(( createdProcesses != null )&&( !createdProcesses.isEmpty() ))
			root.getProcess().addAll(createdProcesses);
		else
			System.out.println("DEBUG - The return value of createProcesses() is null or empty!");
		
		Set<Actors> createdActors = createActors(workflowMonitor);
		if(( createdActors != null )&&( !createdActors.isEmpty() ))
			root.getActors().addAll(createdActors);
		else
			System.out.println("DEBUG - The return value of createActors() is null or empty!");
		
		return root;
	}

	private Set<Workflow> createWorkflows(Set<WorkflowReader> set) {
		/*for( WorkflowReader wf : set ) {
			String wfName = wf.getName();
			
			Workflow workflow = objFactory.createWorkflow();
			workflow.setName(wfName);
			
			for( ActionReader ar : wf.getActions()) {
				String actName = ar.getName();
				String id = wfName+"_"+actName;
				
				ActionType action = objFactory.createActionType();
				action.setId(id);
				action.setName(actName);
				action.setRole(ar.getRole());
				action.setAutomInst(ar.isAutomaticallyInstantiated());
				
				if(ar instanceof SimpleActionReader) {
					SimpleActionReader sar = (SimpleActionReader) ar;
					sar.getPossibleNextActions();
				}
				else if (ar instanceof ProcessActionReader) {
					ProcessActionReader par = (ProcessActionReader) ar;
					WorkflowReader nextWorkflowReader = par.getActionWorkflow();
					Workflow nextWorkflow = objFactory.createWorkflow();
				}
				else
					System.err.println("Error! The ActionReader belongs to a not known type! \n");
			}
		} */
		return null;
	}

	/**
	 * This method converts a set of {@link ProcessReader} into a set of {@link Process}.
	 * @param processes - The set of {@link ProcessReader} 
	 * @return The set of {@link Process}
	 */
	private Set<Process> createProcesses(Set<ProcessReader> processes) {
		int code = 1;
		/*
		for (ProcessReader pr: processes) {
			String startTime = dateFormat.format(pr.getStartTime().getTimeInMillis());
			String wfName = pr.getWorkflow().getName();
			// creating a process
			Element process = doc.createElement( WFElements.PROCESS );
			// setting its attributes
			process.setAttribute( WFAttributes.PROCESS_CODE, "p"+code );
			process.setAttribute( WFAttributes.PROCESS_WFNAME, wfName );
			process.setAttribute( WFAttributes.PROCESS_TIME, startTime);
			
			// For each action print related data
			List<ActionStatusReader> statusSet = pr.getStatus();
			
			for (ActionStatusReader asr : statusSet) {
				Element action = doc.createElement( WFElements.ACTION_STATUS );
								
				action.setAttribute( WFAttributes.ACTION_STATUS_NAME, wfName+"_"+asr.getActionName() );
				
				if (asr.isTakenInCharge()) {		//was the action assigned?
					String actor = asr.getActor().getName().replaceAll(" ", "_");
					action.setAttribute( WFAttributes.ACTION_STATUS_ACTOR, actor );
					if (asr.isTerminated())	{		//was the action completed?
						String endTime = dateFormat.format( asr.getTerminationTime().getTimeInMillis() );
						action.setAttribute( WFAttributes.ACTION_STATUS_TIME, endTime );
					}
					else {
						action.setAttribute( WFAttributes.ACTION_STATUS_TIME, "Not Finished" );
					}
				}
				else
					action.setAttribute( WFAttributes.ACTION_STATUS_TIME, "Not Taken" );
				
				process.appendChild(action);	//appending the action to the process
			}
			
			root.appendChild(process);			//appending the process to the root
			code++;
		}
		*/
		return null;
	}

	private Set<Actors> createActors(WorkflowMonitor workflowMonitor2) {
		// TODO Auto-generated method stub
		return null;
	}


	/**
	 * This method converts a {@link WorkflowManager} object into an XML file.
	 * @param root - A {@link WorkflowManager} object
	 * @param outputFile - A stream related to the file that you want to write.
	 * 
	 * @throws JAXBException - If it is not possible to create the XML file.
	 */
	private void marshallDocument(WorkflowManager root, PrintStream outputFile) throws JAXBException {
		/* - Creating the XML document - */			
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.marshal(root, outputFile);
	}

}
