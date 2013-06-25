package models.events;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import play.Play;
import play.libs.Crypto;
import play.libs.Time;
import play.mvc.Http.Request;
import play.mvc.Http.Response;
import play.mvc.Scope.Session;

public abstract class Event {
	public static final String COOKIE_PREFIX = Play.configuration.getProperty("application.session.cookie", "PLAY");
	public static final boolean COOKIE_SECURE = Play.configuration.getProperty("application.session.secure", "false").toLowerCase().equals("true");
	public static final String COOKIE_EXPIRE = Play.configuration.getProperty("application.session.maxAge", "60s");
	public static final boolean SESSION_HTTPONLY = Play.configuration.getProperty("application.session.httpOnly", "false").toLowerCase().equals("true");
	public static final boolean SESSION_SEND_ONLY_IF_CHANGED = Play.configuration.getProperty("application.session.sendOnlyIfChanged", "false").toLowerCase().equals("true");
	
	public Meta meta;
	
    
    public static class Meta {
    	final public String type;
    	final public Long timestamp;
    	public Meta(String type){
    		this.type = type;
	        this.timestamp = System.currentTimeMillis();
    	}
    	public String getSessionCookie() throws UnsupportedEncodingException{
    		StringBuilder sessionString = new StringBuilder();
    		
    		for (String key : Session.current().all().keySet()) {
    			sessionString.append("\u0000");
    			sessionString.append(key);
    			sessionString.append(":");
    			sessionString.append(Session.current().all().get(key));
    			sessionString.append("\u0000");
    		}
    		String sessionData = URLEncoder.encode(sessionString.toString(), "utf-8");
    		String sign = Crypto.sign(sessionData, Play.secretKey.getBytes());
    		Response dummyResponse = new Response();
    		if (COOKIE_EXPIRE == null) {
    			dummyResponse.setCookie(COOKIE_PREFIX + "_SESSION", sign + "-" + sessionData, null, "/", null, COOKIE_SECURE, SESSION_HTTPONLY);
    		} else {
    			dummyResponse.setCookie(COOKIE_PREFIX + "_SESSION", sign + "-" + sessionData, null, "/", Time.parseDuration(COOKIE_EXPIRE), COOKIE_SECURE, SESSION_HTTPONLY);
    		}
    		
    		Request.current().cookies.put(COOKIE_PREFIX + "_SESSION", dummyResponse.cookies.get(COOKIE_PREFIX + "_SESSION"));
    		return sign + "-" + sessionData;
    	}
    }
    
    public Event(String type) {
    	meta = new Meta(type);
    }
}