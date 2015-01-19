package net.winneonsword.CG.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import net.winneonsword.CG.CG;
import net.winneonsword.CG.utils.InvenUtils;

public class GuiConfirm extends Gui {
	
	private Runnable yes;
	private Runnable no;
	
	public GuiConfirm(CG main, String title, Runnable yes, Runnable no){
		
		super(main, 9, title);
		this.yes = yes;
		this.no = no;
		
	}
	
	@Override
	public void create(){
		
		addButton(0, InvenUtils.createItem("&a&lYes", new String[] { }, Material.INK_SACK, 1, 2));
		addButton(1, InvenUtils.createItem("&c&lNo", new String[] { }, Material.INK_SACK, 1, 1));
		
	}
	
	@Override
	public void actionPerformed(Player p){
		
		switch (slot){
		
		case 0:
			
			yes.run();
			break;
			
		case 1:
			
			no.run();
			break;
			
		}
		
	}
	
}
