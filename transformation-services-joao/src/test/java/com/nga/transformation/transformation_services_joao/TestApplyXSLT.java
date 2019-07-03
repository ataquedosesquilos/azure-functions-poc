package com.nga.transformation.transformation_services_joao;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerException;

public class TestApplyXSLT {

	public static void main(String[] args) throws FileNotFoundException, TransformerException {
		String file = TestApplyXSLT.class.getClassLoader().getResource("ZZAVN00210091562167176.62.xml").getFile();
		ApplyXSLT apply = new ApplyXSLT();
		Map<String, String> params = new HashMap<String, String>();
		params.put("logicalID", "ZZA-VN002-1001");
		params.put("releaseID","90");
		params.put("systemEnvironmentCode","aZURE");
		params.put("languageCode","en-US");
		params.put("referenceID","jowowe");
		String banana = new String(apply.saxonTransform(params, file));
		System.out.println(banana);
		

	}

}
