package serializers;

import java.util.List;

import play.Play;
import play.Play.Mode;
import flexjson.JSONSerializer;
import flexjson.transformer.MapTransformer;

public class Serializers {
	
	public static final JSONSerializer locationSerializer;
	public static final JSONSerializer measurementSerializer;
	public static final JSONSerializer measurementDeepSerializer;
	

	static {
		boolean prettyPrint = Play.mode == Mode.DEV;
		
		locationSerializer = new JSONSerializer().transform(new MapTransformer(), "twats").include(
				"id",
				"name",
				"slug",
				"numberOfMeasurements",
				"percentages.*"
				
		).exclude("*").prettyPrint(prettyPrint);
		
		measurementSerializer = new JSONSerializer().include(
				"id",
				"value",
				"createdAt"
		).exclude("*").prettyPrint(prettyPrint);
		
		measurementDeepSerializer = new JSONSerializer().include(
				"id",
				"value",
				"createdAt",
				"location.name",
				"location.slug",
				"location.numberOfMeasurements",
				"location.percentages.*"
				).exclude("*").prettyPrint(prettyPrint);
	}
}
