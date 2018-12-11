package de.luuuuuis.toGet;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BanInv {

	public static ItemStack[] getBanInv;

	public static void createInv() {

		Thread th = new Thread(new Runnable() {

			@Override
			public void run() {

				Inventory inv = Bukkit.createInventory(null, 54, "§c§l§n[Name]");
				inv.setItem(0, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(1, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(2, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(3, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(5, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(6, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(7, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(8, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(17, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(26, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(35, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(44, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(53, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(52, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(51, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(50, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(49, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(48, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(47, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(46, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(45, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(36, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(27, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(18, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));
				inv.setItem(9, ItemBuilder.createItemStack(Material.RED_STAINED_GLASS_PANE, 1, ""));

				List<String> lore1 = new ArrayList<String>();
				lore1.add("");
				lore1.add("§8▌ §c§lATTENTION:");
				lore1.add("§8▌ §rYou need a proof!");

				ItemStack item = new ItemStack(Material.IRON_SWORD);
				ItemMeta meta = item.getItemMeta();
				meta.setDisplayName("§8▌ §rHacking");
				meta.setLore(lore1);
				item.setItemMeta(meta);

				inv.setItem(10, item);

				List<String> lore2 = new ArrayList<String>();
				lore2.add("");
				lore2.add("§8▌ §c§lATTENTION:");
				lore2.add("§8▌ §rYou need a proof!");

				ItemStack item1 = new ItemStack(Material.WRITABLE_BOOK);
				ItemMeta meta1 = item1.getItemMeta();
				meta1.setDisplayName("§8▌ §rChat");
				meta1.setLore(lore2);
				item1.setItemMeta(meta1);

				inv.setItem(12, item1);

				List<String> lore3 = new ArrayList<String>();
				lore3.add("");
				lore3.add("§8▌ §c§lATTENTION:");
				lore3.add("§8▌ §rYou need a proof!");
				lore3.add("");
				lore3.add("§8▌ §rReasons: Bugusing, Teaming, Spawntrapping, etc.");

				ItemStack item3 = new ItemStack(Material.COMPARATOR);
				ItemMeta meta3 = item3.getItemMeta();
				meta3.setDisplayName("§8▌ §rCoustom (1 Day)");
				meta3.setLore(lore3);
				item3.setItemMeta(meta3);

				inv.setItem(23, item3);

				List<String> lore5 = new ArrayList<String>();
				lore5.add("");
				lore5.add("§8▌ §rCheck the bans/mutes of this player");
				lore5.add("  ");
				lore5.add("§8▌ §rPress shift to change to unban");

				ItemStack item5 = new ItemStack(Material.PAPER);
				ItemMeta meta5 = item5.getItemMeta();
				meta5.setDisplayName("§8▌ §r/Check");
				meta5.setLore(lore5);
				item5.setItemMeta(meta5);

				inv.setItem(25, item5);

				List<String> lore6 = new ArrayList<String>();
				lore6.add("");
				lore6.add("§8▌ §r\"normal\" cheating");

				ItemStack item6 = new ItemStack(Material.LIME_DYE);
				ItemMeta meta6 = item6.getItemMeta();
				meta6.setDisplayName("§8▌ §aLevel 1 §r(7 Days)");
				meta6.setLore(lore6);
				item6.setItemMeta(meta6);

				inv.setItem(19, item6);

				List<String> lore7 = new ArrayList<String>();
				lore7.add("");
				lore7.add("§8▌ §rextreme cheating");

				ItemStack item7 = new ItemStack(Material.ORANGE_DYE);
				ItemMeta meta7 = item7.getItemMeta();
				meta7.setDisplayName("§8▌ §eLevel 2 §r(30 Days)");
				meta7.setLore(lore7);
				item7.setItemMeta(meta7);

				inv.setItem(28, item7);

				List<String> lore8 = new ArrayList<String>();
				lore8.add("");
				lore8.add("§8▌ §rextreme cheating");
				lore8.add("§8▌ §rand suspected on alt account");

				ItemStack item8 = new ItemStack(Material.PURPLE_DYE);
				ItemMeta meta8 = item8.getItemMeta();
				meta8.setDisplayName("§8▌ §cLevel 3 §r(Permanent)");
				meta8.setLore(lore8);
				item8.setItemMeta(meta8);

				inv.setItem(37, item8);

				// Chat

				List<String> lore9 = new ArrayList<String>();
				lore9.add("");
				lore9.add("§8▌ §roffending");

				ItemStack item61 = new ItemStack(Material.LIME_DYE);
				ItemMeta meta61 = item61.getItemMeta();
				meta61.setDisplayName("§8▌ §aLevel 1 §r(3 Hours)");
				meta61.setLore(lore9);
				item61.setItemMeta(meta61);

				inv.setItem(21, item61);

				List<String> lore71 = new ArrayList<String>();
				lore71.add("");
				lore71.add("§8▌ §rbehavior, spamming, black mailing, etc.");

				ItemStack item71 = new ItemStack(Material.ORANGE_DYE);
				ItemMeta meta71 = item61.getItemMeta();
				meta71.setDisplayName("§8▌ §eLevel 2 §r(9 Hours)");
				meta71.setLore(lore71);
				item71.setItemMeta(meta71);

				inv.setItem(30, item71);

				List<String> lore81 = new ArrayList<String>();
				lore81.add("");
				lore81.add("§8▌ §radvertising");

				ItemStack item81 = new ItemStack(Material.PURPLE_DYE);
				ItemMeta meta81 = item81.getItemMeta();
				meta81.setDisplayName("§8▌ §cLevel 3 §r(30 Days)");
				meta81.setLore(lore81);
				item81.setItemMeta(meta81);

				inv.setItem(39, item81);

				getBanInv = inv.getContents();

			}
		});
		th.start();

	}

}
