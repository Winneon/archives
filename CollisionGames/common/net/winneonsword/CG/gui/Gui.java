package net.winneonsword.CG.gui;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class Gui extends Utils {
	
	public CG main;
	
	public String title;
	public int slot;
	public ItemStack item;
	
	public InventoryClickEvent click;
	public InventoryDragEvent drag;
	public InventoryMoveItemEvent move;
	public InventoryInteractEvent interact;
	
	public String current;
	public Inventory inv;
	
	public Gui(CG main, int slots, String title){
		
		super(main);		
		this.main = main;
		
		this.inv = Bukkit.createInventory(null, slots, AS(title));
		this.title = title;
		
	}
	
	public abstract void create();
	
	public abstract void actionPerformed(Player p);
	
	public Inventory getInv(){
		
		return inv;
		
	}
	
	public void setInv(Inventory inv){
		
		this.inv = inv;
		
	}
	
	protected void addButton(int slot, ItemStack item){
		
		inv.setItem(slot, item);
		
	}
	
}
