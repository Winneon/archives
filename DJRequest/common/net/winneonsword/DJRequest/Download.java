package net.winneonsword.DJRequest;

import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.swing.SwingWorker;

public class Download extends SwingWorker<Void, Void> {
	
	private String file;
	private String url;
	
	public Download(String file, String url, PropertyChangeListener main){
		
		this.file = file;
		this.url = url;
		
		addPropertyChangeListener(main);
		execute();
		
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		
		try {
			
			URL URL = new URL(url);
			int size = getFileSize(URL);
			
			System.out.println(size);
			
			BufferedInputStream in = new BufferedInputStream(URL.openStream());
			FileOutputStream out = new FileOutputStream(file);
			
			byte[] data = new byte[1024];
			
			int buf;
			int len = 0;
			
			while ((buf = in.read(data)) != -1){
				
				out.write(data, 0, buf);
				len += buf;
				
				setProgress((int) len * 100 / size);
				System.out.println(len);
				
			}
			
			System.out.println(len);
			
			in.close();
			out.close();
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		}
		
		return null;
		
	}
	
	private int getFileSize(URL url){
		
		HttpURLConnection conn = null;
		
		try {
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("HEAD");
			conn.getInputStream();
			
			return conn.getContentLength();
			
		} catch (Exception e){
			
			return -1;
			
		} finally {
			
			conn.disconnect();
			
		}
		
	}
	
}
