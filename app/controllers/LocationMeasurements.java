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
		if(request.format.equals("json")){
			renderJSON(Serializers.measurementDeepSerializer.serialize(measurement));
			
		} else if (request.format.equals("txt")){
			renderText(location.getTotalsMap());
		}
	}

	public static void index(String slug) {
		Location location = Location.find("bySlug", slug).first();
		notFoundIfNull(location);
		
		if(request.format.equals("json")){
			renderJSON(Serializers.measurementSerializer.serialize(location.measurements));
		} else if (request.format.equals("txt")){
			renderText(location.getTotalsMap());
		}
	}
}
