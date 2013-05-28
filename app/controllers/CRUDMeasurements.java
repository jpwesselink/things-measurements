package controllers;

import models.Measurement;
import play.mvc.Router;
import play.mvc.With;

@With(Secure.class)
@CRUD.For(Measurement.class)
public class CRUDMeasurements extends CRUD {
	public static void deleteAll() {
		String redirect = params.get("redirect");
		if(redirect == null) redirect = Router.reverse("CRUDMeasurements.list").url;
		redirect(redirect);
	}
}
