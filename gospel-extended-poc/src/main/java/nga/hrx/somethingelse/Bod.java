package nga.hrx.somethingelse;

import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

import com.google.common.hash.Hashing;

import nga.hrx.utils.Encryption;
import nga.hrx.utils.Utils;
import nga.hrx.azure.consumer.BlobStorage;
import nga.hrx.gospel.consumer.Client;

public class Bod {
	
	 private final String RECORD_TYPE="EMPLOYEE";
	
	public void storeFile(String employeeId, String bodXml, String filename, String container) throws SomeElseException {
		try {
			JSONObject json = new JSONObject(Client.readRecord(employeeId, this.RECORD_TYPE));
			String employeeKey = json.getString("EmployeeKey");
			if(employeeKey == null)
				throw new SomeElseException("Employee Key can't be null");
			
			byte[] encryptedBod = Encryption.encryptData(Utils.getEnvironmentConfig("SecretKey")+employeeKey,  Hashing.sha256()
					  .hashString(bodXml, StandardCharsets.UTF_8)
					  .toString().getBytes());
			
			BlobStorage.writeBlob(filename, container, encryptedBod );
			
		} catch (Exception e) {
			throw new SomeElseException(e);
		}
	}

}