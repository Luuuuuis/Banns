package de.luuuuuis.httpServer;

import java.io.IOException;
import java.sql.SQLException;

import com.sun.net.httpserver.HttpExchange;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.MojangUUIDResolve;
import de.luuuuuis.TimeManager;
import de.luuuuuis.SQL.BanInfo;
import de.luuuuuis.SQL.Ban;
import de.luuuuuis.SQL.MuteInfo;
import de.luuuuuis.SQL.MuteSQLHandler;

public class BanContextHandler extends LuisHandler {

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			String result = readSite("banlist.html");
			
			Session s = BanHttpServer.getSession(exchange.getRemoteAddress().getAddress().getHostAddress());
			
			if(s != null && s.isValid(exchange)/*and session is valid*/) {
				String bList = getBanListe();
	
				String banListe = "<table id=\"bantable\" class=\"table tabel-hover table-light table-sm\"style=\"width:100%\">"
						+ "<thead class=\"thead-dark\"><tr><th scope=\"col\">UUID</th><th scope=\"col\">Name</th><th scope=\"col\">Time</th><th scope=\"col\">Reason</th><th scope=\"col\">Executor</th></tr></thead>"
						+ "<tbody>" + 
						bList + 
						"</tbody></table>";
				
				if(Banns.update) {
					
					result = result
							.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"))
							.replace("%banliste%", banListe)
							.replace("%update%", "<div class=footer>\r\n" + 
									"		<p>Update available at <a href=\"https://www.spigotmc.org/resources/57031/\">Banns >> BanGUI Webinterface</a></p>\r\n" + 
									"	</div>)")
							;
					
				} else {
				
					result = result
							.replace("%servername%", (CharSequence) Info.ServerInfo.get("domainname"))
							.replace("%banliste%", banListe)
							.replace("%update%", "")
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
	
	private String getBanListe() throws SQLException {
		
		
		String bList = "";
		String Time;
		try {
			
			for(BanInfo banInfo : BanInfo.getInfos()) {
				
				if(banInfo.uuid == null || banInfo.uuid == "null")
					continue;
				
				if(banInfo.perm)
					Time = "Permanent";
				else
					Time = TimeManager.calc(((long) banInfo.timeNext - (long)System.currentTimeMillis())/1000L);
				
				if(Time.equalsIgnoreCase("0 seconds")) {
					new Ban(banInfo.uuid).unban();
					continue;
				}
				
			    String content = "<tr><td>" + banInfo.uuid + "</td><td>" + MojangUUIDResolve.getNameResult(banInfo.uuid).getValue() + "</td><td>" + Time + "</td><td>" + replaceAllZahls(banInfo.reason) + "</td><td>" + banInfo.banner + "</td></tr>";
			    bList += content;
			   
			}
		
		
		for(MuteInfo muteInfo : MuteSQLHandler.getInfos()) {
			if(muteInfo.uuid == null || muteInfo.uuid == "null")
				continue;
			
			if(muteInfo.perm)
				Time = "Permanent";
			else
				Time = TimeManager.calc(((long) muteInfo.timeNext - (long)System.currentTimeMillis())/1000L);
			
			if(Time.equalsIgnoreCase("0 seconds")) {
				MuteSQLHandler.unban(muteInfo.uuid);
				continue;
			}
			
			String content = "<tr><td>" + muteInfo.uuid + "</td><td>" + MojangUUIDResolve.getNameResult(muteInfo.uuid).getValue() + "</td><td>" + Time + "</td><td>" + replaceAllZahls(muteInfo.reason) + "</td><td>" + muteInfo.banner + "</td></tr>";
		    bList += content;
		}
		
	} catch (Exception ex) {
		ex.printStackTrace();
		bList = null;
	}
		
		return bList;
	}
	
}
