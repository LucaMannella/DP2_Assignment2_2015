//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2015.12.30 alle 11:22:13 PM CET 
//


package it.polito.dp2.WF.sol2.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.polito.dp2.WF.sol2.jaxb package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.polito.dp2.WF.sol2.jaxb
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Actors }
     * 
     */
    public Actors createActors() {
        return new Actors();
    }

    /**
     * Create an instance of {@link Process }
     * 
     */
    public Process createProcess() {
        return new Process();
    }

    /**
     * Create an instance of {@link ActionType }
     * 
     */
    public ActionType createActionType() {
        return new ActionType();
    }

    /**
     * Create an instance of {@link Actors.Actor }
     * 
     */
    public Actors.Actor createActorsActor() {
        return new Actors.Actor();
    }

    /**
     * Create an instance of {@link Process.ActionStatus }
     * 
     */
    public Process.ActionStatus createProcessActionStatus() {
        return new Process.ActionStatus();
    }

    /**
     * Create an instance of {@link Workflow }
     * 
     */
    public Workflow createWorkflow() {
        return new Workflow();
    }

    /**
     * Create an instance of {@link WorkflowManager }
     * 
     */
    public WorkflowManager createWorkflowManager() {
        return new WorkflowManager();
    }

    /**
     * Create an instance of {@link ActionType.SimpleAction }
     * 
     */
    public ActionType.SimpleAction createActionTypeSimpleAction() {
        return new ActionType.SimpleAction();
    }

    /**
     * Create an instance of {@link ActionType.ProcessAction }
     * 
     */
    public ActionType.ProcessAction createActionTypeProcessAction() {
        return new ActionType.ProcessAction();
    }

}
