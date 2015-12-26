//
// Questo file è stato generato dall'architettura JavaTM per XML Binding (JAXB) Reference Implementation, v2.2.8-b130911.1802 
// Vedere <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2015.12.26 alle 07:54:46 PM CET 
//


package it.polito.dp2.WF.sol2.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per anonymous complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * <complexType>
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="action_status" maxOccurs="unbounded">
 *           <complexType>
 *             <complexContent>
 *               <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 <attribute name="action" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 <attribute name="actor" type="{http://www.w3.org/2001/XMLSchema}token" />
 *                 <attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *                 <attribute name="terminated" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                 <attribute name="takenInCharge" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *               </restriction>
 *             </complexContent>
 *           </complexType>
 *         </element>
 *       </sequence>
 *       <attribute name="code" type="{http://www.w3.org/2001/XMLSchema}token" />
 *       <attribute name="started" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       <attribute name="workflow" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "actionStatus"
})
@XmlRootElement(name = "process")
public class Process {

    @XmlElement(name = "action_status", required = true)
    protected List<Process.ActionStatus> actionStatus;
    @XmlAttribute(name = "code")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String code;
    @XmlAttribute(name = "started", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar started;
    @XmlAttribute(name = "workflow", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "token")
    protected String workflow;

    /**
     * Gets the value of the actionStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the actionStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActionStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Process.ActionStatus }
     * 
     * 
     */
    public List<Process.ActionStatus> getActionStatus() {
        if (actionStatus == null) {
            actionStatus = new ArrayList<Process.ActionStatus>();
        }
        return this.actionStatus;
    }

    /**
     * Recupera il valore della proprietà code.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Imposta il valore della proprietà code.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Recupera il valore della proprietà started.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStarted() {
        return started;
    }

    /**
     * Imposta il valore della proprietà started.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStarted(XMLGregorianCalendar value) {
        this.started = value;
    }

    /**
     * Recupera il valore della proprietà workflow.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWorkflow() {
        return workflow;
    }

    /**
     * Imposta il valore della proprietà workflow.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWorkflow(String value) {
        this.workflow = value;
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
     *       &lt;attribute name="action" use="required" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="actor" type="{http://www.w3.org/2001/XMLSchema}token" />
     *       &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
     *       &lt;attribute name="terminated" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *       &lt;attribute name="takenInCharge" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ActionStatus {

        @XmlAttribute(name = "action", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String action;
        @XmlAttribute(name = "actor")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "token")
        protected String actor;
        @XmlAttribute(name = "timestamp")
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar timestamp;
        @XmlAttribute(name = "terminated", required = true)
        protected boolean terminated;
        @XmlAttribute(name = "takenInCharge", required = true)
        protected boolean takenInCharge;

        /**
         * Recupera il valore della proprietà action.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAction() {
            return action;
        }

        /**
         * Imposta il valore della proprietà action.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAction(String value) {
            this.action = value;
        }

        /**
         * Recupera il valore della proprietà actor.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getActor() {
            return actor;
        }

        /**
         * Imposta il valore della proprietà actor.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setActor(String value) {
            this.actor = value;
        }

        /**
         * Recupera il valore della proprietà timestamp.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getTimestamp() {
            return timestamp;
        }

        /**
         * Imposta il valore della proprietà timestamp.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setTimestamp(XMLGregorianCalendar value) {
            this.timestamp = value;
        }

        /**
         * Recupera il valore della proprietà terminated.
         * 
         */
        public boolean isTerminated() {
            return terminated;
        }

        /**
         * Imposta il valore della proprietà terminated.
         * 
         */
        public void setTerminated(boolean value) {
            this.terminated = value;
        }

        /**
         * Recupera il valore della proprietà takenInCharge.
         * 
         */
        public boolean isTakenInCharge() {
            return takenInCharge;
        }

        /**
         * Imposta il valore della proprietà takenInCharge.
         * 
         */
        public void setTakenInCharge(boolean value) {
            this.takenInCharge = value;
        }

    }

}
