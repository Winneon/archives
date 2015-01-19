package net.winneonsword.Collision;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityMoveEvent extends Event implements Cancellable {
	
	private MoveableEntity entity;
	private Location loc;
	
	private HandlerList handlers;
	private boolean cancelled;
	
	public EntityMoveEvent(MoveableEntity entity, Location loc){
		
		this.entity = entity;
		this.loc = loc;
		
		handlers = new HandlerList();
		cancelled = false;
		
	}
	
	public MoveableEntity getEntity(){
		
		return entity;
		
	}
	
	public Location getNewLocation(){
		
		return loc;
		
	}
	
	@Override
	public boolean isCancelled(){
		
		return cancelled;
		
	}
	
	@Override
	public void setCancelled(boolean cancel){
		
		cancelled = cancel;
		
	}
	
	@Override
	public HandlerList getHandlers(){
		
		return handlers;
		
	}
	
}
