package nga.hrx.gospel.consumer;


import tech.gospel.sdk.api.consumers.AsyncGospelSdk;
import tech.gospel.sdk.exception.CertificateParsingException;
import tech.gospel.sdk.exception.KeyParsingException;
import nga.hrx.utils.FileUtils;
import nga.hrx.utils.Utils;

import java.io.IOException;

import nga.hrx.gospel.consumer.GospelException;

public class GospelConsumer {
	
	private  String privateKey = "joao.key";
	private  String publicKey = "joao.pem";
	//private  String gospelUrl = "https://nfr4.westeurope.cloudapp.azure.com";
	private String gospelUrl = Utils.getEnvironmentConfig("GospelURL");
	private  String apiVersion = "v1.1";
	private  AsyncGospelSdk sdk; 
	
	
	public GospelConsumer() throws GospelException {
		try {
			this.privateKey = FileUtils.getFilePath() + this.privateKey;
			this.publicKey = FileUtils.getFilePath() + this.publicKey;
			this.start();
		}catch(Exception e){
			throw new GospelException(e);
		}
	}
	
	public GospelConsumer(String privateKey, String publicKey, String gospelUrl) throws GospelException {
		try {
			this.start();
		}catch(Exception e){
			throw new GospelException(e);
		}
		
	}
	
	public GospelConsumer(String privateKey, String publicKey, String gospelUrl, String apiVersion) throws GospelException {
		try {
			this.start();
		}catch(Exception e){
			throw new GospelException(e);
		}
	}
	
	private void start() throws GospelException   {
		try {
			this.sdk = new AsyncGospelSdk(FileUtils.readFileIntoString((this.publicKey)),FileUtils.readFileIntoString((this.privateKey)),this.gospelUrl, this.apiVersion);
		} catch (CertificateParsingException | KeyParsingException | IOException e) {
			throw new GospelException(e);
		}
	} 
	
	public AsyncGospelSdk getSDK() {
		return this.sdk;
		
	}
		
}
