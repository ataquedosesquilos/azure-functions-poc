package com.nga.transformation.transformation_services_joao;

import java.io.ByteArrayOutputStream;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;

public class BlobStorage {
	
	CloudStorageAccount storageAccount;
	CloudBlobClient blobClient = null;
	CloudBlobContainer container=null;
	private static final String storageConnectionString="DefaultEndpointsProtocol=https;AccountName=ingestfile;AccountKey=t0+MJ4WZZPBLKD1kceMp9QnmW36TQMpGxQ/uhPgglyrZxfoo5Fpzw5t66DNBsoxowbz9bvFki7ZDD+CwiuGehQ==;EndpointSuffix=core.windows.net";
	
	
	public String getBlob(String id, String containerName) throws Exception {
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
	
	public ByteArrayOutputStream getBlobStream(String id, String containerName) throws Exception {
		ByteArrayOutputStream stream;
		try {
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containerName);
			
			
			CloudBlob blob =  container.getBlobReferenceFromServer(id);
			stream = new ByteArrayOutputStream();
			blob.download(stream);			
		}catch(Exception e) {
			throw e;
		}
		
		
		return stream;
	}
	
	public void writeBlob(String id, String containerName, String file) throws Exception {
		try {
			storageAccount = CloudStorageAccount.parse(storageConnectionString);
			blobClient = storageAccount.createCloudBlobClient();
			container = blobClient.getContainerReference(containerName);
			
			
			CloudBlockBlob blob = container.getBlockBlobReference(id);
			blob.uploadText(file);
			
		}catch(Exception e) {
			throw e;
		}
	}
		
		
	

}
