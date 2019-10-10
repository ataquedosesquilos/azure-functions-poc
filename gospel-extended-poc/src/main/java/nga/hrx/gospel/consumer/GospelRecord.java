package nga.hrx.gospel.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import nga.hrx.utils.ApiException;
import tech.gospel.sdk.api.consumers.AsyncGospelSdk;
import tech.gospel.sdk.model.Record;
import tech.gospel.sdk.model.RecordType;

public class GospelRecord extends Record {
	
	private final int GOSPEL_TIMEOUT = 120;
	
	//private GospelConsumer gospelConsumer;
	private ArrayList<Record> record;
	private Thread gospelPropagation;
	
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
	/*public void writeRecord(Optional<String> id) throws GospelException {
		try {
			List<Record> records   = this.gospelConsumer.getSDK().createRecords(this.record).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
			for (Record recordX : records) {
			    System.out.println(recordX.toString());
			}
			
		} catch (InterruptedException | ExecutionException | TimeoutException  e) {
			throw new GospelException(e);
		}
	}
	
	public void writeRecordWithCallback(Optional<String> id) throws GospelException {
		this.gospelPropagation = new Thread(new GospelPropagationHandler(id.orElse(this.record.get(0).getId()),this.gospelConsumer));
		this.gospelPropagation.start();
		try {
			List<Record> records   =  this.gospelConsumer.getSDK().createRecords(this.record, this.gospelPropagation).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
			for (Record recordX : records) {
			    System.out.println(recordX.toString());
			}
			
		} catch (InterruptedException | ExecutionException | TimeoutException  e) {
			throw new GospelException(e);
		}
	}
	
	
	public String readRecord(String id, String recordType) throws GospelException {
		try {
			Record record = this.gospelConsumer.getSDK().getRecord(recordType, id).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
			JSONObject json = new JSONObject(record.getFields());
			return json.toString();
		} catch (Exception  e) {
			throw new GospelException(e);
		}
	}
	
	public AsyncGospelSdk getSDK() {
		return this.gospelConsumer.getSDK();
		
	} */
	
}
