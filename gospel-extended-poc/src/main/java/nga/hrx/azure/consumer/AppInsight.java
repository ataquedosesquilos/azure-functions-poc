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
		this.myClient.trackDependency(dependencyName, commandName, new Duration(0L,0,0,0,duration.intValue()), success);
	}

}
