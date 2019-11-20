package nga.hrx.gospel.consumer;


import tech.gospel.sdk.api.consumers.AsyncGospelSdk;
import tech.gospel.sdk.exception.CertificateParsingException;
import tech.gospel.sdk.exception.KeyParsingException;
import tech.gospel.sdk.model.RecordDefinition;
import nga.hrx.utils.FileUtils;
import nga.hrx.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import nga.hrx.azure.consumer.AppInsight;
import nga.hrx.gospel.consumer.GospelException;

public class GospelConsumer {
	
	private  String privateKey = "joao.key";
	private  String publicKey = "joao.pem";
	//private  String gospelUrl = "https://nfr4.westeurope.cloudapp.azure.com";
	private String gospelUrl = Utils.getEnvironmentConfig("GospelURL");
	private  String apiVersion = "v1.1";
	private  AsyncGospelSdk sdk; 
	private Map<String, RecordDefinition> definitions = new HashMap<String, RecordDefinition>();
	private final int GOSPEL_TIMEOUT = 120;
	private static AppInsight appInsight = new AppInsight();
	
	public GospelConsumer() throws GospelException {
		try {
			this.privateKey = FileUtils.getFilePath() + this.privateKey;
			this.publicKey = FileUtils.getFilePath() + this.publicKey;
			this.start();
		}catch(Exception e){
			GospelConsumer.appInsight.trackException(e);
		}
	}
	
	public GospelConsumer(String privateKey, String publicKey, String gospelUrl) throws GospelException {
		try {
			this.start();
		}catch(Exception e){
			GospelConsumer.appInsight.trackException(e);
			throw new GospelException(e);
		}
		
	}
	
	public GospelConsumer(String privateKey, String publicKey, String gospelUrl, String apiVersion) throws GospelException {
		try {
			this.start();
		}catch(Exception e){
			GospelConsumer.appInsight.trackException(e);
			throw new GospelException(e);
		}
	}
	
	private void start() throws GospelException   {
		try {
			this.sdk = new AsyncGospelSdk(FileUtils.readFileIntoString((this.publicKey)),FileUtils.readFileIntoString((this.privateKey)),this.gospelUrl, this.apiVersion);
			this.startDefinitions();
		} catch (CertificateParsingException | KeyParsingException | IOException | InterruptedException | ExecutionException | TimeoutException e) {
			GospelConsumer.appInsight.trackException(e);
			throw new GospelException(e);
		}
	} 
	
	public AsyncGospelSdk getSDK() {
		if(this.sdk == null) {
			try {
				this.start();
			} catch (GospelException e) {
				GospelConsumer.appInsight.trackException(e);
			}
		}
		
		return this.sdk;
		
	}
	
	public Map<String, RecordDefinition> getDefinitions(){
		return this.definitions;
	}
	
	private void startDefinitions() throws InterruptedException, ExecutionException, TimeoutException {
		this.definitions.put("PERSON", this.sdk.getRecordDefinition("PERSON").get(GOSPEL_TIMEOUT, TimeUnit.SECONDS));
		this.definitions.put("BODSTATUS", this.sdk.getRecordDefinition("BODSTATUS").get(GOSPEL_TIMEOUT, TimeUnit.SECONDS));
		this.definitions.put("EMPLOYEE", this.sdk.getRecordDefinition("EMPLOYEE").get(GOSPEL_TIMEOUT, TimeUnit.SECONDS));
		this.definitions.put("EMPLOYEE2", this.sdk.getRecordDefinition("EMPLOYEE2").get(GOSPEL_TIMEOUT, TimeUnit.SECONDS));
		this.definitions.put("PAYSLIP", this.sdk.getRecordDefinition("PAYSLIP").get(GOSPEL_TIMEOUT, TimeUnit.SECONDS));
	}
		
}
