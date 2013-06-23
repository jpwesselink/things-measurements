package serializers;

import play.Play;
import play.Play.Mode;
import flexjson.JSONSerializer;
import flexjson.transformer.MapTransformer;

public class Serializers {
	
	public static final JSONSerializer locationSerializer;
	public static final JSONSerializer locationEventSerializer;
	public static final JSONSerializer measurementSerializer;
	public static final JSONSerializer measurementCompactSerializer;
	public static final JSONSerializer percentagesSerializer;
	public static final JSONSerializer measurementDeepSerializer;
	

	static {
		boolean prettyPrint = Play.mode == Mode.DEV;
		
		locationSerializer = new JSONSerializer().include(
				"id",
				"name",
				"lng",
				"lat",
				"slug",
				"numberOfMeasurements",
				"percentages.*"
				
		).exclude("*").prettyPrint(prettyPrint);
		
		locationEventSerializer = new JSONSerializer().include(
				"type",
				"location.id",
				"location.name",
				"location.lng",
				"location.lat",
				"location.slug",
				"location.numberOfMeasurements",
				"location.percentages.*"
				
				).exclude("*.id").prettyPrint(prettyPrint);
		
		measurementSerializer = new JSONSerializer().include(
				"id",
				"value",
				"createdAt"
		).exclude("*").prettyPrint(prettyPrint);
		
		measurementCompactSerializer = new JSONSerializer().include(
				"value",
				"lng",
				"lat"
				).exclude("*").prettyPrint(false);
		
		percentagesSerializer = new JSONSerializer().exclude("geoLocation", "*.id", "*.entityId").prettyPrint(prettyPrint);
		
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
