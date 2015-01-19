package net.winneonsword.CG.exceptions;

import org.bukkit.entity.Player;

import static net.winneonsword.CG.Utils.*;

public class NoPermissionException extends Exception {
	
	private static final long serialVersionUID = 6318643382077296281L;
	
	public NoPermissionException(Player p){
		
		s(p, "&cYou do not have permission to run that command!");
		
	}
	
	public NoPermissionException(Player p, String cmd){
		
		s(p, "&cYou do not have permission to run " + cmd + "!");
		
	}

}
