package controllers;

import models.Location;
import models.Measurement;
import play.mvc.*;

public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void show(){
    	renderArgs.put("yourVote", flash.get("value"));
    	render();
    }
    
    public static void vote(Long value){
    	Location location = Location.find("bySlug", "the-hague").first();
    	Measurement measurement = new Measurement();
		measurement.location = location;
		measurement.value = params.get("value", Integer.class);
		measurement.save();
		
    	params.flash();
    	show();
    }
}
