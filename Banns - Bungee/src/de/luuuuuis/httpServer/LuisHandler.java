package de.luuuuuis.httpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class LuisHandler implements HttpHandler {

	public void response(int header, byte[] response, HttpExchange exchange) throws IOException {
		exchange.sendResponseHeaders(header, response.length);
		OutputStream os = exchange.getResponseBody();
		os.write(response);
		os.close();
	}

	public String readSite(String fileName) throws IOException {
		InputStream is = BanHttpServer.class.getResourceAsStream("/" + fileName);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));

		String result = "";
		String l = null;
		while ((l = reader.readLine()) != null)
			result += l;
		reader.close();

		return result;
	}

	public static Map<String, String> queryToMap(String query) {
		if (query == null || query.isEmpty())
			return new HashMap<>();

		Map<String, String> result = new HashMap<>();
		for (String param : query.split("&")) {
			String[] entry = param.split("=");
			if (entry.length > 1)
				result.put(entry[0], entry[1]);
			else
				result.put(entry[0], "");
		}
		return result;
	}

	public String replaceAllZahls(String s) {
		String r = "";
		for (char c : s.toCharArray()) {
			if (c >= 48 && c <= 57)
				continue;
			r += c;
		}
		return r;
	}
}
