package it.polito.dp2.WF.sol2;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import it.polito.dp2.WF.ActionReader;
import it.polito.dp2.WF.ActionStatusReader;
import it.polito.dp2.WF.Actor;
import it.polito.dp2.WF.FactoryConfigurationError;
import it.polito.dp2.WF.ProcessActionReader;
import it.polito.dp2.WF.ProcessReader;
import it.polito.dp2.WF.SimpleActionReader;
import it.polito.dp2.WF.WorkflowMonitor;
import it.polito.dp2.WF.WorkflowMonitorException;
import it.polito.dp2.WF.WorkflowMonitorFactory;
import it.polito.dp2.WF.WorkflowReader;
import it.polito.dp2.WF.sol2.jaxb.ActionType;
import it.polito.dp2.WF.sol2.jaxb.Actors;
import it.polito.dp2.WF.sol2.jaxb.ObjectFactory;
import it.polito.dp2.WF.sol2.jaxb.Process;
import it.polito.dp2.WF.sol2.jaxb.Workflow;
import it.polito.dp2.WF.sol2.jaxb.WorkflowManager;
import it.polito.dp2.WF.sol2.util.Utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

/**
 * This class serialize a {@link WorkflowMonitor} into an XML file using the JAXP framework.
 * 
 * @author Luca
 */
public class WFInfoSerializer {
	
	public static final String XSD_NAME = "xsd/WFInfo.xsd";
	public static final String XSD_LOCATION = "http://lucamannella.altervista.org/WFInfo";
	public static final String PACKAGE = "it.polito.dp2.WF.sol2.jaxb";
	
	private ObjectFactory objFactory;
	private WorkflowMonitor workflowMonitor;

	/**
	 * This method create an object {@link WFInfoSerializer} and it transforms its content into an XML file.
	 * @param args [0] - The name of the output file
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
			System.out.println("The data structures were created!\n");
			
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
		catch (JAXBException e) {
			System.err.println("Error creating the new instance of the JAXBContent");
			e.printStackTrace();
			System.exit(2);
		}
		catch(IllegalArgumentException e) {
			System.err.println("Error, some argument are wrong!");
			e.printStackTrace();
			System.exit(3);
		}
		catch (FileNotFoundException e) {
			System.err.println("Error! The file: "+args[0]+" does not exists!");
			e.printStackTrace();
			System.exit(4);
		}
		catch (SAXException e) {
			System.err.println("Error creating the XML Schema object");
			e.printStackTrace();
			System.exit(5);
		}
	}
	
	
	/**
	 * This method create an instance of the WFInfoSerializer.<br>
	 * The serializer contains a {@link JAXBContext} and a {@link WorkflowMonitor}.
	 * 
	 * @throws FactoryConfigurationError - If an error occurs during the creation of the XML {@link Schema} or the {@link WorkflowMonitor}
	 * @throws WorkflowMonitorException - If an error occurs during the creation of the {@link WorkflowMonitor}
	 */
	public WFInfoSerializer() throws WorkflowMonitorException, FactoryConfigurationError {	
		// creating the root element
		workflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();
		
		// creating the object factory
		objFactory = new ObjectFactory();
	}

	/**
	 * This method create an instance of {@link WorkflowManager} taking the data from the {@link WorkflowMonitor}.
	 * 
	 * @return The root of the XML document as {@link WorkflowManager} object.
	 */
	private WorkflowManager createWorkflowManager() {
		WorkflowManager root = objFactory.createWorkflowManager();
		
		System.out.println("Workflows: "+workflowMonitor.getWorkflows());
			// - Adding the workflows to the root element - //
		Set<Workflow> createdWorkflows = createWorkflows(workflowMonitor.getWorkflows());
		if(( createdWorkflows != null )&&( !createdWorkflows.isEmpty() ))
			 root.getWorkflow().addAll(createdWorkflows);
		else
			System.out.println("\nWARNING [WFInfoSerializer - createWfManager()]: The return value of createWorkflows() is null or empty!\n");
		
			// - Building an hash map of workflow - //
		Map<String, Workflow> workflowsMap = Utility.buildWFMap(createdWorkflows);
		
		System.out.println("Processes: "+workflowMonitor.getProcesses());
			// - Adding the processes to the root element - //
		Set<Process> createdProcesses = createProcesses(workflowMonitor.getProcesses(), workflowsMap);
		if(( createdProcesses != null )&&( !createdProcesses.isEmpty() ))
			root.getProcess().addAll(createdProcesses);
		else
			System.out.println("\nWARNING [WFInfoSerializer - createWfManager()]: The return value of createProcesses() is null or empty!\n");

			// - Adding the actors to the root element - //
		Set<Actors> createdActors = createActors();
		if(( createdActors != null )&&( !createdActors.isEmpty() ))
			root.getActors().addAll(createdActors);
		else
			System.out.println("\nWARNING [WFInfoSerializer - createWfManager()]: The return value of createActors() is null or empty!\n");
		
		return root;
	}


	/**
	 * This method converts a set of {@link WorkflowReader} into a set of {@link Workflow}.
	 * @param processes - The set of {@link WorkflowReader} 
	 * @return The set of {@link Workflow}
	 */
	private Set<Workflow> createWorkflows(Set<WorkflowReader> workflows) { 
		Set<Workflow> newWorkflows = new HashSet<Workflow>();
		
		for( WorkflowReader wf : workflows ) {
			String wfName = wf.getName();
			
			// - Creating the workflow object and set its attribute - //
			Workflow workflow = objFactory.createWorkflow();
			workflow.setName(wfName);
			
			Map<String, ActionType> newActions = new HashMap<String, ActionType>();
			// - Building all the actions - //
			for( ActionReader ar : wf.getActions()) {
				ActionType newAct = buildActionType(wfName, ar, newActions);
				newActions.put(newAct.getName(), newAct);
			}
			// - Linking all the actions - //
			for( ActionReader ar : wf.getActions()) {
				if(ar instanceof SimpleActionReader) {
					linkSimpleAction( (SimpleActionReader)ar, newActions );
				}
			}
			
			if( !newActions.isEmpty() )
				workflow.getAction().addAll(newActions.values());
			else
				System.out.println("\nWARNING [WFInfoSerializer - createWorkflows()]: The workflow"+wfName+" does not have actions!\n");
			
			newWorkflows.add(workflow);
		}
		
		return newWorkflows;
	}


	private ActionType buildActionType(String wfName, ActionReader ar, Map<String, ActionType> createdActions) {
		String actName = ar.getName();
		String id = wfName+"_"+actName;
		
		// - Creating the action object - //
		ActionType action = objFactory.createActionType();
		
		action.setId(id);
		action.setName(actName);
		action.setRole(ar.getRole());
		action.setAutomInst(ar.isAutomaticallyInstantiated());
		
		if (ar instanceof ProcessActionReader) {
			// - Casting the action to the right type - //
			ProcessActionReader par = (ProcessActionReader) ar;
			
			// - Creating the ProcessActionReader - //
			ActionType.ProcessAction processAction = objFactory.createActionTypeProcessAction();
			processAction.setNextProcess(par.getActionWorkflow().getName());
			
			// - Setting simpleAction & processAction inside the element - //
			action.setSimpleAction(null);
			action.setProcessAction(processAction);
		}
		else if (ar instanceof SimpleActionReader == false)
			System.err.println("Error! The ActionReader "+ar.getName()+" belongs to a not known type! \n");
	
		return action;
	}


	private void linkSimpleAction(SimpleActionReader actReader, Map<String, ActionType> createdActions) {
		ActionType actType = createdActions.get(actReader.getName());
		
		// - Creating the SimpleActionReader - //
		ActionType.SimpleAction simpleAction = objFactory.createActionTypeSimpleAction();
		
		// - Save all the nextActions inside the list - //
		for(ActionReader possibleAction : actReader.getPossibleNextActions()) {
			ActionType azioneSuccessiva = createdActions.get(possibleAction.getName());
			if(azioneSuccessiva != null)
				simpleAction.getNextActions().add(azioneSuccessiva);
			else
				System.err.println("Error! Unexpected situation! There is no action: "+possibleAction.getName());
				
		}
		
		// - Setting simpleAction & processAction inside the element - //
		actType.setSimpleAction(simpleAction);
		actType.setProcessAction(null);
		
		createdActions.put(actReader.getName(), actType);
	}


	/**
	 * This method converts a set of {@link ProcessReader} into a set of {@link Process}.
	 * @param processes - The set of {@link ProcessReader} 
	 * @param workflowsMap - The set of {@link Workflow} from which the ActionType were taken.
	 * @return The set of {@link Process}
	 */
	private Set<Process> createProcesses(Set<ProcessReader> processes, Map<String, Workflow> workflowsMap) {
		int code = 1;
		Set<Process> newProcesses = new HashSet<Process>();
	
		for (ProcessReader pr: processes) {
			// - Generating XMLGregorianCalendar - //
			XMLGregorianCalendar startTime = Utility.createXMLGregCalendar(pr.getStartTime());
			
			// - Taking the relative workflows name - //
			String wfName = pr.getWorkflow().getName();
			
			// creating a process
			Process process = objFactory.createProcess();
			// setting its attributes
			process.setCode("p"+code);
			process.setStarted(startTime);
			process.setWorkflow(wfName);
			
			Workflow wf = workflowsMap.get(wfName);
			Map<String, ActionType> wfActionsTypeMap = Utility.buildWFActionsMap(wf.getAction());
			
			List<Process.ActionStatus> newActions = new LinkedList<Process.ActionStatus>();
			// - For each process taking the inner actions - //
			for ( ActionStatusReader asr : pr.getStatus() ) {
				Process.ActionStatus action = objFactory.createProcessActionStatus();
				
				action.setAction( wfActionsTypeMap.get(asr.getActionName()) );
				action.setTakenInCharge(asr.isTakenInCharge());
				action.setTerminated(asr.isTerminated());
				
				if (asr.isTakenInCharge()) {		//was the action assigned?
					String actor = asr.getActor().getName();
					action.setActor(actor);
				}

				if (asr.isTerminated())	{		//was the action completed?
					// - Generating a new XMLGregorianCalendar and set it into the action - //
					XMLGregorianCalendar endTime = Utility.createXMLGregCalendar(asr.getTerminationTime());					
					action.setTimestamp(endTime);
				}
				newActions.add(action);
			}
			
			if( !newActions.isEmpty() )
				process.getActionStatus().addAll(newActions);
			else
				System.out.println("\nWarning [WFInfoSerializer - createProcesses()]: The process p"+code+" does not have actions!\n");
			
			newProcesses.add(process);
			code++;
		}
	
		return newProcesses;
	}


	private Set<Actors> createActors() {
		// creating the actors container
		Set<Actor> actorsSet = new HashSet<Actor>();
		
		for( ProcessReader pr: workflowMonitor.getProcesses() ) // for each process
		{
			for( ActionStatusReader asr : pr.getStatus() )		// I take all the actions
			{									 
				if(asr.isTakenInCharge())	// and I take the actor only if the action was taken in charge
					actorsSet.add( asr.getActor() );
			}
		}
		
		Set<Actors> newDepartments = new HashSet<Actors>();
		// add here an outer loop
		Actors department = objFactory.createActors();
		//TODO: change this part if you want to manage the departments
		
		Set<Actors.Actor> actorInDepartment = new HashSet<Actors.Actor>();
		for (Actor a : actorsSet) {
			Actors.Actor xmlActor = objFactory.createActorsActor();
			xmlActor.setName(a.getName());
			xmlActor.setRole(a.getRole());
			
			actorInDepartment.add(xmlActor);
		}
		if( !actorInDepartment.isEmpty() )
			department.getActor().addAll(actorInDepartment);
		else
			System.out.println("\nWarning [WFInfoSerializer - createActors()]: The Actors tag has no actor elements!\n");
		
		newDepartments.add(department);
		// end of the outer loop
		return newDepartments;
	}


	/**
	 * This method converts a {@link WorkflowManager} object into a valid XML file.
	 * @param root - A {@link WorkflowManager} object
	 * @param outputFile - A stream related to the file that you want to write.
	 * 
	 * @throws JAXBException - If it is not possible to create the JAXB context or the XML file.
	 * @throws SAXException - If it is not possible to create an XML Schema for validating the output file.
	 */
	private void marshallDocument(WorkflowManager root, PrintStream outputFile) throws JAXBException, SAXException, IllegalArgumentException {
		/* - Creating the JAXB context to perform a validation - */
		JAXBContext jc = JAXBContext.newInstance(PACKAGE);
		
		/* - Creating an instance of the XML Schema - */
		Schema schema;
		try {
			schema = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(new File(XSD_NAME));
		}
		catch(IllegalArgumentException e) {
			System.err.println("Error! No implementation of the schema language is available");
			throw e;
		}
		catch(NullPointerException e) {
			System.err.println("Error! The instance of the schema or the file of the schema is not well created!\n");
			throw new SAXException("The schema file is null!");
		}
		
		/* - Creating the XML document - */			
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, XSD_LOCATION+" "+XSD_NAME);
		m.setSchema(schema);
		m.marshal(root, outputFile);
	}

}
