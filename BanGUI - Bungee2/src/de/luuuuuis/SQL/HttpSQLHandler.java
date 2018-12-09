package de.luuuuuis.SQL;

import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.luuuuuis.httpServer.LoginContextHandler;

public class HttpSQLHandler {
	
	  public static boolean UserExists(String username) {
		    try
		    {
		      ResultSet rs = MySQL.getResult("SELECT * FROM WebInterface WHERE USERNAME='" + username + "'");
		      if (rs.next()) {
		        return rs.getString("USERNAME") != null;
		      }
		      return false;
		    }
		    catch (SQLException e)
		    {
		      e.printStackTrace();
		    }
		    return false;
		  }
	
	  
	  
	  public static String getPassword(String username) {
	    String i = "";
	    if (UserExists(username))
	    {
	      try
	      {
	        ResultSet rs = MySQL.getResult("SELECT * FROM WebInterface WHERE USERNAME='" + username + "'");
	        if (rs.next()) {
	          String.valueOf(rs.getString("PASSWORD"));
	        }
	        i = String.valueOf(rs.getString("PASSWORD"));
	      }
	      catch (SQLException e)
	      {
	        e.printStackTrace();
	      }
	    }
	    return i;
	  }
	  
	  
	  public static void createPlayer(String username, String password) {
	    if (!UserExists(username)) {
		      try {
				MySQL.update("INSERT INTO WebInterface (USERNAME, PASSWORD) VALUES ('" + username + "', '" + LoginContextHandler.hashString(password) + "');");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				return;
			}
		 }
				
	  }
	  
	  public static void deletePlayer(String username) throws NoSuchAlgorithmException {
		   MySQL.update("DELETE FROM WebInterface WHERE USERNAME='" + username + "'");
		  }
	  
	  public static ArrayList<String> getInfos() throws SQLException {
		  ResultSet rs = MySQL.getResult("SELECT * FROM WebInterface");
		  ArrayList<String> users = new ArrayList<>();
		  while(rs.next()) {
			  users.add(rs.getString("USERNAME"));
		  }
		  return users;
	  }
	  
}
