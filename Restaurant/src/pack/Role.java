package pack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

class Role {

	PreparedStatement statement;
	private ResultSet result;
	String sql;
	
	static JPanel Profilepanel = new JPanel();
	JPanel panel_reg = new JPanel();
	JPanel panel_boss;
	JPanel panel_waiter;
	static JPanel imagePanel;
	private JLabel fname = new JLabel();
	private JLabel lname = new JLabel();
	private JLabel passl = new JLabel();
	private JLabel pos = new JLabel();
	private JLabel egnl = new JLabel();
	
	static JComboBox<String> combo = new JComboBox<>();
	
	static JTextField name = new JTextField();
	static JTextField lastname = new JTextField();
	static JTextField pass = new JTextField();
	static JTextField egn = new JTextField();
	
	JButton regbtn;
	JButton HomeIcon;
	
	static JScrollPane scroll;
	
	ImageIcon img_home1;
	ImageIcon img_home2;

	String idbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "marti";
	String password = "marti";
	
	static int flagtable = 0;
	
	Role() {
	
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			
			//Statement statement2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			sql = "SELECT e.Firstname, p.Pos_NAME "
					+ "FROM EMPLOYEES e "
					+ "JOIN positions p ON e.pos_id = p.pos_id "
					+ "WHERE Firstname = ?";
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, RestaurantMain.username.getText());
			
			
			result = statement.executeQuery(); 
			
			/*
			result = statement.executeQuery("SELECT e.Firstname, p.Pos_NAME FROM EMPLOYEES e "
					+ "JOIN positions p ON e.pos_id = p.pos_id "
					+ "WHERE Firstname = '" + RestaurantMain.username.getText() + "'");
			*/ 
			if (result.next()) //Results crawling
			{
				String storedpostion = result.getString("Pos_NAME"); //Getting text from the position drop down menu!
				Boolean flag = storedpostion.equals("Boss");
		
				if (flag == true) {
					
					boss(); //Role is the Boss - full access in the software!
					profile();
				}
				
				else {
					waiter(); //Role is the Waiter - limited access in the software!
				    profile();
				}
			}
			else { 
				
			}
				result = statement.executeQuery("SELECT pos_id, Pos_NAME FROM positions");
				
				while(result.next()) {
					combo.addItem(result.getString("pos_id") + " " + result.getString("Pos_NAME")); //add all positions!
					}
			
			conn.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	
	}
	
	void boss() {
		
		flagtable = 1;
		
		fname.setText("ИМЕ");
		fname.setFont(new Font("Calibri", Font.BOLD, 30));
		fname.setBounds(150, 150, 100,30);
		name.setBounds(70, 190, 220, 50); 
		name.setFont(RestaurantMain.font);
		
		lname.setText("ФАМИЛИЯ");
		lname.setFont(new Font("Calibri", Font.BOLD, 30));
		lname.setBounds(120, 355, 150,30);
		lastname.setBounds(70, 390, 220, 50); 
		lastname.setFont(RestaurantMain.font);
		
		egnl.setText("ЕГН");
		egnl.setFont(new Font("Calibri", Font.BOLD, 30));
		egnl.setBounds(145, 500, 110,30);
		egn.setBounds(70, 530, 220, 50); 
		egn.setFont(RestaurantMain.font);
		
		passl.setText("ПАРОЛА");
		passl.setFont(new Font("Calibri", Font.BOLD, 30));
		passl.setBounds(110, 650, 110,30);
		pass.setBounds(70, 680, 220, 50);
		
		pos.setText("ДЛЪЖНОСТ");
		pos.setFont(new Font("Calibri", Font.BOLD, 30));
		pos.setBounds(120, 755, 110,30);
	    combo.setBounds(70, 785, 220, 50);
		
		regbtn = new JButton();
		regbtn.setText("РЕГИСТРИРАЙ");
		regbtn.setBounds(70,840,220,50);
		regbtn.setFocusPainted(false);
		regbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection conn = DriverManager.getConnection(idbcURL,user,password);
				
					MessageDigest digest;
					
					try {
						digest = MessageDigest.getInstance("SHA-256"); //Hash algorithm
						
						byte[] hashedBytes = digest.digest(Role.pass.getText().getBytes());
						
						StringBuilder hexString = new StringBuilder();
						for (byte b: hashedBytes) {
							hexString.append(String.format("%02x", b));
						}
						
						String resultHashedPass = hexString.toString();
						
						String selectedid = combo.getSelectedItem().toString().split(" ")[0];
						
						
						sql = "{CALL P_EMPLOYEES(?, ?, ?, ?, ?)}"; //The Registration/insert procedure
						
						statement = conn.prepareStatement(sql);
						statement.setString(1, Role.name.getText());
						statement.setString(2, Role.lastname.getText());
						statement.setString(3, Role.egn.getText());
						statement.setString(4, resultHashedPass);
						statement.setString(5, selectedid);
						
						result = statement.executeQuery(); 
						
					} catch (NoSuchAlgorithmException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					conn.close();
					
				} catch (SQLException er) {
					// TODO Auto-generated catch block
					er.printStackTrace();
				} 
				
			}
			
		});
		
		Profilepanel.removeAll();
		
		panel_boss = new JPanel();
		JLabel BossName = new JLabel();
		BossName.setFont(RestaurantMain.font);
		BossName.setText("Hello, " + RestaurantMain.username.getText());
		BossName.setVerticalAlignment(JLabel.CENTER);
		BossName.setHorizontalAlignment(JLabel.CENTER);
		panel_boss.setBackground(new Color (240,255,255)); //The color is azure
		panel_boss.setLayout(new BorderLayout());
		panel_boss.add(BossName);
       
		panel_boss.setBounds(1290, 110, 200, 220);
	
		panel_reg.setBackground(new Color(240,255,255)); //The color is azure
		panel_reg.setBounds(0,110,350,950);
		panel_reg.add(fname);
		panel_reg.add(name);
		panel_reg.add(lname);
		panel_reg.add(lastname);
		panel_reg.add(egnl);
		panel_reg.add(egn);
		panel_reg.add(passl);
		panel_reg.add(pass);
		panel_reg.add(pos);
		panel_reg.add(combo);
		panel_reg.add(regbtn);
		panel_reg.setLayout(null);
		
		Profilepanel.add(panel_reg);
		Profilepanel.add(panel_boss);
		
	}
	
	void waiter() {
		
		Profilepanel.removeAll();
		
		panel_waiter = new JPanel();
		JLabel WaiterName = new JLabel();
		WaiterName.setFont(RestaurantMain.font);
		WaiterName.setText("Hello, " + RestaurantMain.username.getText());
		// panel_waiter.setText(COUNT POR14KI);
		WaiterName.setVerticalAlignment(JLabel.CENTER);
		WaiterName.setHorizontalAlignment(JLabel.CENTER);
		panel_waiter.setBackground(new Color (240,255,255)); //The color is azure
		panel_waiter.setLayout(new BorderLayout());
		panel_waiter.add(WaiterName);
		panel_waiter.setBounds(0, 110, 200, 220);
		
		Profilepanel.add(panel_waiter);
		
	}
	
	MouseListener click_HomeIcon = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			RestaurantMain.switch_the_scene();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			HomeIcon.setIcon(img_home2);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			HomeIcon.setIcon(img_home1);
			
		}
	
	};
	
	void profile() {
		
		ImageIcon logo = new ImageIcon("logo.png");
		JLabel logoframe = new JLabel();
		logoframe.setIcon(logo);
		logoframe.setBounds(650, 400, 500, 200);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.orange);
		panel1.setBounds(0,0,1520,100);
		
		ImageIcon menufr = new ImageIcon("menuframe.png");
		JLabel menuframe = new JLabel();
		menuframe.setIcon(menufr);
		menuframe.setBounds(500,0,500,100);
		
		img_home1 = new ImageIcon("home1.png");
	    img_home2 = new ImageIcon("home2.png");
		HomeIcon = new JButton();
		HomeIcon.setIcon(img_home1);
		HomeIcon.setBorderPainted(false);
		HomeIcon.setBounds(720, 15, 70, 70);
		HomeIcon.setContentAreaFilled(false);
		HomeIcon.addMouseListener(click_HomeIcon);
		HomeIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		Profilepanel.add(logoframe);
		Profilepanel.add(HomeIcon);
		Profilepanel.add(menuframe);
		Profilepanel.add(panel1);
	    Profilepanel.setLayout(null);
	    
	    displaytables();
	    
	}
	
	static void displaytables() {
		
		final String SAVE_DIR = "Restaurant_tables";
		File dir = new File(SAVE_DIR);
		BufferedImage img;
		int dotIndex;
		String FileNAME;
		ImageIcon image;
		JLabel imgLabel = new JLabel();
		JPanel imageContainer = new JPanel(); // for the image and the image text
		imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.Y_AXIS)); //Vertical arrangement);
		imagePanel = new JPanel();
		imagePanel.setLayout(new GridLayout(0,3,10,10));
	
		if (dir.exists()) {
			for (File file : dir.listFiles() ) {
				dotIndex = file.getName().lastIndexOf(".");
				FileNAME = file.getName().substring(0, dotIndex-1);
						
				try {
					img = ImageIO.read(file);
					image = new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
					
					imgLabel = new JLabel();
					imgLabel.setIcon(image);
					imgLabel.setText(FileNAME);
					imgLabel.setHorizontalTextPosition(JLabel.CENTER);
			        imgLabel.setVerticalTextPosition(JLabel.BOTTOM);
			        
					imageContainer.add(imgLabel);
					imageContainer.setBackground(Color.WHITE);
				    
					imagePanel.add(imgLabel);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			   
			}
		}
		
		scroll = new JScrollPane(imagePanel);
		scroll.setBounds(700, 620, 500, 500);
		
		RestaurantMain.frame.add(scroll);
		 
	}
	
}

