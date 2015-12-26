package it.polito.dp2.WF.sol2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	private HashMap<String, ActionReader> nextPossibleActions;

	public SimpleActionR(ActionType action, WorkflowReader workflowReader) {
		super(action, workflowReader);
		nextPossibleActions = new HashMap<String, ActionReader>();
		
		ActionType.SimpleAction simpleAction = action.getSimpleAction();
		if(simpleAction == null)
			System.err.println("Error! The processAction is null... Something wrong happens!\n");
	}



	@Override
	public Set<ActionReader> getPossibleNextActions() {
		return new TreeSet<ActionReader>(nextPossibleActions.values());
	}
	

	public void addPossibleNextAction(ActionReader ar) {
		nextPossibleActions.put(ar.getName(), ar);
	}
	
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer("NextActions: ");
		for(ActionReader ar : nextPossibleActions.values())
			buf.append(ar.getName());
		
		return super.toString()+"\n\t\t"+buf.toString();
	}



	public void setPossibleNextActions(List<Object> nextActions, Map<String,ActionReader> actions) {
		for(Object o : nextActions) {
			if(o instanceof ActionType) {
				ActionType action = (ActionType) o;
				ActionReader nextAction = actions.get(action.getName());
				nextPossibleActions.put(nextAction.getName(), nextAction);
			}
			else {
				System.err.println("Error! This is not an ActionType! It will be ignored");
			}
		}
		
	}

}
