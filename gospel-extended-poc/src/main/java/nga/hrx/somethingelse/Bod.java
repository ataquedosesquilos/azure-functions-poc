package nga.hrx.somethingelse;


import org.json.JSONException;
import org.json.JSONObject;

import nga.hrx.azure.consumer.AppInsight;
import nga.hrx.azure.consumer.BlobStorage;
import nga.hrx.gospel.consumer.Client;
import nga.hrx.utils.ApiException;
import nga.hrx.utils.Encryption;
import nga.hrx.utils.Utils;

public class Bod {
	
	 private final String RECORD_TYPE="EMPLOYEE2";
	 private Client client;
	 private static AppInsight appInsight = new AppInsight();

	 
	 public Bod(Client client) {
		 this.client=client;
	 }
	
	public void storeFile(String employeeId, String bodXml, String filename, String container) throws SomeElseException {
		try {			
			byte[] encryptedBod = Encryption.encryptData(Utils.getEnvironmentConfig("SecretKey")+getEmployeeKey(employeeId),  bodXml.getBytes());
			BlobStorage.writeBlob(filename, container, encryptedBod );
			
		} catch (Exception e) {
			throw new SomeElseException(e);
		}
	}
	
	public String getFile(String filename, String employeeId, String container) throws SomeElseException {
		try {
			JSONObject json = new JSONObject();
			json.put("EmployeeId", employeeId);
			json.put("Filename", filename);
			byte[] encryptedBod = BlobStorage.getBlob(filename, container);
			if (encryptedBod == null)
				throw new SomeElseException("Couldn't find file");
			//
			json.put("Bod", new String (Encryption.decryptData(Utils.getEnvironmentConfig("SecretKey")+getEmployeeKey(employeeId), encryptedBod))  );
			
			return json.toString();
		} catch (Exception e) {
			throw new SomeElseException(e);
		}
	}
	
	private String getEmployeeKey(String employeeId) throws JSONException, ApiException, SomeElseException {
		long lStartTime = System.currentTimeMillis();
		String employeeKey = this.client.getFieldFromRecord(employeeId, this.RECORD_TYPE, "EmployeeKey");
		if(employeeKey == null)
			throw new SomeElseException("Employee Key can't be null");
		long lEndTime = System.currentTimeMillis();
		Bod.appInsight.trackMetric("GetEmployeeKey", lEndTime - lStartTime);
		return employeeKey;
	}

}
