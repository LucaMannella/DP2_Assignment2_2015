package it.polito.dp2.WF.sol2;

import java.util.Calendar;
import java.util.List;

import it.polito.dp2.WF.ActionStatusReader;
import it.polito.dp2.WF.ProcessReader;
import it.polito.dp2.WF.WorkflowReader;
import it.polito.dp2.WF.sol2.jaxb.Process;

public class ConcreteProcessReader implements ProcessReader {

	public ConcreteProcessReader(Process p, WorkflowReader myWF) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Calendar getStartTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActionStatusReader> getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WorkflowReader getWorkflow() {
		// TODO Auto-generated method stub
		return null;
	}

}
