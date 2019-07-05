package com.nga.transformation.transformation_services_joao;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlToMap {
	private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	private String bodId;
	
	public String getBodId() {
		return bodId;
	}

	
	public Map<String, Object> xmlToMap(String xmlString) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilder docBuilder = this.docBuilderFactory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xmlString));
		Document doc = docBuilder.parse(is);
		doc.getDocumentElement().normalize();
		doc.getElementsByTagName("");
		
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("BODID", this.getValueFromDoc("oa:BODID", doc));
		this.bodId = this.getValueFromDoc("oa:BODID", doc);
		fields.put("LCC", this.getValueFromDoc("oa:LogicalID", doc).split("-")[1]);
		fields.put("ComponentID", this.getValueFromDoc("oa:ComponentID", doc));
		fields.put("ReferenceID", this.getValueFromDoc("oa:ReferenceID", doc));
		fields.put("ConfirmationCode", this.getValueFromDoc("oa:ConfirmationCode", doc));
		fields.put("LogicalID", this.getValueFromDoc("oa:LogicalID", doc));
		fields.put("CreationDateTime", this.getValueFromDoc("oa:CreationDateTime", doc));
		fields.put("Status", "NEW");
		fields.put("ActionExpression", this.getAttributeFromDoc("actionCode","oa:ActionExpression", doc));
		fields.put("PersonID", this.getValueFromDoc("hr:PersonID", doc));
		fields.put("PersonLegalID", this.getValueFromDoc("hr:PersonLegalID", doc));
		fields.put("GivenName", this.getValueFromDoc("oa:GivenName", doc));
		fields.put("FamilyName", this.getValueFromDoc("hr:FamilyName", doc));
		fields.put("BirthDate", this.getValueFromDoc("hr:BirthDate", doc));
		fields.put("CountrySubDivisionCode", this.getValueFromDoc("oa:CountrySubDivisionCode", doc));
		fields.put("CountryCode", this.getValueFromDoc("hr:CountryCode", doc));
		fields.put("GenderCode", this.getValueFromDoc("hr:GenderCode", doc));
		fields.put("MaritalStatusCode", this.getValueFromDoc("hr:MaritalStatusCode", doc));
		fields.put("EmploymentLifecycleValidFrom", this.getAttributeFromDoc("validFrom","hr:EmploymentLifecycle", doc));
		fields.put("EmployeeID", this.getValueFromDoc("hr:EmployeeID", doc));
		fields.put("EmployeeGroupCode", this.getValueFromDoc("hr:EmployeeGroupCode", doc));
		fields.put("HireTypeCode", this.getValueFromDoc("hr:HireTypeCode", doc));
		fields.put("HireDate", this.getValueFromDoc("hr:HireDate", doc));
		fields.put("IndicativeDeploymentValidFrom", this.getAttributeFromDoc("validFrom","hr:IndicativeDeployment", doc));
		fields.put("OrganizationID", this.getValueFromDoc("hr:OrganizationID", doc));
		fields.put("LocationID", this.getValueFromDoc("hr:LocationID", doc));
		fields.put("CityName", this.getValueFromDoc("oa:CityName", doc));
		fields.put("CountrySubDivisionCode", this.getValueFromDoc("oa:CountrySubDivisionCode", doc));
		fields.put("PostalCode", this.getValueFromDoc("oa:PostalCode", doc));
		fields.put("JobTitle", this.getValueFromDoc("oa:JobTitle", doc));
		fields.put("DayScheduleId", this.getNestValueFromDoc("hr:DaySchedule","hr:ID", doc));
		fields.put("WorkLevelCode", this.getValueFromDoc("hr:WorkLevelCode", doc));
		fields.put("PayGroupCode", this.getValueFromDoc("hr:PayGroupCode", doc));
	
		return fields;
	}
	
	private String getValueFromDoc(String tagName, Document doc) {
		if(doc.getElementsByTagName(tagName) != null) {
			if(doc.getElementsByTagName(tagName).item(0) != null)
				return doc.getElementsByTagName(tagName).item(0).getTextContent();
			else
				return null;
		}else
			return null;
	}
	
	private String getNestValueFromDoc(String rootName, String tagName, Document doc) {
		if(doc.getElementsByTagName(rootName) != null) {
			if(doc.getElementsByTagName(rootName).item(0) != null) {
				if (doc.getElementsByTagName(rootName).item(0).getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) doc.getElementsByTagName(rootName).item(0);
					if(eElement.getElementsByTagName(tagName) != null)
						return eElement.getElementsByTagName(tagName).item(0).getTextContent();
					else
						return null;
				}else 
					return null;
			
			}else
				return null;
		}else
			return null;
	}
	
	private String getAttributeFromDoc(String attName, String tagName, Document doc) {
		if(doc.getElementsByTagName(tagName) != null) {
			if(doc.getElementsByTagName(tagName).item(0) != null) {
				return doc.getElementsByTagName(tagName).item(0).getAttributes().getNamedItem(attName).getTextContent();
			}else
				return null;
		}else 
			return null;

	}
}
