package de.luuuuuis.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BanSQLHandler {

	  public static boolean playerExists(String uuid) {
	    try
	    {
	      ResultSet rs = MySQL.getResult("SELECT * FROM bannedPlayers WHERE UUID='" + uuid + "'");
	      if (rs.next()) {
	        return rs.getString("UUID") != null;
	      }
	      return false;
	    }
	    catch (SQLException e)
	    {
	      e.printStackTrace();
	    }
	    return false;
	  }
	  
	  public static boolean playerExistsbyIP(String IP) {
		    try
		    {
		      ResultSet rs = MySQL.getResult("SELECT * FROM bannedPlayers WHERE IP='" + IP + "'");
		      if (rs.next()) {
		        return rs.getString("UUID") != null;
		      }
		      return false;
		    }
		    catch (SQLException e)
		    {
		      e.printStackTrace();
		    }
		    return false;
		  }
  
  public static void createPlayer(String uuid) {
    long banned_time = 0L;
    long banned_next = 0L;
    String reason = "-";
    if (!playerExists(uuid)) {
      MySQL.update("INSERT INTO bannedPlayers (UUID, BANNER, IP, BANNED_TIME, BANNED_NEXT, REASON , PERM) VALUES ('" + uuid + "', 'no', '0.0.0.0', '" + banned_time + "','" + banned_next + "','" + reason + "', '0');");
    }
  }
 
  
  /**
   * UUID BANNER REASON BANNED_TIME BANNED_NEXT PERM
   * 
   * @return
   * @throws SQLException
   */
  public static List<BanInfo> getInfos() throws SQLException {
	  ResultSet rs = MySQL.getResult("SELECT * FROM bannedPlayers");
	  List<BanInfo> infoList = new ArrayList<>();
	  while(rs.next()) {
		  BanInfo banInfo = new BanInfo(rs);
		  
		  infoList.add(banInfo);
	  }
	  return infoList;
  }
  
  
 /**
  * 
  * creates a bann (int perma >> 0 = temp; 1 = perma)
  * 
  * @param uuid
  * @param reason
  * @param Ip
  * @param Banner
  * @param perma
  */
  
  public static void createBan(String uuid, String reason, String Ip, String Banner, long time, int perma) {
      
	  Thread th = new Thread(new Runnable() {
		
		@Override
		public void run() {
			
		    if (playerExists(uuid)) {
		        
		        long currenttime = System.currentTimeMillis();
		        long next = currenttime + time;
		        
		        
		        MySQL.update("UPDATE bannedPlayers SET BANNED_TIME='" + currenttime + "', BANNED_NEXT='" + next + "', REASON='" + reason + "', IP='" + Ip + "', BANNER='" + Banner + "', PERM='" + perma + "' WHERE UUID='" + uuid + "'; ");
		      } else {
		        createPlayer(uuid);
		        createBan(uuid, reason, Ip, Banner, time, perma);
		      }
			
		}
	});
	th.start();
	  

  }
  
  public static void unban(String uuid) {
    MySQL.update("DELETE FROM bannedPlayers WHERE UUID='" + uuid + "'");
  }


  
}
