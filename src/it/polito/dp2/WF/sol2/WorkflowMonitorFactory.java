package it.polito.dp2.WF.sol2;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import it.polito.dp2.WF.WorkflowMonitor;
import it.polito.dp2.WF.WorkflowMonitorException;

/**
 * This is a concrete implementation of the interface {@link it.polito.dp2.WF.WorkflowMonitorFactory}.
 * 
 * @author Luca
 */
public class WorkflowMonitorFactory extends it.polito.dp2.WF.WorkflowMonitorFactory {

	/**
	 * This method creates an instance of the {@link ConcreteWorkflowMonitor} class, 
	 * a concrete implementation of the {@link WorkflowMonitor} interface.
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
	
	//toString() implemented for debugging purposes
	@Override
	public String toString(){
		return "This is a custom WorkflowMonitorFactory implementation for the assignment 2.";
	}

}
