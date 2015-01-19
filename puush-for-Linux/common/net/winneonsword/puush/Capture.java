package net.winneonsword.puush;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

public class Capture {
	
	private static Robot robot;
	private static Map<String, String> data;
	
	public Capture(){
		
		try {
			
			this.robot = new Robot();
			this.data = new HashMap<String, String>();
			
			data.put("z", "poop");
			data.put("e", "winneonsword@gmail.com");
			data.put("k", "443F5C93272267D3D3E6D8011A1089A3");
			data.put("f", "image.png");
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void desktop(){
		
		try {
			
			robot.keyPress(KeyEvent.VK_PRINTSCREEN);
			robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
			
			Thread.sleep(500);
			
			BufferedImage image = (BufferedImage) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.imageFlavor);
			ImageIO.write(image, "PNG", new File("image.png"));
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void window(){
		
		try {
			
			robot.keyPress(KeyEvent.VK_ALT);
			robot.keyPress(KeyEvent.VK_PRINTSCREEN);
			robot.keyRelease(KeyEvent.VK_ALT);
			robot.keyRelease(KeyEvent.VK_PRINTSCREEN);
			
			Thread.sleep(500);
			
			BufferedImage image = (BufferedImage) Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).getTransferData(DataFlavor.imageFlavor);
			ImageIO.write(image, "PNG", new File("image.png"));
			
			URL url = new URL("https://puush.me/api/up");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			Set keys = data.keySet();
			Iterator iter = keys.iterator();
			String content = "";
			
			for (int i = 0; iter.hasNext(); i++){
				
				Object key = iter.next();
				
				if (i != 0){
					
					content += "&";
					
				}
				
				if (key.equals("f")){
					
					content += key + "=" + new File(data.get(key));
					
				} else {
					
					content += key + "=" + data.get(key);
					
				}
				
			}
			
			System.out.println(content);
			out.writeBytes(content);
			out.flush();
			out.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			
			while ((line = in.readLine()) != null){
				
				System.out.println(line);
				
			}
			
			System.out.println(conn.getResponseCode());
			
			in.close();
			conn.disconnect();
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void selection(){
		
		
		
	}
	
}
