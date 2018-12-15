/*
 *
 * Created by Luis
 * 02.12.2018 at 12:37:24
 *
 */
package de.luuuuuis.Channels;

import java.io.IOException;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.MojangUUIDResolve;
import de.luuuuuis.TimeManager;
import de.luuuuuis.SQL.Ban;
import de.luuuuuis.SQL.BanInfo;
import de.luuuuuis.SQL.Mute;
import de.luuuuuis.commands.CheckCmd;
import de.luuuuuis.listener.ChatEvent;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PluginMessageListener implements Listener {

	/**
	 * 
	 * Recives all Messages from Spigot Servers
	 * 
	 * @param e
	 */

	@EventHandler
	public void onRecive(PluginMessageEvent e) {
		Object[] data = PluginMessageUtils.readData(e.getData());
		String[] args = new String[data.length];

		for (int i = 0; i < data.length; i++) {
			Object o = data[i];
			if (o instanceof String) {
				args[i] = ((String) o);
			}
		}

		if (args.length <= 0) {
			e.setCancelled(true);
			return;
		}

		String channel = args[0];

		if (channel.equals("Banns")) {
			try {
				String name = args[1], reason = args[2], banner = args[3], type = args[5];
				Long time = Long.parseLong(args[4]);

				bann(name, reason, banner, time, type);

			} catch (Exception ex) {
				e.setCancelled(true);
				ex.printStackTrace();
				return;
			}

		}

	}

	public static String typeTime = "null";
	public static String tReason = "null";

	/**
	 * 
	 * method to ban
	 * 
	 * @param name
	 * @param reason
	 * @param banner
	 * @param time
	 */
	@SuppressWarnings("deprecation")
	public static void bann(String nameName, String reason, String bannerName, Long time, String type) {
		ProxiedPlayer banner = ProxyServer.getInstance().getPlayer(bannerName),
				name = BungeeCord.getInstance().getPlayer(nameName);

		String ip = "null", uuid = "null";
		Integer perma = 0;
		tReason = reason;

		if (name != null) {
			ip = name.getAddress().getHostString();
			uuid = name.getUniqueId().toString();
		} else {
			uuid = MojangUUIDResolve.getUUIDResult(nameName).getValue();
		}

		Ban ban = new Ban(uuid);

		if (time == -1) {
			perma = 1;
			typeTime = "§4Permanent§7";
		} else {
			perma = 0;
			typeTime = "§cTemporary§7";
		}

		time = time * 1000L;

		if (type.equals("BAN")) {

			if (banner.hasPermission(Banns.getBanperm())) {
				if (name != null) {
					if (!name.hasPermission(Banns.getBanperm())) {

						try {
							name.disconnect(Info.getBanReason());
						} catch (IOException e) {
							System.out.println("Banns >> Can not disconnet player on ban");
							e.printStackTrace();
							System.out.println("Banns >> Can not disconnet player on ban");
						}

					} else {
						banner.sendMessage(
								new TextComponent(Banns.getPrefix() + "§4You are not allowed to ban team members."));
						return;
					}
				}

				// send ban to SQL (async for server not for sql)
				ban.ban(reason, ip, bannerName, time, perma);

				banner.sendMessage(Banns.getPrefix() + "§7You baned §c" + nameName + "§7 for §e" + reason);

				Info.LastBanName = nameName;
				Info.LastBanReason = reason;
				Info.LastBanTime = TimeManager.calc(time / 1000L);

				ProxyServer.getInstance().getPlayers().forEach(all -> {
					if (all.hasPermission(Banns.getBanperm())) {
						all.sendMessage(Banns.getPrefix() + "§e" + banner.getDisplayName() + " §7baned §c" + nameName
								+ "§7 for §c§l" + reason + "§7.");
					}
				});

				if (Banns.dcbot != null)
					Banns.dcbot.onBann(nameName, reason, TimeManager.calc(time / 1000L));

			} else {
				banner.sendMessage(Banns.getPrefix()
						+ "§4You don't have proxy permissions! Please report this to an administrator!");
			}

		} else if (type.equals("MUTE")) {

			if (banner.hasPermission(Banns.getBanperm())) {
				if (name != null) {
					if (!name.hasPermission(Banns.getBanperm())) {
						try {
							name.sendMessage(Info.getMuteReason());
							ChatEvent.mutedList.put(name.getUniqueId(), reason);
						} catch (IOException e) {
							System.out.println("Banns ERROR >> Can not send message to player on mute");
							e.printStackTrace();
							System.out.println("Banns ERROR >> Can not send message to player on mute");
						}
					}
				}

				// send mute to SQL (async for server not for sql)
				new Mute(uuid).mute(reason, bannerName, time, perma);

				banner.sendMessage(
						new TextComponent(Banns.getPrefix() + "§7You muted §c" + nameName + "§7 for §e" + reason));

				Info.LastMuteName = nameName;
				Info.LastMuteReason = reason;
				Info.LastMuteTime = TimeManager.calc(time / 1000L);

				ProxyServer.getInstance().getPlayers().forEach(all -> {
					if (all.hasPermission(Banns.getBanperm())) {
						all.sendMessage(Banns.getPrefix() + "§e" + banner.getDisplayName() + " §7muted §c" + nameName
								+ "§7 for §c§l" + reason + "§7.");
					}
				});

			} else {
				banner.sendMessage(Banns.getPrefix()
						+ "§4You don't have proxy permissions! Please report this to an administrator!");
			}

		} else if (type.equals("CHECK")) {
			CheckCmd.checkcheck(nameName, banner);
		} else if (type.equals("UNBAN")) {
			if (BanInfo.getBanInfo(uuid) != null) {
				ban.unban();
				banner.sendMessage(
						new TextComponent(Banns.getPrefix() + "§aYou unbaned §e" + nameName + " §asuccessfully."));
			} else {
				banner.sendMessage(
						new TextComponent(Banns.getPrefix() + "The player §e" + nameName + " §7isn't baned!"));
			}
		} else if (type.equals("COUSTOM")) {
			banner.sendMessage("");
			banner.sendMessage(Banns.getPrefix() + "Enter a reason for which you want to ban this player");

			// name, time
			String[] value = { nameName, time.toString() };

			ChatEvent.inBanProcess.put(banner, value);
		}

	}

}
