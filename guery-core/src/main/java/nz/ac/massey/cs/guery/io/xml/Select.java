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
 * <p>Java class for select complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="select">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="constraint" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="role" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "select", propOrder = {
        "constraint"
})
@Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
public class Select {

    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected List<String> constraint;
    @XmlAttribute
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    @Generated(value = "com.sun.tools.internal.xjc.Driver", date = "2010-06-23T02:18:43+12:00", comments = "JAXB RI vJAXB 2.1.10 in JDK 6")
    protected String role;

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

}
