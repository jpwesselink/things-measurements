package models.events;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;

import play.data.validation.Required;

public class MeasurementEvent extends Event {
	final public Map<Integer, Float> percentages;
	final public Measurement measurement;
	
	// HACK: not enough time
	public static class Measurement {
		public Date createdAt = new Date();
		
		public Integer value;
		
		public double lat;
		
		public double lng;
		
		public Measurement(models.Measurement measurement){
			createdAt = measurement.createdAt;
			value = measurement.value;
			lat = measurement.lat;
			lng = measurement.lng;
		}
	}
	public MeasurementEvent(String type) {
		super("measurement");
		percentages = new HashMap<Integer, Float>();
		measurement = null;
	}

	public MeasurementEvent(Map<Integer, Float> percentages) {
		super("measurement");
		this.percentages = percentages;
		measurement = null;
	}
	public MeasurementEvent(models.Measurement measurement, Map<Integer, Float> percentages) {
		super("measurement");
		this.measurement = new Measurement(measurement);
		this.percentages = percentages;
	}

}

