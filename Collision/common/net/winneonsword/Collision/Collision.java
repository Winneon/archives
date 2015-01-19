package net.winneonsword.Collision;

/*
 * Collision is a minigame that mimics Tron Legacy's Lightcycles
 * game. Collision will only work with WCAPI installed,
 * therefore it will only work properly on the Worlds Collide
 * Network, abbreviated WCN. Collision is not designed for every
 * day use on your average server. If you decide to build this
 * plugin, keep in mind that, without the certain dependencies
 * and circumstances, it will not function properly.
 * 
 * - Creator of Collision
 */

import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import net.winneonsword.Collision.game.Gameplay;
import net.winneonsword.Collision.listeners.EntityMove;

import com.github.lyokofirelyte.WCAPI.WCAPI;
import com.github.lyokofirelyte.WCAPI.WCManager;
import com.github.lyokofirelyte.WCAPI.WCNode;
import com.github.lyokofirelyte.WCAPI.Loops.WCDelay;

import static com.github.lyokofirelyte.WCAPI.WCUtils.*;

public class Collision extends WCNode {
	
	// Global Variables
	
	private Gameplay game;
	private Logging logging;
	
	private YamlConfiguration datacore;
	
	private WCManager wcm;
	private WCAPI api;
	
	// Local Variables
	
	private String data;
	
	/**
	 * Returns the Gameplay object used in Collision.
	 * @return The main Gameplay object.
	 */
	public Gameplay game(){
		
		return game;
		
	}
	
	/**
	 * Returns the Logging object used in Collision.<br />
	 * (Custom logger for colours in console.)
	 * 
	 * @return The main Logging object.
	 */
	public Logging logging(){
		
		return logging;
		
	}
	
	/**
	 * Returns the YamlConfiguration object known as<br />
	 * the main datacore object used in Collision.
	 * 
	 * @return The main datacore (YamlConfiguration) object.
	 */
	public YamlConfiguration datacore(){
		
		return datacore;
		
	}
	
	/**
	 * Returns the WCAPI object used in Collision.
	 * @return A WCAPI object.
	 */
	public WCAPI api(){
		
		return api;
		
	}
	
	/**
	 * Returns the WCManager object used in Collision.
	 * @return A WCManager object.
	 */
	public WCManager wcm(){
		
		return wcm;
		
	}
	
	@Override
	public void onEnable(){
		
		game = new Gameplay();
		logging = new Logging(this);
		getServer().getPluginManager().registerEvents(new EntityMove(this), this);
		
		api = getAPI();
		wcm = new WCManager(api);
		
		data = "Collision/datacore";
		
		api().fm.registerConfig(data);
		datacore = api().fm.getConfig(data);
		
		setLobby();
		
		String enabled = "Collision has been enabled.";
		api().reg.registerCommands(new Commands(this));
		
		logging().log(Level.INFO, enabled);
		api().ls.callDelay("announce", this, "&a" + enabled);
		
	}
	
	@Override
	public void onDisable(){
		
		saveLobby();
		
		boolean save = api().fm.saveConfig(data);
		logging().log(save ? Level.INFO : Level.SEVERE, save ? "&eSaved the datacore." : "Unable to save the datacore.");
		
		String disabled = "&cCollision has been disabled.";
		
		logging().log(Level.INFO, disabled);
		b(disabled);
		
	}
	
	@WCDelay(time = 60)
	public void announce(String message){
		
		b(message);
		
	}
	
	private void setLobby(){
		
		double x = datacore().getDouble("lobby.x");
		double y = datacore().getDouble("lobby.y");
		double z = datacore().getDouble("lobby.z");
		String w = datacore().getString("lobby.w");
		
		if (w != null){
			
			game().setLobby(new Location(getServer().getWorld(w), x, y, z));
			
		} else {
			
			logging().log(Level.SEVERE, "There is no lobby in the datacore! Set it with &e/cl set lobby&c!");
			
		}
		
	}
	
	private void saveLobby(){
		
		if (game().isLobby()){
			
			Location loc = game().getLobby();
			
			datacore().set("lobby.x", loc.getX());
			datacore().set("lobby.y", loc.getY());
			datacore().set("lobby.z", loc.getZ());
			datacore().set("lobby.w", loc.getWorld().getName());
			
		}
		
	}
	
}
