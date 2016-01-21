package it.polito.dp2.WF.myTests;

import it.polito.dp2.WF.FactoryConfigurationError;
import it.polito.dp2.WF.WorkflowMonitor;
import it.polito.dp2.WF.WorkflowMonitorException;
import it.polito.dp2.WF.WorkflowMonitorFactory;

public class WFParser {
	
	public static void main(String[] args) {
		
		//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss:SS z");
		WorkflowMonitor testWorkflowMonitor=null;
		
		//set system property
		System.setProperty("it.polito.dp2.WF.sol2.WorkflowInfo.file", "xsd/WFInfo.xml");
        System.setProperty("it.polito.dp2.WF.WorkflowMonitorFactory", "it.polito.dp2.WF.sol2.WorkflowMonitorFactory");
        
        // Create my implementation
        try {
			testWorkflowMonitor = WorkflowMonitorFactory.newInstance().newWorkflowMonitor();
		} catch (WorkflowMonitorException | FactoryConfigurationError e) {
			System.err.println("Could not instantiate the manager class: "+e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
        
        System.out.println(testWorkflowMonitor.toString());
	
	}

}
