package net.winneonsword.CG;

import net.winneonsword.CG.exceptions.InvalidArgumentException;
import net.winneonsword.CG.exceptions.NoPermissionException;
import net.winneonsword.CG.gui.GuiMainMenu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CGCommand extends CommandInterpreter {
	
	public CGCommand(CG main){
		
		super("cg", main);
		
	}
	
	@Override
	public void run(Player p, String[] args) throws Exception {
		
		if (main.sys.getPlayer(p.getName()).getInterface()){
			
			if (args.length == 0){
				
				s(p, "Try /cg help.");
				
			} else {
				
				String[] help = {
						
						CG + "CollisionGames Help Menu",
						"&5- &d/cg help &5// &dShow the main help menu.",
						"&5- &d/cg join &5// &dJoin a game.",
						"&5- &d/cg leave &5// &dLeave a game.",
						"&5- &d/cg list &5// &dShow the player list.",
						"&5- &d/cg interface &5// &dToggle the interface.",
						"&5- &d/cg staff &5// &dShow the staff help menu."
						
				};
				
				String[] staff = {
						
						CG + "CollisionGames Staff Menu",
						"&5- &d/cg start &5// &dStart a game.",
						"&5- &d/cg leave &5// &dLeave a game.",
						"&5- &d/cg kick <player> &5// &dKick a player from the game."
						
				};
				
				switch (args[0]){
				
				default:
					
					throw new InvalidArgumentException(p, "/cg help");
					
				case "help":
					
					s(p, help);
					break;
					
				case "join":
					
					if (main.game.addPlayer(p.getName())){
						
						s(p, "Joined the game! Teleporting to the lobby...");
						
					} else {
						
						s(p, "&cYou have already joined!");
						
					}
					
					break;
					
				case "leave":
					
					if (main.game.removePlayer(p.getName())){
						
						s(p, "Left the game! Teleporting to spawn...");
						
					} else {
						
						s(p, "&cYou have not joined yet!");
						
					}
					
					break;
					
				case "list":
					
					if (main.game.getPlayers().isEmpty()){
						
						s(p, "&cThere are no players!");
						
					} else {
						
						s(p, "CollisionGames Player List");
						
						for (String pp : main.game.getPlayers()){
							
							Player ppp = Bukkit.getPlayer(pp);
							s(p, new String[] {
									
									"&6- &7" + ppp.getDisplayName()
									
							});
							
						}
						
					}
					
					break;
					
				case "interface":
					
					boolean toggle = main.sys.toggleInterface(p);
					
					if (toggle == true){
						
						s(p, "Toggled the interface to &6chat&d.");
						
					} else {
						
						s(p, "Toggled the interface to &6gui&d.");
						
					}
					
					break;
					
				case "staff":
					
					if (p.hasPermission("wa.staff")){
						
						s(p, staff);
						
					} else {
						
						throw new NoPermissionException(p, "/cg staff");
						
					}
					
					break;
					
				}
				
			}
			
		} else {
			
			main.sys.displayGui(p, new GuiMainMenu(main));
			
		}
		
	}
	
}
