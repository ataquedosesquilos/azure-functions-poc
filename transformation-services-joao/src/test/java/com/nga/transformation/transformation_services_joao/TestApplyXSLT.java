package com.nga.transformation.transformation_services_joao;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.nga.transformation.transformation_services_joao.ApplyXSLT;

import javax.xml.transform.TransformerException;

public class TestApplyXSLT {

	public static void main(String[] args) throws TransformerException, IOException {
		
		ApplyXSLT apply = new ApplyXSLT();
		Map<String, String> params = new HashMap<String, String>();
		params.put("logicalID", "ZZA-VN002-1001");
		params.put("releaseID","90");
		params.put("systemEnvironmentCode","aZURE");
		params.put("languageCode","en-US");
		params.put("referenceID","jowowe");
		String banana = null;
		InputStream is = new FileInputStream("ZZAVN00210091562167176.62.xml");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is)); 
		String line = buf.readLine(); 
		StringBuilder sb = new StringBuilder();
		while(line != null){ 
			sb.append(line).append("\n"); 
			line = buf.readLine();
		}
		buf.close();

		try {
			banana = new String(apply.saxonTransform(params, sb.toString()));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(banana);
		

	}

}
