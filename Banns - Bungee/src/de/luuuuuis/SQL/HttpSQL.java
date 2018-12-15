package de.luuuuuis.SQL;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import de.luuuuuis.httpServer.LoginContextHandler;

public class HttpSQL {

	static MySQL MySQL = new MySQL();

	public static boolean UserExists(String username) {
		try (ResultSet rs = MySQL.getResult("SELECT * FROM WebInterface WHERE USERNAME='" + username + "'");) {
			if (rs.next()) {
				return rs.getString("USERNAME") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getPassword(String username) {
		if (UserExists(username)) {
			try (ResultSet rs = MySQL.getResult("SELECT * FROM WebInterface WHERE USERNAME='" + username + "'");) {
				if (rs.next()) {
					return rs.getString("PASSWORD");
				}
				return null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void createPlayer(String username, String password) {
		if (!UserExists(username)) {
			try (PreparedStatement pre = de.luuuuuis.SQL.MySQL.con
					.prepareStatement("INSERT INTO WebInterface (USERNAME, PASSWORD) VALUES (?, ?)");) {
				pre.setString(1, username);
				pre.setString(2, LoginContextHandler.hashString(password));
				
				pre.executeUpdate();
			} catch (SQLException | NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			}
		}

	}

	public static void deletePlayer(String username) {
		MySQL.update("DELETE FROM WebInterface WHERE USERNAME='" + username + "'");
	}

	public static ArrayList<String> getInfos() {
		ArrayList<String> users = new ArrayList<>();
		try (ResultSet rs = MySQL.getResult("SELECT * FROM WebInterface");) {
			while (rs.next()) {
				users.add(rs.getString("USERNAME"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return users;
	}

}
