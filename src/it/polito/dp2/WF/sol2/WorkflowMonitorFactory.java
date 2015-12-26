package it.polito.dp2.WF.sol2;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import it.polito.dp2.WF.WorkflowMonitor;
import it.polito.dp2.WF.WorkflowMonitorException;

public class WorkflowMonitorFactory extends it.polito.dp2.WF.WorkflowMonitorFactory {

    /**
	 * This method creates an instance of my concrete class that implements the WorkflowMonitor interface.
	 */
	@Override
	public WorkflowMonitor newWorkflowMonitor() throws WorkflowMonitorException {
		
		WorkflowMonitor myMonitor = null;
		try {
			myMonitor = new ConcreteWorkflowMonitor();
		} catch (JAXBException | SAXException e) {
			System.err.println("Error: "+e.getMessage());
			e.printStackTrace();
			throw new WorkflowMonitorException(e.getMessage());
		}
		
		return myMonitor;
	}
	
	public String toString(){
		return "This is a custom WorkflowMonitorFactory implementation for the assignment 2.";
	}

}
