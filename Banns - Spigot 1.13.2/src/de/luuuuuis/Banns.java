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
		
		System.out.println("\r\n" + 
				" ______     ______     __   __     __   __     ______    \r\n" + 
				"/\\  == \\   /\\  __ \\   /\\ \"-.\\ \\   /\\ \"-.\\ \\   /\\  ___\\   \r\n" + 
				"\\ \\  __<   \\ \\  __ \\  \\ \\ \\-.  \\  \\ \\ \\-.  \\  \\ \\___  \\  \r\n" + 
				" \\ \\_____\\  \\ \\_\\ \\_\\  \\ \\_\\\\\"\\_\\  \\ \\_\\\\\"\\_\\  \\/\\_____\\ \r\n" + 
				"  \\/_____/   \\/_/\\/_/   \\/_/ \\/_/   \\/_/ \\/_/   \\/_____/ "
				+ "\r\n");
		
		System.out.println("");
		System.out.println("");

		
		System.out.println("Banns >> Bootstraping plugin with version: " + getDescription().getVersion() + " made for MC Version 1.13.2");
		System.out.println("");
		System.out.println("Discord for Support: https://discord.gg/wKuHFWa");
		System.out.println("");
		
		
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		
		
	    PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Click(), this);
		
		getCommand("ban").setExecutor(new bancommand());
		
		BanInv.createInv();
		
		System.out.println("Banns >> Spigot Addon was started!");
		
		
	}
	
	public static Banns getInstance() {
		return instance;
	}
	
}
