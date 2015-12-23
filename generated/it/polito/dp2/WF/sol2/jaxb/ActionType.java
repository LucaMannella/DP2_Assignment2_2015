//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2015.12.23 alle 09:43:14 PM CET 
//


package it.polito.dp2.WF.sol2.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Classe Java per action_type complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="action_type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="simple_action">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="nextActions" type="{http://www.w3.org/2001/XMLSchema}IDREFS" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="process_action">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="nextProcess" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="role" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       &lt;attribute name="automInst" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "action_type", propOrder = {
    "simpleAction",
    "processAction"
})
public class ActionType {

    @XmlElement(name = "simple_action")
    protected ActionType.SimpleAction simpleAction;
    @XmlElement(name = "process_action")
    protected ActionType.ProcessAction processAction;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String name;
    @XmlAttribute(name = "role", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String role;
    @XmlAttribute(name = "automInst")
    protected Boolean automInst;

    /**
     * Recupera il valore della proprietà simpleAction.
     * 
     * @return
     *     possible object is
     *     {@link ActionType.SimpleAction }
     *     
     */
    public ActionType.SimpleAction getSimpleAction() {
        return simpleAction;
    }

    /**
     * Imposta il valore della proprietà simpleAction.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionType.SimpleAction }
     *     
     */
    public void setSimpleAction(ActionType.SimpleAction value) {
        this.simpleAction = value;
    }

    /**
     * Recupera il valore della proprietà processAction.
     * 
     * @return
     *     possible object is
     *     {@link ActionType.ProcessAction }
     *     
     */
    public ActionType.ProcessAction getProcessAction() {
        return processAction;
    }

    /**
     * Imposta il valore della proprietà processAction.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionType.ProcessAction }
     *     
     */
    public void setProcessAction(ActionType.ProcessAction value) {
        this.processAction = value;
    }

    /**
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà name.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Imposta il valore della proprietà name.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Recupera il valore della proprietà role.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRole() {
        return role;
    }

    /**
     * Imposta il valore della proprietà role.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Recupera il valore della proprietà automInst.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isAutomInst() {
        if (automInst == null) {
            return false;
        } else {
            return automInst;
        }
    }

    /**
     * Imposta il valore della proprietà automInst.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutomInst(Boolean value) {
        this.automInst = value;
    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="nextProcess" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ProcessAction {

        @XmlAttribute(name = "nextProcess", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String nextProcess;

        /**
         * Recupera il valore della proprietà nextProcess.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNextProcess() {
            return nextProcess;
        }

        /**
         * Imposta il valore della proprietà nextProcess.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNextProcess(String value) {
            this.nextProcess = value;
        }

    }


    /**
     * <p>Classe Java per anonymous complex type.
     * 
     * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="nextActions" type="{http://www.w3.org/2001/XMLSchema}IDREFS" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SimpleAction {

        @XmlAttribute(name = "nextActions")
        @XmlIDREF
        @XmlSchemaType(name = "IDREFS")
        protected List<Object> nextActions;

        /**
         * Gets the value of the nextActions property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the nextActions property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getNextActions().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Object }
         * 
         * 
         */
        public List<Object> getNextActions() {
            if (nextActions == null) {
                nextActions = new ArrayList<Object>();
            }
            return this.nextActions;
        }

    }

}
