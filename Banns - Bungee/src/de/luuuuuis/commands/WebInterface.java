package de.luuuuuis.commands;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.SQL.HttpSQL;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class WebInterface extends Command {

	public WebInterface(String name) {
		super(name, "", "wi");
	}

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

		if (args.length == 1) {

			if (args[0].equalsIgnoreCase("pwreset")) {

				p.sendMessage(Banns.getPrefix() + "§c/webinterface <Passwort>");
				p.sendMessage(Banns.getPrefix() + "§c/webinterface pwreset [New password]");
				p.sendMessage(Banns.getPrefix() + "§c/webinterface remove [Player]");

			} else if (args[0].equalsIgnoreCase("remove")) {

				p.sendMessage(Banns.getPrefix() + "§c/webinterface <Passwort>");
				p.sendMessage(Banns.getPrefix() + "§c/webinterface pwreset [New password]");
				p.sendMessage(Banns.getPrefix() + "§c/webinterface remove [Player]");

			} else {

				if (!HttpSQL.UserExists(p.getName())) {

					HttpSQL.createPlayer(p.getName(), args[0]);

					p.sendMessage(Banns.getPrefix()
							+ "Your WebInterface account was successfully created with the password §e" + args[0]
							+ " §7.");

				} else {

					TextComponent msg = new TextComponent(Banns.getPrefix()
							+ "You already have an account. If you want you can recover your password here.");
					msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("§a§lRecover password here!").create()));
					msg.setClickEvent(
							new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/webinterface pwreset [new password]"));

					p.sendMessage(msg);

				}

			}
		} else if (args.length == 2) {

			if (args[0].equalsIgnoreCase("pwreset")) {

				if (HttpSQL.UserExists(p.getName())) {

					HttpSQL.deletePlayer(p.getName());
					HttpSQL.createPlayer(p.getName(), args[1]);

					p.sendMessage(Banns.getPrefix() + "Your password has changed to §e" + args[1]
							+ " §7. Please take better care of your password this time!");

				} else {

					p.sendMessage(Banns.getPrefix() + "You don't have an account.");

				}

			} else if (args[0].equalsIgnoreCase("remove")) {

				if (p.hasPermission("Banns.administrate")) {

					if (HttpSQL.UserExists(args[1])) {

						HttpSQL.deletePlayer(args[1]);

						p.sendMessage(Banns.getPrefix() + "You have removed the account from §e" + args[1] + " §7.");

					} else {

						p.sendMessage(Banns.getPrefix() + "The Account §e" + args[1] + " §7 doesn't exist.");

					}

				} else {

					p.sendMessage(Info.getNoPerm());
					return;
				}

			} else {

				p.sendMessage(Banns.getPrefix() + "§c/webinterface <Passwort>");
				p.sendMessage(Banns.getPrefix() + "§c/webinterface pwreset [New password]");
				p.sendMessage(Banns.getPrefix() + "§c/webinterface remove [Player]");

			}

		} else {

			p.sendMessage(Banns.getPrefix() + "§c/webinterface <Passwort>");
			p.sendMessage(Banns.getPrefix() + "§c/webinterface pwreset [New password]");
			p.sendMessage(Banns.getPrefix() + "§c/webinterface remove [Player]");

		}
	}

}
