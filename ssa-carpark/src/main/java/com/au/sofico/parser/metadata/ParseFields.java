//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.09.16 at 03:55:01 PM EST 
//


package com.au.sofico.parser.metadata;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ParseFields" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="className" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mandatory" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="isList" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="padding" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="targetFieldLength" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="targetFieldName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="targetFieldType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "parseFields"
})
@XmlRootElement(name = "ParseFields")
public class ParseFields {

    @XmlElement(name = "ParseFields")
    protected List<ParseFields> parseFields;
    @XmlAttribute(name = "className")
    protected String className;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "mandatory")
    protected String mandatory;
    @XmlAttribute(name = "isList")
    protected Boolean isList;
    @XmlAttribute(name = "padding")
    protected String padding;
    @XmlAttribute(name = "targetFieldLength")
    protected BigInteger targetFieldLength;
    @XmlAttribute(name = "targetFieldName")
    protected String targetFieldName;
    @XmlAttribute(name = "targetFieldType", required = true)
    protected String targetFieldType;

    /**
     * Gets the value of the parseFields property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parseFields property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParseFields().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ParseFields }
     * 
     * 
     */
    public List<ParseFields> getParseFields() {
        if (parseFields == null) {
            parseFields = new ArrayList<ParseFields>();
        }
        return this.parseFields;
    }

    /**
     * Gets the value of the className property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassName() {
        return className;
    }

    /**
     * Sets the value of the className property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassName(String value) {
        this.className = value;
    }

    /**
     * Gets the value of the id property.
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
     * Sets the value of the id property.
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
     * Gets the value of the mandatory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMandatory() {
        return mandatory;
    }

    /**
     * Sets the value of the mandatory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMandatory(String value) {
        this.mandatory = value;
    }

    /**
     * Gets the value of the isList property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsList() {
        return isList;
    }

    /**
     * Sets the value of the isList property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsList(Boolean value) {
        this.isList = value;
    }

    /**
     * Gets the value of the padding property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPadding() {
        return padding;
    }

    /**
     * Sets the value of the padding property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPadding(String value) {
        this.padding = value;
    }

    /**
     * Gets the value of the targetFieldLength property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTargetFieldLength() {
        return targetFieldLength;
    }

    /**
     * Sets the value of the targetFieldLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTargetFieldLength(BigInteger value) {
        this.targetFieldLength = value;
    }

    /**
     * Gets the value of the targetFieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetFieldName() {
        return targetFieldName;
    }

    /**
     * Sets the value of the targetFieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetFieldName(String value) {
        this.targetFieldName = value;
    }

    /**
     * Gets the value of the targetFieldType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetFieldType() {
        return targetFieldType;
    }

    /**
     * Sets the value of the targetFieldType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetFieldType(String value) {
        this.targetFieldType = value;
    }

}
