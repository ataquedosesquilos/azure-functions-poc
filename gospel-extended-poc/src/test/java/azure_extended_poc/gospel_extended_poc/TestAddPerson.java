package azure_extended_poc.gospel_extended_poc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;

import nga.hrx.gospel.consumer.GospelException;
import nga.hrx.gospel.consumer.GospelRecord;
import nga.hrx.gospel.consumer.RecordActionTypes;

public class TestAddPerson {

	public static void main(String[] args) throws GospelException {
		System.out.println("executing ....");
		GospelRecord recordz  = new GospelRecord();
		System.out.println("connection established ....");
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("GenderCode", "MALE");
		fields.put("PersonID", "Ajoaotest");
		fields.put("FamilyName", "Alves");
		fields.put("BODID", "_test");
		fields.put("PreferredSalutationCode", "boss");
		fields.put("GCC", "OWN");
		fields.put("GivenName", "Da boss");
		fields.put("PreferredName", "Da boss");
		fields.put("MiddleName", "Da boss");
		fields.put("EmailAddress", "pitbull@jlow.com");
		fields.put("BirthDate", "00-00-0000");
		recordz.addRecord("Ajoaotest", "PERSON", RecordActionTypes.INSERT , fields);
		JSONObject json = new JSONObject(fields);
		System.out.print(json.toString());

		recordz.writeRecord(Optional.empty());
		
		System.out.println("Record is written" + recordz.toString());
	
		
	}

}
