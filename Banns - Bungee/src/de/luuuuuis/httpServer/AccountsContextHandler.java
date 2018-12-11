/*
 *
 * Created by Luis
 * 29.09.2018 at 22:59:28
 *
 */
package de.luuuuuis.httpServer;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.SQL.HttpSQL;

public class AccountsContextHandler extends LuisHandler {
	
	Gson Gson = new Gson();
	public static int out = 200;
	
	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Map<String,String> request = queryToMap(exchange.getRequestURI().getQuery());
		
		try {
			String result = readSite("accounts.html");
			String script = "";
			
			Session s = BanHttpServer.getSession(exchange.getRemoteAddress().getAddress().getHostAddress());
			
			if(s != null && s.isValid(exchange)/*and session is valid*/) {
				
				
				if(request.containsKey("del") && !request.containsKey("q")) {
					String user = request.get("del");
					
					if(HttpSQL.UserExists(user)) {
						
						HttpSQL.deletePlayer(user);

					}

						script ="<script type=\"text/javascript\">\r\n" + 
								"\r\n" + 
								"	$(window).ready(function () {\r\n" + 
								"		swal({\r\n" + 
								"			  title: \"Successfully removed\",\r\n" + 
								"			  text: \" " + user + " was removed\",\r\n" + 
								"			  type: \"success\"\r\n" + 
								"			});\r\n" + 
								"	history.pushState({}, \"/accounts\", window.location.pathname);\r\n" +
								"	});\r\n" + 
								"\r\n" + 
								"</script>";
					
				} else if(request.containsKey("user") && request.containsKey("password") && !request.containsKey("del")) {
					// .../accounts?user=user&password=password
					String user = request.get("user"), password = request.get("password");
					
					if(!HttpSQL.UserExists(user)) {
						HttpSQL.createPlayer(user, password);
						script ="<script type=\"text/javascript\">\r\n" + 
								"\r\n" + 
								"	$(window).ready(function () {\r\n" + 
								"		swal({\r\n" + 
								"			  title: \"Successfully created\",\r\n" + 
								"			  text: \" " + user + " was created with password: " + password + "\",\r\n" + 
								"			  type: \"success\"\r\n" + 
								"			});\r\n" + 
								"	history.pushState({}, \"/accounts\", window.location.pathname);\r\n" +
								"	});\r\n" + 
								"\r\n" + 
								"</script>";
					} else {
						script ="<script type=\"text/javascript\">\r\n" + 
								"\r\n" + 
								"	$(window).ready(function () {\r\n" + 
								"		swal({\r\n" + 
								"			  title: \"Error\",\r\n" + 
								"			  text: \" " + user + " already exists :)\",\r\n" + 
								"			  type: \"error\"\r\n" + 
								"			});\r\n" + 
								"	history.pushState({}, \"/accounts\", window.location.pathname);\r\n" +
								"	});\r\n" + 
								"\r\n" + 
								"</script>";
					}
				}
				
				String back = "";
				
				for(String users : HttpSQL.getInfos()) {
					
					back += "<tr><td>" + users + "</td><td><a href=\"/accounts?del=" + users + "\"><i class=\"fa fa-trash\"></i></a></td></tr>";
					
				}
				
				if(Banns.update) {
					
					result = result
							.replace("%users%", back)
							.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"))
							.replace("%update%", "<div class=footer>\r\n" + 
									"		<p>Update available at <a href=\"https://www.spigotmc.org/resources/57031/\">Banns >> BanGUI Webinterface</a></p>\r\n" + 
									"	</div>)")
							.replace("%script%", script)
							;
					
				} else {
				
					result = result
							.replace("%users%", back)
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
			
			response(out, result.getBytes(), exchange);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
