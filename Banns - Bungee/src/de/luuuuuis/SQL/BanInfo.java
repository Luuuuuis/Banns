package de.luuuuuis.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BanInfo {

	public String name, uuid, reason, banner, ip;
	public long timeBanned, timeNext;
	public boolean perm;

	static MySQL MySQL = new MySQL();

	public BanInfo(ResultSet rs) throws SQLException {
		uuid = rs.getString("UUID");
		banner = rs.getString("BANNER");
		ip = rs.getString("IP");
		timeBanned = rs.getLong("BANNED_TIME");
		timeNext = rs.getLong("BANNED_NEXT");
		reason = replaceAllZahls(rs.getString("REASON"));
		if (rs.getInt("PERM") == 1)
			perm = true;
		else
			perm = false;
	}

	public static BanInfo getBanInfo(String uuid) {

		try (ResultSet rs = MySQL.getResult("SELECT * FROM bannedPlayers WHERE UUID='" + uuid + "'");) {
			if (rs.next()) {
				return new BanInfo(rs);
			}
			return null;
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String replaceAllZahls(String s) {
		String r = "";
		for (char c : s.toCharArray()) {
			if (c >= 48 && c <= 57)
				continue;
			r += c;
		}
		return r;
	}

	/**
	 * UUID BANNER REASON BANNED_TIME BANNED_NEXT PERM
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static List<BanInfo> getInfos() {
		List<BanInfo> infoList = new ArrayList<>();
		try (ResultSet rs = MySQL.getResult("SELECT * FROM bannedPlayers");) {
			while (rs.next()) {
				BanInfo banInfo = new BanInfo(rs);
				infoList.add(banInfo);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return infoList;
	}
}