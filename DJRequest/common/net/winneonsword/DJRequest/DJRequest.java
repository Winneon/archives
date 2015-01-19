package net.winneonsword.DJRequest;

import net.winneonsword.DJRequest.gui.GuiManager;
import net.winneonsword.DJRequest.gui.MainWindow;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class DJRequest implements ActionListener, PropertyChangeListener {
	
	private JFrame frame;
	private JFrame updater;
	
	private String version;
	
	private JPanel main;
	private JLabel face;
	
	private JProgressBar progress;
	private JButton cancel;
	
	private JPanel playing;
	private JLabel nowplaying;
	
	private JPanel left;
	private JPanel right;
	private JPanel queue;
	
	private JButton login;
	private JButton add;
	private JButton drop;
	private JButton random;
	
	private List<JButton> buttons;
	
	private JTextField username;
	private JPasswordField password;
	
	private JTextField video;
	
	private List<Request> refresh;
	private boolean started;
	
	private boolean net;
	
	private BufferedReader in;
	private PrintWriter out;
	
	private String user;
	private String pass;
	
	private String SPLIT;
	
	public DJRequest() throws IOException {
		
		version = "1.1.5";
		SPLIT = "%split%";
		
		Socket socket = null;
		
		refresh = new ArrayList<Request>();
		started = false;
		
		try {
			
			socket = new Socket("144.76.184.51", 25002);
			//socket = new Socket("localhost", 25002);
			
			// TODO Change these before packaging!
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			
		} catch (Exception e){
			
			error("The server is offline! Now go cry in a corner.");
			System.exit(0);
			
		}
		
		frame = createGui("dJRequest v" + version(), start());
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("icon.png")));
		
		updater = createGui("dJRequest Updater", updater(), JFrame.DISPOSE_ON_CLOSE);
		updater.setLocationRelativeTo(frame);
		
		frame.setVisible(true);
		updater.setVisible(false);
		
		popup("The server is online! Please login with your ohsototes.com info.");
		
		if (!(net)){
			
			error("Could not connect to the skin server! That can't be healthy.");
			
		}
		
		updateCheck();
		run();
		
	}
	
	public static void main(String[] args){
		
		try {
			
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new DJRequest();
			
			//GuiManager.displayGui(new MainWindow("Testing window"));
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		}
		
	}
	
	public JFrame createGui(String name, JPanel panel){
		
		return createGui(name, panel, JFrame.EXIT_ON_CLOSE);
		
	}
	
	public JFrame createGui(String name, JPanel panel, int close){
		
		JFrame frame = new JFrame(name);
		frame.add(panel);
		
		frame.setDefaultCloseOperation(close);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		
		frame.pack();
		frame.setVisible(false);
		
		return frame;
		
	}
	
	private JPanel start(){
		
		main = new JPanel();
		JPanel content = new JPanel();
		
		content.setLayout(new GridLayout(1, 2));
		
		left = new JPanel();
		right = new JPanel();
		
		main.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		left.setLayout(new GridLayout(4, 1));
		left.setBorder(BorderFactory.createTitledBorder("Actions"));
		
		login = new JButton("Login");
		
		login.setActionCommand("login");
		login.addActionListener(this);
		
		left.add(login);
		
		add = new JButton("Add Request");
		
		add.setActionCommand("add");
		add.addActionListener(this);
		
		left.add(add);
		
		drop = new JButton("Drop Request");
		
		drop.setActionCommand("drop");
		drop.addActionListener(this);
		
		left.add(drop);
		
		random = new JButton("Random Request");
		
		random.setActionCommand("random");
		random.addActionListener(this);
		
		left.add(random);
		
		right.setLayout(new GridLayout(1, 1));
		right.setBorder(BorderFactory.createTitledBorder("Player Head"));
		
		BufferedImage image = null;
		
		try {
			
			image = ImageIO.read(new URL("http://minecraft.aggenkeech.com/face.php?u=" + user + "&s=64"));
			face = new JLabel(new ImageIcon(image));
			
			net = true;
			
		} catch (Exception e){
			
			face = new JLabel("<html>Pretend this<br />is a face.</html>");
			net = false;
			
			face.setHorizontalAlignment(JLabel.CENTER);
			
		}
		
		right.add(face);
		
		content.add(left);
		content.add(right);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 1;
		c.gridy = 0;
		c.gridwidth = 1;
		main.add(playing = playing(), c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		main.add(content, c);
		
		main.setBorder(new EmptyBorder(5, 5, 5, 5));
		buttons = Arrays.asList(login, add, drop, random);
		
		disableButtons(login);
		
		return main;
		
	}
	
	private JPanel updater(){
		
		JPanel main = new JPanel();
		
		main.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5), BorderFactory.createTitledBorder("Updater")));
		main.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		progress = new JProgressBar(0, 100);
		
		progress.setValue(0);
		progress.setStringPainted(true);
		
		JButton update = new JButton("Update");
		
		update.setActionCommand("update");
		update.addActionListener(this);
		
		cancel = new JButton("Cancel");
		
		cancel.setActionCommand("cancel");
		cancel.addActionListener(this);
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		
		main.add(progress, c);
		
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		
		main.add(update, c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		
		main.add(cancel, c);
		
		return main;
		
	}
	
	private JPanel playing(){
		
		JPanel main = new JPanel();
		
		main.setBorder(BorderFactory.createTitledBorder("Now Playing"));
		main.setLayout(new GridLayout(1, 1));
		
		nowplaying = linkify("None.", "http://lmgtfy.com/?q=nothing", "Can't you read?");
		
		main.add(nowplaying);
		main.setVisible(false);
		
		return main;
		
	}
	
	private JPanel staff(){
		
		JPanel main = new JPanel();
		
		main.setBorder(BorderFactory.createTitledBorder("Staff Options"));
		main.setLayout(new GridLayout(2, 1, 0, 2));
		
		JButton veto = new JButton("<html><div style=\"text-align: center;\">Veto<br />Now Playing<br />(Instant)</div></html>");
		
		veto.setActionCommand("staff_veto");
		veto.addActionListener(this);
		
		JButton vetoQueue = new JButton("<html><div style=\"text-align: center;\">Veto A Song<br />In The Queue<br />(Instant)</div></html>");
		
		vetoQueue.setActionCommand("staff_veto_queue");
		vetoQueue.addActionListener(this);
		
		main.add(veto);
		main.add(vetoQueue);
		
		return main;
		
	}
	
	private JPanel options(){
		
		JPanel main = new JPanel();
		
		main.setBorder(BorderFactory.createTitledBorder("Options"));
		main.setLayout(new GridLayout(1, 1, 0, 2));
		
		JButton manual = new JButton("<html><div style=\"text-align: center;\">Manual<br />Refresh</div></html>");
		
		manual.setActionCommand("refresh");
		manual.addActionListener(this);
		
		main.add(manual);
		
		return main;
		
	}
	
	private JPanel login(){
		
		JPanel main = new JPanel();
		
		main.setBorder(BorderFactory.createTitledBorder("Login..."));
		main.setLayout(new GridLayout(2, 1, 0, 2));
		
		username = new HintTextField("Username");
		password = new HintPasswordField("Password");
		
		main.add(username);
		main.add(password);
		
		return main;
		
	}
	
	private JPanel add(){
		
		JPanel main = new JPanel();
		
		main.setBorder(BorderFactory.createTitledBorder("Add Request..."));
		main.setLayout(new GridLayout(1, 1, 0, 2));
		
		video = new HintTextField("Song / Video Link");
		main.add(video);
		
		return main;
		
	}
	
	private JPanel vetoQueue(){
		
		JPanel main = new JPanel();
		
		main.setBorder(BorderFactory.createTitledBorder("Veto A Song In The Queue"));
		main.setLayout(new GridLayout(1, 1, 0, 2));
		
		video = new HintTextField("Enter the username of the person being vetoed...");
		main.add(video);
		
		return main;
		
	}
	
	private JPanel queue(){
		
		queue = new JPanel();
		
		queue.setLayout(new GridLayout(1, 1, 0, 2));
		queue.setBorder(BorderFactory.createTitledBorder("dJ Queue"));
		
		queue.add(linkify("None.", "http://lmgtfy.com/?q=nothing", "Can't you read?"));
		
		return queue;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e){
		
		int result;
		
		switch (e.getActionCommand()){
		
		case "staff_veto":
			
			result = JOptionPane.showConfirmDialog(frame, "Are you sure you want to\nveto the currently playing song?", "Veto Now Playing", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION){
				
				out.println(ServerRequest.STAFF_VETO_SONG.go() + SPLIT + user);
				
			}
			
			break;
			
		case "staff_veto_queue":
			
			result = JOptionPane.showConfirmDialog(frame, vetoQueue(), "Veto A Song In The Queue", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && checkButtons(video)){
				
				try {
					
					out.println(ServerRequest.STAFF_VETO_QUEUE.go() + SPLIT + user + SPLIT + video.getText());
					
				} catch (Exception ee){
					
					error("That is not a valid entry!");
					
				}
				
			}
			
			break;
			
		case "refresh":
			
			out.println(ServerRequest.NOW_PLAYING.go() + SPLIT + user);
			out.println(ServerRequest.REFRESH.go() + SPLIT + user);
			
			popup("Refreshing your current queue.");
			
			break;
			
		case "login":
			
			result = JOptionPane.showConfirmDialog(frame, login(), "Login to ohsototes.com", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION && checkButtons(username, password)){
				
				user = new String(username.getText());
				pass = new String(password.getPassword());
				
				out.println(ServerRequest.LOGIN.go() + SPLIT + user + SPLIT + pass);
				
			}
			
			break;
			
		case "add":
			
			result = JOptionPane.showConfirmDialog(frame, add(), "Add Request", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			
			try {
				
				if (result == JOptionPane.OK_OPTION && checkButtons(video)){

					out.println(ServerRequest.SEND_SONG.go() + SPLIT + user + SPLIT + video.getText());
					
				}
				
			} catch (Exception ee){
				
				ee.printStackTrace();
				
			}
			
			break;
			
		case "drop":
			
			result = JOptionPane.showConfirmDialog(frame, "Are you sure you want\nto drop your request?", "Drop Request", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			
			if (result == JOptionPane.OK_OPTION){
				
				out.println(ServerRequest.DROP_SONG.go() + SPLIT + user);
				
			}
			
			break;
			
		case "random": case "veto": case "veto_queue":
			
			popup("This feature has not been\nfully implemented yet.");
			break;
			
		case "update":
			
			try {
				
				String path = getClass().getResource(getClass().getSimpleName() + ".class").getFile();
				String name = path.startsWith("/") ? "DJRequest.jar" : new File(path.substring(0, path.lastIndexOf('!'))).getName();
				
				final Download task = new Download(name, "https://github.com/WinneonSword/DJRequest/raw/master/target/DJRequest.jar", this);
				
				updater.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				cancel.setEnabled(false);
				
				new Thread(){
					
					public void run(){
						
						while (!(task.isDone())){  }
						
						JOptionPane.showMessageDialog(frame, "Download successful! Closing your client...");
						System.exit(0);
						
					}
					
				}.start();
				
				frame.setEnabled(false);
				
			} catch (Exception ee){
				
				ee.printStackTrace();
				error("An internal error has occured.");
				
				frame.setEnabled(true);
				updater.dispose();
				
			}
			
			break;
			
		case "cancel":
			
			updater.dispose();
			break;
			
		}
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent e){
		
		if (e.getPropertyName().equals("progress")){
			
			int prog = (int) e.getNewValue();
			progress.setValue(prog);
			
		}
		
	}
	
	private void run() throws IOException {
		
		while (true){
			
			String line = in.readLine();
			String[] lines = line.split(SPLIT);
			ServerRequest req = ServerRequest.CUSTOM;
			
			for (ServerRequest s : ServerRequest.values()){
				
				if (line.startsWith(s.go())){
					
					req = s;
					
				}
				
			}
			
			switch (req){
			
			case CUSTOM:
				
				break;
				
			case LOGIN:
				
				if (lines[1].equals("ACCEPT")){
					
					updateAvatar();
					enableButtons(login);
					
					GridBagConstraints c = new GridBagConstraints();
					c.fill = GridBagConstraints.HORIZONTAL;
					
					c.gridx = 1;
					c.gridy = 2;
					c.gridwidth = 1;
					c.gridheight = 1;
					main.add(queue(), c);
					
					c.fill = GridBagConstraints.BOTH;
					
					if (lines.length == 3 && lines[2].equals("STAFFACCEPT")){
						
						c.gridx = 0;
						c.gridy = 0;
						c.gridwidth = 1;
						c.gridheight = 3;
						main.add(staff(), c);
						
					}
					
					c.gridx = 2;
					c.gridy = 0;
					c.gridwidth = 1;
					c.gridheight = 3;
					main.add(options(), c);
					
					main.revalidate();
					playing.setVisible(true);
					
					frame.pack();
					frame.revalidate();
					
					popup("Login success! Welcome to dJRequest!");
					
					out.println(ServerRequest.REFRESH.go() + SPLIT + user);
					out.println(ServerRequest.NOW_PLAYING.go() + SPLIT + user);
					
				} else {
					
					error("Login failed. Do you exist on ohsototes.com?...");
					
				}
				
				break;
				
			case NOW_PLAYING:
				
				if (playing.isVisible()){
					
					playing.remove(nowplaying);
					
					if (lines[1].equals("")){
						
						nowplaying = linkify(lines[2].split("%1%")[0], lines[2].split("%1%")[1], "Can't you read?");
						
					} else {
						
						String name = lines[2].split("%1%")[0];
						
						if (name.length() > 24){
							
							name = name.substring(0, 24) + "...";
							
						}
						
						nowplaying = linkify("<strong>" + lines[1] + ": </strong>" + name, lines[2].split("%1%")[1], "Duration: " + lines[2].split("%1%")[2] + " seconds");
						
					}
					
					playing.add(nowplaying);
					playing.revalidate();
					
				}
				
				break;
				
			case SEND_SONG:
				
				if (lines[1].equals("ACCEPT")){
					
					popup("Song accepted! Added to queue. Refreshing...");
					
				} else if (lines[1].equals("ALREADY")){
					
					error("You have already added your request! Drop your current\nrequest before adding a new one.");
					
				} else {
					
					error("Invalid request. Try checking your duration is less than\n11 minutes and it is a valid YouTube link.");
					
				}
				
				break;
				
			case STAFF_VETO_SONG:
				
				if (lines[1].equals("ACCEPT")){
					
					popup("Successfully vetoed the current song.");
					
				} else {
					
					error("Failed to veto the current song.\nIs there even a song playing?...");
					
				}
				
				break;
				
			case STAFF_VETO_QUEUE:
				
				if (lines[1].equals("ACCEPT")){
					
					popup("Successfully vetoed the specified song..");
					
				} else {
					
					error("Failed to veto the specified song.\nIs there even a song playing?...");
					
				}
				
				break;
				
			case REFRESH:
				
				if (lines.length == 3){
					
					refresh.add(Request.toRequest(lines[1], lines[2]));
					
				}
				
				if (!(started)){
					
					started = true;
					System.out.println("Started refresh.");
					
					new Timer().schedule(new TimerTask(){
						
						@Override
						public void run(){
							
							queue.removeAll();
							queue.setLayout(new GridLayout(refresh.size(), 1, 0, 2));
							
							if (refresh.isEmpty()){
								
								queue.add(linkify("None.", "http://lmgtfy.com/?q=nothing", "Can't you read?"));
								
							}
							
							for (int i = 0; i < refresh.size(); i++){
								
								Request req = refresh.get(i);
								String name = req.getSongName();
								
								if (name.length() > 24){
									
									name = name.substring(0, 24) + "...";
									
								}
								
								JLabel link = linkify("<strong>" + req.getUser() + ": </strong>" + name, req.getSongLink(), "Duration: " + req.getSongDuration() + " seconds");
								
								queue.add(link);
								
							}
							
							queue.revalidate();
							
							frame.pack();
							frame.revalidate();
							
							System.out.println("Finished refresh.");
							started = false;
							
							refresh = new ArrayList<Request>();
							
						}
						
					}, 2 * 1000);
					
				}
				
				break;
				
			case DISPOSE:
				
				out.println(ServerRequest.DISPOSE.go() + SPLIT + user);
				System.exit(0);
				break;
				
			case DROP_SONG:
				
				if (lines[1].equals("ACCEPT")){
					
					popup("Dropped request!");
					
				} else {
					
					error("Unable to drop request. Did you even send in a request?...");
					
				}
				
				break;
				
			case UPDATE:
				
				if (!(version().equals(lines[1])) && net){
					
					popup("An update is available for download!");
					updater.setVisible(true);
					
				}
				
				break;
				
			}
			
		}
		
	}
	
	private void enableButtons(JButton... exempt){
		
		List<JButton> e = Arrays.asList(exempt);
		
		for (JButton b : buttons){
			
			if (e.contains(b)){
				
				b.setEnabled(false);
				b.revalidate();
				
			} else {
				
				b.setEnabled(true);
				b.revalidate();
				
			}
			
		}
		
	}
	
	private void disableButtons(JButton... exempt){
		
		List<JButton> e = Arrays.asList(exempt);
		
		for (JButton b : buttons){
			
			if (e.contains(b)){
				
				b.setEnabled(true);
				b.revalidate();
				
			} else {
				
				b.setEnabled(false);
				b.revalidate();
				
			}
			
		}
		
	}
	
	private boolean checkButtons(JTextField... fields){
		
		int count = 0;
		
		for (JTextField field : fields){
			
			if (!(field.getText().equals(""))){
				
				count++;
				
			}
			
		}
		
		if (count == fields.length){
			
			return true;
			
		} else {
			
			error("You did not fill in all of the fields!");
			
			return false;
			
		}
		
	}
	
	// Linkify ALL the things!
	private JLabel linkify(final String text, final String URL, final String tip){
		
		URI temp = null;
		
		try {
			
			temp = new URI(URL);
			
		} catch (Exception e){
			
			e.printStackTrace();
			
		}
		
		final URI url = temp;
		final JLabel link = new JLabel();
		
		link.setToolTipText(tip);
		link.setText("<html>" + text + "</html>");
		link.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(216, 216, 216)), BorderFactory.createEmptyBorder(2, 5, 2, 5)));
		
		link.setFont(new Font("Segoe UI", Font.PLAIN, 11));
		link.setOpaque(true);
		link.setBackground(Color.white);
		
		link.setCursor(new Cursor(Cursor.HAND_CURSOR));
		link.addMouseListener(new MouseListener(){
			
			@Override
			public void mouseEntered(MouseEvent e){
				
				link.setText("<html><strong>" + text + "</strong></html>");
				
			}
			
			@Override
			public void mouseExited(MouseEvent e){
				
				link.setText("<html>" + text + "</html>");
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e){
				
				if (Desktop.isDesktopSupported()){
					
					try {
						
						Desktop.getDesktop().browse(url);
						
					} catch (Exception ee){
						
						ee.printStackTrace();
						
					}
					
				} else {
					
					error("Your computer's OS does not support link opening.");
					
				}
				
			}
			
			@Override
			public void mousePressed(MouseEvent e){  }
			
			@Override
			public void mouseReleased(MouseEvent e){  }
			
		});
		
		return link;
		
	}
	
	private void updateCheck(){
		
		out.println(ServerRequest.UPDATE.go());
		
	}
	
	private void updateAvatar(){
		
		if (net){
			
			BufferedImage image = null;
			right.remove(face);
			
			try {
				
				image = ImageIO.read(new URL("http://minecraft.aggenkeech.com/face.php?u=" + user + "&s=64"));
				
			} catch (Exception e){
				
				e.printStackTrace();
				
			}
			
			face = new JLabel(new ImageIcon(image));
			
			right.add(face);
			right.revalidate();
			
		}
		
	}
	
	private void popup(String message){
		
		JOptionPane.showMessageDialog(frame, message, "Massage", JOptionPane.INFORMATION_MESSAGE);
		
	}
	
	private void error(String message){
		
		JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
		
	}
	

	private int getSeconds(String time){
		
		if (time.contains(":")){
			
			String[] array = time.split(":");
			
			if (array.length == 2){
				
				try {
					
					int minutes = Integer.parseInt(array[0]);
					int seconds = Integer.parseInt(array[1]);
					
					seconds = (minutes * 60) + seconds;
					
					return seconds;
					
				} catch (Exception e){
					
					// w0t.
					
				}
				
			}
			
		}
		
		error("You did not enter a proper duration!");
		
		return -202;
		
	}

	public String version(){
		
		return version;
		
	}
	
}
