package de.luuuuuis.discord;

import java.awt.Color;

import javax.security.auth.login.LoginException;

import de.luuuuuis.Info;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Game.GameType;

public class DiscordBot {

	public JDA jda;

	public DiscordBot() {
		try {
			jda = new JDABuilder(AccountType.BOT).setGame(Game.of(GameType.WATCHING, "cheaters"))
					.setToken((String) Info.Discord.get("Token")).setStatus(OnlineStatus.ONLINE)
					.addEventListener(new Event()).buildBlocking();
			jda.setAutoReconnect(true);
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void onBann(String name, String reason, String time) {

		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.red);
		embed.setFooter("Watch out you keep calm and don't break the rules", null);
		embed.setDescription("Someone has broken the rules");

		embed.setTitle("Ban Notification");

		embed.addField("Username", name, true);
		embed.addField("Reason", reason, true);
		embed.addField("Time", "" + time, true);

		jda.getTextChannelById((String) Info.Discord.get("Channel-ID")).sendMessage(embed.build()).queue();
	}

}
