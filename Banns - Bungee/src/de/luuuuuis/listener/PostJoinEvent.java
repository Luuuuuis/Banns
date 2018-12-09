/*
 *
 * Created by Luis
 * 28.09.2018 at 18:53:22
 *
 */
package de.luuuuuis.listener;

import java.io.IOException;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.TimeManager;
import de.luuuuuis.SQL.MuteInfo;
import de.luuuuuis.SQL.MuteSQLHandler;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PostJoinEvent implements Listener {
	
	public static String MReason;
	public static String MTime;
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPostJoin(PostLoginEvent e) throws IOException {
		ProxiedPlayer p = e.getPlayer();
		
	    MuteInfo muteInfo = MuteInfo.getBanInfo(p.getUniqueId().toString());
	    
	    if (muteInfo != null) {
	    	
	        long unbannedtime = muteInfo.timeNext;
	        long crt = System.currentTimeMillis();
	        if(crt > unbannedtime || p.hasPermission(Banns.getBanperm())) {
	          MuteSQLHandler.unban(p.getUniqueId().toString());          
	        } else {
	            long uz = unbannedtime / 1000L;
	            long zi = crt / 1000L;
	            long finalC = uz - zi;
	            String lastSecs = TimeManager.calc(finalC);
	            
	        	MReason = muteInfo.reason;
	        	MTime = lastSecs;
	        	
	        	ChatEvent.mutedList.put(p.getUniqueId(), muteInfo.reason);
	        	
	        	p.sendMessage(Info.getMutedJoin());
	        	p.sendMessage("");
	        	
	        }
	      	
	        
	    }
	}

}
