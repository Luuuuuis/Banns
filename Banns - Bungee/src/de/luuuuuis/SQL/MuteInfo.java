package de.luuuuuis.SQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MuteInfo {

	public String name, uuid, reason, banner, ip;
	public long timeBanned, timeNext;
	public boolean perm;

	static MySQL MySQL = new MySQL();

	public MuteInfo(ResultSet rs) throws SQLException {
		uuid = rs.getString("UUID");
		banner = rs.getString("BANNER");
		timeBanned = rs.getLong("BANNED_TIME");
		timeNext = rs.getLong("BANNED_NEXT");
		reason = replaceAllZahls(rs.getString("REASON"));
	}

	public static MuteInfo getMuteInfo(String uuid) {
		try (ResultSet rs = MySQL.getResult("SELECT * FROM MutedPlayers WHERE UUID='" + uuid + "'");) {
			if (rs.next()) {
				return new MuteInfo(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
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

	public static List<MuteInfo> getInfos() {
		List<MuteInfo> infoList = new ArrayList<>();
		try (ResultSet rs = MySQL.getResult("SELECT * FROM MutedPlayers");) {
			while (rs.next()) {
				MuteInfo MuteInfo = new MuteInfo(rs);
				infoList.add(MuteInfo);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return infoList;
	}

}