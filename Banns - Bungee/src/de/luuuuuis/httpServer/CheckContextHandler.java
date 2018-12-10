package de.luuuuuis.httpServer;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.MojangUUIDResolve;
import de.luuuuuis.TimeManager;
import de.luuuuuis.SQL.BanInfo;
import de.luuuuuis.SQL.Ban;
import de.luuuuuis.SQL.MuteInfo;
import de.luuuuuis.SQL.MuteSQLHandler;

public class CheckContextHandler extends LuisHandler {

	
	String checktable;
	String mutetable;
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		Integer HTTPCode = 200;
		String script = "";
		String result = readSite("check.html");
		
		Session s = BanHttpServer.getSession(exchange.getRemoteAddress().getAddress().getHostAddress());
		if(s != null && s.isValid(exchange)/*and session is valid*/) {
			Map<String, String> req = queryToMap(exchange.getRequestURI().getQuery());
			
			if(req.isEmpty()) {
				exchange.getResponseHeaders().add("Location", "/overview");
				exchange.getResponseHeaders().put("Context-Type", Collections.singletonList("text/plain; charset=UTF-8"));
				HTTPCode = 302;
			} else if(req.containsKey("unban")) {
				String type = req.get("unban");
				String name = req.get("username");
				String uuid = MojangUUIDResolve.getUUIDResult(name).getValue();
				
				if(type.equals("BAN")) {
					if(BanInfo.getBanInfo(uuid) != null) {
						new Ban(uuid).unban();
						script =
								"		swal({\r\n" + 
								"			  title: \"Successfully unbanned\",\r\n" + 
								"			  text: \" " + name + " was unbanned\",\r\n" + 
								"			  icon: \"success\",\r\n" + 
								"			});\r\n";
					} else {
						script =
								"		swal({\r\n" + 
								"			  title: \"Can not unban\",\r\n" + 
								"			  text: \"Error while unbanning " + name + ",\r\n" + 
								"			  icon: \"error\",\r\n" + 
								"			});\r\n";
					}
				} else if(type.equals("MUTE")) {
					if(MuteSQLHandler.playerExists(uuid)) {
						MuteSQLHandler.unban(uuid);
						script =
								"		swal({\r\n" + 
								"			  title: \"Successfully unmuted\",\r\n" + 
								"			  text: \" " + name + " was unmuted\",\r\n" + 
								"			  icon: \"success\",\r\n" + 
								"			});\r\n";
					} else {
						script =
								"		swal({\r\n" + 
								"			  title: \"Can not unmute\",\r\n" + 
								"			  text: \"Error while unmuteing " + name + ",\r\n" + 
								"			  icon: \"error\",\r\n" + 
								"			});\r\n";
					}
				}
			}
			if(req.containsKey("username")) {
				String name = req.get("username"); //req.values().stream().findFirst().orElse(null);
				// localhost/check?name=Luuuuuis req.get("name");
				// display check:display mit dem namen lel
				result = readSite("check.html");
				
				try {
					
					String uuid = MojangUUIDResolve.getUUIDResult(name).getValue();
					
					String mute = "no info found";
					String ban = "no info found";
				
					BanInfo banInfo = BanInfo.getBanInfo(uuid);
					MuteInfo muteInfo = MuteInfo.getBanInfo(uuid);
				
					if(muteInfo != null) {
						
			              Date date = new Date(muteInfo.timeBanned);
			              DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm 'Uhr'");
			              String dateFormatted = formatter.format(date);
						
						if(muteInfo.perm == true) {
							mute = "Date >> " + dateFormatted
									+ "<br>"
									+ "Reason >> " + muteInfo.reason
									+ "<br>"
									+ "Executor >> " + muteInfo.banner
									+ "</p>"
									+ "<a href=\"/check?username=%name%&unban=MUTE\" class=\"btn btn-primary\">Unmute</a>"
									;
						} else {
							
				             long unbannedtime = muteInfo.timeNext;
				              long crt = System.currentTimeMillis();
				              long uz = unbannedtime / 1000L;
				              long zi = crt / 1000L;
				              long hz = uz - zi;
				              String lastSecs = TimeManager.calc(hz);
							
							mute = "Date >> " + dateFormatted
									+ "<br>"
									+ "Time left >> " + lastSecs
									+ "<br>"
									+ "Reason >> " + muteInfo.reason
									+ "<br>"
									+ "Executor >> " + muteInfo.banner
									+ "</p>"
									+ "<a href=\"/check?username=%name%&unban=MUTE\" class=\"btn btn-primary\">Unmute</a>"
									;
							
						}
						
					} else {
						
						mute = "This player isn't muted";
						
					}
					
					if(banInfo != null) {
						
			              Date date = new Date(banInfo.timeBanned);
			              DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm 'Uhr'");
			              String dateFormatted = formatter.format(date);
						
						if(banInfo.perm) {
							
							ban = "Date >> " + dateFormatted
									+ "<br>"
									+ "Reason >> " + banInfo.reason
									+ "<br>"
									+ "Executor >> " + banInfo.banner
									+ "</p>"
									+ "<a href=\"/check?username=%name%&unban=BAN\" class=\"btn btn-primary\">Unban</a>"
									;
							
						} else {
							
				             long unbannedtime = banInfo.timeNext;
				              long crt = System.currentTimeMillis();
				              long uz = unbannedtime / 1000L;
				              long zi = crt / 1000L;
				              long hz = uz - zi;
				              String lastSecs = TimeManager.calc(hz);
							
							ban = "Date >> " + dateFormatted
									+ "<br>"
									+ "Time left >> " + lastSecs
									+ "<br>"
									+ "Reason >> " + banInfo.reason
									+ "<br>"
									+ "Executor >> " + banInfo.banner
									+ "</p>"
									+ "<a href=\"/check?username=%name%&unban=BAN\" class=\"btn btn-primary\">Unban</a>"
									;
							
						}
						
	 				} else {
	 					
	 					ban = "This player isn't banned";
	 					
	 				}
					
					
					if(Banns.update) {
						
						result = result
								.replace("%mute%", mute)
								.replace("%ban%", ban)
								.replace("%name%", name)
								.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"))
								.replace("%update%", "<div class=footer>\r\n" + 
										"		<p>Update available at <a href=\"https://www.spigotmc.org/resources/57031/\">Banns >> BanGUI Webinterface</a></p>\r\n" + 
										"	</div>)")
								.replace("%script%", script)
								;
						
					} else {
					
						result = result
								.replace("%mute%", mute)
								.replace("%ban%", ban)
								.replace("%name%", name)
								.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"))
								.replace("%update%", "")
								.replace("%script%", script)
								;
					
					}
				
				} catch (Exception ex) {
					ex.printStackTrace();
					result = result
							.replace("%mute%", "null")
							.replace("%ban%", "null")
							.replace("%name%", name)
							.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"))
							.replace("%update%", "")
							.replace("%script%", script)
							;
				}
				
				
			}
		} else {
			result = readSite("login.html")
					.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"));
		}
		
		response(HTTPCode, result.getBytes(), exchange);
			
	}

}
