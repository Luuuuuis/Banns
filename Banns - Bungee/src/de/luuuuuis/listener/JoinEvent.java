package de.luuuuuis.listener;

import java.io.IOException;

import de.luuuuuis.Info;
import de.luuuuuis.TimeManager;
import de.luuuuuis.SQL.BanInfo;
import de.luuuuuis.SQL.BanSQLHandler;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinEvent implements Listener {
	
	
	  static String Bannreason;
	  public static String TimeAngabe1;
	  public static String grund1;
	
	static String Reason;
	public static String TimeAngabe = "null";
	public static String grund = "null";
	
	public static String lastSecs = "null";
	
  @SuppressWarnings("deprecation")
@EventHandler
  public void onConnect(LoginEvent e) {
	  
	  BanInfo banInfo = BanInfo.getBanInfo(e.getConnection().getUniqueId().toString());
    
    if (banInfo != null) {
    	
        long unbannedtime = banInfo.timeNext;
        long crt = System.currentTimeMillis();
        if (crt > unbannedtime && !banInfo.perm) {
          BanSQLHandler.unban(e.getConnection().getUniqueId().toString());
          return;
        }
 
        if(banInfo.perm)
        	TimeAngabe = "§4Permanent§7";
        else {
            	TimeAngabe = "§ctemporary§7";
        }
    	
        grund = banInfo.reason;
        
        long uz = unbannedtime / 1000L;
        long zi = crt / 1000L;
        long finalC = uz - zi;
        lastSecs = TimeManager.calc(finalC);
        
        if(banInfo.perm) {
			try {
				e.setCancelReason(Info.getPermaBan());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        } else {
			try {
				e.setCancelReason(Info.getBannedJoin());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
	      e.setCancelled(true);
	      return;
	      
    }
    
    
    
    if(BanSQLHandler.playerExistsbyIP(e.getConnection().getAddress().getHostString()) && !BanSQLHandler.playerExists(e.getConnection().getUniqueId().toString())) {
    	
          TimeAngabe = "§4Permanent§7";
	      grund = "Ban bypassing";
  	  
		  if(e.getConnection() != null) {
			  
	        try {
				e.setCancelReason(Info.getPermaBan());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	        e.setCancelled(true);
		  
	  }
    }
  }
}