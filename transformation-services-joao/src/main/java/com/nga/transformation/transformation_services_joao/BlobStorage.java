package com.nga.transformation.transformation_services_joao;

import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

public class BlobStorage {
	
	CloudStorageAccount storageAccount;
	CloudBlobClient blobClient = null;
	CloudBlobContainer container=null;
	private static final String storageConnectionString="DefaultEndpointsProtocol=https;AccountName=ingestfile;AccountKey=t0+MJ4WZZPBLKD1kceMp9QnmW36TQMpGxQ/uhPgglyrZxfoo5Fpzw5t66DNBsoxowbz9bvFki7ZDD+CwiuGehQ==;EndpointSuffix=core.windows.net";
	private static final String containerName = "batchfiles";
	
	
	public String getBlob(String id) throws Exception {
		String file;
		try {
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containerName);
			
			
			CloudBlob blob =  container.getBlobReferenceFromServer(id);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			blob.download(stream);
			file = new String(stream. toByteArray());
			
		}catch(Exception e) {
			throw e;
		}
		
		
		return file;
	}

}
