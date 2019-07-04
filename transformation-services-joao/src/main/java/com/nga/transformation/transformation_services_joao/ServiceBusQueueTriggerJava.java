package com.nga.transformation.transformation_services_joao;

import com.microsoft.azure.functions.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;


import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Bus Trigger.
 */
public class ServiceBusQueueTriggerJava {
	private static final String containerName = "batchfiles";
	private static final String containerNameCanonical = "canonical";

	
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
        	String originalFile = blobStorage.getBlob(message,containerName);
        	context.getLogger().info(originalFile);
        	ApplyXSLT apply = new ApplyXSLT();
    		Map<String, String> params = new HashMap<String, String>();
    		params.put("logicalID", "ZZA-VN002-1001");
    		params.put("releaseID","90");
    		params.put("systemEnvironmentCode","aZURE");
    		params.put("languageCode","en-US");
    		params.put("referenceID","jowowe");
    		context.getLogger().info("transforming file " + message);
    		BufferedReader buf = new BufferedReader(new InputStreamReader(ServiceBusQueueTriggerJava.class.getClassLoader().getResourceAsStream("PECI2BOD.xsl"))); 
    		String line = buf.readLine(); 
    		StringBuilder sb = new StringBuilder();
    		while(line != null){ 
    			sb.append(line).append("\n"); 
    			line = buf.readLine(); 
    		} 
    		String fileAsString = sb.toString();
    		context.getLogger().info("XSLT: " + fileAsString);
    		String transformedFile = new String(apply.saxonTransform(params, originalFile));
    		context.getLogger().info("transformed file " + message);
    		context.getLogger().info(transformedFile);
    		blobStorage.writeBlob(message+"transformed.xml", containerNameCanonical, transformedFile);
        	
        }catch(Exception e) {
        	context.getLogger().info("Failed " +  e.getLocalizedMessage());
        }
		
    }
}	
