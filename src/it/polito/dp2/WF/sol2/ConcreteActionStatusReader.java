package it.polito.dp2.WF.sol2;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;

import it.polito.dp2.WF.ActionStatusReader;
import it.polito.dp2.WF.Actor;
import it.polito.dp2.WF.sol2.jaxb.ActionType;
import it.polito.dp2.WF.sol2.jaxb.Process.ActionStatus;

/**
 * This is a concrete implementation of the interface {@link ActionStatusReader} based on the JAXB framework.
 * 
 * @author Luca
 */
public class ConcreteActionStatusReader implements ActionStatusReader, Comparable<ActionStatusReader> {

	private String name;
	private Actor actor;
	private Calendar terminationTime;
	private boolean takenInCharge;
	private boolean terminated;

	public ConcreteActionStatusReader(ActionStatus action, String workflowName, Map<String, Actor> actors) {
//TODO:	if((action == null) return;	//safety lock
		if( action.getAction() instanceof ActionType ) {
			ActionType azione = (ActionType) action.getAction();
			//System.out.println("DEBUG - an ActionStatus will be created: "+azione.getId());
			this.name = azione.getName().replace(workflowName+"_", "");
		}
		else
			System.err.println("\n Error! The IDREF does not refer to an ActionType! \n"
					+ "Impossible to set the name of the ActionStatusReader!");
		
		this.takenInCharge = action.isTakenInCharge();
		this.terminated = action.isTerminated();
		this.terminationTime = new GregorianCalendar();
			terminationTime.setTimeInMillis(0);			//default value
		
		//TODO: attenzione! è possibile che nelle actionReader vengono ritornate stringhe e non oggetti... Controllare!!!
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

	@Override
	public int compareTo(ActionStatusReader o) {
		if( terminated && (o.isTerminated()) )
			return terminationTime.compareTo(o.getTerminationTime());
		else if( terminated && (!o.isTerminated()) )
			return -1;
		else if( (!terminated) && (o.isTerminated()) )
			return 1;
		else if( takenInCharge && (!o.isTakenInCharge()))
			return -1;
		else if( (!takenInCharge) && (o.isTakenInCharge()) )
			return 1;
		else
			return 0;
	}

}
