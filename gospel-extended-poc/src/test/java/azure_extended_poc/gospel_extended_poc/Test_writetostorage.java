package azure_extended_poc.gospel_extended_poc;



import nga.hrx.azure.consumer.AzureException;
import nga.hrx.azure.consumer.BlobStorage;
import nga.hrx.gospel.consumer.GospelException;

public class Test_writetostorage {

		private static String connection = "DefaultEndpointsProtocol=https;AccountName=gospelextendedpocstorage;AccountKey=BEUwxw7ZNa57XXsywAI1QPJvUjReqzs2Xyem/x4jmDgEWzpeqRMLr01hpn+l1qsgjQm1fmc//NDZl+7+5JfXAg==;EndpointSuffix=core.windows.net";
		
		public static void main(String[] args) throws GospelException, AzureException {
			writeBlob("test", "bods", "banana");
		}
		
		
		public static void writeBlob(String id, String containerName, String file) throws AzureException {
			try {
				BlobStorage.writeBlob(id, containerName, file.getBytes(), connection);
				 
			}catch(Exception e) {
				throw new AzureException(e);
			}
		}
		
		
}
