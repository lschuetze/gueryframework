//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.06.23 at 02:18:43 PM NZST 
//


package nz.ac.massey.cs.guery.io.xml;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the nz.ac.massey.cs.guery.xml package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nz.ac.massey.cs.guery.xml
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Select }
     */
    public Select createSelect() {
        return new Select();
    }

    /**
     * Create an instance of {@link Motif }
     */
    public Motif createMotif() {
        return new Motif();
    }

    /**
     * Create an instance of {@link Annotate }
     */
    public Annotate createAnnotate() {
        return new Annotate();
    }

    /**
     * Create an instance of {@link NotConnectedBy }
     */
    public NotConnectedBy createNotConnectedBy() {
        return new NotConnectedBy();
    }

    /**
     * Create an instance of {@link GroupBy }
     */
    public GroupBy createGroupBy() {
        return new GroupBy();
    }

    /**
     * Create an instance of {@link ConnectedBy }
     */
    public ConnectedBy createConnectedBy() {
        return new ConnectedBy();
    }

}
