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
			long lStartTime = System.nanoTime();
			this.gospelConsumer = new GospelConsumer();
			long lEndTime = System.nanoTime();
			Client.appInsight.trackMetric("Instantiate SDK",( lEndTime - lStartTime)/1000);
		} catch ( Exception e) {
			
		}
	}
	
	public Client(ExecutionContext context){
		try {
			long lStartTime = System.nanoTime();
			this.gospelConsumer = new GospelConsumer();
			 long lEndTime = System.nanoTime();
			 Client.appInsight.trackMetric("Instantiate SDK",( lEndTime - lStartTime)/1000);
		} catch ( Exception e) {
			context.getLogger().log(Level.SEVERE, ExceptionUtils.getStackTrace(e), e);
		}
	}
	
	public void writeRecord( ArrayList<Record> record) throws ApiException {
		try {
			long lStartTime = System.nanoTime();
			this.gospelConsumer.getSDK().createRecords(record).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
			 long lEndTime = System.nanoTime();
			 Client.appInsight.trackMetric("write records via SDK", (lEndTime - lStartTime)/1000);
			 Client.appInsight.trackDependency("WriteRecordToGospel", "HTTPPOST", lEndTime - lStartTime, true);
		} catch ( Exception e) {
			throw new ApiException( e);
		}
	}
	
	public   String readRecord(String id, String type) throws ApiException {
		try {
			long lStartTime = System.nanoTime();
			Record record= this.gospelConsumer.getSDK().getRecord(type, id).get(GOSPEL_TIMEOUT, TimeUnit.SECONDS);
			 long lEndTime = System.nanoTime();
			 Client.appInsight.trackMetric("read record via SDK", (lEndTime - lStartTime)/1000);
			 Client.appInsight.trackDependency("ReadRecordFromGospel", "HTTPGET", lEndTime - lStartTime, true);
			JSONObject json = new JSONObject(record.getFields());
			return json.toString();
		} catch ( Exception e) {
			throw new ApiException(e);
		}
	}
	
}
