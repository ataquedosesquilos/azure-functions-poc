package com.nga.transformation.transformation_services_joao;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Bus Trigger.
 */
public class ServiceBusQueueTriggerJava {
    /**
     * This function will be invoked when a new message is received at the Service Bus Queue.
     */
    @FunctionName("Transform-File")
    public void run(
            @ServiceBusQueueTrigger(name = "message", queueName = "before-transformation", connection = "AzureServiceBusConnection") String message,
            final ExecutionContext context
    ) {
        context.getLogger().info("Java Service Bus Queue trigger function executed.");
        context.getLogger().info(message);
        BlobStorage blobStorage = new BlobStorage();
        try {
        	String originalFile = blobStorage.getBlob(message);
        	context.getLogger().info(originalFile);
        }catch(Exception e) {
        	context.getLogger().info(e.getLocalizedMessage());
        }
		
    }
}	
