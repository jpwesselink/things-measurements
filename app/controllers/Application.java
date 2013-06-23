package controllers;



import models.Feed;
import models.Measurement;

import org.joda.time.DateTime;

import play.Play;
import play.mvc.Controller;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.PrecisionModel;

public class Application extends Controller {

    public static void index() {
    	DateTime lastVote = null;
    	if(session.get("lastVote") != null){
    		try {
    			lastVote = DateTime.parse(session.get("lastVote"));
    			if(new DateTime().minusSeconds(Integer.parseInt(Play.configuration.getProperty("voting.interval.minutes", "480"))).isAfter(lastVote)){
    				session.remove("yourVote");
    			}
    		}  finally {
    			// 
    		}
    	}
    	if(session.get("yourVote") != null){
    		Integer yourVote = Integer.parseInt(session.get("yourVote"));
    		if(yourVote != null && yourVote >=0 && yourVote <= 3){
    			renderArgs.put("yourVote", yourVote);
    		}
    	}
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
        	feed.chatEvents.publish(new Feed.Vote(measurement, Measurement.getPercentages()));
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
