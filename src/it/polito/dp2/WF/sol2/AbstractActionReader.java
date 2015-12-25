package it.polito.dp2.WF.sol2;

import it.polito.dp2.WF.ActionReader;
import it.polito.dp2.WF.WorkflowReader;

/**
 * This is an abstract implementation of the interface ActionReader based on JAXB framework.<BR>
 * If you want to use that class you have to instantiate one of the following implementation.<BR>
 * {@link it.polito.dp2.WF.sol2.SimpleActionR}<BR>{@link it.polito.dp2.WF.sol2.ProcessActionR}<BR><BR>
 * If you want more detail about the interface look to {@link it.polito.dp2.WF.ActionReader}
 * 
 * @author Luca
 */
public abstract class AbstractActionReader implements ActionReader {

	private String name;
	private String role;
	private boolean automInst;
	private WorkflowReader parent;

	/*
	public AbstractActionReader(Element action, WorkflowReader parent) {
//TODO:	if(action == null)	return;
		this.name = action.getAttribute( WFAttributes.ACTION_NAME );				//"name"
		this.role = action.getAttribute( WFAttributes.ACTION_ROLE );				//"role"
		
		String isAuto = action.getAttribute( WFAttributes.ACTION_INSTANTIATION );	//"automInst"
		if( isAuto.equalsIgnoreCase("true") )
			this.automInst = true;
		else
			this.automInst = false;
		
		this.parent = parent;
	}
	*/

	@Override
	public WorkflowReader getEnclosingWorkflow() {
		return this.parent;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getRole() {
		return this.role;
	}

	@Override
	public boolean isAutomaticallyInstantiated() {
		return automInst;
	}
	
	@Override
	public String toString() {
		return "\tAction: "+name+" - Requested Role: "+role+" - Parent workflow: "+parent.getName()+" - AutomInst: "+automInst;
	}

}
