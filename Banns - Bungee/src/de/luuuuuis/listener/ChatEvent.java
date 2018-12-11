package de.luuuuuis.listener;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.luuuuuis.Info;
import de.luuuuuis.Channels.PluginMessageListener;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent implements Listener {

	public static HashMap<UUID, String> mutedList = new HashMap<>();
	public static HashMap<ProxiedPlayer, String[]> inBanProcess = new HashMap<>();

	public static String reason;

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChat(net.md_5.bungee.api.event.ChatEvent e) {
		ProxiedPlayer p = (ProxiedPlayer) e.getSender();
		String msg = e.getMessage();

		if (mutedList.containsKey(p.getUniqueId())) {

			if (msg.startsWith("/msg") || msg.startsWith("/r") || !msg.startsWith("/")) {
				e.setCancelled(true);

				reason = mutedList.get(p.getUniqueId());

				try {
					p.sendMessage(Info.getMutedChat());
					p.sendMessage("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

			return;
		}

		if (inBanProcess.containsKey(p)) {

			try {
				p.sendMessage("");

				PluginMessageListener.bann(inBanProcess.get(p)[0], msg, p.getName(),
						new Long(inBanProcess.get(p)[1]) / 1000L, "BAN");

			} catch (Exception ex) {
				System.out.println("Banns ERROR >> Wrong Argument");
				ex.printStackTrace();
				System.out.println("Banns ERROR >> Wrong Argument");
			}

			inBanProcess.remove(p);

			e.setCancelled(true);
		}

	}
}