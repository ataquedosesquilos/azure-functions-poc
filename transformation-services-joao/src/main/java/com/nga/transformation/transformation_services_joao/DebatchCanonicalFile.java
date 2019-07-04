package com.nga.transformation.transformation_services_joao;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.ServiceBusQueueTrigger;

public class DebatchCanonicalFile {
	
	
	@FunctionName("Debatch-File")
    public void run(
            @ServiceBusQueueTrigger(name = "message", queueName = "spliting", connection = "AzureServiceBusConnection") String message,
            final ExecutionContext context
    ) {
		context.getLogger().info("Java Service Bus Queue trigger function executed."+getClass().getName());
        context.getLogger().info(message);
        try {
        	DebatchCanonicalImp dbatch = new DebatchCanonicalImp();
    		dbatch.splitAndPublish(message);    	
        }catch(Exception e) {
        	 StringWriter writer = new StringWriter();
             PrintWriter printWriter= new PrintWriter(writer);
             e.printStackTrace(printWriter);
        	context.getLogger().severe("Failed " +  writer.toString());
        }
		
	}
	
	
	
}
