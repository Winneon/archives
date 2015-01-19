package net.winneonsword.Collision;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftEntity;
import org.bukkit.entity.LivingEntity;

public class MoveableEntity {
	
	private LivingEntity entity;
	private LivingEntity dummy;
	
	private CraftEntity craft;
	
	public MoveableEntity(LivingEntity entity){
		
		this.entity = entity;
		dummy = null;
		
		craft = (CraftEntity) entity;
		
	}
	
	public LivingEntity getEntity(){
		
		return entity;
		
	}
	
	public LivingEntity getDummy(){
		
		return dummy;
		
	}
	
	public CraftEntity getCraftEntity(){
		
		return craft;
		
	}
	
	public void move(Location loc){
		
		
		
	}
	
	public void setDummy(LivingEntity e){
		dummy = e;
	}
}