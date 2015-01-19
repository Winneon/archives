package net.winneonsword.Collision.listeners;

import net.winneonsword.Collision.Collision;
import net.winneonsword.Collision.MoveableEntity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EntityMove implements Listener {
	
	
	Collision main;
	
	public EntityMove(Collision i){
		main = i;
	}
	
	/*@EventHandler
	public void onEntityMove(EntityMoveEvent e){
		
		MoveableEntity entity = e.getEntity();
		
		LivingEntity ent = entity.getEntity();
		LivingEntity dum = entity.getDummy();
		
		Location loc = ent.getLocation();
		
		if (loc.distance(dum.getLocation()) < 10){
			
			Location newLoc = e.getNewLocation();
			
			double x = newLoc.getX();
			double y = newLoc.getY();
			double z = newLoc.getZ();
			
			
			
		}
		
	}*/
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
	
		if (e.getPlayer().isInsideVehicle()){
			LivingEntity wolf;
			Player p = e.getPlayer();
			try {
				wolf = (LivingEntity) p.getVehicle();
			} catch (Exception ee){
				return;
			}
			if (main.game().getMoveableEntity(p.getName()) != null){
				Location l = p.getLocation();
				MoveableEntity wolfyy = main.game().getMoveableEntity(p.getName());
				if (wolfyy.getEntity().isDead() || wolfyy.getDummy().isDead()){
					main.game().setMoveableEntity(p.getName(), null);
					return;
				}
				try {
					wolfyy.getDummy().teleport(getCardinalMove(p));
				} catch (Exception ee){}
			}
		}
	}
	
	public Location getCardinalMove(Player p) {
		
		double rotation = (p.getLocation().getYaw() - 180) % 360;
		
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 22.5) {
            return new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()-10);
        } else if (22.5 <= rotation && rotation < 67.5) {
        	return new Location(p.getWorld(), p.getLocation().getX()+10, p.getLocation().getY(), p.getLocation().getZ()-10);
        } else if (67.5 <= rotation && rotation < 112.5) {
        	return new Location(p.getWorld(), p.getLocation().getX()+10, p.getLocation().getY(), p.getLocation().getZ());
        } else if (112.5 <= rotation && rotation < 157.5) {
        	return new Location(p.getWorld(), p.getLocation().getX()+10, p.getLocation().getY(), p.getLocation().getZ()+10);
        } else if (157.5 <= rotation && rotation < 202.5) {
        	return new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()+10);
        } else if (202.5 <= rotation && rotation < 247.5) {
        	return new Location(p.getWorld(), p.getLocation().getX()-10, p.getLocation().getY(), p.getLocation().getZ()+10);
        } else if (247.5 <= rotation && rotation < 292.5) {
        	return new Location(p.getWorld(), p.getLocation().getX()-10, p.getLocation().getY(), p.getLocation().getZ());
        } else if (292.5 <= rotation && rotation < 337.5) {
        	return new Location(p.getWorld(), p.getLocation().getX()-10, p.getLocation().getY(), p.getLocation().getZ()-10);
        } else if (337.5 <= rotation && rotation < 360.0) {
        	 return new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ()-10);
        } else {
            return null;
        }
	}
}