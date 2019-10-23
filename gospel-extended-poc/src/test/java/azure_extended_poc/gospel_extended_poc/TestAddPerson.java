package azure_extended_poc.gospel_extended_poc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import nga.hrx.gospel.consumer.Client;
import nga.hrx.gospel.consumer.GospelException;
import nga.hrx.gospel.consumer.GospelRecord;
import nga.hrx.gospel.consumer.RecordActionTypes;
import nga.hrx.utils.ApiException;

public class TestAddPerson {

	public static void main(String[] args) throws GospelException, ApiException {
		System.out.println("executing ....");
		GospelRecord recordz  = new GospelRecord();
		System.out.println("connection established ....");
		Map<String, Object> fields = new HashMap<String, Object>();
		String id = "A" +  UUID.randomUUID().toString();

		fields.put("GenderCode", "MALE");
		fields.put("PersonID", id);
		fields.put("FamilyName", "Alves");
		fields.put("BODID", "_test");
		fields.put("PreferredSalutationCode", "boss");
		fields.put("GCC", "OWN");
		fields.put("GivenName", "Da boss");
		fields.put("PreferredName", "Da boss");
		fields.put("MiddleName", "Da boss");
		fields.put("EmailAddress", "pitbull@jlow.com");
		fields.put("BirthDate", "00-00-0000");
		recordz.addRecord(id, "PERSON", RecordActionTypes.INSERT , fields);
		JSONObject json = new JSONObject(fields);
		System.out.print(json.toString());
		Client client = new Client();
		System.out.println("instantiated gospel consumer/client");
		client.writeRecord(recordz.getRecords());
		
		System.out.println("Record is written" + recordz.toString());
	
		
	}

}
