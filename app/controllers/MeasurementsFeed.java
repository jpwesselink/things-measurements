package controllers;

import static play.libs.F.Matcher.ClassOf;
import static play.libs.F.Matcher.Equals;
import static play.mvc.Http.WebSocketEvent.SocketClosed;
import static play.mvc.Http.WebSocketEvent.TextFrame;
import models.Feed;
import models.Measurement;
import play.libs.F.Either;
import play.libs.F.EventStream;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Http.WebSocketClose;
import play.mvc.Http.WebSocketEvent;
import play.mvc.WebSocketController;
public class MeasurementsFeed extends Controller {
    
    public static void room(String user) {
        render(user);
    }

    public static class FeedSocket extends WebSocketController {
        
        public static void join() {
        	String user = "nobody";
            Feed room = Feed.get();
            
            // Socket connected, join the chat room
            EventStream<Feed.Event> messagesStream = room.join(user);
         
            // Loop while the socket is open
            while(inbound.isOpen()) {
                
                // Wait for an event (either something coming on the inbound socket channel, or ChatRoom messages)
                Either<WebSocketEvent,Feed.Event> e = await(Promise.waitEither(
                    inbound.nextEvent(), 
                    messagesStream.nextEvent()
                ));
                
                // Case: User typed 'quit'
                for(String userMessage: TextFrame.and(Equals("quit")).match(e._1)) {
                    room.leave(user);
                   
                    outbound.send("quit:ok");
                    disconnect();
                }
                
                
                for(Feed.Join joined: ClassOf(Feed.Join.class).match(e._2)) {
                	System.out.println("join");
                	outbound.send(serializers.Serializers.percentagesSerializer.exclude("geoLocation").serialize(Measurement.find("from Measurement m order by id desc").fetch(1)));
                }
                
                for(Feed.LocationEvent locationEvent: ClassOf(Feed.LocationEvent.class).match(e._2)) {
                    outbound.send(serializers.Serializers.locationEventSerializer.serialize(locationEvent));
                }
                
                
                for(Feed.Vote vote: ClassOf(Feed.Vote.class).match(e._2)) {
                    outbound.send(serializers.Serializers.percentagesSerializer.serialize(vote));
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

