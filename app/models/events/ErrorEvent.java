package models.events;

import java.util.HashMap;
import java.util.Map;

public class ErrorEvent extends Event {
	public final String message;
	public ErrorEvent(String message) {
		super("error");
		this.message = message;
	}
}

