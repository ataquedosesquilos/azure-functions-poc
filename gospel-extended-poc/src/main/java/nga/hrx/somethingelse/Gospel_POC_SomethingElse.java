package nga.hrx.somethingelse;

import java.util.Base64;
import java.util.Optional;
import java.util.logging.Level;

import org.json.JSONObject;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import nga.hrx.utils.ApiException;
import nga.hrx.utils.Utils;
import nga.hrx.somethingelse.Bod;

public class Gospel_POC_SomethingElse {

	 @FunctionName("somethingelsewrite")
	    public HttpResponseMessage run(
	            @HttpTrigger(name = "req", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "somethingelse" ) HttpRequestMessage<Optional<String>> request, 
	            final ExecutionContext context) {

	        // Parse query parameter
	       Bod bod = new Bod();
	        try {
	 	        JSONObject json = new JSONObject( request.getBody().orElseThrow(() -> new ApiException("bod xml can't be null " )) );
	 	        bod.storeFile(json.getString("EmployeeId"), json.getString("Bod") , json.getString("Filename"), Utils.getEnvironmentConfig("ContainerNameBlobStorage"));
	 	       return request.createResponseBuilder(HttpStatus.OK).body("bod xml uploaded").build();
			} catch (Exception e) {
				context.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);
	            return request.createResponseBuilder(  HttpStatus.INTERNAL_SERVER_ERROR).body("{\"ErrorCode\"  : \"500\", \"Error Message\" : \"" + e.getLocalizedMessage() + "\"}" ).build(); 
			} 
	    }
	 
	 
	 @FunctionName("somethingelseget")
	    public HttpResponseMessage get(
	            @HttpTrigger(name = "req", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "somethingelse/{id}" ) HttpRequestMessage<Optional<String>> request, @BindingName("id") String id, 
	            final ExecutionContext context) {

	        // Parse query parameter
	       Bod bod = new Bod();
	       String employeeId = request.getQueryParameters().get("employeeId");
	        if(id == null || employeeId == null)
	        	return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("mandatory fields are missing").build();
	        try {
	 	       return request.createResponseBuilder(HttpStatus.OK).body(bod.getFile(id, employeeId, Utils.getEnvironmentConfig("ContainerNameBlobStorage"))).build();
			} catch (Exception e) {
				context.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);
	            return request.createResponseBuilder(  HttpStatus.INTERNAL_SERVER_ERROR).body("{\"ErrorCode\"  : \"500\", \"Error Message\" : \"" + e.getLocalizedMessage() + "\"}" ).build(); 
			} 
	    }
}
