package com.nga.transformation.transformation_services_joao;

import net.sf.saxon.TransformerFactoryImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import javax.xml.transform.Transformer;


public class ApplyXSLT{	
	
	public   byte[] saxonTransform(Map<String, String> params, String file) throws Exception  {
		  TransformerFactoryImpl f = new net.sf.saxon.TransformerFactoryImpl();
		  f.setAttribute("http://saxon.sf.net/feature/version-warning", Boolean.FALSE);
		  
		  StreamSource xsrc = new StreamSource(new FileInputStream("D:\\home\\site\\wwwroot\\PECI2BOD.xsl"));
		  f.setURIResolver(new MyURIResolver(null, null));
		  Transformer t = f.newTransformer(xsrc);
		    if (params != null) {
		       for (Map.Entry<String, String> entry : params.entrySet()) {
		          t.setParameter(entry.getKey(), entry.getValue());
		       }
		 }
		 File temp = whenWriteToTmpFile_thenCorrect(file);
		  StreamSource src = new StreamSource(new FileInputStream(temp));
		  ByteArrayOutputStream out = new ByteArrayOutputStream();
		  StreamResult res = new StreamResult(out);
		  t.transform(src, res);    
		  temp.delete();
		  return out.toByteArray(); 
		  //return "mokey".getBytes();
		}
	
	public File whenWriteToTmpFile_thenCorrect(String content) throws IOException {
		    
		    File tmpFile = File.createTempFile("test", ".tmp");
		    tmpFile.deleteOnExit();
		    FileWriter writer = new FileWriter(tmpFile);
		    writer.write(content);
		    writer.flush();
		    writer.close();
		 
		   return tmpFile;
		}

	
}
