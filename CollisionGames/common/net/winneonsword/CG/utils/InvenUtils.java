package net.winneonsword.CG.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.Utils;

public class InvenUtils extends Utils {
	
	public InvenUtils(CG main){
		
		super(main);
		
	}
	
	public static ItemStack createItem(String display, String[] lore, Material mat, int... data){
		
		ItemStack item = null;
		
		switch (data.length){
		
		default:
			
			item = new ItemStack(mat, 1);
			break;
			
		case 1:
			
			item = new ItemStack(mat, data[0]);
			break;
			
		case 2:
			
			item = new ItemStack(mat, data[0], (short) data[1]);
			break;
			
		}
		
		ItemMeta meta = item.getItemMeta();
		lore = ChatUtils.AS(lore);
		List<String> loreL = Arrays.asList(lore);
		
		meta.setDisplayName(ChatUtils.AS(display));
		meta.setLore(loreL);
		item.setItemMeta(meta);
		
		return item;
		
	}
	
	public static ItemStack createItem(String display, String[] lore, short durability, Material mat, int... data){
		
		ItemStack item = createItem(display, lore, mat, data);
		item.setDurability(durability);
		
		return item;
		
	}
	
	public static ItemStack createItem(String display, String[] lore, Enchantment enchant, int amp, Material mat, int... data){
		
		ItemStack item = createItem(display, lore, mat, data);
		ItemMeta meta = item.getItemMeta();
		
		meta.addEnchant(enchant, amp, true);
		item.setItemMeta(meta);
		
		return item;
		
	}
	
	public static ItemStack createItem(String display, String[] lore, Enchantment enchant, int amp, short durability, Material mat, int... data){
		
		ItemStack item = createItem(display, lore, durability, mat, data);
		ItemMeta meta = item.getItemMeta();
		
		meta.addEnchant(enchant, amp, true);
		item.setItemMeta(meta);
		
		return item;
		
	}
	
}
