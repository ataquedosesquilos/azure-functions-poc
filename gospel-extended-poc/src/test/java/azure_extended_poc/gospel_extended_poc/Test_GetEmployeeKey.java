package azure_extended_poc.gospel_extended_poc;

import nga.hrx.gospel.consumer.Client;
import nga.hrx.gospel.consumer.GospelException;
import nga.hrx.utils.ApiException;

public class Test_GetEmployeeKey {
	public static void main(String[] args) throws GospelException, ApiException {
		Client client = new Client();
		System.out.println("Emmployee Key: " + client.getFieldFromRecord("A1", "EMPLOYEE", "EmployeeKey"));
	}
}
