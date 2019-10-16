package nga.hrx.azure.consumer;

import java.util.Base64;
import java.util.Optional;
import java.util.logging.Level;

import org.apache.commons.lang3.exception.ExceptionUtils;
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

public class Gospel_StoreSourceData {
	@FunctionName("savefile")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS , route = "somethingelse/file") HttpRequestMessage<Optional<String>> request, 
            final ExecutionContext context) {
		try {
 	        JSONObject json = new JSONObject( request.getBody().orElseThrow(() -> new ApiException("payslip can't be null " )) );
			BlobStorage.writeBlob(json.getString("Filename"), Utils.getEnvironmentConfig("ContainerNameBlobSourceData"),Base64.getDecoder().decode( json.getString("File") ));
	 	       return request.createResponseBuilder(HttpStatus.OK).body("File uploaded to " + Utils.getEnvironmentConfig("ContainerNameBlobSourceData")).build();
		}catch (Exception e) {
			context.getLogger().log(Level.SEVERE,ExceptionUtils.getStackTrace(e), e);
            return request.createResponseBuilder(  HttpStatus.INTERNAL_SERVER_ERROR).body("{\"ErrorCode\"  : \"500\", \"Error Message\" : \"" + ExceptionUtils.getStackTrace(e) + "\"}" ).build(); 
		} 
		
	}
}
