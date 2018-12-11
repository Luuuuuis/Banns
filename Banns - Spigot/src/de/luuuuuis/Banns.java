package de.luuuuuis;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import de.luuuuuis.command.bancommand;
import de.luuuuuis.listener.Click;
import de.luuuuuis.toGet.BanInv;

public class Banns extends JavaPlugin {

	static Banns instance;

	@Override
	public void onEnable() {

		instance = this;

		System.out.println("\r\n" + " ______     ______     __   __     __   __     ______    \r\n"
				+ "/\\  == \\   /\\  __ \\   /\\ \"-.\\ \\   /\\ \"-.\\ \\   /\\  ___\\   \r\n"
				+ "\\ \\  __<   \\ \\  __ \\  \\ \\ \\-.  \\  \\ \\ \\-.  \\  \\ \\___  \\  \r\n"
				+ " \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\\\\"\\_\\  \\ \\_\\\\\"\\_\\  \\/\\_____\\ \r\n"
				+ "  \\/_____/   \\/_/\\/_/   \\/_/ \\/_/   \\/_/ \\/_/   \\/_____/ " + "\r\n");


		System.out.println("Banns >> Enabling Banns (" + getDescription().getVersion()
				+ ") for Minecraft version 1.8.X to 1.12.2");
		System.out.println("Banns >> Discord for Support: https://discord.gg/2aSSGcz");

		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Click(), this);

		getCommand("ban").setExecutor(new bancommand());

		BanInv.createInv();
	}

	public static Banns getInstance() {
		return instance;
	}

}
