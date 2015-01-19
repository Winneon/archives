package net.winneonsword.CG;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import net.winneonsword.CG.utils.ChatUtils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Utils extends ChatUtils {
	
	public static CG main;
	
	public Utils(CG main){
		
		super(main);
		this.main = main;
		
	}
	
	public static void chatInput(Player p, Runnable run){
		
		if (main.chat.chatInput.containsKey(p.getName())){
			
			main.chat.chatInput.remove(p.getName());
			
		}
		
		main.chat.chatInput.put(p.getName(), run);
		
	}
	
	public static void delay(Runnable run, int ticks){
		
		Bukkit.getScheduler().scheduleSyncDelayedTask(main, run, ticks);
		
	}
	
	public static boolean ifInt(String integer){
		
		try {
			
			Integer.parseInt(integer);
			
			return true;
			
		} catch (NumberFormatException e){
			
			return false;
			
		}
		
	}
	
	public static int toInteger(String integer){
		
		return Integer.parseInt(integer);
		
	}
	
	public static void copy(InputStream in, File file){
		
		try {
			
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			
			while ((len = in.read(buf)) > 0){
				
				out.write(buf, 0, len);
				
			}
			
			out.close();
			in.close();
			
		} catch (Exception e){
			
			// Nothing.
			
		}
		
	}
	
}
