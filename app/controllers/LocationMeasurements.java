package controllers;

import models.Location;
import models.Measurement;
import play.mvc.Controller;
import serializers.Serializers;

public class LocationMeasurements extends Controller {

	public static void create(String slug) {
		Location location = Location.find("bySlug", slug).first();
		notFoundIfNull(location);
		Integer value = params.get("value", Integer.class);
		if (value == null) {
			badRequest();
		}
		Measurement measurement = new Measurement();
		measurement.location = location;
		measurement.value = params.get("value", Integer.class);
		measurement.save();
		renderJSON(Serializers.measurementDeepSerializer.serialize(measurement));
	}

	public static void index(String slug) {
		Location location = Location.find("bySlug", slug).first();
		notFoundIfNull(location);
		renderJSON(Serializers.measurementSerializer.serialize(location.measurements));
	}
}
