package de.luuuuuis.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MuteSQLHandler {
  
	  public static boolean playerExists(String uuid)
	  {
	    try
	    {
	      ResultSet rs = MySQL.getResult("SELECT * FROM MutedPlayers WHERE UUID='" + uuid + "'");
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
    String reason = "Error";
    if (!MuteSQLHandler.playerExists(uuid)) {
      MySQL.update("INSERT INTO MutedPlayers (UUID, BANNER, BANNED_TIME , BANNED_NEXT, REASON) VALUES ('" + uuid + "', 'null', '" + banned_time + "','" + banned_next + "','" + reason + "');");
    }
  }
  
  public static List<MuteInfo> getInfos() throws SQLException {
	  ResultSet rs = MySQL.getResult("SELECT * FROM MutedPlayers");
	  List<MuteInfo> infoList = new ArrayList<>();
	  while(rs.next()) {
		  MuteInfo MuteInfo = new MuteInfo(rs);
		  
		  infoList.add(MuteInfo);
	  }
	  return infoList;
  }
  
  
  /**
   * 
   * creates a mute (int perma >> 0 = temp; 1 = perma)
   * 
   * @param uuid
   * @param reason
   * @param Ip
   * @param Banner
   * @param perma
   */
  
	  public static void createBan(String uuid, String reason, String Banner, long time, int perma) {
		  
		  Thread th = new Thread(new Runnable() {
			
			@Override
			public void run() {
			    if (MuteSQLHandler.playerExists(uuid)) {
			        
			        long currenttime = System.currentTimeMillis();
			        long next = currenttime + time;
			        
			        MySQL.update("UPDATE MutedPlayers SET BANNED_TIME='" + currenttime + "', BANNED_NEXT='" + next + "', REASON='" + reason + "', BANNER='" + Banner + "' WHERE UUID='" + uuid + "'");
			        
	
			      } else{
			        createPlayer(uuid);
			        createBan(uuid, reason, Banner, time, perma);
			      }
				
			}
		});
		th.start();
	
	  }
  
	  public static void unban(String uuid) {
	    MySQL.update("DELETE FROM MutedPlayers WHERE UUID='" + uuid + "'");
	  }
  
  
}
