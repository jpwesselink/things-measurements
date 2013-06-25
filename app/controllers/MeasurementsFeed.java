package controllers;

import static play.libs.F.Matcher.ClassOf;
import static play.libs.F.Matcher.Equals;
import static play.libs.F.Matcher.StartsWith;
import static play.mvc.Http.WebSocketEvent.SocketClosed;
import static play.mvc.Http.WebSocketEvent.TextFrame;
import models.Feed;
import models.Measurement;
import models.events.ErrorEvent;
import models.events.Event;
import models.events.Join;
import models.events.MeasurementEvent;
import models.events.Vote;

import org.joda.time.DateTime;

import play.Logger;
import play.Play;
import play.data.validation.Validation;
import play.libs.F.Either;
import play.libs.F.EventStream;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Http.WebSocketClose;
import play.mvc.Http.WebSocketEvent;
import play.mvc.WebSocketController;
import utils.SessionUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import flexjson.JSONDeserializer;
public class MeasurementsFeed extends Controller {
    
	
    public static void room(String user) {
        render(user);
    }

    public static class FeedSocket extends WebSocketController {
        public static void join() {
        	String user = "nobody";
            Feed room = Feed.get();
            
            // Socket connected, join the chat room
            EventStream<Event> messagesStream = room.join(user);
         
            // Loop while the socket is open
            while(inbound.isOpen()) {
                
                // Wait for an event (either something coming on the inbound socket channel, or ChatRoom messages)
                Either<WebSocketEvent,Event> e = await(Promise.waitEither(
                    inbound.nextEvent(), 
                    messagesStream.nextEvent()
                ));
                
                // unwrap json
                for(String userMessage: TextFrame.and(StartsWith("{")).match(e._1)) {
                	try {
                		JsonElement jsonElement = new JsonParser().parse(userMessage);
                    	if(jsonElement!=null){
                        	if(jsonElement.isJsonObject()){
                        		JsonObject json = jsonElement.getAsJsonObject();
                        		JsonElement sessionCookieElement = json.get("sessionCookie");
                        		boolean failed = false;
                        		if(sessionCookieElement != null){
                        			if(!SessionUtils.restore(session, sessionCookieElement.getAsString())){
                        				session.clear();
                        				session.getAuthenticityToken();
                        				failed = true;
                        				outbound.send(serializers.Serializers.eventSerializer.serialize(new ErrorEvent("That wasn't very nice")));
                        			}
                        		}
                        		if(!failed){
                        			
                            		JsonElement commandElement = json.get("command");
                            		if(commandElement != null){
                            			String command = commandElement.getAsString();
                            			if(command != null){
                            				if(command.equals("vote")){
                            					if(json.get("data") != null){
                            						
                            						Measurement measurement = null;
                            						try {
                            							measurement = new JSONDeserializer<Measurement>().deserialize(json.get("data").toString(), Measurement.class);
                            						} catch (Exception someException) {
                            							// yeah do something
                            						}
                        							if(measurement != null){
                        								if(Validation.current().valid(measurement).ok){
                        									if(session.get("lastVote") == null){
                        										// yes we can vote
                        										measurement.save();
                        										//session.put("someid", measurement.id);
                        										session.put("lastVote", new DateTime().toString());
                        										outbound.send(serializers.Serializers.percentagesSerializer.serialize(new Vote()));
                        										room.chatEvents.publish(new MeasurementEvent(measurement, Measurement.getPercentages()));
                        									} else {
                        										// is datetime?
                        										DateTime lastVote = new DateTime(session.get("lastVote"));
                        										int voteGap = Integer.parseInt(Play.configuration.getProperty("votes.gap.seconds", "60"));
                        										if(new DateTime().isAfter(lastVote.plusSeconds(voteGap))){
                        											measurement.save();
                        											//session.put("someid", measurement.id);
                        											session.put("lastVote", new DateTime().toString());
                        											room.chatEvents.publish(new MeasurementEvent(measurement, Measurement.getPercentages()));
                        											outbound.send(serializers.Serializers.eventSerializer.serialize(new Vote()));
                        										} else {
                        											outbound.send(serializers.Serializers.eventSerializer.serialize(new ErrorEvent("tisk")));
                        										}
                        									}
                        								} else {
                        									// tell invalid
                        								}
                        							}
                            					}
                            				}
                            			} 
                            		}
                            	}
                        	}
                    	}
                	} catch (Throwable t){
                		Logger.error(t, t.getMessage(), null);
                	}
                }
                
                // Case: User typed 'quit'
                for(String userMessage: TextFrame.and(Equals("quit")).match(e._1)) {
                    room.leave(user);
                   
                    outbound.send("quit:ok");
                    disconnect();
                }
                
                
                for(Join joined: ClassOf(Join.class).match(e._2)) {
                	outbound.send(serializers.Serializers.percentagesSerializer.exclude("geoLocation").serialize(Measurement.find("from Measurement m order by id desc").fetch(1)));
                }
                
                for(Feed.LocationEvent locationEvent: ClassOf(Feed.LocationEvent.class).match(e._2)) {
                    outbound.send(serializers.Serializers.locationEventSerializer.serialize(locationEvent));
                }
                
                
                for(Vote vote: ClassOf(Vote.class).match(e._2)) {
                    outbound.send(serializers.Serializers.eventSerializer.serialize(vote));
                }
                for(MeasurementEvent measurementEvent: ClassOf(MeasurementEvent.class).match(e._2)) {
                	outbound.send(serializers.Serializers.eventSerializer.serialize(measurementEvent));
                }
                
                // Case: The socket has been closed
                for(WebSocketClose closed: SocketClosed.match(e._1)) {
                	room.leave(user);
                    disconnect();
                }
            }
        }
    }
}

