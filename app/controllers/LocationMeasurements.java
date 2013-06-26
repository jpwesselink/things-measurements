package controllers;

import models.Feed;
import models.Location;
import models.Measurement;
import models.events.Vote;
import play.mvc.Controller;
import play.mvc.Router;
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
		
		Feed feed = Feed.get();
    	//feed.chatEvents.publish(new Vote());
    	//feed.chatEvents.publish(new Feed.LocationEvent(location, Feed.LocationEvent.EventType.NEW_MEASUREMENT));
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
	
	public static void delete(String slug){
		Location location = Location.find("bySlug", slug).first();
		notFoundIfNull(location);
		for(Measurement measurement : location.measurements){
			measurement.delete();
		}
		
		location.measurements.clear();
		location.save();
		
		
		String redirect = params.get("redirect");
		if(redirect == null) redirect = Router.reverse("CRUDLocations.index").url;
		redirect(redirect);
		
		renderText(params.allSimple());
	}
}
