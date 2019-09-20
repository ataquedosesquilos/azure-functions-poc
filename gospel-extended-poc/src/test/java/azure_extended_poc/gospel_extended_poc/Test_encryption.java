package azure_extended_poc.gospel_extended_poc;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.stream.Stream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


import nga.hrx.azure.consumer.AzureException;
import nga.hrx.gospel.consumer.GospelException;
import nga.hrx.utils.Encryption;

public class Test_encryption {
	
	public static void main(String[] args) throws GospelException, AzureException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException, IOException {
		byte[] encrypted = Encryption.encryptData("uhlala",  "123".getBytes());
		System.out.println(  Encryption.decryptData ("123",  readLineByLineJava8("C:\\Users\\joaoa\\Downloads\\1239")));
	}
	
	
	private static byte[] readLineByLineJava8(String filePath) throws IOException
	{
		File file = new File(filePath);
		  //init array with file length
		  byte[] bytesArray = new byte[(int) file.length()]; 

		  FileInputStream fis = new FileInputStream(file);
		  fis.read(bytesArray); //read file into bytes[]
		  fis.close();
					
		  return bytesArray;
	}
}


