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
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import nga.hrx.utils.ApiException;
import nga.hrx.utils.Utils;
import nga.hrx.somethingelse.Bod;

public class Gospel_POC_SomethingElse {

	 @FunctionName("somethingelse")
	    public HttpResponseMessage run(
	            @HttpTrigger(name = "req", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS ) HttpRequestMessage<Optional<String>> request, 
	            final ExecutionContext context) {

	        // Parse query parameter
	       Bod bod = new Bod();
	       context.getLogger().log(Level.SEVERE,Utils.getEnvironmentConfig("ContainerNameBlobStorage") + " container name");
	        try {
	 	        JSONObject json = new JSONObject( request.getBody().orElseThrow(() -> new ApiException("bod xml can't be null " )) );
	 	        bod.storeFile(json.getString("EmployeeId"),new String( Base64.getDecoder().decode(json.getString("Bod"))) , json.getString("Filename"), Utils.getEnvironmentConfig("ContainerNameBlobStorage"));
	 	       return request.createResponseBuilder(HttpStatus.OK).body("bod xml uploaded").build();
			} catch (Exception e) {
				context.getLogger().log(Level.SEVERE, e.getLocalizedMessage(), e);
	            return request.createResponseBuilder(  HttpStatus.INTERNAL_SERVER_ERROR).body("{\"ErrorMessage\"  : " + e.getLocalizedMessage()  + "\"}" ).build(); 
			} 

	        
	    }
}
