package nga.hrx.azure.consumer;

import java.io.ByteArrayOutputStream;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.CloudPageBlob;

import nga.hrx.azure.consumer.AppInsight;
import nga.hrx.utils.Utils;

public class BlobStorage {
	
	private static AppInsight appInsight = new AppInsight();
	
	public static byte[] getBlob(String id, String containerName) throws AzureException {
		try {
			long lStartTime = System.currentTimeMillis();
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(Utils.getEnvironmentConfig("AzureWebJobsStorageBods"));
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference(containerName);
			CloudPageBlob  blob =   container.getPageBlobReference(id);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			blob.download(stream);
			long lEndTime = System.currentTimeMillis();
			BlobStorage.appInsight.trackMetric("GETBLOB"+containerName, lEndTime - lStartTime);
			return stream.toByteArray();
		}catch(Exception e) {
			BlobStorage.appInsight.trackException(e);
			throw new AzureException(e);
		}
	}
	
	public static void writeBlob(String id, String containerName, String file) throws AzureException {
		try {
			writeBlob(id, containerName, file.getBytes());
		}catch(Exception e) {
			BlobStorage.appInsight.trackException(e);
			throw new AzureException(e);
		}
	}
	
	public static void writeBlob(String id, String containerName, byte[] file, String connection) throws AzureException {
		try {
			long lStartTime = System.currentTimeMillis();
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(connection);
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference(containerName);
			CloudBlockBlob blob = container.getBlockBlobReference(id);
			blob.uploadFromByteArray(file, 0, file.length);
			long lEndTime = System.currentTimeMillis();
			BlobStorage.appInsight.trackMetric("WRITEBLOBBYTEARRAY"+containerName,lEndTime - lStartTime);
		}catch(Exception e) {
			BlobStorage.appInsight.trackException(e);
			throw new AzureException(e);
		}
	}
	
	
	public static void writeBlob(String id, String containerName, byte[] file) throws AzureException {
		try {
			long lStartTime = System.currentTimeMillis();
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(Utils.getEnvironmentConfig("AzureWebJobsStorageBods"));
			CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
			CloudBlobContainer container = blobClient.getContainerReference(containerName);
			CloudBlockBlob blob = container.getBlockBlobReference(id);
			blob.uploadFromByteArray(file, 0, file.length);
			long lEndTime = System.currentTimeMillis();
			BlobStorage.appInsight.trackMetric("WRITEBLOBBYTEARRAY"+containerName,lEndTime - lStartTime);
		}catch(Exception e) {
			BlobStorage.appInsight.trackException(e);
			throw new AzureException(e);
		}
	}
}
