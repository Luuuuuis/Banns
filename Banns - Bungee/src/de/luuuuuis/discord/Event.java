package de.luuuuuis.discord;

import java.awt.Color;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import de.luuuuuis.Banns;
import de.luuuuuis.Info;
import de.luuuuuis.MojangUUIDResolve;
import de.luuuuuis.TimeManager;
import de.luuuuuis.SQL.BanInfo;
import de.luuuuuis.SQL.Ban;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Event extends ListenerAdapter {

	static String one = "\u0031\u20E3";
	static String two = "\u0032\u20E3";
	static String three = "\u0033\u20E3";

	static Executor exec = Executors.newCachedThreadPool();

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {

		JDA jda = event.getJDA();

		User author = event.getAuthor(); // sender
		Message message = event.getMessage(); // message

		String msg = message.getContentDisplay(); // readable

		if (event.isFromType(ChannelType.TEXT)) {

			TextChannel textChannel = event.getTextChannel();
			Member member = event.getMember();

			if (textChannel.getIdLong() == Long.parseLong(Info.Discord.get("Channel-ID").toString())) {

				if (msg.startsWith("/ban")) {

					if (msg.length() < 6 || msg.length() > 21)
						return;

					String toBan = msg.substring(5);

					if (member.hasPermission(Permission.BAN_MEMBERS)) {

						EmbedBuilder embed = new EmbedBuilder();
						embed.setColor(Color.red);
						embed.setTitle("For what do you want to ban " + toBan + "?");

						jda.getTextChannelById((String) Info.Discord.get("Channel-ID")).sendMessage(embed.build())
								.queue(m -> {

									m.addReaction(one).queue();
									m.addReaction(two).queue();
									m.addReaction(three).queue();

									exec.execute(new MessageRunnable(textChannel, m.getIdLong(),
											jda.getUserById(author.getIdLong()), toBan, author.getName(), member));
								});

						message.delete().queue();

					}

				} else if (msg.startsWith("/check")) {

					if (msg.length() < 8 || msg.length() > 24)
						return;

					String toCheck = msg.substring(7);

					if (member.hasPermission(Permission.BAN_MEMBERS)) {

						String uuid = MojangUUIDResolve.getUUIDResult(toCheck).getValue();

						BanInfo banInfo = BanInfo.getBanInfo(uuid);

						if (banInfo != null) {
							if (banInfo.perm) {
								Date date = new Date(banInfo.timeBanned);
								DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
								String dateFormatted = formatter.format(date);

								EmbedBuilder embed = new EmbedBuilder();
								embed.setColor(Color.red);
								embed.setDescription("Here are some information about " + toCheck);

								embed.setTitle("Check Informations");

								embed.addField("Ban type", "Permanent", false);
								embed.addField("Banner", banInfo.banner, true);
								embed.addField("Reason", banInfo.reason.replaceAll("[0-9]", ""), true);
								embed.addField("Banned on", dateFormatted, true);

								jda.getTextChannelById((String) Info.Discord.get("Channel-ID"))
										.sendMessage(embed.build()).queue();
								jda.getTextChannelById((String) Info.Discord.get("Channel-ID"))
										.sendMessage("More Informations under http://"
												+ Info.ServerInfo.get("domainname") + ":1337/check?username=" + toCheck)
										.queue();

							} else {

								long unbannedtime = banInfo.timeNext;
								long crt = System.currentTimeMillis();
								long uz = unbannedtime / 1000L;
								long zi = crt / 1000L;
								long hz = uz - zi;
								String lastSecs = TimeManager.calc(hz);

								Date date = new Date(banInfo.timeBanned);
								DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 'at' HH:mm");
								String dateFormatted = formatter.format(date);

								EmbedBuilder embed = new EmbedBuilder();
								embed.setColor(Color.red);
								embed.setDescription("Here are some information about " + toCheck);

								embed.setTitle("Check Informations");

								embed.addField("Ban type", "Temporary", false);
								embed.addField("Banner", banInfo.banner, true);
								embed.addField("Reason", banInfo.reason.replaceAll("[0-9]", ""), true);
								embed.addField("Banned on", dateFormatted, true);
								embed.addField("Remaining time", lastSecs, true);

								jda.getTextChannelById((String) Info.Discord.get("Channel-ID"))
										.sendMessage(embed.build()).queue();
								jda.getTextChannelById((String) Info.Discord.get("Channel-ID"))
										.sendMessage("More Informations under http://"
												+ Info.ServerInfo.get("domainname") + ":1337/check?username=" + toCheck)
										.queue();

							}
						} else {
							EmbedBuilder embed = new EmbedBuilder();
							embed.setColor(Color.red);
							embed.setTitle(toCheck + " isn't banned.");

							jda.getTextChannelById((String) Info.Discord.get("Channel-ID")).sendMessage(embed.build())
									.queue();
						}

					}

					message.delete().queue();

				} else if (msg.startsWith("/unban")) {

					if (msg.length() < 6 || msg.length() > 22)
						return;

					String toUnban = msg.substring(7);

					if (member.hasPermission(Permission.BAN_MEMBERS)) {

						String uuid = MojangUUIDResolve.getUUIDResult(toUnban).getValue();

						if (BanInfo.getBanInfo(uuid) != null) {

							new Ban(uuid).unban();

							EmbedBuilder embed = new EmbedBuilder();
							embed.setColor(Color.red);
							embed.setTitle(toUnban + " was unbanned by " + author.getName() + ".");

							jda.getTextChannelById((String) Info.Discord.get("Channel-ID")).sendMessage(embed.build())
									.queue();
						} else {

							EmbedBuilder embed = new EmbedBuilder();
							embed.setColor(Color.red);
							embed.setTitle(toUnban + " isn't banned.");

							jda.getTextChannelById((String) Info.Discord.get("Channel-ID")).sendMessage(embed.build())
									.queue();
						}

					}

					message.delete().queue();

				} else {
					if (!author.isBot())
						message.delete().queue();
				}

			}

		}

	}

	static class MessageRunnable implements Runnable {

		private long messageID;
		private Message message;

		private String toBann;
		private String banner;

		private TextChannel textChannel;
		private User user;
		private Member member;

		public MessageRunnable(TextChannel textChannel, long messageID, User user, String toBann, String banner,
				Member member) {
			this.messageID = messageID;
			this.toBann = toBann;
			this.banner = banner;
			this.textChannel = textChannel;
			this.user = user;
			this.member = member;
		}

		@Override
		public void run() {
			String reason = "null";

			for (int i = 0; i < 10; i++) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				message = textChannel.getMessageById(messageID).complete();

				boolean exit = false;

				for (MessageReaction reaction : message.getReactions()) {
					String name = reaction.getReactionEmote().getName();
					if (name.startsWith("1") && reaction.getCount() > 1
							&& reaction.getUsers().complete().contains(user)) {
						reason = "Hacking";
						ban(member, toBann, reason, banner.replaceAll("[^\\p{ASCII}]", ""), 604800L, 0);
						exit = true;
					} else if (name.startsWith("2") && reaction.getCount() > 1
							&& reaction.getUsers().complete().contains(user)) {
						reason = "Hacking";
						ban(member, toBann, reason, banner.replaceAll("[^\\p{ASCII}]", ""), 2592000L, 0);
						exit = true;
					}
					if (name.startsWith("3") && reaction.getCount() > 1
							&& reaction.getUsers().complete().contains(user)) {
						reason = "Hacking";
						ban(member, toBann, reason, banner.replaceAll("[^\\p{ASCII}]", ""), -1L, 1);
						exit = true;
					}
				}

				if (exit) {
					break;
				}
			}

			message.delete().queue();
		}

		@SuppressWarnings("deprecation")
		private void ban(Member member, String toBan, String reason, String banner, Long time, Integer perma) {

			ProxiedPlayer name = ProxyServer.getInstance().getPlayer(toBan);
			String uuid = MojangUUIDResolve.getUUIDResult(toBan).getValue();

			String ip = "null";
			time = time * 1000L;

			if (member.hasPermission(Permission.BAN_MEMBERS)) {
				if (name != null) {
					if (!name.hasPermission(Banns.getBanperm())) {
						ip = name.getAddress().getHostString();
						try {
							name.disconnect(Info.getBanReason());
						} catch (IOException e) {
							System.out.println("Banns >> Can not disconnet player on ban");
							e.printStackTrace();
							System.out.println("Banns >> Can not disconnet player on ban");
						}

					} else {
						return;
					}
				}

				// send ban to SQL (async for server not for sql)
				new Ban(uuid).ban(reason, ip, banner, time, perma);

				Info.LastBanName = toBan;
				Info.LastBanReason = reason;
				Info.LastBanTime = TimeManager.calc(time / 1000L);

				Banns.dcbot.onBann(toBan, reason, TimeManager.calc(time / 1000L));

				ProxyServer.getInstance().getPlayers().forEach(all -> {
					if (all.hasPermission(Banns.getBanperm())) {
						all.sendMessage(Banns.getPrefix() + "§e" + banner + " §7banned §c" + toBan + "§7 for §c§l"
								+ reason + "§7.");
					}
				});

			}

		}
	}

}