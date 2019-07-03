package com.nga.transformation.transformation_services_joao;

import net.sf.saxon.TransformerFactoryImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;


public class ApplyXSLT {

	
	public static  byte[] saxonTransform(Map<String, String> params, String file) throws FileNotFoundException, TransformerException  {
		  TransformerFactoryImpl f = new net.sf.saxon.TransformerFactoryImpl();
		  f.setAttribute("http://saxon.sf.net/feature/version-warning", Boolean.FALSE);
		  StreamSource xsrc = new StreamSource(new File(ApplyXSLT.class
					.getClassLoader().getResource("PECI2BOD.xsl").getFile()));
		  f.setURIResolver(new MyURIResolver(null, null));
		  Transformer t = f.newTransformer(xsrc);
		    if (params != null) {
		       for (Map.Entry<String, String> entry : params.entrySet()) {
		          t.setParameter(entry.getKey(), entry.getValue());
		       }
		 }
		  
		  t.setURIResolver(new MyURIResolver(null,null));
		  StreamSource src = new StreamSource(new FileInputStream(file));
		  ByteArrayOutputStream out = new ByteArrayOutputStream();
		  StreamResult res = new StreamResult(out);
		  t.transform(src, res);    
		  return out.toByteArray();    
		}
	
}
