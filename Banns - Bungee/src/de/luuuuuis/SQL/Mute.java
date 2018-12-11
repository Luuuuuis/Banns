package de.luuuuuis.SQL;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Mute {

	static MySQL MySQL = new MySQL();

	String uuid;

	public Mute(String uuid) {
		this.uuid = uuid;
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

	public void mute(String reason, String Banner, long time, int perma) {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {
				if (MuteInfo.getMuteInfo(uuid) == null) {

					long currenttime = System.currentTimeMillis();
					long next = currenttime + time;

					try (PreparedStatement pre = de.luuuuuis.SQL.MySQL.con.prepareStatement(
							"INSERT INTO MutedPlayers (UUID, BANNER, BANNED_TIME, BANNED_NEXT, REASON) VALUES (?, ?, ?, ?, ?)");) {
						pre.setString(1, uuid);
						pre.setString(2, Banner);
						pre.setLong(3, currenttime);
						pre.setLong(4, next);
						pre.setString(5, reason);

						pre.executeUpdate();

					} catch (SQLException e) {
						e.printStackTrace();
					}

				} else {
					unban();
					mute(reason, Banner, time, perma);
				}

			}
		});
		th.start();

	}

	public void unban() {
		MySQL.update("DELETE FROM MutedPlayers WHERE UUID='" + uuid + "'");
	}

}
