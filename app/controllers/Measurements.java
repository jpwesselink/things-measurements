package controllers;

import java.util.List;

import models.Measurement;
import play.mvc.Controller;
import flexjson.JSONSerializer;

public class Measurements extends Controller {
	public static void percentages(){
    	renderText(new JSONSerializer().serialize(Measurement.getPercentages()));
    }
}
