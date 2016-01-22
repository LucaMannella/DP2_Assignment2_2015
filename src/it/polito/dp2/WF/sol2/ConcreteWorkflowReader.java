package it.polito.dp2.WF.sol2;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import it.polito.dp2.WF.ActionReader;
import it.polito.dp2.WF.ProcessActionReader;
import it.polito.dp2.WF.ProcessReader;
import it.polito.dp2.WF.WorkflowMonitor;
import it.polito.dp2.WF.WorkflowReader;
import it.polito.dp2.WF.sol2.jaxb.ActionType;
import it.polito.dp2.WF.sol2.jaxb.Workflow;

/**
 * This is a concrete implementation of the interface {@link WorkflowReader} based on the JAXB framework.
 * 
 * @author Luca
 */
public class ConcreteWorkflowReader implements WorkflowReader, Comparable<WorkflowReader> {

	private String name;
	private Map<String, ActionReader> actions;
	private Set<ProcessReader> processes;
	
	public ConcreteWorkflowReader(Workflow workflow) {		
		actions = new HashMap<String, ActionReader>();
		processes = new HashSet<ProcessReader>();
		
		if(workflow == null) return;	//safety lock
		
		this.name = workflow.getName();
		
		// set the actions inside the object
		for( ActionType azione : workflow.getAction() ){	
			if(azione.getSimpleAction() != null) {
				//it's a simple action
				if (azione.getProcessAction() != null)
					System.err.println("Error! The action has simpleAction and processAction set (it will be treated as simple)\n");
				
				SimpleActionR sar = new SimpleActionR(azione, this);
				actions.put(sar.getName(), sar);
			}
			else if((azione.getSimpleAction() == null) && (azione.getProcessAction() != null)) {
				//it's a process action
				ActionReader ar = new ProcessActionR(azione, this);
				actions.put(ar.getName(), ar);
			}
		}
		// This loop is to managing the simple actions
		for( ActionType azione : workflow.getAction() ) {
			ActionReader actReader = actions.get(azione.getName());
			
			if(actReader instanceof SimpleActionR) {
				SimpleActionR sar = (SimpleActionR)actReader;
				List<Object> nextActions = azione.getSimpleAction().getNextActions();
				sar.setPossibleNextActions(nextActions, actions);
			}
			else if(actReader instanceof ProcessActionR == false)
				System.err.println("Error! The actionReader: "+actReader.getName()+" has un unknown type!");
			
			//The nextWorkflow for processAction will be set by th WorkflowMonitor
		}
		
	}
	
	/**
	 * This method looks if in the processesSet given as parameter there is one or more {@link ProcessReader}
	 * that belongs to this {@link WorkflowReader}, if they exist, they will be added. 
	 * 
	 * @param processesSet - A set of {@link ProcessReader} that you want to analize and set.
	 */
	public void setProcesses(Collection<ProcessReader> processesSet) { 
		// set the processes that refer this Workflow
		for( ProcessReader p : processesSet) {
			//if(process.workflowName == workflow.name)
			if(p.getWorkflow().getName().equals(this.name))
				processes.add(p);
		}
	}
	
	/**
	 * This method set inside each {@link ProcessActionReader} of this {@link WorkflowReader}
	 * the Workflow that will be instantiated after that this action will be completed.
	 *  
	 * @param workflows - All the workflows of the {@link WorkflowMonitor}
	 */
	public void setWfsInsideProcessActions(HashMap<String, WorkflowReader> workflows) {
		for( ActionReader actReader : actions.values() ) {
			
			if(actReader instanceof ProcessActionR) {
				ProcessActionR par = (ProcessActionR)actReader;
				par.setNextWorkflow(workflows);
			}
		}
	}


	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Set<ActionReader> getActions() {
		return new TreeSet<ActionReader>(actions.values());
	}

	@Override
	public ActionReader getAction(String actionName) {
		return actions.get(actionName);
	}

	@Override
	public Set<ProcessReader> getProcesses() {
		return processes;
	}

	@Override
	public int compareTo(WorkflowReader o) {
		return this.name.compareTo(o.getName());
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer("Workflow Name: "+name+"\n");
		
		buf.append("\tActions:\n");
		for(ActionReader ar : actions.values()) {
			buf.append("\t\t"+ar.toString()+"\n");
		}
		
		buf.append("\tProcesses:\n");
		if(processes.isEmpty()){
			buf.append("\t\t No Processes \n");
		}
		else {
			for(ProcessReader pr : processes) {
				if(pr instanceof ConcreteProcessReader) {
					ConcreteProcessReader cpr = (ConcreteProcessReader) pr;
					buf.append("\t\t"+cpr.toShortString()+"\n");
				}
				else {
					buf.append("\t\t"+pr.toString()+"\n");
				}
			}
		}
		
		return buf.toString();
	}

}
