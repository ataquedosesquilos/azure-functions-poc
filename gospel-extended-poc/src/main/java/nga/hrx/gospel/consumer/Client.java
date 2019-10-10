package nga.hrx.gospel.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.ExecutionContext;


import nga.hrx.utils.ApiException;
import tech.gospel.sdk.model.Record;

public class Client {
	private final int GOSPEL_TIMEOUT = 120;
	private GospelConsumer gospelConsumer;
	
	public Client(){
		try {
			this.gospelConsumer = new GospelConsumer();
		} catch ( Exception e) {
			
		}
	}
	
	public Client(ExecutionContext context){
		try {
			this.gospelConsumer = new GospelConsumer();
		} catch ( Exception e) {
			context.getLogger().log(Level.SEVERE, ExceptionUtils.getStackTrace(e), e);
		}
	}
	
/*	@SuppressWarnings("unchecked")
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
	}*/
	
	public void writeRecord( ArrayList<Record> record) throws ApiException {
		try {
			this.gospelConsumer.getSDK().createRecords(record).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
		} catch ( Exception e) {
			throw new ApiException( e);
		}
	}
	
	public   String readRecord(String id, String type) throws ApiException {
		try {
			Record record= this.gospelConsumer.getSDK().getRecord(type, id).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
			JSONObject json = new JSONObject(record.getFields());
			return json.toString();
		} catch ( Exception e) {
			throw new ApiException(e);
		}
	}
	
}
