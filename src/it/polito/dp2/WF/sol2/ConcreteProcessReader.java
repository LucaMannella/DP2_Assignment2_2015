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

public class ConcreteProcessReader implements ProcessReader, Comparable<ProcessReader> {

	private WorkflowReader myWorkflow;
	private Calendar startTime;
	private List<ActionStatusReader> statusActions;

	public ConcreteProcessReader(Process p, WorkflowReader myWF, Map<String,Actor> actors) {
		statusActions = new ArrayList<ActionStatusReader>();
		
		this.myWorkflow = myWF;
//TODO:	if(proc == null) return;	//safety lock
		this.startTime = p.getStarted().toGregorianCalendar();
		
		for( ActionStatus action : p.getActionStatus() ) {
			ActionStatusReader asr = new ConcreteActionStatusReader( action, myWorkflow.getName(), actors );
			statusActions.add(asr);
		}
		//FIXME: provo ad ordinare la lista secondo il comparable
		//Collections.sort(statusActions, new ActionStatusReaderComparator());
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
			buf.append(asr.toString()+"\n");
		}
		return buf.toString();
	}
}
