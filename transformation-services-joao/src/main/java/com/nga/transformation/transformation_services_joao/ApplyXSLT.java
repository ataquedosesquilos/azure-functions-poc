package com.nga.transformation.transformation_services_joao;

import net.sf.saxon.TransformerFactoryImpl;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;


public class ApplyXSLT {

	
	public   byte[] saxonTransform(Map<String, String> params, String file) throws TransformerException, IOException, URISyntaxException  {
		  TransformerFactoryImpl f = new net.sf.saxon.TransformerFactoryImpl();
		  f.setAttribute("http://saxon.sf.net/feature/version-warning", Boolean.FALSE);
		  StreamSource xsrc = new StreamSource(ApplyXSLT.class
					.getClassLoader().getResourceAsStream("PECI2BOD.xsl"));
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
