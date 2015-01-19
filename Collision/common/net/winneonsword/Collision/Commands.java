package net.winneonsword.Collision;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.winneonsword.Collision.game.Arena;
import net.winneonsword.Collision.game.User;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftCow;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftWolf;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.github.lyokofirelyte.WCAPI.Command.WCArg;
import com.github.lyokofirelyte.WCAPI.Command.WCCommand;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class Commands {
	
	private Collision main;
	
	public Commands(Collision main){
		
		this.main = main;
		
	}
	
	@WCCommand(aliases = { "cl", "collision" }, help = "Try &6/cl ?&d.", desc = "The main Collision command.", min = 1)
	public void collision(CommandSender s, String[] args){
		
		main.api().reg.args(this, s, "cl", args);
		
	}
	
	@WCArg(refs = { "?", "help" })
	public void help(CommandSender s, String[] args){
		
		String[] help = {
				
				WC + "Collision Help Menu",
				"&6- &e/cl ? &6// &eView the main help menu.",
				"&6- &e/cl join <arena> &6// &eJoin a Collision arena.",
				"&6- &e/cl leave &6// &eLeave Collision."
				
		};
		
		s(s, help);
		
	}
	
	@WCArg(refs = { "join" }, player = true)
	public void join(Player p, String[] args){
		
		if (args.length < 2){
			
			s(p, "Usage: /cl join <arena>");
			
		} else if (main.game().containsArena(args[1])){
			
			Arena arena = main.game().getArena(args[1]);
			
			if (arena.addPlayer(p)){
				
				Location loc = arena.getLocation();
				
				s(p, "Joined the arena &6" + arena.getName() + " &din Collision!");
				p.teleport(loc);

				
			} else {
				
				s(p, "&cYou have already joined Collision!");
				
			}
			
		} else {
			
			s(p, "&cThere is no arena by that name!");
			
		}
		
	}
	
	@WCArg(refs = { "leave" }, player = true)
	public void leave(Player p, String[] args){
		
		User player = main.game().getPlayer(p.getName());
		
		if (player != null){
			
			Arena arena = player.getArena();
			arena.removePlayer(player);
			
			s(p, "Left &6Collision&d!");
			p.performCommand("spawn");
			
		} else {
			
			s(p, "&cYou haven't joined yet!");
			
		}
		
	}
	
	@WCArg(refs = { "list" })
	public void list(CommandSender s, String[] args){
		
		s(s, "Collision Player List");
		
		for (Arena arena : main.game().getArenas()){
			
			s(s, "&9" + arena.getName() + "&3:");
			
			if (arena.isPlayers()){
				
				for (User player : arena.getPlayers()){
					
					s(s, "&6- &e" + player.getPlayer().getDisplayName());
					
				}
				
			} else {
				
				s(s, "&6- &eNo players joined!");
				
			}
			
		}
		
	}
	
	@WCArg(refs = { "set" }, perm = "wa.staff", player = true)
	public void set(Player p, String[] args){
		
		if (args.length == 1){
			
			String[] help = {
					
					WC + "Collision Set Help Menu",
					"&6- &e/cl set arena &6// &eCreate an arena in Collision.",
					"&6- &e/cl set lobby &6// &eSet the Collision lobby location."
					
			};
			
			s(p, help);
			
		} else {
			
			Location loc;
			
			switch (args[1].toLowerCase()){
			
			case "arena":
				
				Block block = p.getTargetBlock(null, 200);
				
				if (block.getType() == Material.WALL_SIGN || block.getType() == Material.SIGN_POST){
					
					Sign sign = (Sign) block.getState();
					String[] lines = sign.getLines();
					
					if (!(lines[0].isEmpty())){
						
						String name = lines[0];
						Arena arena = new Arena(name, block.getLocation());
						boolean success = main.game().addArena(arena);
						
						s(p, success ? "Created the arena &6" + arena.getName() + " &dat the sign's location!" : "There is already an arena named &6" + arena.getName() + "&d!");
						
						if (success){
							
							block.setType(Material.AIR);
							
						}
						
						return;
						
					}
					
				}
				
				s(p, "&ePlease put a sign where you want the center of the arena to be located, with the name of the arena on the first line.");
				s(p, "&6Then, put your feet where the sign is, and run this command again.");
				
				break;
			
			case "lobby":
				
				loc = p.getLocation();
				main.game().setLobby(loc);
				
				s(p, "Set the location of the lobby to &6"
						+ loc.getBlockX() +
						"&5, &6"
						+ loc.getBlockY() +
						"&5, &6"
						+ loc.getBlockZ() +
						" &din the world &6"
						+ loc.getWorld().getName() +
						"&d.");
				
				break;
				
			}
			
		}
		
	}
	
	@WCArg(refs = { "test" }, player = true)
	public void test(Player p, String[] args){
		
		World world = main.getServer().getWorld("world");
		LivingEntity entity = (LivingEntity) world.spawnEntity(p.getLocation(), EntityType.WOLF);
		LivingEntity ent = (LivingEntity) world.spawnEntity(new Location(p.getWorld(), p.getLocation().getX() + 60, p.getLocation().getY(), p.getLocation().getZ()), EntityType.COW);
		CraftWolf wolf = (CraftWolf) entity;
		CraftCow cow = (CraftCow) ent;
		
		cow.getHandle().setInvisible(true);
		cow.setRemoveWhenFarAway(false);
		
		ent.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 99999, 128));
		ent.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 99999, 128));
		entity.setPassenger(p);
		
		wolf.getHandle().setTarget((Entity)((CraftEntity) ent).getHandle());
		wolf.getHandle().setGoalTarget((EntityLiving)((CraftEntity) ent).getHandle());
		
		main.game().setMoveableEntity(p.getName(), new MoveableEntity(entity));
		main.game().getMoveableEntity(p.getName()).setDummy(ent);
	}
	
}
