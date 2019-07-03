package com.nga.transformation.transformation_services_joao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

public class MyURIResolver  implements URIResolver{
	  private String path;
	  private URIResolver alt;  
	  
	  public MyURIResolver(String path, URIResolver alt) {
	    super();
	    this.path = path;
	    this.alt = alt;
	  }


	  @Override
	  public Source resolve(String href, String base) throws TransformerException {
	    try {
	      if (href.startsWith("http://") || href.startsWith("https://")) {
	        if (alt != null) {
	          Source s = alt.resolve(href, base);
	          if (s != null)
	            return s;
	        }
	        return TransformerFactory.newInstance().getURIResolver().resolve(href, base);
	      } else
	        return new StreamSource(new FileInputStream(href.contains(File.separator) ? href : path+href));
	    } catch (FileNotFoundException e) {
	      throw new TransformerException(e);
	    }
	  }
}
