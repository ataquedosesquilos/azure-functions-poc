package nga.hrx.azure.consumer;

import java.util.Map;

import com.microsoft.applicationinsights.TelemetryClient;
import com.microsoft.applicationinsights.TelemetryConfiguration;
import com.microsoft.applicationinsights.telemetry.Duration;

public class AppInsight {
	
	private TelemetryClient myClient;
	
	public AppInsight() {
		this.myClient = new TelemetryClient();
	}
	
	public AppInsight(String instrumentationKey) {
		TelemetryConfiguration conf = new TelemetryConfiguration() ;
		conf.setInstrumentationKey(instrumentationKey);
		this.myClient = new TelemetryClient(conf);
	}
	
	public void trackMetric(String name, double value, Integer sampleCount, Double min, Double max, Double stdDev, Map<String, String> properties) {
		this.myClient.trackMetric(name, value, sampleCount, min, max, stdDev, properties);
	}
	
	public void trackMetric(String name, double value ) {
		this.myClient.trackMetric(name, value);
	}
	
	public void trackDependency(String dependencyName, String commandName, Long duration, boolean success) {
		Long minutes =  (duration / 1000) / 60;
		Long seconds =  (duration / 1000) % 60;
		Long durationMilliseconds = duration % 1000;
		this.myClient.trackDependency(dependencyName, commandName, new Duration(0L,0, minutes.intValue(), seconds.intValue() , durationMilliseconds.intValue()), success);
	}
	
	public void trackException(Exception exception) {
		this.myClient.trackException(exception);
	}

}
