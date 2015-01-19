package net.winneonsword.CG;

import net.winneonsword.CG.utils.ChatUtils;
import net.winneonsword.CG.utils.InvenUtils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CommandInterpreter extends Utils implements CommandExecutor {
	
	public ChatUtils chat;
	public InvenUtils inven;
	
	private String cmd;
	
	public CommandInterpreter(String cmd, CG main){
		
		super(main);
		this.cmd = cmd;
		
		this.chat = new ChatUtils(main);
		this.inven = new InvenUtils(main);
		
	}
	
	public abstract void run(Player p, String[] args) throws Exception;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if (label.equalsIgnoreCase(this.cmd)){
			
			if (sender instanceof Player){
				
				try {
					
					run((Player) sender, args);
					main.saveYAMLs();
					
				} catch (Exception e){
					
					// Nothing here. All of the exceptions are handled in the exceptions themselves.
					
				}
				
			} else {
				
				main.getServer().getConsoleSender().sendMessage("CG is not meant to be used in the console. Sorry.");
				
			}
			
			return true;
			
		}
		
		return false;
		
	}
	
}
