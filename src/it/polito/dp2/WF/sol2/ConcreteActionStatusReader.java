package it.polito.dp2.WF.sol2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import it.polito.dp2.WF.ActionStatusReader;
import it.polito.dp2.WF.Actor;
import it.polito.dp2.WF.sol2.jaxb.Process.ActionStatus;

public class ConcreteActionStatusReader implements ActionStatusReader {

	private String name;
	private Actor actor;
	private Calendar terminationTime;
	private boolean takenInCharge;
	private boolean terminated;

	public ConcreteActionStatusReader(ActionStatus action, String workflowName, Map<String, Actor> actors) {
//TODO:	if((action == null) return;	//safety lock
		String actionID = action.getAction();
		this.name = actionID.replace(workflowName+"_", "");
		
		this.takenInCharge = action.isTakenInCharge();
		this.terminated = action.isTerminated();
		this.terminationTime = new GregorianCalendar();
			terminationTime.setTimeInMillis(0);			//default value
		
		//TODO: attenzione! è possibile che non vada una cazzo nelle actionReader perchè vengono ritornate stringhe e non oggetti... Controllare!!!
		if(takenInCharge) {
			String actorName = action.getActor();
			actor = actors.get(actorName);
			
			if(actor == null)
				System.err.println("The actor in the ActionStatusReader: "+name+" is still null... Something wrong in the document!");
		}
		if(terminated) {
			this.terminationTime = action.getTimestamp().toGregorianCalendar();
		}
		
	}

	@Override
	public String getActionName() {
		return this.name;
	}

	@Override
	public Actor getActor() {
		return this.actor;
	}

	@Override
	public Calendar getTerminationTime() {
		return this.terminationTime;
	}

	@Override
	public boolean isTakenInCharge() {
		return takenInCharge;
	}

	@Override
	public boolean isTerminated() {
		return terminated;
	}
	
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
		
		StringBuffer buf = new StringBuffer("Action name: "+this.name);
		if(takenInCharge) {
			buf.append(" - taken in charge by: "+actor.getName());
			if(terminated)
				buf.append(" - terminated at: "+dateFormat.format(terminationTime.getTimeInMillis()));
			else
				buf.append(" - not yet terminated");
		}
		else
			buf.append(" - not taken in charge by anyone");

		return buf.toString();
	}

}
