package de.luuuuuis.httpServer;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class LoginContextHandler extends LuisHandler {
	
	@Override
	public void handle(HttpExchange arg0) throws IOException {
		Map<String,String> request = queryToMap(arg0.getRequestURI().getQuery());
		
		String response = "";
		
		String location = "/overview";
		
		if (request.containsKey("username") && request.containsKey("password")) {
			String username = request.get("username");
			String password = request.get("password");
			
			try {
				if (BanHttpServer.checkLogin(username, password)) {
					Session sess = BanHttpServer.createSession(username, arg0.getRemoteAddress().getAddress().getHostAddress());
					response = "sessionID=" + sess.sessionID;
					location += "?name=" + username;
				} else {
					response = "error=wrong login";
				}
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		} else 
			response = "error=bad request";
		
        arg0.getResponseHeaders().add("Location", location);
        arg0.getResponseHeaders().put("Context-Type", Collections.singletonList("text/plain; charset=UTF-8"));
        response(302, response.getBytes(), arg0);
	}
	
	  public static String hashString(String s) throws NoSuchAlgorithmException {
			byte[] hash = null;
			try {
			    MessageDigest md = MessageDigest.getInstance("SHA-256");
			    hash = md.digest(s.getBytes());
			
			} catch (NoSuchAlgorithmException e) { e.printStackTrace(); }
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < hash.length; ++i) {
			    String hex = Integer.toHexString(hash[i]);
			    if (hex.length() == 1) {
			        sb.append(0);
			        sb.append(hex.charAt(hex.length() - 1));
			    } else {
			        sb.append(hex.substring(hex.length() - 2));
			    }
			}
			return sb.toString();
}

}
