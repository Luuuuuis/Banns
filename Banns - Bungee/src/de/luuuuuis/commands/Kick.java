/*
 *
 * Created by Luis
 * 15.12.2018 at 18:50:26
 *
 */
package de.luuuuuis.commands;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Kick extends Command {

	/**
	 * @param name
	 * @param permission
	 * @param aliases
	 */
	public Kick(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.md_5.bungee.api.plugin.Command#execute(net.md_5.bungee.api.CommandSender,
	 * java.lang.String[])
	 */

	public static String reason = "";

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage("It seems like you are not a player?? MHHHHHHHM");
			return;
		}
		ProxiedPlayer p = (ProxiedPlayer) sender;

		if (!p.hasPermission(Banns.getBanperm())) {
			p.sendMessage(Info.getNoPerm());
			return;
		}

		if (args.length == 0 || args.length == 1) {
			p.sendMessage(Banns.getPrefix() + "§c/kick <Player> <Reason>");
			return;
		} else {
			ProxiedPlayer toKick = ProxyServer.getInstance().getPlayer(args[0]);

			Integer counter = 1;
			while (counter < args.length) {
				reason += args[counter] + " ";
				counter++;
			}

			if (toKick != null) {
				if(toKick.hasPermission(Banns.getBanperm())) {
					p.sendMessage(Banns.getPrefix() + "§4You are not allowed to kick team members.");
					return;
				}
				toKick.disconnect(Info.getKick());
				ProxyServer.getInstance().getPlayers().forEach(all -> {
					all.sendMessage(Banns.getPrefix() + "§e" + p.getDisplayName() + " §7kicked §c"
							+ toKick.getDisplayName() + " §7for §c" + reason);
				});
			} else {
				p.sendMessage(Banns.getPrefix() + "§cThis player is not online!");
				return;
			}
		}

	}

}
