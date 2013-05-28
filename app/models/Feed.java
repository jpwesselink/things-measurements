package models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import play.libs.F.ArchivedEventStream;
import play.libs.F.EventStream;
import play.libs.F.IndexedEvent;
import play.libs.F.Promise;

public class Feed {
    
    // ~~~~~~~~~ Let's chat! 

	

	public final ArchivedEventStream<Feed.Event> chatEvents = new ArchivedEventStream<Feed.Event>(40);
    
    /**
     * For WebSocket, when a user join the room we return a continuous event stream
     * of ChatEvent
     */
    public EventStream<Feed.Event> join(String user) {
        chatEvents.publish(new Join(user));
        return chatEvents.eventStream();
    }
    
    /**
     * A user leave the room
     */
    public void leave(String user) {
        chatEvents.publish(new Leave(user));
    }
    
   
    /**
     * For long polling, as we are sometimes disconnected, we need to pass 
     * the last event seen id, to be sure to not miss any message
     */
    public Promise<List<IndexedEvent<Feed.Event>>> nextMessages(long lastReceived) {
        return chatEvents.nextEvents(lastReceived);
    }
    
    /**
     * For active refresh, we need to retrieve the whole message archive at
     * each refresh
     */
    public List<Feed.Event> archive() {
        return chatEvents.archive();
    }
    
    // ~~~~~~~~~ Chat room events

    public static abstract class Event {
        
        final public String type;
        final public Long timestamp;
        
        public Event(String type) {
            this.type = type;
            this.timestamp = System.currentTimeMillis();
        }
        
    }
    
    public static class Join extends Event {
        
        final public String user;
        
        public Join(String user) {
            super("join");
            this.user = user;
        }
        
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
    
    public static class Vote extends Event {
    	public Map<Integer, Float> percentages = new HashMap<Integer, Float>();
    	public Measurement measurement;
		public Vote(String type) {
			super("vote");
			// TODO Auto-generated constructor stub
		}

		public Vote(Map<Integer, Float> percentages) {
			super("vote");
			this.percentages = percentages;
		}

		public Vote(Measurement measurement, Map<Integer, Float> percentages) {
			super("vote");
			this.measurement = measurement;
			this.percentages = percentages;
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

