package nga.hrx.somethingelse;

import org.json.JSONException;
import org.json.JSONObject;

import nga.hrx.azure.consumer.BlobStorage;
import nga.hrx.gospel.consumer.Client;
import nga.hrx.utils.ApiException;
import nga.hrx.utils.Encryption;
import nga.hrx.utils.Utils;

public class Payslip {
	
	 private final String RECORD_TYPE="EMPLOYEE";
	 private Client client;
	 private String payslipName;
	 private String employeeId;
	 private String container;
	 
	 public Payslip(Client client) {
		 this.client=client;
	 }
	 
	 public void storePayslip(String payslip, String payslipName, String container, String employeeId) throws SomeElseException {
		try {
			this.start(payslipName, container,employeeId);
			byte[] encryptedBod = Encryption.encryptData(Utils.getEnvironmentConfig("SecretKey")+getEmployeeKey(this.employeeId),  payslip.getBytes());
			BlobStorage.writeBlob(this.payslipName, this.container, encryptedBod );
		}catch(Exception e) {
			throw new SomeElseException(e);
		}

	 }
	 
	 
	 public String getPayslip(String payslipName, String employeeId, String container) throws SomeElseException {
		try {
			this.start(payslipName, container, employeeId);
			JSONObject json = new JSONObject();
			json.put("EmployeeId", this.employeeId);
			json.put("Filename", this.payslipName);
			byte[] encryptedBod = BlobStorage.getBlob(this.payslipName, this.container);
			if (encryptedBod == null)
				throw new SomeElseException("Couldn't find file");
			json.put("Payslip", new String (Encryption.decryptData(Utils.getEnvironmentConfig("SecretKey")+getEmployeeKey(this.employeeId), encryptedBod))  );
			return json.toString();
			
		} catch (Exception e) {
			throw new SomeElseException(e);
		}
	 }
	 

		private String getEmployeeKey(String employeeId) throws JSONException, ApiException, SomeElseException {
			JSONObject json = new JSONObject(this.client.readRecord(employeeId, this.RECORD_TYPE));
			String employeeKey =  json.getString("EmployeeKey");
			if(employeeKey == null)
				throw new SomeElseException("Employee Key can't be null");
			
			return employeeKey;
		}
		
		private void start(String payslipName, String container, String employeeId) {
			this.payslipName = payslipName;
			this.parseEmployeeFromPayslip();
			 this.container = container;
			this.employeeId = employeeId;
		}
		
		private void parseEmployeeFromPayslip() {
			this.employeeId = this.payslipName.substring(this.payslipName.indexOf("_E") + 2);
			int indexOf = this.employeeId.indexOf("_");
			if (indexOf <0)
				indexOf = this.employeeId.indexOf(".");
			this.employeeId = this.employeeId.substring(0,indexOf);
		}
	 
}
