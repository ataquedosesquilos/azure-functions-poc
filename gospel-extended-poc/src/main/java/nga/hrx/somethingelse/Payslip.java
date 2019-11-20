package nga.hrx.somethingelse;

import org.json.JSONException;
import org.json.JSONObject;

import nga.hrx.azure.consumer.AppInsight;
import nga.hrx.azure.consumer.BlobStorage;
import nga.hrx.gospel.consumer.Client;
import nga.hrx.utils.ApiException;
import nga.hrx.utils.Encryption;
import nga.hrx.utils.Utils;

public class Payslip {
	
	 private final String RECORD_TYPE="EMPLOYEE2";
	 private Client client;
	 private String payslipName;
	 private String employeeId;
	 private String container;
	 private static AppInsight appInsight = new AppInsight();
	 
	 public Payslip(Client client) {
		 this.client=client;
	 }
	 
	 public void storePayslip(String payslip, String payslipName, String container, String employeeId) throws SomeElseException {
		try {
			this.start(payslipName, container,employeeId);
			byte[] encryptedBod = Encryption.encryptData(Utils.getEnvironmentConfig("SecretKey")+getEmployeeKey(this.employeeId),  payslip.getBytes());
			BlobStorage.writeBlob(this.payslipName, this.container, encryptedBod );
		}catch(Exception e) {
			Payslip.appInsight.trackException(e);
			throw new SomeElseException(e);
		}

	 }
	 
	 
	 public String getPayslip(String payslipName, String employeeId, String container) throws SomeElseException {
		try {
			long lStartTime = System.currentTimeMillis();
			this.start(payslipName, container, employeeId);
			JSONObject json = new JSONObject();
			json.put("EmployeeId", this.employeeId);
			json.put("Filename", this.payslipName);
			byte[] encryptedBod = BlobStorage.getBlob(this.payslipName, this.container);
			if (encryptedBod == null)
				throw new SomeElseException("Couldn't find file");
			json.put("Payslip", new String (Encryption.decryptData(Utils.getEnvironmentConfig("SecretKey")+getEmployeeKey(this.employeeId), encryptedBod))  );
			long lEndTime = System.currentTimeMillis();
			Payslip.appInsight.trackMetric("GetPayslip", lEndTime - lStartTime);
			return json.toString();
		} catch (Exception e) {
			Payslip.appInsight.trackException(e);
			throw new SomeElseException(employeeId, e);
		}
	 }
	 

		private String getEmployeeKey(String employeeId) throws JSONException, ApiException, SomeElseException {
			long lStartTime = System.currentTimeMillis();
			String employeeKey = this.client.getFieldFromRecord(employeeId, this.RECORD_TYPE, "EmployeeKey");
			if(employeeKey == null || employeeKey.isEmpty())
				throw new SomeElseException("Employee Key can't be null");
			long lEndTime = System.currentTimeMillis();
			Payslip.appInsight.trackMetric("GetEmployeeKey", lEndTime - lStartTime);
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
