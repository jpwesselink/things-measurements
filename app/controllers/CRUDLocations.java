package controllers;

import controllers.CRUD.ObjectType;
import models.Location;
import play.data.binding.Binder;
import play.db.Model;
import play.exceptions.TemplateNotFoundException;
import play.mvc.With;

@With(Secure.class)
@CRUD.For(Location.class)
public class CRUDLocations extends CRUD {
	
}
