package nga.hrx.gospel;

import java.util.Optional;
import java.util.logging.Level;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import nga.hrx.gospel.consumer.Client;
import nga.hrx.utils.ApiException;

public class Gospel_Extended_POC {

	 @FunctionName("writetogospel")
	    public HttpResponseMessage run(
	            @HttpTrigger(name = "req", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS , route = "records") HttpRequestMessage<Optional<String>> request, 
	            final ExecutionContext context) {

	        // Parse query parameter
	        String id = null;
	        String bodyJson = request.getBody().orElse("{}");
	        context.getLogger().log(Level.INFO, bodyJson);
	        try {
				id = Client.createRecord(bodyJson);
			} catch (ApiException e) {
				context.getLogger().log(Level.SEVERE, ExceptionUtils.getStackTrace(e), e);
	            return request.createResponseBuilder(  HttpStatus.INTERNAL_SERVER_ERROR).body("{\"ErrorMessage\"  : " + ExceptionUtils.getStackTrace(e)  + "\"}" ).build(); 
			}

	        if (id == null) {
	            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
	        } else {
	        	 context.getLogger().log(Level.INFO, "{name:" + id +" }");
	            return request.createResponseBuilder(HttpStatus.OK).body("{name:" + id +" }").build();
	        }
	    }
	 
	 
	 @FunctionName("readfromgospel")
	    public HttpResponseMessage get(
	            @HttpTrigger(name = "req", methods = { HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS , route = "records/{id}") HttpRequestMessage<Optional<String>> request,  @BindingName("id") String id,  
	            final ExecutionContext context) {
	       
	        
	        String type = request.getQueryParameters().get("type");
	        if(id == null || type == null)
	        	return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("mandatory fields are missing").build();
	        try {
	        	return request.createResponseBuilder(HttpStatus.OK).body(Client.readRecord(id, type)).build();
			} catch (ApiException e) {
				context.getLogger().log(Level.SEVERE, ExceptionUtils.getStackTrace(e), e);
	            return request.createResponseBuilder(  HttpStatus.INTERNAL_SERVER_ERROR).body("{\"ErrorMessage\"  : " +  ExceptionUtils.getStackTrace(e)   + "\"}" ).build(); 
			}

	       
	    }
}
