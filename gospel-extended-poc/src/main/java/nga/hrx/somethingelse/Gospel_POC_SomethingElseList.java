package nga.hrx.somethingelse;

import java.util.Optional;
import java.util.logging.Level;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import nga.hrx.gospel.consumer.Client;

public class Gospel_POC_SomethingElseList {
	
	 @FunctionName("writetogospelx")
	    public HttpResponseMessage run(
	            @HttpTrigger(name = "req", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS , route = "recordlist") HttpRequestMessage<Optional<String>> request, 
	            final ExecutionContext context) {
	        // Parse query parameter
	        String id = null;
	        String bodyJson = request.getBody().orElse("{}");
	        context.getLogger().log(Level.INFO, bodyJson);
	        try {
	        	 Client client = new Client();
	        	 JSONObject req = new JSONObject(bodyJson);
	        	 JSONArray recs = req.getJSONArray("RecordList");
	        	 for (int i = 0; i < recs.length(); ++i) {
	        		    JSONObject rec = recs.getJSONObject(i);
	        		    client.addRecord(rec.toString());
	        		}
	        	 client.writeRecord();
	        	 id = "multipleIds";
	        	 return request.createResponseBuilder(HttpStatus.OK).body("{name:" + id +" }").build();
			} catch (Exception e) {
				context.getLogger().log(Level.SEVERE, ExceptionUtils.getStackTrace(e), e);
	            return request.createResponseBuilder(  HttpStatus.INTERNAL_SERVER_ERROR).body("{\"ErrorMessage\"  : " + ExceptionUtils.getStackTrace(e)  + "\"}" ).build(); 
			}

	    }
}
