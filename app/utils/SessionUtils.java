package utils;

import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import play.Play;
import play.exceptions.UnexpectedException;
import play.libs.Crypto;
import play.libs.Time;
import play.mvc.Http;
import play.mvc.Http.Cookie;
import play.mvc.Scope.Session;


public class SessionUtils {
	static Pattern sessionParser = Pattern.compile("\u0000([^:]*):([^\u0000]*)\u0000");
	public static final String AT_KEY = "___AT";
	public static final String ID_KEY = "___ID";
	public static final String TS_KEY = "___TS";
    public static final String COOKIE_PREFIX = Play.configuration.getProperty("application.session.cookie", "PLAY");
	public static final boolean COOKIE_SECURE = Play.configuration.getProperty("application.session.secure", "false").toLowerCase().equals("true");
	public static final String COOKIE_EXPIRE = Play.configuration.getProperty("application.session.maxAge", "60s");
	public static final boolean SESSION_HTTPONLY = Play.configuration.getProperty("application.session.httpOnly", "false").toLowerCase().equals("true");
	public static final boolean SESSION_SEND_ONLY_IF_CHANGED = Play.configuration.getProperty("application.session.sendOnlyIfChanged", "false").toLowerCase().equals("true");
	
	
    
    public static boolean restore(Session session, String sessionCookie) {
        try {
            // Http.Cookie cookie = Http.Request.current().cookies.get(COOKIE_PREFIX + "_SESSION");

            if (sessionCookie != null && Play.started && !sessionCookie.trim().equals("")) {
			 	int firstDashIndex = sessionCookie.indexOf("-");
			    if(firstDashIndex > -1) {
                	String sign = sessionCookie.substring(0, firstDashIndex);
                	String data = sessionCookie.substring(firstDashIndex + 1);
                	if (sign.equals(Crypto.sign(data, Play.secretKey.getBytes()))) {
                    	String sessionData = URLDecoder.decode(data, "utf-8");
                    	Matcher matcher = sessionParser.matcher(sessionData);
                    	while (matcher.find()) {
                        	session.put(matcher.group(1), matcher.group(2));
                    	}
                	} else {
                		session.clear();
                		return false;
                	}
				} else {
					session.clear();
					return false;
				}
            } 

        } catch (Exception e) {
            throw new UnexpectedException("Corrupted HTTP session from " + Http.Request.current().remoteAddress, e);
        }
        return true;
    }
}
