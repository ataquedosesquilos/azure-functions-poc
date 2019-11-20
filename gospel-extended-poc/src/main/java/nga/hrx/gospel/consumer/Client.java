package nga.hrx.gospel.consumer;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;
import com.microsoft.azure.functions.ExecutionContext;

import nga.hrx.azure.consumer.AppInsight;
import nga.hrx.utils.ApiException;
import tech.gospel.sdk.model.Record;

public class Client {
	private final int GOSPEL_TIMEOUT = 120;
	private GospelConsumer gospelConsumer;
	private static AppInsight appInsight = new AppInsight();
	
	public Client(){
		try {
			long lStartTime = System.currentTimeMillis();
			this.gospelConsumer = new GospelConsumer();
			long lEndTime = System.currentTimeMillis();
			Client.appInsight.trackMetric("Instantiate SDK", lEndTime - lStartTime);
		} catch ( Exception e) {
			Client.appInsight.trackException(e);
		}
	}
	
	public Client(ExecutionContext context){
		try {
			long lStartTime = System.currentTimeMillis();
			this.gospelConsumer = new GospelConsumer();
			 long lEndTime = System.currentTimeMillis();
			 Client.appInsight.trackMetric("Instantiate SDK",lEndTime - lStartTime);
		} catch ( Exception e) {
			context.getLogger().log(Level.SEVERE, ExceptionUtils.getStackTrace(e), e);
			Client.appInsight.trackException(e);
		}
	}
	
	public void writeRecord( ArrayList<Record> record) throws ApiException {
		try {
			long lStartTime = System.currentTimeMillis();
			this.gospelConsumer.getSDK().createRecords(record, this.gospelConsumer.getDefinitions()).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
			 long lEndTime = System.currentTimeMillis();
			 Client.appInsight.trackMetric("write records via SDK", lEndTime - lStartTime);
		} catch ( Exception e) {
			Client.appInsight.trackException(e);
			throw new ApiException( e);
		}
	}
	

	
	public   String readRecord(String id, String type) throws ApiException {
		try {
			long lStartTime = System.currentTimeMillis();
			Record record= this.gospelConsumer.getSDK().getRecord(type, id).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
			 long lEndTime = System.currentTimeMillis();
			 Client.appInsight.trackMetric("read record via SDK", lEndTime - lStartTime);
			JSONObject json = new JSONObject(record.getFields());
			return json.toString();
		} catch ( Exception e) {
			Client.appInsight.trackException(e);
			throw new ApiException(e);
		}
	}
	
	public String getFieldFromRecord(String id, String type, String fieldName) throws ApiException {
		try {
			long lStartTime = System.currentTimeMillis();
			Record record= this.gospelConsumer.getSDK().getRecord(type, id).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
			 long lEndTime = System.currentTimeMillis();
			 Client.appInsight.trackMetric("read record via SDK", lEndTime - lStartTime);
			 return record.getFields().get("EmployeeKey").toString();
		} catch ( Exception e) {
			Client.appInsight.trackException(e);
			throw new ApiException(e);
		}
	}
	
}
