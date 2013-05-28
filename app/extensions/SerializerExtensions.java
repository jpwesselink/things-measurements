package extensions;

import java.util.Collection;

import play.templates.JavaExtensions;
import serializers.Serializers;
import flexjson.JSONSerializer;

public class SerializerExtensions extends JavaExtensions {
	public static String toJson(Collection<Object> models){
		return new JSONSerializer().serialize(models);
	}

	/**
	 * Serialize a model to JSON with a given serializer.
	 */
	public static String serializeWith(Object model, String serializer) throws IllegalArgumentException,
			SecurityException, IllegalAccessException, NoSuchFieldException {
		JSONSerializer js = (JSONSerializer) Serializers.class.getField(serializer).get(null);
		return js.serialize(model);
	}

	/**
	 * Serialize a list of models to JSON with a given serializer.
	 */
	public static String serializeWith(Collection<Object> models, String serializer) throws IllegalArgumentException,
			SecurityException, IllegalAccessException, NoSuchFieldException {
		JSONSerializer js = (JSONSerializer) Serializers.class.getField(serializer).get(null);
		return js.serialize(models);
	}
}
