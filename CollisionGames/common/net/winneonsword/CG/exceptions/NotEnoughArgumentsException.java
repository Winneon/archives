package net.winneonsword.CG.exceptions;

import org.bukkit.entity.Player;

import static net.winneonsword.CG.Utils.*;

public class NotEnoughArgumentsException extends Exception {

	private static final long serialVersionUID = -8020839440329648780L;
	
	public NotEnoughArgumentsException(Player p){
		
		s(p, "&cYou did not type enough arguments!");
		
	}
	
	public NotEnoughArgumentsException(Player p, String cmd){
		
		s(p, "&cYou did not type enough arguments! Try " + cmd + " for help.");
		
	}

}
