package it.polito.dp2.WF.sol2;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

import it.polito.dp2.WF.ActionReader;
import it.polito.dp2.WF.SimpleActionReader;
import it.polito.dp2.WF.WorkflowReader;
import it.polito.dp2.WF.sol2.jaxb.ActionType;

/**
 * This is a concrete implementation of abstract class AbstractActionReader (that implements the interface ActionReader).<BR>
 * Another implementation of that abstract class is {@link it.polito.dp2.WF.sol2.ProcessActionR}<br>
 * This implementation is based on JAXB framework.<BR><BR>
 * If you want more detail about the interface look to<BR>
 * {@link it.polito.dp2.WF.sol2.AbstractActionReader}<BR>
 * {@link it.polito.dp2.WF.ActionReader}
 * 
 * @author Luca
 */
public class SimpleActionR extends AbstractActionReader implements SimpleActionReader {

	private HashMap<String, ActionReader> nextActions;
/*
	public SimpleActionReader(Element action, WorkflowReader workflow) {
		super(action, workflow);
		
		nextActions = new HashMap<String, ActionReader>();		
	}*/

	public SimpleActionR(ActionType action, WorkflowReader workflowReader) {
		// TODO Auto-generated constructor stub
	}



	@Override
	public Set<ActionReader> getPossibleNextActions() {
		return new TreeSet<ActionReader>(nextActions.values());
	}
	

	
	public void setPossibleNextActions(Set<ActionReader> actions) {
		for(ActionReader a : actions) {
			nextActions.put(a.getName(), a);
		}
	}

	public void addPossibleNextAction(ActionReader ar) {
		nextActions.put(ar.getName(), ar);
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("NextActions: ");
		for(ActionReader ar : nextActions.values())
			buf.append(ar.getName());
		
		return super.toString()+"\n\t\t"+buf.toString();
	}

}
