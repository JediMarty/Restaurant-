package pack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class Role {

	private PreparedStatement statement;
	private ResultSet result;
	private String sql;
	
	static JPanel Profilepanel = new JPanel();
	private JPanel panel_reg = new JPanel();
	private JPanel panel_boss;
	private JPanel panel_waiter;
	static JPanel imagePanel;
	private JLabel fname = new JLabel();
	private JLabel lname = new JLabel();
	private JLabel passl = new JLabel();
	private JLabel pos = new JLabel();
	private JLabel egnl = new JLabel();
	private JLabel idl = new JLabel();
	static JLabel imgLabel;
	
	private JComboBox<String> combo = new JComboBox<>();
	
	private JTextField name = new JTextField();
	private JTextField lastname = new JTextField();
	private static JTextField pass = new JTextField();
	private JTextField egn = new JTextField();
	private JTextField id = new JTextField();
    static JTextField archive_text = new JTextField();
	
	private JButton regbtn;
	private JButton HomeIcon;
	private JButton upbtn;
	private JButton delbtn;
	private JButton archivebtn;
	private JButton archivedelbtn;
	private JButton archiveemplobtn;

	static JScrollPane scroll;
	private JScrollPane scroll_table;
	
	private ImageIcon img_home1;
	private ImageIcon img_home2;

	private String idbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
	private String user = "marti";
	private String password = "marti";
    private String str_selectedid;
    
	private int id_emplo;
    private int selectedid;
	int flagtable = 0;
	static String lastClicktable;
	
	private DefaultTableModel tableModel;
	private JTable table;
	
	Role() {
	
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sql = "SELECT e.Firstname, p.Pos_NAME "
					+ "FROM EMPLOYEES e "
					+ "JOIN positions p ON e.pos_id = p.pos_id "
					+ "WHERE Firstname = ? AND Password = ? ";
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, RestaurantMain.username.getText());
			statement.setString(2, Login.resultHashedPass);
			
			result = statement.executeQuery(); 
			
			if (result.next()) //Results crawling
			{
				String storedpostion = result.getString("Pos_NAME");
				Boolean flag = storedpostion.equals("Boss");
				Boolean flag2 = storedpostion.equals("Waiter");
				
				if (flag == true) {
					
					boss(); //Role is the Boss - full access in the software!
					profile();
				}
				
				else if (flag2 == true ){
					waiter(); //Role is the Waiter - limited access in the software!
				    profile();
				}
			}
			else { 
				JOptionPane.showMessageDialog(RestaurantMain.frame, "Грешни данни!", "Грешка", JOptionPane.OK_OPTION);
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
		
		flagtable = 1; //Only for the Boss - access to add more tables or delete!
		
		fname.setText("ИМЕ");
		fname.setFont(new Font("Calibri", Font.BOLD, 30));
		fname.setBounds(150, 40, 100,30);
		name.setBounds(70, 70, 220, 50); 
		name.setFont(RestaurantMain.font);
		
		lname.setText("ФАМИЛИЯ");
		lname.setFont(new Font("Calibri", Font.BOLD, 30));
		lname.setBounds(110, 140, 150,30);
		lastname.setBounds(70, 170, 220, 50); 
		lastname.setFont(RestaurantMain.font);
		
		egnl.setText("ЕГН");
		egnl.setFont(new Font("Calibri", Font.BOLD, 30));
		egnl.setBounds(150, 240, 110,30);
		egn.setBounds(70, 270, 220, 50); 
		egn.setFont(RestaurantMain.font);
		
		passl.setText("ПАРОЛА");
		passl.setFont(new Font("Calibri", Font.BOLD, 30));
		passl.setBounds(125, 350, 110,30);
		pass.setBounds(70, 380, 220, 50);
		
		pos.setText("ДЛЪЖНОСТ");
		pos.setFont(new Font("Calibri", Font.BOLD, 30));
		pos.setBounds(100, 440, 170, 30);
	    combo.setBounds(70, 470, 220, 50);
		
		regbtn = new JButton();
		regbtn.setText("РЕГИСТРИРАЙ");
		regbtn.setBounds(70,570,220,50);
		regbtn.setFocusPainted(false);
		regbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				str_selectedid = combo.getSelectedItem().toString().split(" ")[0];
				selectedid = Integer.parseInt(str_selectedid);
				
				SQL_Handler.addEmployee(name,lastname,egn,selectedid);
				
			}
			
		});
		
		idl.setText("ID - служител");
		idl.setFont(new Font("Calibri", Font.BOLD, 30));
		idl.setBounds(85, 650, 200, 30);
		id.setBounds(70, 680, 220, 50); 
		id.setFont(RestaurantMain.font);
		
		upbtn = new JButton();
		upbtn.setText("Промени");
		upbtn.setBounds(70,730,220,50);
		upbtn.setFocusPainted(false);
		upbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				str_selectedid = combo.getSelectedItem().toString().split(" ")[0];
				selectedid = Integer.parseInt(str_selectedid);
				id_emplo = Integer.parseInt(id.getText());
				
				SQL_Handler.updateEmployee(id_emplo,name,lastname,egn,selectedid);
				
			}
		});
		
		delbtn = new JButton();
		delbtn.setText("Изтрии");
		delbtn.setBounds(70,800,220,50);
		delbtn.setFocusPainted(false);
		delbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				id_emplo = Integer.parseInt(id.getText());
				
				SQL_Handler.deleteEmployee(id_emplo);
				
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
		panel_reg.add(idl);
		panel_reg.add(id);
		panel_reg.add(upbtn);
		panel_reg.add(delbtn);
		panel_reg.setLayout(null);
		
		archive_text.setBounds(870,760,220,50); //text field
		
		tableModel = new DefaultTableModel();
		
		archivebtn = new JButton();
		archivebtn.setText("АРХИВ");
		archivebtn.setBounds(870,810,220,50);
		archivebtn.setFocusPainted(false);
		archivebtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (tableModel.getRowCount() !=0) {
					
					tableModel.setColumnCount(0); //delete columns for the new ones!
					tableModel.setRowCount(0); //delete rows for the new ones!
					
				}
				
				else {
					//Just in case, if the button archive with an empty field is pressed!
					tableModel.setColumnCount(0); //delete columns for the new ones!
					
					table = new JTable(tableModel);
					scroll_table = new JScrollPane(table);
					scroll_table.setBounds(660, 500, 610, 200);
					scroll_table.setVisible(true);
					
					Profilepanel.add(scroll_table);
					
				}
				
				tableModel.addColumn("Сметка");
				tableModel.addColumn("МАСА");
				tableModel.addColumn("Сервитьор");
				tableModel.addColumn("ястие/напитка");
				tableModel.addColumn("Дата");
				
				SQL_Handler.showArchiveOrders(tableModel);
				
			}
		});
		
		archivedelbtn = new JButton();
		archivedelbtn.setText("ИЗТРИЙ АРХИВА");
		archivedelbtn.setBackground(Color.RED);
		archivedelbtn.setBounds(870,870,220,30);
		archivedelbtn.setFocusPainted(false);
		archivedelbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				int confirm1 = JOptionPane.showConfirmDialog(RestaurantMain.frame, "Наистина ли желаете да изтриете архива ?", "АРХИВ", JOptionPane.YES_NO_OPTION);
				
				if (confirm1 == JOptionPane.YES_OPTION) {
					
					String confirm2 = JOptionPane.showInputDialog(RestaurantMain.frame, "ПОТВЪРЖДАВАТЕ ли, че желаете да изтриете архива ? \n (Напишете ПОТВЪРЖДАВАМ)", "АРХИВ", JOptionPane.YES_NO_OPTION); 
					
					if (confirm2!=null && confirm2.toUpperCase().equals("ПОТВЪРЖДАВАМ")) {
						
						SQL_Handler.deleteArchive();
					
				}
					else {
						JOptionPane.showMessageDialog(RestaurantMain.frame, "Изтриването прекратено!", "АРХИВ", JOptionPane.OK_OPTION);
						
					}
				}
				
				else {
					JOptionPane.showMessageDialog(RestaurantMain.frame, "Изтриването прекратено!", "АРХИВ", JOptionPane.OK_OPTION);
					
				}
				
			}
		});
		
		archiveemplobtn = new JButton();
		archiveemplobtn.setText("Сервитьори");
		archiveemplobtn.setBackground(new Color (240,255,255)); //The color is azure
		archiveemplobtn.setBounds(870,910,220,30);
		archiveemplobtn.setFocusPainted(false);
		archiveemplobtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (tableModel.getRowCount() !=0) {
					
					tableModel.setColumnCount(0); //delete columns for the new ones!
					tableModel.setRowCount(0); //delete rows for the new ones!
					
				}
				
				else {
					//Just in case, if the button archive with an empty field is pressed!
					tableModel.setColumnCount(0); //delete columns for the new ones!
					
					table = new JTable(tableModel);
				
					scroll_table = new JScrollPane(table);
					scroll_table.setBounds(660, 500, 610, 200);
					scroll_table.setVisible(true);
					
					Profilepanel.add(scroll_table);
					
				}
				
				tableModel.addColumn("ID");
				tableModel.addColumn("Име");
				tableModel.addColumn("Фамилия");
				tableModel.addColumn("ЕГН");
				tableModel.addColumn("Парола");
				tableModel.addColumn("Позиция");
				
				SQL_Handler.waiterTable(tableModel);
				
				}
			
		});
		
		Profilepanel.add(archive_text);
		Profilepanel.add(archiveemplobtn);
		Profilepanel.add(archivedelbtn);
		Profilepanel.add(archivebtn);
		Profilepanel.add(panel_reg);
		Profilepanel.add(panel_boss);
		
	}
	
	void waiter() {
		
		Profilepanel.removeAll();
		
		panel_waiter = new JPanel();
		JLabel WaiterName = new JLabel();
		JLabel label_logo = new JLabel();
		ImageIcon logo = new ImageIcon("logo.png");
		
		WaiterName.setFont(RestaurantMain.font);
		WaiterName.setText("Hello, " + RestaurantMain.username.getText());
		WaiterName.setVerticalAlignment(JLabel.CENTER);
		WaiterName.setHorizontalAlignment(JLabel.CENTER);
		
		label_logo.setIcon(logo);
		label_logo.setBounds(570, 610, 600, 200);
		
		panel_waiter.setBackground(new Color (240,255,255)); //The color is azure
		panel_waiter.setLayout(new BorderLayout());
		panel_waiter.add(WaiterName);
		panel_waiter.setBounds(0, 110, 200, 220);
		
		Profilepanel.add(panel_waiter);
		Profilepanel.add(label_logo);
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
		
		JPanel toppanel = new JPanel(); //panel for top ordered items(meals or drinks)!
		
		toppanel.setBounds(500, 110, 600, 200); 
		toppanel.setBackground(Color.orange);
		toppanel.setLayout(new BoxLayout(toppanel, BoxLayout.Y_AXIS));
		
		List<String> data = new ArrayList<>();
		data = SQL_Handler.topOrders();
		
		String items = "";
		
		JLabel label_text = new JLabel("НАЙ-ПОРЪЧВАНИ:");
		label_text.setFont(RestaurantMain.font);
		label_text.setForeground(Color.WHITE);
		
		JLabel label_items = new JLabel();
		label_items.setFont(new Font("Calibri", Font.BOLD, 20));
		label_items.setForeground(Color.WHITE);
		
		int num = 1;
		
		for (String i : data) {
			
			items += " " +num +". "+ i;
			num++;
		}
		
		label_items.setText(items);
		
		label_text.setAlignmentX(Component.CENTER_ALIGNMENT);
		label_items.setAlignmentX(Component.CENTER_ALIGNMENT);
		toppanel.add(label_text);
		toppanel.add(label_items);
		
		Profilepanel.add(toppanel);
		Profilepanel.add(HomeIcon);
		Profilepanel.add(menuframe);
		Profilepanel.add(panel1);
	    Profilepanel.setLayout(null);
	    
	    displaytables();
	    
	}

	static String hashedpassword() {
	
		MessageDigest digest;
		String resultHashedPass ="";
		
		try {
			digest = MessageDigest.getInstance("SHA-256"); //Hash algorithm
			
			byte[] hashedBytes = digest.digest(pass.getText().getBytes());
			
			StringBuilder hexString = new StringBuilder();
			for (byte b: hashedBytes) {
				hexString.append(String.format("%02x", b));
			}
			
			resultHashedPass = hexString.toString();
					
			
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return resultHashedPass;	
	}
	
	static void displaytables() {
		
		final String SAVE_DIR = "Restaurant_tables";
		File dir = new File(SAVE_DIR);
		BufferedImage img;
		int dotIndex;
		String FileNAME;
		ImageIcon image;
		imgLabel = new JLabel();
		JPanel imageContainer = new JPanel(); // for the image and the image text
		imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.Y_AXIS)); //Vertical arrangement);
		imagePanel = new JPanel();
		imagePanel.setLayout(new GridLayout(0,3,10,10));
	    
		if (dir.exists()) {
			
			File[] files = dir.listFiles();
	
			if (files !=null) {
				Arrays.sort(files, new Comparator<File>( ) { //Sorting the files (tables name)

					@Override
					public int compare(File f1, File f2) {
						
						// Get index for both files
						int idxFile1 = f1.getName().lastIndexOf(".");
						int idxFile2 = f2.getName().lastIndexOf(".");
						
						String fileName1 = f1.getName().substring(0, idxFile1-1);
						String fileName2 = f2.getName().substring(0, idxFile2-1);
						
						int num1 = Integer.parseInt(fileName1);
						int num2 = Integer.parseInt(fileName2);
						return Integer.compare(num1, num2);
					}
					
				});
			}

	       for (File file : files) {
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
			        imgLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
					imgLabel.addMouseListener(new MouseListener() {
						
						@Override
						public void mouseClicked(MouseEvent e) {
							int dotIndex2 = file.getName().lastIndexOf(".");
							String strid_table = file.getName().substring(0, dotIndex2-1);
							lastClicktable = strid_table;
							
							ReserveTable table = new ReserveTable();
							table.table();
							
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
							// TODO Auto-generated method stub
							
						}

						@Override
						public void mouseExited(MouseEvent e) {
							// TODO Auto-generated method stub
							
						}
						
					});
			        
					
				    
					imagePanel.add(imgLabel);
				
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			   
			}
		}
		
		scroll = new JScrollPane(imagePanel);
		scroll.setBounds(650, 270, 500, 500); 
		RestaurantMain.Homepanel.add(scroll);
		
	    Role.scroll.revalidate();
	    Role.scroll.repaint();
		
		}
	
}
