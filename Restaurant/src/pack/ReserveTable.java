package pack;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


class ReserveTable {
	
	static JFrame tableframe;
	
	private static String idbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String user = "marti";
	private static String password = "marti";
	
	static File newfile;
	
	private static String sqlQ;

	private static PreparedStatement statement;
	private static ResultSet result;
	
	private static int stored_mid;
	private static int stored_eid;
	private static int id;
	private static int id_table;
	private static int billName;
	
	static LocalDateTime datetime = LocalDateTime.now();
	
	private static JPanel panel = new JPanel();
	private static JLabel label_orderid = new JLabel();
	private static JLabel label_mealname = new JLabel();
	public static JTextField orderid = new JTextField();
	static JComboBox<String> mealname_combo = new JComboBox<>();
	private JButton button_update = new JButton();
	private JButton button_delete = new JButton();
	private JButton button_get_bill = new JButton();
	private JButton button_final = new JButton();
	private JButton button_music = new JButton();
	
	static Map<String, Integer> dataMap = new HashMap<>();
	
    private static DefaultTableModel tableModel;
	private static JTable table;
	private static JScrollPane scroll;
	
	private ImageIcon img_music1 = new ImageIcon("music1.png");
	private ImageIcon img_music2 = new ImageIcon("music2.png");
	
	public void table() {
		
		String status = "Свободна";
		//if the selected table is not taken by other waiter!
		if (status.equals(SQL_Handler.checkTableAvailable1()) || Login.resultHashedPass.equals(SQL_Handler.checkTableAvailable2())) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			panel = new JPanel();
			mealname_combo = new JComboBox<>(); 
			
			sqlQ = "SELECT * FROM MENU_ITEMS";
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery(); 
			
			while(result.next()) {
				
				id = result.getInt("MID");
				mealname_combo.addItem(result.getString("ITEM")); //add all items-food/drinks!
				dataMap.put(result.getString("ITEM"),id);
				
			}
			
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		label_orderid.setText("ID");
		label_orderid.setFont(new Font("Calibri", Font.BOLD, 30));
		label_orderid.setBounds(145, 30, 100,50);
		orderid.setBounds(45, 80, 220, 50); 
		orderid.setFont(RestaurantMain.font);
		
		label_mealname.setText("Поръчано");
		label_mealname.setFont(new Font("Calibri", Font.BOLD, 30));
		label_mealname.setBounds(90, 185, 150,30);
		mealname_combo.setBounds(45, 220, 220, 50); 
		mealname_combo.setFont(RestaurantMain.font);
		
	    button_update.setText("Промени");
		button_update.setBounds(45,360,220,50);
		button_update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (SQL_Handler.changesProtector() != 1) {
				
				String orderIdString = orderid.getText();

				SQL_Handler.updateOrder(orderIdString);
				
				tableModel.setRowCount(0); //delete rows for the new ones with the changes!
				SQL_Handler.showOrder(tableModel);
				
				}
				
			}
			
		});
	    
		button_delete.setText("Изтрии поръчка");
		button_delete.setBounds(45,420,220,50);
		button_delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (SQL_Handler.changesProtector() != 1) {
					
					String orderIdString = orderid.getText();
					
					SQL_Handler.deleteOrder(orderIdString);
				
				tableModel.setRowCount(0); //delete rows!
				SQL_Handler.showOrder(tableModel);
				
			}
				
			}
			
			
		});
		
		button_get_bill.setText("СМЕТКА");
		button_get_bill.setBounds(45,480,220,50);
		button_get_bill.addActionListener(new ActionListener() {
       
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (tableModel.getRowCount() !=0) { 
					
					tableModel.setColumnCount(0); //delete columns for the new ones!
					tableModel.setRowCount(0); //delete rows for the new ones!
					
					tableModel.addColumn("ястие/напитка");
					tableModel.addColumn("Брой");
					tableModel.addColumn("Сума");
					
					SQL_Handler.billOrder(tableModel);
					
				}
				
			}
		});
		
		button_final.setText("Приключване");
		button_final.setBounds(45,540,220,50);
		button_final.addActionListener(new ActionListener() {
       
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tableModel.setRowCount(0); //delete rows!
				SQL_Handler.tablerelease();
				
			}
		});
		
		tableModel = new DefaultTableModel();
		table = new JTable(tableModel);
		tableModel.addColumn("ID");
		tableModel.addColumn("Сметка");
		tableModel.addColumn("МАСА");
		tableModel.addColumn("Сервитьор");
		tableModel.addColumn("ястие/напитка");
		tableModel.addColumn("Дата");
		
		button_music.setBounds(115,590, 70, 70);
		button_music.setIcon(img_music1);
		button_music.setBorderPainted(false);
		button_music.setContentAreaFilled(false);
		button_music.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button_music.addMouseListener(click_musicicon);
		
		SQL_Handler.showOrder(tableModel);
		
		panel.setBackground(Color.orange);
		panel.setLayout(null);
		panel.setBounds(483,100,300,715);
		panel.add(label_orderid);
		panel.add(orderid);
		panel.add(label_mealname);
		panel.add(mealname_combo);
		panel.add(button_update);
		panel.add(button_delete);
		panel.add(button_get_bill);
		panel.add(button_final);
		panel.add(button_music);
		
		tableframe = new JFrame();
		tableframe.setTitle("Table");
		tableframe.setSize(800,800);
		tableframe.setResizable(false);
		tableframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    
		tableframe.setLayout(null);
		scroll = new JScrollPane(table);
		scroll.setBounds(0, 0, 800, 120);
		tableframe.add(scroll);
		tableframe.add(panel);
		tableframe.revalidate(); tableframe.repaint();
		tableframe.setVisible(true);
		
		}
		
		else {
			JOptionPane.showMessageDialog(RestaurantMain.frame, "Масата е заета!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	public static void order() {
		
		int stored_IDbill = 0000;
		
		SQL_Handler.addOrder(id_table, stored_eid, stored_mid, datetime, billName, stored_IDbill);
		
		DrinkMenu.item = ""; //Clear the DrinkMenu item
		FoodMenu.item = ""; //Clear the FoodMenu item
	}
	
	public static void addtable() {

		final String SAVE_DIR = "Restaurant_tables";
		int number = 0;
		String strnumber; //The table name
		File imgfile = new File("table.png");
		File dir = new File(SAVE_DIR);
		int dotIndex;
		String fileNameToSearch;
		String status = "Свободна";
		
		if (dir.exists()) {
			for (File file : dir.listFiles() ) {
				
				dotIndex = file.getName().lastIndexOf(".");
				fileNameToSearch = file.getName().substring(0, dotIndex-1);
				int e = Integer.parseInt(fileNameToSearch);
				
				if (e > number) {
					number = e;
				}
				
			}
			
		}
			number +=1;
			strnumber = String.valueOf(number);
			try {
				
				BufferedImage img = ImageIO.read(imgfile);
				newfile = new File(SAVE_DIR, strnumber + " .png");
				ImageIO.write(img, "png", newfile); //save the img in the new file!
				
				SQL_Handler.sql_addTable(strnumber, status);
				
			} catch (IOException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(tableframe, "File грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
	}
	
	public static void deltable() {
		
		final String SAVE_DIR = "Restaurant_tables";
		File dir = new File(SAVE_DIR);
		int number = 0;
		int filenumber = 0;
		
		File lastFile = new File("");
		
		for (File file : dir.listFiles() ) {
			
			int dotIndex = file.getName().lastIndexOf(".");
			String fileName = file.getName().substring(0, dotIndex-1);
			filenumber = Integer.parseInt(fileName);
			
			if (filenumber > number) {
				number = filenumber;
				lastFile = file;
			}
			
		}
		
		lastFile.delete(); 
		RestaurantMain.Homepanel.remove(Role.scroll);
		
		SQL_Handler.sql_deleteTable(number);
		
		Role.displaytables();
		Role.imagePanel.revalidate();
		Role.imagePanel.repaint();
		Role.scroll.revalidate();
		Role.scroll.repaint();
		
	}
	
	MouseListener click_musicicon = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			button_music.setIcon(img_music2);
			// Discount % for the orchestra!
			String response = JOptionPane.showInputDialog("НАМАЛЕНИЕ с:");
			
			int percentage = Integer.parseInt(response);// Converting String to int!
			
			if (tableModel.getRowCount() !=0) {
				
				tableModel.setColumnCount(0); //delete columns for the new ones!
				tableModel.setRowCount(0); //delete rows for the new ones!
				
				tableModel.addColumn("ястие/напитка");
				tableModel.addColumn("Брой");
				tableModel.addColumn("Сума");
				
				SQL_Handler.music_discount(tableModel, percentage);
				
				}

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
			button_music.setIcon(img_music2);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			button_music.setIcon(img_music1);
			
		}
		
	};
	
}
