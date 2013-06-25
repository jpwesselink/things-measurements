package controllers;



import models.Feed;
import models.Measurement;
import models.events.MeasurementEvent;
import models.events.Vote;

import org.joda.time.DateTime;

import play.mvc.Controller;

import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

public class Application extends Controller {

    public static void index() {
    	
    	renderArgs.put("percentages", Measurement.getPercentages());
    	render();
    }
   
    public static void show(){
    	render();
    }
    
    public static void test(){
    	render();
    }
    
    public static void vote(Long value, double lng, double lat){
    	
    	if(value != null && value >=0 && value <=3){
    		
    		GeometryFactory factory = new GeometryFactory(new PrecisionModel());
    		Measurement measurement = new Measurement();
    		measurement.lat = lat;
    		measurement.lng = lng;
    		measurement.value = params.get("value", Integer.class);
    		measurement.save();
    		
        	params.flash();
        	session.put("lastVote", new DateTime());
        	session.put("yourVote", value);
        	Feed feed = Feed.get();
        	feed.chatEvents.publish(new MeasurementEvent(measurement, Measurement.getPercentages()));
        	feed.chatEvents.publish(new Vote());
        	ok();
    	}
    	badRequest();
    }
    
    public static void thanks(){
    	renderArgs.put("percentages", Measurement.getPercentages());
    	render();
    }
    
    public static void nuke(){
    	render();
    }
}
