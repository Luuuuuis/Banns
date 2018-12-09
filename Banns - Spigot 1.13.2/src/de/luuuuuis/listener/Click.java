package de.luuuuuis.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import de.luuuuuis.Banns;
import de.luuuuuis.command.bancommand;

public class Click implements Listener{
	

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		Inventory inv = e.getClickedInventory();
		
		try {
		
			if(inv.getTitle().startsWith("§c§l§n")) {
				e.setCancelled(true);
			
				if(p.hasPermission("Banns.Ban")) {
			
					
						if(!e.getAction().equals(InventoryAction.PICKUP_ALL)) {
							
							if(e.getCurrentItem().getType().equals(Material.PAPER)) {
									
									List<String> lore5 = new ArrayList<String>();
									lore5.add(" ");
									lore5.add("§8▌ §rUnban this player");
									lore5.add("  ");
									lore5.add("§8▌ §rPress shift to change to check");
									
									ItemStack item5 = new ItemStack(Material.MILK_BUCKET);
									ItemMeta meta5 = item5.getItemMeta();
									meta5.setDisplayName("§8▌ §rUnban");
									meta5.setLore(lore5);
									item5.setItemMeta(meta5);
									
									inv.setItem(25, item5);
								
								
							} else if(e.getCurrentItem().getType().equals(Material.MILK_BUCKET)) {
								
									List<String> lore5 = new ArrayList<String>();
									lore5.add(" ");
									lore5.add("§8▌ §rCheck the bans/mutes of this player");
									lore5.add("  ");
									lore5.add("§8▌ §rPress shift to change to unban");
									
									ItemStack item5 = new ItemStack(Material.PAPER);
									ItemMeta meta5 = item5.getItemMeta();
									meta5.setDisplayName("§8▌ §r/Check");
									meta5.setLore(lore5);
									item5.setItemMeta(meta5);
									
									inv.setItem(25, item5);
								
							}
							
							return;
							
						}
						
					if(e.getCurrentItem().getType() == Material.PAPER && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §r/Check")) {
						performClick("Check", p, "0", "CHECK");
					} else if(e.getCurrentItem().getType().equals(Material.MILK_BUCKET) && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §rUnban")) {
						performClick("Unban", p, "0", "UNBAN");
					} else if(e.getCurrentItem().getType() == Material.COMPARATOR && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §rCoustom (1 Day)")) {
						performClick("null", p, "86400", "COUSTOM");
					} else if(e.getCurrentItem().getType() == Material.LIME_DYE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §aLevel 1 §r(7 Days)")) {
						performClick("Hacking", p, "604800", "BAN");
					}else if(e.getCurrentItem().getType() == Material.ORANGE_DYE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §eLevel 2 §r(30 Days)")) {
						performClick("Hacking", p, "2592000", "BAN");
					} else if(e.getCurrentItem().getType() == Material.PURPLE_DYE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §cLevel 3 §r(Permanent)")) {
						performClick("Hacking", p, "-1", "BAN");
					} else if(e.getCurrentItem().getType() == Material.LIME_DYE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §aLevel 1 §r(3 Hours)")) {
						performClick("Offending", p, "10800", "MUTE");
					} else if(e.getCurrentItem().getType() == Material.ORANGE_DYE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §eLevel 2 §r(9 Hours)")) {
						performClick("behavior", p, "32400", "MUTE");
					} else if(e.getCurrentItem().getType() == Material.PURPLE_DYE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §cLevel 3 §r(30 Days)")) {
						performClick("advertising", p, "2592000", "MUTE");
					} else if(e.getCurrentItem().getType() == Material.REDSTONE && e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§8▌ §rUnwelcome (Permanent)")) {
						if(p.hasPermission("Banns.administrate")) {
							performClick("Unwelcome", p, "-1", "BAN");
						}
					}
		
				}
		
			}
		
		} catch(Exception ex) {
			ex.getStackTrace();
		}
		
	}

	public static void performClick(String Reason, Player p, String time, String type) {
	    p.closeInventory();
		
	    ByteArrayDataOutput out = ByteStreams.newDataOutput();
	    
	    out.writeUTF("Banns");
	    out.writeUTF((String)bancommand.NAME.get(p));
	    out.writeUTF(Reason);
	    out.writeUTF(p.getName());
	    out.writeUTF(time);
	    out.writeUTF(type);
	    
	    p.sendPluginMessage(Banns.getInstance(), "BungeeCord", out.toByteArray());
		
	    bancommand.NAME.remove(p);
	    
	    System.out.println("Banns >> Message send");
	    
	}
	
	
}
