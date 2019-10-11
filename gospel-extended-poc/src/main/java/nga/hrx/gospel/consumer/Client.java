package nga.hrx.gospel.consumer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
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
