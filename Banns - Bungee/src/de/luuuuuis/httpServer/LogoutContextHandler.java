package de.luuuuuis.httpServer;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

public class LogoutContextHandler extends LuisHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		BanHttpServer.sessions.remove(arg0.getRemoteAddress().getAddress().getHostAddress());
		arg0.getResponseHeaders().add("Location", "/overview");
		response(302, "loggedout".getBytes(), arg0);
	}

}
