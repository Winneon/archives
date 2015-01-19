package net.winneonsword.CG;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.winneonsword.CG.listeners.ChatListener;
import net.winneonsword.CG.listeners.ClickListener;
import net.winneonsword.CG.listeners.JoinListener;
import net.winneonsword.CG.utils.ChatUtils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import static net.winneonsword.CG.Utils.*;

public class CG extends JavaPlugin {
	
	public static File folder;
	public static File userFolder;
	public static String[] yamls;
	
	public CGSystem sys;
	public ClickListener click;
	
	public CGGame game;
	public ChatListener chat;
	
	public PluginManager pm;
	public CGCommand cg;
	
	public static YamlConfiguration config;
	public static YamlConfiguration datacore;
	
	private File configFile;
	private File datacoreFile;
	
	@Override
	public void onEnable(){
		
		pm = this.getServer().getPluginManager();
		
		firstRun();
		setupPlayerData();
		
		click = new ClickListener(this);
		game = new CGGame(this);
		chat = new ChatListener();
		
		pm.registerEvents(new JoinListener(this), this);
		pm.registerEvents(click, this);
		pm.registerEvents(chat, this);
		
		cg = new CGCommand(this);
		getCommand("cg").setExecutor(cg);
		
		game.setArenas(datacore.getStringList("arenaList"));
		
		delay(new Runnable(){
			
			public void run(){
				
				int size = sys.getUsers().size();
				String users = "&aLoaded " + size + " user.";
				
				if (size == 1){
					
					b(users);
					
				} else {
					
					StringBuilder sb = new StringBuilder(users);
					
					sb.insert(users.length() - 1, "s");
					b(sb.toString());
					
				}
				
			}
			
		}, 20);
		
	}
	
	@Override
	public void onDisable(){
		
		saveArenaData();
		savePlayerData();
		
		int size = sys.getUsers().size();
		String users = "&cSaved " + size + " user.";
		
		if (size == 1){
			
			ChatUtils.b(users);
			
		} else {
			
			StringBuilder sb = new StringBuilder(users);
			
			sb.insert(users.length() - 1, "s");
			ChatUtils.b(sb.toString());
			
		}
		
	}
	
	public void saveYAMLs(){
		
		try {
			
			config.save(configFile);
			datacore.save(datacoreFile);
			
		} catch (Exception e){
			
			getLogger().severe("Failed to save the YAMLs.");
			e.printStackTrace();
			
		}
		
	}
	
	public void loadYAMLs(){
		
		try {
			
			config.load(configFile);
			datacore.load(datacoreFile);
			
		} catch (Exception e){
			
			getLogger().severe("Failed to load the YAMLs.");
			e.printStackTrace();
			
		}
		
	}
	
	private void setupPlayerData(){
		
		List<String> users = datacore.getStringList("users");
		sys = new CGSystem(this);
		
		sys.setUsers(users);
		getLogger().info("Loading " + users.size() + " users.");
		
		for (String name : sys.getUsers()){
			
			sys.setupPlayer(name, false);
			
		}
		
		getLogger().info("Loaded " + users.size() + " users.");
		
	}
	
	private void saveArenaData(){
		
		List<CGArena> arenas = game.getArenas();
		List<String> list = new ArrayList<String>();
		
		for (CGArena arena : arenas){
			
			list.add(arena.getName());
			
			datacore.set("arenas." + arena.getName() + ".world", arena.getWorld().getName());
			datacore.set("arenas." + arena.getName() + ".x", arena.getX());
			datacore.set("arenas." + arena.getName() + ".y", arena.getY());
			datacore.set("arenas." + arena.getName() + ".z", arena.getZ());
			datacore.set("arenas." + arena.getName() + ".r", arena.getRadius());
			
		}
		
		datacore.set("arenaList", list);
		
	}
	
	private void savePlayerData(){
		
		List<String> users = sys.getUsers();
		getLogger().info("Saving " + users.size() + " users.");
		
		for (String name : users){
			
			sys.savePlayer(name);
			
		}
		
		datacore.set("users", users);
		saveYAMLs();
		getLogger().info("Saved " + users.size() + " users.");
		
	}
	
	private void firstRun(){
		
		folder = this.getDataFolder();
		userFolder = new File(folder, "users");
		
		config = new YamlConfiguration();
		datacore = new YamlConfiguration();
		
		userFolder.mkdirs();
		
		YamlConfiguration yaml;
		String name;
		File file;
		
		yamls = new String[] {
				
				"config", "datacore"
				
		};
		
		for (int i = 0; i < yamls.length; i++){
			
			name = yamls[i] + ".yml";
			file = new File(folder, name);
			
			if (!(file.exists())){
				
				file.getParentFile().mkdirs();
				Utils.copy(getResource(name), file);
				
			}
			
			yaml = YamlConfiguration.loadConfiguration(file);
			
			switch (i){
			
			case 0:
				
				configFile = file;
				datacore = yaml;
				break;
				
			case 1:
				
				datacoreFile = file;
				datacore = yaml;
				break;
				
			}
			
		}
		
		loadYAMLs();
		
	}
	
}
