package controllers;

import org.joda.time.DateTime;

import play.mvc.*;

public class RequireJS extends Controller {

    public static void config() {
    	request.format = "js";
        render();
    }
    
    public static void IOT() {
    	request.format = "js";
    	render();
    }
    
    public static void main() {
    	DateTime lastVote = null;
    	System.out.println(session.all());
    	if(session.get("lastVote") != null){
    		System.out.println("asdasd ");
    		renderArgs.put("lastVote", new DateTime(session.get("lastVote")).toDate().getTime());
    	}
    	request.format = "js";
    	render();
    }

}
