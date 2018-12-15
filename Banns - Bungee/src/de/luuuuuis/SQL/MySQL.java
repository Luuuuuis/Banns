package de.luuuuuis.SQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.luuuuuis.Info;

public class MySQL {

	public MySQL() {
		// idk
	}

	public static Connection con;

	public void connect() {
		if (!isConnected()) {
			try {
				con = DriverManager.getConnection(
						"jdbc:mysql://" + Info.MySQL.get("Host").toString() + ":" + Info.MySQL.get("Port").toString()
								+ "/" + Info.MySQL.get("Database").toString() + "?autoReconnect=true&useUnicode=yes",
						Info.MySQL.get("User").toString(), Info.MySQL.get("Password").toString());
				System.out.println("Banns >> MySQL Connected");
				createTable();
			} catch (SQLException e) {
				System.err.println("Banns ERROR >> Wrong MySQL Credentials");
				e.printStackTrace();
				System.err.println("Banns ERROR >> Wrong MySQL Credentials");
			}
		}
	}

	public void createTable() {
		if (isConnected()) {
			try {
				con.createStatement().executeUpdate(
						"CREATE TABLE IF NOT EXISTS bannedPlayers(UUID VARCHAR(36), BANNER VARCHAR(16), IP VARCHAR(100), BANNED_TIME BIGINT(100), BANNED_NEXT BIGINT(100), REASON VARCHAR(100), PERM int(2))");
				con.createStatement().executeUpdate(
						"CREATE TABLE IF NOT EXISTS MutedPlayers(UUID VARCHAR(36), BANNER VARCHAR(16), BANNED_TIME BIGINT(100), BANNED_NEXT BIGINT(100), REASON VARCHAR(100), PERM int(2))");
				con.createStatement().executeUpdate(
						"CREATE TABLE IF NOT EXISTS WebInterface(USERNAME VARCHAR(16), PASSWORD VARCHAR(128))");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void close() {
		if (isConnected()) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isConnected() {
		return con != null;
	}

	public void update(String qry) {
		if (isConnected()) {
			try {
				con.createStatement().executeUpdate(qry);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			connect();
			update(qry);
		}
	}

	public ResultSet getResult(String qry) {
		if (isConnected()) {
			ResultSet rs = null;
			try {
				Statement st = con.createStatement();
				rs = st.executeQuery(qry);
				return rs;
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return rs;
		} else {
			connect();
			getResult(qry);
		}
		return null;
	}
}
