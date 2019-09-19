package azure_extended_poc.gospel_extended_poc;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import nga.hrx.azure.consumer.AzureException;
import nga.hrx.gospel.consumer.GospelException;

public class Test_writetostorage {

		private static String connection = "DefaultEndpointsProtocol=https;AccountName=gospelextendedpocbod;AccountKey=G4CNGVlcDO581olikaGOlWIkdWotAMjMbNuM8m+nIAu+zxSiVUDfJFRCcfIw3PMQQADyWV2FeG09qHBog2PnNw==;EndpointSuffix=core.windows.net";
		
		public static void main(String[] args) throws GospelException, AzureException {
			writeBlob("test", "bods", "banana");
		}
		
		
		public static void writeBlob(String id, String containerName, String file) throws AzureException {
			try {
				CloudStorageAccount storageAccount = CloudStorageAccount.parse(connection);
				CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
				CloudBlobContainer container = blobClient.getContainerReference(containerName);
				
				
				CloudBlockBlob blob = container.getBlockBlobReference(id);
				blob.uploadText(file);
				 
			}catch(Exception e) {
				throw new AzureException(e);
			}
		}
		
		
}
