package de.luuuuuis.httpServer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.google.common.io.ByteStreams;
import com.sun.net.httpserver.HttpExchange;

import de.luuuuuis.Banns;

public class AssetsHandler extends LuisHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		Map<String, String> req = queryToMap(arg0.getRequestURI().getQuery());
		if (req.containsKey("id")) {
			String id = req.get("id");
			if (id.equals("468739.jpg") || id.equals("favicon.ico")) {
				try {
					String url = "/" + id;

					InputStream in = Banns.class.getResourceAsStream(url);
					byte[] bytes = ByteStreams.toByteArray(in);
					response(200, bytes, arg0);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			} else {
				response(200, "illegal request".getBytes(), arg0);
			}
			response(200, "error".getBytes(), arg0);
		} else {
			response(200, "illegal request".getBytes(), arg0);
		}
	}

}
