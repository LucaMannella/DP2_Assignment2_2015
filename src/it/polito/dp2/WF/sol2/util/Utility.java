package it.polito.dp2.WF.sol2.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.dp2.WF.sol2.jaxb.ActionType;
import it.polito.dp2.WF.sol2.jaxb.Workflow;

public class Utility {

	/**
	 * This method create a Map of Workflow starting from a Set
	 * 
	 * @param workflowsSet
	 * @return A workflow HashMap that use the workflow's name as key
	 */
	public static Map<String, Workflow> buildWFMap(Set<Workflow> workflowsSet) {
		Map<String, Workflow> workflowsMap = new HashMap<String, Workflow>();
		for(Workflow wf : workflowsSet) {
			workflowsMap.put(wf.getName(), wf);
		}
		return workflowsMap;
	}
	
	/**
	 * This method create a Map of {@link ActionType} starting from a List.
	 * The name of Action will be the key of the Map.
	 * 
	 * @param actionsList
	 * @return An ActionType HashMap that use the action's name as key
	 */
	public static Map<String, ActionType> buildWFActionsMap(List<ActionType> actionsList) {
		Map<String, ActionType> wfActionsMap = new HashMap<String, ActionType>();
		for(ActionType act : actionsList) {
			wfActionsMap.put(act.getName(), act);
		}
		return wfActionsMap;
	}
	
}
