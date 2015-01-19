package net.winneonsword.CG.exceptions;

import org.bukkit.entity.Player;

import static net.winneonsword.CG.Utils.*;

public class InvalidArgumentException extends Exception {

	private static final long serialVersionUID = -473301346573800990L;
	
	public InvalidArgumentException(Player p){
		
		s(p, "&cUnknown argument.");
		
	}
	
	public InvalidArgumentException(Player p, String cmd){
		
		s(p, "&cUnknown argument. Try " + cmd + " for help.");
		
	}
	
}
