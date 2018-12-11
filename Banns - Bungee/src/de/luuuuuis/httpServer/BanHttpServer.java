package de.luuuuuis.httpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import de.luuuuuis.SQL.HttpSQL;

public class BanHttpServer {

	static HashMap<String, Session> sessions = new HashMap<>();

	String[] rndString = { "/overview", "/banlist", "/accounts", "/login", "/logout", "/check", "/assets" };

	private HttpServer server;

	public BanHttpServer() {

		try {
			server = HttpServer.create(new InetSocketAddress(1337), 0);
			System.out.println("Banns >> WebInterface started!");
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.createContext("/", new LuisHandler() {

			@Override
			public void handle(HttpExchange arg0) throws IOException {
				if (arg0.getRequestURI() == null || arg0.getRequestURI().toString().isEmpty()
						|| arg0.getRequestURI().toString().equalsIgnoreCase("/")) {
					arg0.getResponseHeaders().add("Location", "/overview");
					response(302, "If you see this you are totaly wrong here!".getBytes(), arg0);
				} else {
					String q = arg0.getRequestURI().toString();
					for (String s : rndString) {
						if (q.toLowerCase().startsWith(s.toLowerCase())) {
							return;
						}
					}

					arg0.getResponseHeaders().add("Location", "/overview");
					arg0.getResponseHeaders().put("Context-Type",
							Collections.singletonList("text/plain; charset=UTF-8"));
					response(302, readSite("404.html").getBytes(), arg0);

				}
			}
		});
		server.createContext("/overview", new OverviewHandler());
		server.createContext("/banlist", new BanContextHandler());
		server.createContext("/accounts", new AccountsContextHandler());
		server.createContext("/login", new LoginContextHandler());
		server.createContext("/logout", new LogoutContextHandler());
		server.createContext("/check", new CheckContextHandler());
		server.createContext("/assets", new AssetsHandler());

		server.setExecutor(null);

		server.start();
	}

	public void stop() {
		server.stop(0);
	}

	public static boolean checkLogin(String username, String password) throws NoSuchAlgorithmException {
		if (HttpSQL.UserExists(username)) {
			if (LoginContextHandler.hashString(password).equals(HttpSQL.getPassword(username))) {
				return true;
			}
		}
		return false;
	}

	public static Session createSession(String user, String ipAddress) {
		Session s = new Session(user, ipAddress);
		sessions.put(ipAddress, s);
		return s;
	}

	public static Session getSession(String ipAddress) {
		return sessions.getOrDefault(ipAddress, null);
	}

}
