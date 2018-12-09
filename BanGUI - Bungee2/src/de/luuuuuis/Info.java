/*
 *
 * Created by Luis
 * 24.09.2018 at 18:02:49
 *
 */
package de.luuuuuis;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import de.luuuuuis.Channels.PluginMessageListener;
import de.luuuuuis.listener.ChatEvent;
import de.luuuuuis.listener.JoinEvent;
import de.luuuuuis.listener.PostJoinEvent;
import net.md_5.bungee.api.ChatColor;

public class Info {
	
	public static HashMap<String, Object> ServerInfo = new HashMap<>();
	public static HashMap<String, Object> Discord = new HashMap<>();
	public static HashMap<String, Object> Webinterface = new HashMap<>();
	public static HashMap<String, Object> MySQL = new HashMap<>();

	public static HashMap<String, String> Messages = new HashMap<>();
	
	public static String LastBanName = "No ban noted", LastBanReason = "", LastBanTime = "";
	public static String LastMuteName = "No mute noted", LastMuteReason = "", LastMuteTime = "";
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void readMessages() {
		
		
		try {
			Object obj = new JSONParser().parse(new FileReader(Banns.FilePath + "/" + Info.ServerInfo.get("Language-File") + ".json"));
			JSONObject JObj = (JSONObject) obj;
			
			Map messagesJSON = (Map) JObj.get("Messages");
			
			Iterator<Map.Entry> itr1 = messagesJSON.entrySet().iterator();
			while(itr1.hasNext()) {
				Map.Entry pair = itr1.next();
				Info.Messages.put(pair.getKey().toString(), pair.getValue().toString());
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Error while reading JSON file");
		}
		
		
	}
	
	public static String getNoPerm() {
		String back = Messages.get("NoPermissions")
				.replaceAll("%prefix%", Banns.prefix)
				.replaceAll("§", "&");
			
		return ChatColor.translateAlternateColorCodes('&', back);
		
	}
	
	public static String getMutedChat() throws IOException {
	    String back = Messages.get("MutedChat")
	    		.replaceAll("%prefix%", Banns.prefix)
	    		.replaceAll("%reason%", ChatEvent.reason)
	    		.replaceAll("§", "&");
	    return ChatColor.translateAlternateColorCodes('&', back);
	}
	
	public static String getMutedJoin() throws IOException {
	    String back = Messages.get("MutedJoin")
	    		.replaceAll("%prefix%", Banns.prefix)
	    		.replaceAll("%reason%", PostJoinEvent.MReason)
	    		.replaceAll("%remTime%", PostJoinEvent.MTime)
	    		.replaceAll("§", "&");
	    return ChatColor.translateAlternateColorCodes('&', back);
	}
	
	public static String getBannedJoin() throws IOException {
	    String back = Messages.get("BannedJoin")
	    		.replaceAll("%servername%", (String) ServerInfo.get("Servername"))
	    		.replaceAll("%timeperm%", JoinEvent.TimeAngabe)
	    		.replaceAll("%reason%", JoinEvent.grund)
	    		.replaceAll("%remtime%", JoinEvent.lastSecs)
	    		.replaceAll("§", "&");
	    return ChatColor.translateAlternateColorCodes('&', back);
	}
	
	public static String getPermaBan() throws IOException {
	    String back = Messages.get("PermaBan")
	    		.replaceAll("%servername%", (String) ServerInfo.get("Servername"))
	    		.replaceAll("%timeperm%", JoinEvent.TimeAngabe)
	    		.replaceAll("%reason%", JoinEvent.grund)
	    		.replaceAll("§", "&");
	    return ChatColor.translateAlternateColorCodes('&', back);
	}
	
	public static String getMuteReason() throws IOException {
	    String back = Messages.get("MuteReason")
	    		.replaceAll("%prefix%", Banns.prefix)
	    		.replaceAll("%reason%", PluginMessageListener.tReason)
	    		.replaceAll("§", "&");
	    return ChatColor.translateAlternateColorCodes('&', back);
	}
	
	public static String getBanReason() throws IOException {
	    String back = Messages.get("BanReason")
	    		.replaceAll("%servername%", (String) ServerInfo.get("Servername"))
	    		.replaceAll("%timeperm%", PluginMessageListener.typeTime)
	    		.replaceAll("%reason%", PluginMessageListener.tReason)
	    		.replaceAll("§", "&");
	    return ChatColor.translateAlternateColorCodes('&', back);
	}
	
	
}
