package controllers;

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
    	request.format = "js";
    	render();
    }

}
