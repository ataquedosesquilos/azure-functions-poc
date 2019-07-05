package com.nga.transformation.transformation_services_joao;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class TestXmlToMap {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><nga:ProcessPayServEmp languageCode=\"en-US\" releaseID=\"90\" systemEnvironmentCode=\"aZURE\"><oa:ApplicationArea><oa:Sender><oa:LogicalID>ZZA-VN002-1001</oa:LogicalID><oa:ComponentID>PAYROLL</oa:ComponentID><oa:ReferenceID>jowowe</oa:ReferenceID><oa:ConfirmationCode>Always</oa:ConfirmationCode></oa:Sender><oa:CreationDateTime>2018-07-27T03:46:18</oa:CreationDateTime><oa:BODID>32C331C6-8062-46B5-82FF-A36A607E7090</oa:BODID></oa:ApplicationArea><nga:DataArea><oa:Process><oa:ActionCriteria><oa:ActionExpression actionCode=\"ADD\"/></oa:ActionCriteria></oa:Process><nga:PayServEmp><hr:IndicativeData><hr:IndicativePersonDossier><hr:IndicativePerson validFrom=\"2018-07-01\"><hr:PersonID>yadTJgXqWMSi</hr:PersonID><hr:PersonName><oa:GivenName>Kevin</oa:GivenName><hr:FamilyName>Rogers</hr:FamilyName></hr:PersonName><hr:Communication><hr:ChannelCode>Email</hr:ChannelCode><hr:UseCode>Business</hr:UseCode><oa:URI>Kevin.Rogers11@ngahr.com</oa:URI></hr:Communication><hr:BirthDate>1981-01-01</hr:BirthDate><hr:BirthPlace><oa:CountrySubDivisionCode>NSW</oa:CountrySubDivisionCode><hr:CountryCode>AU</hr:CountryCode></hr:BirthPlace><hr:GenderCode>Male</hr:GenderCode><hr:CertifiedMaritalStatus><hr:MaritalStatusCode/></hr:CertifiedMaritalStatus><hr:PrimaryLanguageCode>en_US</hr:PrimaryLanguageCode></hr:IndicativePerson><hr:IndicativeEmployee validFrom=\"2018-07-01\"><hr:EmployeeID>yadTJgXqWMSiZZAU</hr:EmployeeID><hr:EmployeeGroupCode>180</hr:EmployeeGroupCode></hr:IndicativeEmployee><hr:IndicativeEmployment><hr:ProposedHire><hr:ExpectedDutyEntryDate>2018-07-01</hr:ExpectedDutyEntryDate></hr:ProposedHire><hr:EmploymentLifecycle validFrom=\"2018-07-01\"><hr:Hire><hr:HireTypeCode>NewHire</hr:HireTypeCode><hr:HireDate>2018-07-01</hr:HireDate><hr:OriginalHireDate>2018-07-01</hr:OriginalHireDate></hr:Hire></hr:EmploymentLifecycle></hr:IndicativeEmployment><hr:IndicativeDeployment validFrom=\"2018-07-01\"><hr:DeploymentOrganization><hr:OrganizationIdentifiers><hr:OrganizationID>ZZAU</hr:OrganizationID></hr:OrganizationIdentifiers><hr:OrganizationalIdentifiers><hr:OrganizationalName>AUS B</hr:OrganizationalName></hr:OrganizationalIdentifiers></hr:DeploymentOrganization><hr:WorkLocation><hr:LocationID>0002</hr:LocationID><hr:Address><oa:CityName>Melbourne</oa:CityName><oa:CountrySubDivisionCode>Victoria</oa:CountrySubDivisionCode><oa:PostalCode>3000</oa:PostalCode></hr:Address></hr:WorkLocation><hr:Job><oa:JobTitle>Integration Engineer</oa:JobTitle></hr:Job><hr:IndicativePosition><hr:PositionTitle>Integration Engineer</hr:PositionTitle></hr:IndicativePosition><hr:Schedule><hr:ScheduledHours scheduleBasis=\"Week\">40</hr:ScheduledHours><hr:ScheduledHours scheduleBasis=\"PayCycle\">173.33</hr:ScheduledHours><hr:ScheduledDays scheduleBasis=\"Week\">5</hr:ScheduledDays><hr:DaySchedule><hr:ID>WORK_SHIFT-6-8</hr:ID></hr:DaySchedule></hr:Schedule><hr:WorkLevelCode>FullTime</hr:WorkLevelCode><hr:FullTimeEquivalentRatio>1.000</hr:FullTimeEquivalentRatio></hr:IndicativeDeployment><hr:PayCycleRemuneration validFrom=\"2018-07-01\"><hr:PayGroupCode>VN</hr:PayGroupCode></hr:PayCycleRemuneration></hr:IndicativePersonDossier></hr:IndicativeData><nga:PayServEmpExtension><nga:CostAssignment validFrom=\"2018-07-01\"><nga:CostCenterCode>0000010000</nga:CostCenterCode><oa:Percentage>100</oa:Percentage></nga:CostAssignment><nga:PayScales validFrom=\"2018-07-01\"><nga:PayScaleGroup>Non-Management</nga:PayScaleGroup><nga:PayScaleLevel>Non-Management</nga:PayScaleLevel></nga:PayScales><nga:DateSpecifications><nga:Date DateType=\"Active_Status_Date\">2018-07-01</nga:Date><nga:Date DateType=\"Hire_Date\">2018-07-01</nga:Date><nga:Date DateType=\"ContinuousServiceDate\">2018-07-01</nga:Date><nga:Date DateType=\"SeniorityDate\">2018-07-01</nga:Date></nga:DateSpecifications><nga:PayrollSpecificGroupings><nga:PayrollSpecificGrouping1 validFrom=\"2018-07-01\">1</nga:PayrollSpecificGrouping1><nga:PayrollSpecificGrouping2 validFrom=\"2018-07-01\">2</nga:PayrollSpecificGrouping2><nga:PayrollSpecificGrouping3 validFrom=\"2018-07-01\">3</nga:PayrollSpecificGrouping3><nga:PayrollSpecificGrouping4 validFrom=\"2018-07-01\">4</nga:PayrollSpecificGrouping4><nga:PayrollSpecificGrouping5 validFrom=\"2018-07-01\">5</nga:PayrollSpecificGrouping5><nga:PayrollSpecificGrouping6 validFrom=\"2018-07-01\">6</nga:PayrollSpecificGrouping6><nga:PayrollSpecificGrouping7 validFrom=\"2018-07-01\">7</nga:PayrollSpecificGrouping7><nga:PayrollSpecificGrouping8 validFrom=\"2018-07-01\">8</nga:PayrollSpecificGrouping8><nga:PayrollSpecificGrouping9 validFrom=\"2018-07-01\">9</nga:PayrollSpecificGrouping9><nga:PayrollSpecificGrouping10 validFrom=\"2018-07-01\">10</nga:PayrollSpecificGrouping10></nga:PayrollSpecificGroupings><nga:AlternateIdentifiers><nga:AlternateID>yadTJgXqWMSi</nga:AlternateID><nga:AlternateID Type=\"AUS-TFN\">630493843</nga:AlternateID></nga:AlternateIdentifiers><nga:AlternateDescriptions validFrom=\"2018-07-01\"><nga:Description Type=\"HireReason\">Hire_Employee_New_Hire_Fill_Vacancy</nga:Description></nga:AlternateDescriptions></nga:PayServEmpExtension><nga:PayServEmpPayElements><nga:PayElement validFrom=\"2018-07-01\"><hr:ID>0010</hr:ID><nga:PayElementType>RECURRING</nga:PayElementType><nga:Amount>4500.00</nga:Amount><hr:CurrencyCode>AUD</hr:CurrencyCode></nga:PayElement><nga:PayElement validFrom=\"2018-07-01\"><hr:ID>0990</hr:ID><nga:PayElementType>RECURRING</nga:PayElementType><nga:Amount>54000.00</nga:Amount><hr:CurrencyCode>AUD</hr:CurrencyCode></nga:PayElement></nga:PayServEmpPayElements></nga:PayServEmp></nga:DataArea></nga:ProcessPayServEmp>";
		XmlToMap xml2Map = new XmlToMap();
		System.out.println(xml2Map.xmlToMap(xml));
	}

}
