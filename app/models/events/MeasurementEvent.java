package models.events;

import java.util.HashMap;
import java.util.Map;

import models.Measurement;

public class MeasurementEvent extends Event {
	final public Map<Integer, Float> percentages;
	final public Measurement measurement;
	public MeasurementEvent(String type) {
		super("measurement");
		percentages = new HashMap<Integer, Float>();
		measurement = null;
		// TODO Auto-generated constructor stub
	}

	public MeasurementEvent(Map<Integer, Float> percentages) {
		super("measurement");
		this.percentages = percentages;
		measurement = null;
	}
	public MeasurementEvent(Measurement measurement, Map<Integer, Float> percentages) {
		super("measurement");
		this.measurement = measurement;
		this.percentages = percentages;
	}

}

