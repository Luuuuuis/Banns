package de.luuuuuis.httpServer;

import java.util.Base64;

import com.sun.net.httpserver.HttpExchange;

public class Session {

	public String sessionID;
	public String user;

	public Session(String user, String ipAddress) {
		this.sessionID = Base64.getEncoder().encodeToString((System.currentTimeMillis() + ipAddress).getBytes());
		this.user = user;
	}

	public boolean isValid(HttpExchange exchange) {
		return new String(Base64.getDecoder().decode(sessionID.getBytes()))
				.endsWith(exchange.getRemoteAddress().getAddress().getHostAddress());
	}

}
