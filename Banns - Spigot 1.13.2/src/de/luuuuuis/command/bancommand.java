package de.luuuuuis.command;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.luuuuuis.toGet.BanInv;


public class bancommand implements CommandExecutor {
	
	  public static HashMap<Player, String> NAME = new HashMap<>();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("You must be a Player!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if(p.hasPermission("Banns.Ban")) {
		
		if(args.length == 0) {
			p.sendMessage("§c/ban <Player>");
		} else if(args.length == 1){
		
		NAME.put(p, args[0]);
			 
		Inventory inv = Bukkit.createInventory(p, 54, "§c§l§n" + args[0]);
		
		inv.setContents(BanInv.getBanInv);
		
		ItemStack item01 = new ItemStack(Material.PLAYER_HEAD, 1, (short)3);
		SkullMeta sm01 = (SkullMeta) item01.getItemMeta();
		sm01.setOwningPlayer(Bukkit.getOfflinePlayer(args[0]));
		sm01.setDisplayName("§c§l" + args[0]);
		item01.setItemMeta(sm01);
		
		inv.setItem(4, item01);
		
		if(p.hasPermission("Banns.administrate")) {
			
			ItemStack item4 = new ItemStack(Material.REDSTONE);
			ItemMeta meta4 = item4.getItemMeta();
			meta4.setDisplayName("§8▌ §rUnwelcome (Permanent)");
			meta4.addEnchant(Enchantment.DURABILITY, 1, true);
			item4.setItemMeta(meta4);
			
			inv.setItem(43, item4);
			
			
		}

		p.openInventory(inv);
		
		} else {
			p.sendMessage("§c/ban <Player>");
		}
		
		} else {
			p.sendMessage("§cYou have no authorization for this.");
		}
		
		return false;
	}

}
