package azure_extended_poc.gospel_extended_poc;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.microsoft.azure.storage.core.Base64;

import nga.hrx.gospel.consumer.GospelException;

public class FileToString {
	
	public static void main(String[] args) throws GospelException, IOException {
		String filename = "C:\\Users\\joaoa\\Downloads\\PAYSLIP_ZCS_VN002_VN_Y2019_P03_EAAAJLvNPIHDS.pdf";
		Path path = Paths.get(filename);
		String fileTemp = Base64.encode(Files.readAllBytes(path));
		System.out.println(fileTemp);
		
		FileOutputStream fos = new FileOutputStream("C:\\Users\\joaoa\\Downloads\\test.pdf");
		fos.write(Base64.decode(fileTemp));
		fos.close();
		 
	}
	
}
