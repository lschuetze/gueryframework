//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.06.23 at 02:18:43 PM NZST 
//


package nz.ac.massey.cs.guery.io.xml;

import javax.annotation.Generated;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for notConnectedBy complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="notConnectedBy">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="constraint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *       &lt;attribute name="from" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="to" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *       &lt;attribute name="minLength" type="{http://www.w3.org/2001/XMLSchema}int" default="1" />
 *       &lt;attribute name="maxLength" type="{http://www.w3.org/2001/XMLSchema}int" default="-1" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "notConnectedBy", propOrder = {
        "constraint"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class NotConnectedBy {

    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected List<String> constraint;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String role;
    @XmlAttribute
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected Object from;
    @XmlAttribute
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected Object to;
    @XmlAttribute
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected Integer minLength;
    @XmlAttribute
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected Integer maxLength;

    /**
     * Gets the value of the constraint property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the constraint property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConstraint().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public List<String> getConstraint() {
        if (constraint == null) {
            constraint = new ArrayList<String>();
        }
        return this.constraint;
    }

    /**
     * Gets the value of the role property.
     *
     * @return possible object is
     * {@link String }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public String getRole() {
        return role;
    }

    /**
     * Sets the value of the role property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Gets the value of the from property.
     *
     * @return possible object is
     * {@link Object }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public Object getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     *
     * @param value allowed object is
     *              {@link Object }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setFrom(Object value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     *
     * @return possible object is
     * {@link Object }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public Object getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     *
     * @param value allowed object is
     *              {@link Object }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setTo(Object value) {
        this.to = value;
    }

    /**
     * Gets the value of the minLength property.
     *
     * @return possible object is
     * {@link Integer }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public int getMinLength() {
        if (minLength == null) {
            return 1;
        } else {
            return minLength;
        }
    }

    /**
     * Sets the value of the minLength property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setMinLength(Integer value) {
        this.minLength = value;
    }

    /**
     * Gets the value of the maxLength property.
     *
     * @return possible object is
     * {@link Integer }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public int getMaxLength() {
        if (maxLength == null) {
            return -1;
        } else {
            return maxLength;
        }
    }

    /**
     * Sets the value of the maxLength property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    public void setMaxLength(Integer value) {
        this.maxLength = value;
    }

}
