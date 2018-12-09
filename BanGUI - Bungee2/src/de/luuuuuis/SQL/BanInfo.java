package de.luuuuuis.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BanInfo {
	
	  public String name, uuid, reason, banner, ip;
	  public long timeBanned, timeNext;
	  public boolean perm;
	  
	  public BanInfo(ResultSet rs) throws SQLException {
		  uuid = rs.getString("UUID");
		  banner = rs.getString("BANNER");
		  ip = rs.getString("IP");
		  timeBanned = rs.getLong("BANNED_TIME");
		  timeNext = rs.getLong("BANNED_NEXT");
		  reason = replaceAllZahls(rs.getString("REASON"));
		  if(rs.getInt("PERM") == 1)
			  perm = true;
		  else
			  perm = false;
	}
	  
	  public static BanInfo getBanInfo(String uuid) {

	      try
	      {
	        ResultSet rs = MySQL.getResult("SELECT * FROM bannedPlayers WHERE UUID='" + uuid + "'");
	        if (rs.next()) {
	          return new BanInfo(rs);
	        }
	      }
	      catch (SQLException e)
	      {
	        e.printStackTrace();
	      }
	    return null;
	  }
		
		public String replaceAllZahls(String s) {
			String r = "";
			for(char c : s.toCharArray()) {
				if(c >= 48 && c <= 57) continue;
				r+=c;
			}
			return r;
		}
	  
  }