package it.polito.dp2.WF.sol2;

import it.polito.dp2.WF.ActionStatusReader;
import it.polito.dp2.WF.Actor;
import it.polito.dp2.WF.ProcessReader;
import it.polito.dp2.WF.WorkflowReader;
import it.polito.dp2.WF.sol2.jaxb.Process;
import it.polito.dp2.WF.sol2.jaxb.Process.ActionStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;

/**
 * This is a concrete implementation of the interface {@link ProcessReader} based on the JAXB framework.
 *
 * @author Luca
 */
public class ConcreteProcessReader implements ProcessReader, Comparable<ProcessReader> {

	private WorkflowReader myWorkflow;
	private Calendar startTime;
	private List<ActionStatusReader> statusActions;

	/**
	 * This class create an implementation of the {@link ProcessReader} interface.
	 * @param p - The {@link Process} starting object.
	 * @param myWF - The {@link WorkflowReader} whom this process belongs.
	 * @param actors - A map of {@link Actor} with their name as keys.
	 * @throws JAXBException - If the selected an actor is not able to perform the action assigned to it.
	 */
	public ConcreteProcessReader(Process p, WorkflowReader myWF, Map<String,Actor> actors) throws JAXBException {
		statusActions = new ArrayList<ActionStatusReader>();
		Actor actor = null;
		
		this.myWorkflow = myWF;
//TODO:	if(proc == null) return;	//safety lock
		this.startTime = p.getStarted().toGregorianCalendar();
		
		for( ActionStatus action : p.getActionStatus() ) {
			// extract the related actor from the map (if exists) //
			String actorName = action.getActor();
			if(actorName != null)
				actor = actors.get(actorName);
			
			ActionStatusReader asr = new ConcreteActionStatusReader( action, myWorkflow.getName(), actor );
			statusActions.add(asr);
		}
	}

	@Override
	public Calendar getStartTime() {
		return this.startTime;
	}

	@Override
	public List<ActionStatusReader> getStatus() {
		return this.statusActions;
	}

	@Override
	public WorkflowReader getWorkflow() {
		return this.myWorkflow;
	}
	
	/**
	 * The comparison is based on the starting time. 
	 * If the time are equal is based on the name of the relative workflows.
	 */
	@Override
	public int compareTo(ProcessReader o) {
		int toRet = startTime.compareTo(o.getStartTime());
		if(toRet == 0)
			toRet = myWorkflow.getName().compareTo(o.getWorkflow().getName());
		
		return toRet;
	}

	@Override
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		
		StringBuffer buf = new StringBuffer("Process related to workflow: "+myWorkflow.getName()+" ");
		buf.append("Started at: "+dateFormat.format(startTime.getTimeInMillis())+"\n");
		
		for(ActionStatusReader asr : statusActions) {
			buf.append("\t"+asr.toString()+"\n");
		}
		return buf.toString();
	}
	
	/**
	 * This method gives a short version of the toString method.
	 * 
	 * @return A shorted version of the toStrin method.
	 */
	public String toShortString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		
		StringBuffer buf = new StringBuffer("Process started at ");
		buf.append( dateFormat.format(startTime.getTimeInMillis()) );
		buf.append(" with "+statusActions.size()+" ActionStatus.");
		
		return buf.toString();
	}
}
