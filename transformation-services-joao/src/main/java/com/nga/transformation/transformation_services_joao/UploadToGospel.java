package com.nga.transformation.transformation_services_joao;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.ServiceBusQueueTrigger;


public class UploadToGospel {

	@FunctionName("Upload-To-Gospel")
    public void run(
            @ServiceBusQueueTrigger(name = "message", queueName = "trigger-invidual-bods", connection = "AzureServiceBusConnection") String message,
            final ExecutionContext context 
    ) throws Exception{
		context.getLogger().info("Java Service Bus Queue trigger function executed."+getClass().getName());
        context.getLogger().info(message);
        try {
        	//GospelSDK sdk = new GospelSDK("D:\\home\\site\\wwwroot\\private_zza_jowowe.key","D:\\home\\site\\wwwroot\\zza2.pem","https://gospel.ukwest.cloudapp.azure.com/");
        	//context.getLogger().info(sdk.createRecord(message));
        }catch(Exception e) {
        	context.getLogger().severe(e.getLocalizedMessage());
        	throw e;
        }
        
	}
}
