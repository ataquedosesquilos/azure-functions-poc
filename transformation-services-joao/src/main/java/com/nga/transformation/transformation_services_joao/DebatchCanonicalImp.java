package com.nga.transformation.transformation_services_joao;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.microsoft.azure.servicebus.Message;
import com.microsoft.azure.servicebus.QueueClient;
import com.microsoft.azure.servicebus.ReceiveMode;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;

public class DebatchCanonicalImp {
	
	private String file;
	private String filename;
	private  BlobStorage blobStorage = new BlobStorage();
	private static final String containerNameCanonical = "canonical";
	private QueueClient sendClient;
	private final String connectionString = "Endpoint=sb://message-transformation.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=U7SDM17BQFJ/9AE5q7U+TofKYaKjAlapNBF7dgpkG/A=";
	private final String queueName = "trigger-invidual-bods";
	private DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	 
	
	
	public void splitAndPublish(String filename) throws Exception {
		this.filename=filename;
		this.getFileFromBlobStorage();
		this.sendClient = new QueueClient(new ConnectionStringBuilder(this.connectionString, this.queueName), ReceiveMode.PEEKLOCK);
		DocumentBuilder docBuilder = this.docBuilderFactory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(this.file));
		Document doc = docBuilder.parse(is);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("nga:ProcessPayServEmp");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String bodId = eElement.getElementsByTagName("oa:BODID").item(0).getTextContent();
				this.sendMessage(this.elementToString(nNode), bodId);
			}
		}
	}
	
	
	private void getFileFromBlobStorage() throws Exception {
		this.file = this.blobStorage.getBlob(this.filename,containerNameCanonical);
	}
	
	
	private void sendMessage(String messageContent, String bodId) {
        Message message = new Message(messageContent);
        message.setContentType("application/text");
        message.setLabel("bod");
        message.setMessageId(bodId);
		this.sendClient.sendAsync(message);
	}
	
	private String elementToString(Node n) throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory
                .newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(n);
        StreamResult result = new StreamResult(new StringWriter());



        transformer.transform(source, result);

        return result.getWriter().toString();
	}
	

}
