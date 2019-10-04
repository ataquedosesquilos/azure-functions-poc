package nga.hrx.azure.consumer;

import java.io.ByteArrayOutputStream;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import nga.hrx.utils.Utils;

public class BlobStorage {
	
	
	public static byte[] getBlob(String id, String containerName) throws AzureException {
		try {
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(Utils.getEnvironmentConfig("AzureWebJobsStorageBods"));
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference(containerName);
			
			
			CloudBlob blob =  container.getBlobReferenceFromServer(id);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			blob.download(stream);
			return stream.toByteArray();
		}catch(Exception e) {
			throw new AzureException(e);
		}
	}
	
	public static void writeBlob(String id, String containerName, String file) throws AzureException {
		try {
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(Utils.getEnvironmentConfig("AzureWebJobsStorageBods"));
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference(containerName);
			
			
			CloudBlockBlob blob = container.getBlockBlobReference(id);
			blob.uploadText(file);
			 
		}catch(Exception e) {
			throw new AzureException(e);
		}
	}
	
	
	public static void writeBlob(String id, String containerName, byte[] file) throws AzureException {
		try {
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(Utils.getEnvironmentConfig("AzureWebJobsStorageBods"));
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference(containerName);
			
			
			CloudBlockBlob blob = container.getBlockBlobReference(id);
			blob.uploadFromByteArray(file, 0, file.length);;
			
		}catch(Exception e) {
			throw new AzureException(e);
		}
	}
}
