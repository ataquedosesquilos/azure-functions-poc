package nga.hrx.somethingelse;

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
import com.microsoft.azure.functions.annotation.BindingName;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import nga.hrx.utils.ApiException;
import nga.hrx.utils.Utils;
import nga.hrx.azure.consumer.AppInsight;
import nga.hrx.gospel.consumer.Client;

public class Gospel_POC_SomethingElse_Payslip {

	private static Client client = new Client();
	private Payslip payslip = new Payslip(client);
	 private static AppInsight appInsight = new AppInsight();

	
	 @FunctionName("somethingelsepayslipwrite")
	    public HttpResponseMessage run(
	            @HttpTrigger(name = "req", methods = { HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS, route = "somethingelse/payslip" ) HttpRequestMessage<Optional<String>> request, 
	            final ExecutionContext context) {

	        try {
	 	        JSONObject json = new JSONObject( request.getBody().orElseThrow(() -> new ApiException("payslip can't be null " )) );
	 	        payslip.storePayslip( json.getString("Payslip") , json.getString("Filename"), Utils.getEnvironmentConfig("ContainerNameBlobStoragePayslips"), json.getString("EmployeeId"));
	 	       return request.createResponseBuilder(HttpStatus.OK).body("Payslip uploaded").build();
			} catch (Exception e) {
				context.getLogger().log(Level.SEVERE,ExceptionUtils.getStackTrace(e), e);
	            return request.createResponseBuilder(  HttpStatus.INTERNAL_SERVER_ERROR).body("{\"ErrorCode\"  : \"500\", \"Error Message\" : \"" + ExceptionUtils.getStackTrace(e) + "\"}" ).build(); 
			} 
	    }
	 
	 
	 @FunctionName("somethingelsepayslipget")
	    public HttpResponseMessage get(
	            @HttpTrigger(name = "req", methods = { HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS, route = "somethingelse/payslip/{id}" ) HttpRequestMessage<Optional<String>> request, @BindingName("id") String id, 
	            final ExecutionContext context) {
		 	long lStartTime = System.currentTimeMillis();
		 	String employeeId = request.getQueryParameters().get("employeeId");
	        if(id == null || employeeId == null)
	        	return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("mandatory fields are missing").build();
	        
	        try {
	        	String results = payslip.getPayslip(id, employeeId, Utils.getEnvironmentConfig("ContainerNameBlobStoragePayslips"));
	        	long lEndTime = System.currentTimeMillis();
	        	HttpResponseMessage response = request.createResponseBuilder(HttpStatus.OK).body(results).build();
	        	Gospel_POC_SomethingElse_Payslip.appInsight.trackMetric("SomethingElsePayslipGetCustom", lEndTime - lStartTime);
	        	return response;
			} catch (Exception e) {
				context.getLogger().log(Level.SEVERE, ExceptionUtils.getStackTrace(e), e);
	            return request.createResponseBuilder(  HttpStatus.INTERNAL_SERVER_ERROR).body("{\"ErrorCode\"  : \"500\", \"Error Message\" : \"" + ExceptionUtils.getStackTrace(e) + "\"}" ).build(); 
			} 
	    }
}
