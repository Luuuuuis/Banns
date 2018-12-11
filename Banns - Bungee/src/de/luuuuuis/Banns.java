package de.luuuuuis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.luuuuuis.Channels.PluginMessageListener;
import de.luuuuuis.SQL.MySQL;
import de.luuuuuis.commands.CheckCmd;
import de.luuuuuis.commands.UnBanCmd;
import de.luuuuuis.commands.WebInterface;
import de.luuuuuis.commands.unMute;
import de.luuuuuis.discord.DiscordBot;
import de.luuuuuis.httpServer.BanHttpServer;
import de.luuuuuis.listener.ChatEvent;
import de.luuuuuis.listener.JoinEvent;
import de.luuuuuis.listener.PostJoinEvent;
import de.luuuuuis.listener.QuitEvent;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class Banns extends Plugin {

	static Banns instance;
	static String prefix;

	public static String FilePath;

	public static boolean update;

	public static int PERMANENT = 1;
	public static int TEMPORARY = 0;

	public static DiscordBot dcbot;
	public BanHttpServer banserver;

	MySQL MySQL;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void onEnable() {

		System.out.println("\r\n" + " ______     ______     __   __     __   __     ______    \r\n"
				+ "/\\  == \\   /\\  __ \\   /\\ \"-.\\ \\   /\\ \"-.\\ \\   /\\  ___\\   \r\n"
				+ "\\ \\  __<   \\ \\  __ \\  \\ \\ \\-.  \\  \\ \\ \\-.  \\  \\ \\___  \\  \r\n"
				+ " \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\\\\"\\_\\  \\ \\_\\\\\"\\_\\  \\/\\_____\\ \r\n"
				+ "  \\/_____/   \\/_/\\/_/   \\/_/ \\/_/   \\/_/ \\/_/   \\/_____/ " + "\r\n");

		/**
		 *
		 * Updater
		 * 
		 */

		System.out.println("Banns >> Enabling Banns (" + getDescription().getVersion() + ")");
		System.out.println("Banns >> Discord for Support: https://discord.gg/2aSSGcz");

		URL url;
		HttpURLConnection con = null;
		try {
			url = new URL("http://luis.bplaced.net/Banns/Auto-Updater/version.html");
			con = (HttpURLConnection) url.openConnection();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));) {

			System.out.println("Banns >> Searching for new builds!");

			String l;
			l = in.readLine();
			if (!(l.equalsIgnoreCase(getDescription().getVersion()))) {

				System.out.println("Banns >> New build (" + l + ") found!");
				System.out.println("Banns >> Download here: https://www.spigotmc.org/resources/.57031/");

				update = true;
			} else {
				con.disconnect();
				in.close();
				l = null;
				update = false;
				System.out.println("Banns >> You are running on the latest build ;)");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		FilePath = getDataFolder().getPath();

		URL dowURL = null;
		File file;

		file = new File(getDataFolder().getPath(), "config.json");
		try {
			dowURL = new URL("http://luis.bplaced.net/Banns/Auto-Updater/config.json");
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}

		if (!file.exists()) {
			try (InputStream input = dowURL.openStream(); FileOutputStream output = new FileOutputStream(file);) {

				if (!getDataFolder().exists()) {
					getDataFolder().mkdir();
				}

				byte[] buffer = new byte[4096];
				int n = 0;
				while (-1 != (n = input.read(buffer))) {
					output.write(buffer, 0, n);
				}

				input.close();
				output.close();

				System.out.println("Banns >> New config.json downloaded");

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		try {
			dowURL = new URL("http://luis.bplaced.net/Banns/Auto-Updater/us-en.json");
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		}

		file = new File(getDataFolder().getPath(), "us-en.json");

		if (!file.exists()) {
			try (InputStream input = dowURL.openStream(); FileOutputStream output = new FileOutputStream(file);) {

				byte[] buffer = new byte[4096];
				int n = 0;
				while (-1 != (n = input.read(buffer))) {
					output.write(buffer, 0, n);
				}

				input.close();
				output.close();

				System.out.println("Banns >> New us-en.json downloaded");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				try {
					Object obj = new JSONParser()
							.parse(new FileReader(getDataFolder().getPath() + "/" + "config.json"));
					JSONObject JObj = (JSONObject) obj;

					prefix = (String) JObj.get("Prefix");

					Map serverinfoJSON = (Map) JObj.get("Serverinfo");

					Iterator<Map.Entry> itr1 = serverinfoJSON.entrySet().iterator();
					while (itr1.hasNext()) {
						Map.Entry pair = itr1.next();
						Info.ServerInfo.put(pair.getKey().toString(), pair.getValue());
					}

					Map mysqlJSON = (Map) JObj.get("MySQL");

					Iterator<Map.Entry> itr3 = mysqlJSON.entrySet().iterator();
					while (itr3.hasNext()) {
						Map.Entry pair = itr3.next();
						Info.MySQL.put(pair.getKey().toString(), pair.getValue());
					}

					Map discordJSON = (Map) JObj.get("Discord");

					Iterator<Map.Entry> itr4 = discordJSON.entrySet().iterator();
					while (itr4.hasNext()) {
						Map.Entry pair = itr4.next();
						Info.Discord.put(pair.getKey().toString(), pair.getValue());
					}

					Map webInterfaceJSON = (Map) JObj.get("Webinterface");

					Iterator<Map.Entry> itr5 = webInterfaceJSON.entrySet().iterator();
					while (itr5.hasNext()) {
						Map.Entry pair = itr5.next();
						Info.Webinterface.put(pair.getKey().toString(), pair.getValue());
					}

				} catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("Banns ERROR >> Error while reading JSON file");
				}

			}
		});
		th.start();
		try {
			th.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Info.readMessages();

		MySQL = new MySQL();
		MySQL.connect();

		if ((boolean) Info.Webinterface.get("State"))
			banserver = new BanHttpServer();

		if ((boolean) Info.Discord.get("State"))
			dcbot = new DiscordBot();

		instance = this;

		getProxy().registerChannel("BungeeCord");

		PluginManager pm = getProxy().getPluginManager();

		pm.registerListener(this, new PluginMessageListener());
		pm.registerListener(this, new JoinEvent());
		pm.registerListener(this, new PostJoinEvent());
		pm.registerListener(this, new ChatEvent());
		pm.registerListener(this, new QuitEvent());

		pm.registerCommand(this, new CheckCmd("check"));
		pm.registerCommand(this, new UnBanCmd("unban"));
		pm.registerCommand(this, new unMute("unmute"));
		pm.registerCommand(this, new WebInterface("webinterface"));

	}

	@Override
	public void onDisable() {
		MySQL.close();
		banserver.stop();
	}

	public static Banns getInstance() {
		return instance;
	}

	/**
	 * 
	 * by https://stackoverflow.com/a/13678355/10011954
	 * 
	 * @param length
	 * @return
	 */

	public static String getRandomKey(int length) {
		String randomStr = UUID.randomUUID().toString();
		while (randomStr.length() < length) {
			randomStr += UUID.randomUUID().toString();
		}
		return randomStr.substring(0, length);
	}

	public static String getPrefix() {
		return ChatColor.translateAlternateColorCodes('&', prefix);
	}

	public static String getServername() {
		return ChatColor.translateAlternateColorCodes('&', (String) Info.ServerInfo.get("Servername"));
	}

	public static String getBanperm() {
		return "Banns.Ban";
	}

	public static String getUnbanperm() {
		return "Banns.Unban";
	}

}
