package de.luuuuuis.SQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ban {

	static MySQL MySQL = new MySQL();
	
	String uuid;
	
	public Ban(String uuid) {
		this.uuid = uuid;
	}

	public boolean playerExistsbyIP(String IP) {
		try ( ResultSet rs = MySQL.getResult("SELECT * FROM bannedPlayers WHERE IP='" + IP + "'"); ) {
			if (rs.next()) {
				return rs.getString("UUID") != null;
			}
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * creates ban (int perma >> 0 = temp; 1 = perma)
	 * 
	 * @param uuid
	 * @param reason
	 * @param Ip
	 * @param Banner
	 * @param perma
	 */

	public void ban(String reason, String Ip, String Banner, long time, int perma) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				if (BanInfo.getBanInfo(uuid) == null) {
					long currenttime = System.currentTimeMillis();
					long next = currenttime + time;
					
					try (PreparedStatement pre = MySQL.con.prepareStatement("INSERT INTO bannedPlayers (UUID, BANNER, IP, BANNED_TIME, BANNED_NEXT, REASON, PERM) VALUES (?, ?, ?, ?, ?, ?)"); ) {
						pre.setString(1, uuid);
						pre.setString(2, Banner);
						pre.setString(3, Ip);
						pre.setLong(4, currenttime);
						pre.setLong(5, next);
						pre.setString(6, reason);
						pre.setInt(7, perma);
						
						
						pre.executeUpdate();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} else {
					unban();
					ban(reason, Ip, Banner, time, perma);
				}

			}
		});
		th.start();

	}

	public void unban() {
		MySQL.update("DELETE FROM bannedPlayers WHERE UUID='" + uuid + "'");
	}

}
