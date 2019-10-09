package nga.hrx.gospel.consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import nga.hrx.utils.ApiException;

public class Client {
	
	private GospelRecord recordList;
	
	public Client() throws ApiException{
		try {
			this.recordList = new GospelRecord();
		} catch ( Exception e) {
			throw new ApiException( e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public   String  createRecord(String jsonString) throws ApiException {
		String id = null;
		try {
			Map<String, Object> fields = new ObjectMapper().readValue(jsonString, HashMap.class);
			id = (String) fields.get("id");
			String recordAction = (String) fields.get("RecordAction"); 
			String recordType = (String) fields.get("RecordType"); 
			if (id == null || recordAction == null || recordType == null)
				throw new ApiException("Missing msandatory values");
			GospelRecord recordz  = new GospelRecord();
			recordz.addRecord(id, recordType, RecordActionTypes.valueOf( recordAction) , (Map<String, Object>) fields.get("Fields"));
			recordz.writeRecord( Optional.of(id));
			return id;
		} catch ( Exception e) {
			throw new ApiException( e);
		}
	}
	
	public   String readRecord(String id, String type) throws ApiException {
		try {
			GospelRecord recordz  = new GospelRecord();
			return recordz.readRecord(id, type);
		} catch ( Exception e) {
			throw new ApiException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void addRecord(String jsonString) throws ApiException{
		String id = null;
		try {
			Map<String, Object> fields = new ObjectMapper().readValue(jsonString, HashMap.class);
			id = (String) fields.get("id");
			String recordAction = (String) fields.get("RecordAction"); 
			String recordType = (String) fields.get("RecordType"); 
			if (id == null || recordAction == null || recordType == null)
				throw new ApiException("Missing msandatory values");
			this.recordList.addRecord(id, recordType,  RecordActionTypes.valueOf( recordAction) , (Map<String, Object>) fields.get("Fields"));
		} catch ( Exception e) {
			throw new ApiException( e);
		}
	}
	
	public void writeRecord() throws ApiException {
		try {
			this.recordList.writeRecord(null);
		} catch ( Exception e) {
			throw new ApiException( e);
		}
	}
	
}
