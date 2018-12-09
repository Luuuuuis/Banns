package de.luuuuuis.httpServer;

import java.io.IOException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;

public class OverviewHandler extends LuisHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Map<String,String> request = queryToMap(exchange.getRequestURI().getQuery());
		
		try {
			String result = readSite("overview.html");
			String script = "";
			
			Session s = BanHttpServer.getSession(exchange.getRemoteAddress().getAddress().getHostAddress());
			
			if(s != null && s.isValid(exchange)/*and session is valid*/) {
				
				if(request.containsKey("name")) {
					String name = request.get("name");
					
					script = "<script src=\"https://unpkg.com/sweetalert/dist/sweetalert.min.js\"></script>\r\n" + 
							"\r\n" + 
							"<script type=\"text/javascript\">\r\n" + 
							"\r\n" + 
							"	$(window).ready(function () {\r\n" + 
							"		swal({\r\n" + 
							"			  title: \"Welcome back, " + name + "!\",\r\n" + 
							"			  icon: \"success\",\r\n" + 
							"			  timer: 1000\r\n" +
							"			});\r\n" + 
							"	history.pushState({}, \"/overview\", window.location.pathname);\r\n" +
							"	});\r\n" + 
							"\r\n" + 
							"</script>";
					
					
				}
				
				
				if(Banns.update) {
					
					result = result
							.replace("%lastBan%", "<td>" + Info.LastBanName + "</td>"
									+ "<td>" + Info.LastBanReason + "</td>"
									+ "<td>" + Info.LastBanTime + "</td>")
							.replace("%lastMute%", "<td>" + Info.LastMuteName + "</td>"
									+ "<td>" + Info.LastMuteReason + "</td>"
									+ "<td>" + Info.LastMuteTime + "</td>")
							.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"))
							.replace("%update%", "<div class=footer>\r\n" + 
									"		<p>Update available at <a href=\"https://www.spigotmc.org/resources/57031/\">Banns >> BanGUI Webinterface</a></p>\r\n" + 
									"	</div>)")
							.replace("%script%", script)
							;
					
				} else {
				
					result = result
							.replace("%lastBan%", "<td>" + Info.LastBanName + "</td>"
												+ "<td>" + Info.LastBanReason + "</td>"
												+ "<td>" + Info.LastBanTime + "</td>")
							.replace("%lastMute%", "<td>" + Info.LastMuteName + "</td>"
												+ "<td>" + Info.LastMuteReason + "</td>"
												+ "<td>" + Info.LastMuteTime + "</td>")
							.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"))
							.replace("%update%", "")
							.replace("%script%", script)
							;
					
				}
			} else {
				result = readSite("login.html")
						.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"))
						;
			}
			
			response(200, result.getBytes(), exchange);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
