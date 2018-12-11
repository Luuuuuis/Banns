package de.luuuuuis.toGet;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	@SuppressWarnings("deprecation")
	public static ItemStack createItemStack(Material mat, int subid, String DisplayName) {

		ItemStack item = new ItemStack(mat, 1, (short) subid);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(DisplayName);
		item.setItemMeta(meta);
		return item;

	}
}
