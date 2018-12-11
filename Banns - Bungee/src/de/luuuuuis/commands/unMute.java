package de.luuuuuis.commands;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.MojangUUIDResolve;
import de.luuuuuis.SQL.MuteInfo;
import de.luuuuuis.SQL.Mute;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class unMute extends Command {
	public unMute(String cmd) {
		super(cmd);
	}

	@SuppressWarnings("deprecation")
	public void execute(CommandSender sender, String[] args) {
		if (!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage("You are no player!");
			return;
		}
		ProxiedPlayer p = (ProxiedPlayer) sender;
		if (!p.hasPermission(Banns.getUnbanperm())) {
			p.sendMessage(Info.getNoPerm());
			return;
		}
		if (args.length == 1) {
			String uuid = MojangUUIDResolve.getUUIDResult(args[0]).getValue();
			MuteInfo muteInfo = MuteInfo.getMuteInfo(uuid);

			if (muteInfo != null) {
				new Mute(uuid).unban();

				p.sendMessage(Banns.getPrefix() + "§aYou unmuted §e" + args[0] + " §asuccessfully.");

			} else {
				p.sendMessage(Banns.getPrefix() + "This player is not muted.");
			}
		} else {
			p.sendMessage(Banns.getPrefix() + "§c/unmute <Player>");
		}
	}
}
