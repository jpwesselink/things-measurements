package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.events.Event;
import models.events.Join;
import play.libs.F.ArchivedEventStream;
import play.libs.F.EventStream;
import play.libs.F.IndexedEvent;
import play.libs.F.Promise;

public class Feed {

	public final ArchivedEventStream<Event> chatEvents = new ArchivedEventStream<Event>(40);
	
    public EventStream<Event> join(String user) {
        chatEvents.publish(new Join(user));
        return chatEvents.eventStream();
    }
    
    public void leave(String user) {
        chatEvents.publish(new Leave(user));
    }
    
    public Promise<List<IndexedEvent<Event>>> nextMessages(long lastReceived) {
        return chatEvents.nextEvents(lastReceived);
    }
    
    public List<Event> archive() {
        return chatEvents.archive();
    }
    
    public static class Leave extends Event {
        
        final public String user;
        
        public Leave(String user) {
            super("leave");
            this.user = user;
        }
        
    }
    
    public static class LocationEvent extends Event {
    	public enum EventType {
			NEW_MEASUREMENT, UPDATE, CREATE, DELETE
    	}
		public Location location;
		public EventType eventType;
    	public LocationEvent(Location location, EventType eventType){
    		super("locationEvent");
    		this.location = location;
    		this.eventType = eventType;
    	}
    }
    
    
    // ~~~~~~~~~ Chat room factory

    static Feed instance = null;
    public static Feed get() {
        if(instance == null) {
            instance = new Feed();
        }
        return instance;
    }
    
}

