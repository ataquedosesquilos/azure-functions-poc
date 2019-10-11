package nga.hrx.gospel.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

import nga.hrx.utils.ApiException;
import tech.gospel.sdk.model.Record;
import tech.gospel.sdk.model.RecordType;

public class GospelRecord extends Record {
	
	
	//private GospelConsumer gospelConsumer;
	private ArrayList<Record> record;
	
	public GospelRecord(String privateKey, String publicKey, String gospelUrl) throws GospelException {
	//	this.gospelConsumer =  new GospelConsumer(privateKey, publicKey, gospelUrl);
		this.record = new ArrayList<Record>();
	}
	
	public GospelRecord()  {
		//this.gospelTimeout = Integer.parseInt(Utils.getEnvironmentConfig("GospelTimeout"));
		//this.gospelConsumer = new GospelConsumer();
		this.record = new ArrayList<Record>();
	}
	
	public void addRecord(String id, String recordType, RecordActionTypes recordAction, java.util.Map<java.lang.String,java.lang.Object> fields){
		Record record = new Record();
		record.setFields(fields);
		record.setId(id);
		record.setRecordAction(recordAction + "");
		RecordType type = new RecordType();
		type.setName(recordType);
		record.setType(type);
		this.record.add(record);
	}
	
	@SuppressWarnings("unchecked")
	public void addRecord(String jsonString) throws ApiException {
		String id = null;
		try {
			Map<String, Object> fields = new ObjectMapper().readValue(jsonString, HashMap.class);
			id = (String) fields.get("id");
			String recordAction = (String) fields.get("RecordAction"); 
			String recordType = (String) fields.get("RecordType"); 
			if (id == null || recordAction == null || recordType == null)
				throw new ApiException("Missing msandatory values");
			this.addRecord(id, recordType,  RecordActionTypes.valueOf( recordAction) , (Map<String, Object>) fields.get("Fields"));
		} catch ( Exception e) {
			throw new ApiException( e);
		}
	}
	
	
	public  ArrayList<Record> getRecords(){
		return this.record;
	}
	
}
