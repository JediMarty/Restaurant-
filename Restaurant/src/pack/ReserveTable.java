package pack;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


class ReserveTable {
	
	static JFrame tableframe;
	
	private static String idbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String user = "marti";
	private static String password = "marti";
	
	static File newfile;
	
	private static String sql;
	private static String sqlQ;
	private static String sql_order;
	private static PreparedStatement statement;
	private static ResultSet result;
	
	private static int stored_mid;
	private static int stored_eid;
	private static int id;
	private static String stored_ordersid;
	private static String stored_tname;
	private static String stored_firstname;
	private static String stored_item;
	private static String stored_time;
	
	static LocalDateTime datetime = LocalDateTime.now();
	
	private static JLabel label_orderid = new JLabel();
	private static JLabel label_tablename = new JLabel();
	private static JLabel label_mealname = new JLabel();
	private static JTextField orderid = new JTextField();
	private static JComboBox<String> tablename_combo = new JComboBox<>();
	private static JComboBox<String> mealname_combo = new JComboBox<>();
	private static JButton button_update = new JButton();
	private static JButton button_delete = new JButton();
	
	private static Map<String, Integer> dataMap1 = new HashMap<>();
	private static Map<String, Integer> dataMap2 = new HashMap<>();
	
	public static void table() {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT * FROM RTABLES";
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery(); 
			while(result.next()) {
				
				id = result.getInt("TID");
				tablename_combo.addItem(result.getString("TNAME")); //add all tables!
				dataMap1.put(result.getString("TNAME"),id);
				
			}
			
			sqlQ = "SELECT * FROM MENU_ITEMS";
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery(); 
			while(result.next()) {
				
				id = result.getInt("MID");
				mealname_combo.addItem(result.getString("ITEM")); //add all items-food/drinks!
				dataMap2.put(result.getString("ITEM"),id);
				
			}
			
			
			
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		label_orderid.setText("ID");
		label_orderid.setFont(new Font("Calibri", Font.BOLD, 30));
		label_orderid.setBounds(100, 105, 100,30);
		orderid.setBounds(70, 130, 50, 50); 
		orderid.setFont(RestaurantMain.font);
		
		label_tablename.setText("МАСА");
		label_tablename.setFont(new Font("Calibri", Font.BOLD, 30));
		label_tablename.setBounds(150, 195, 100,30);
		tablename_combo.setBounds(70, 220, 220, 50); 
		tablename_combo.setFont(RestaurantMain.font);
		
		label_mealname.setText("Поръчано");
		label_mealname.setFont(new Font("Calibri", Font.BOLD, 30));
		label_mealname.setBounds(120, 285, 150,30);
		mealname_combo.setBounds(70, 320, 220, 50); 
		mealname_combo.setFont(RestaurantMain.font);
		
		button_update = new JButton();
		button_update.setText("Промени");
		button_update.setBounds(70,390,220,50);
		button_update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					Connection conn = DriverManager.getConnection(idbcURL,user,password);
				
					int id_order = Integer.parseInt(orderid.getText());
				
					sql_order = "{CALL UP_ORDERS(?,?,?)}"; //The update procedure
					
					String selectedName = tablename_combo.getSelectedItem().toString();
					int id_table = dataMap1.get(selectedName);
					String selecteditem = mealname_combo.getSelectedItem().toString();
					int id_item = dataMap2.get(selecteditem);
				
					statement = conn.prepareStatement(sql_order);
					statement.setInt(1, id_order);
			        statement.setInt(2, id_table);
				    statement.setInt(3, id_item);
				    result = statement.executeQuery();
					
				    conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
	    
		button_delete = new JButton();
		button_delete.setText("Изтрии");
		button_delete.setBounds(70,450,220,50);
		button_delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		tableframe = new JFrame();
		tableframe.setTitle("Table");
		tableframe.setSize(800,800);
		tableframe.setResizable(false);
		tableframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    
		tableframe.setLayout(null);
		tableframe.add(label_orderid);
		tableframe.add(orderid);
		tableframe.add(label_tablename);
		tableframe.add(tablename_combo);
		tableframe.add(label_mealname);
		tableframe.add(mealname_combo);
		tableframe.add(button_update);
		tableframe.add(button_delete);
		tableframe.setVisible(true);
	}
	
	public static void order() {
		
		String billstatus = "Отворена";
		int stored_IDbill = 0000;
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		/*
			sqlQ = "SELECT o.bill, t.TNAME, e.FIRSTNAME, m.ITEM , o.TIMEORDERING "
					+ "FROM ORDERS o "
					+ "JOIN RTABLES t "
					+ "ON o.TID = t.TID "
					+ "JOIN EMPLOYEES e "
					+ "ON o.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON o.MID = m.MID";
			
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery(); 
			
			while (result.next()) 
			{
				String stored_ordersid = result.getString("ORDERSID");
				String stored_tname = result.getString("TNAME");
				String stored_firstname = result.getString("FIRSTNAME");
				String stored_item = result.getString("ITEM");
				String stored_time = result.getString("TIMEORDERING");
				
			}
			*/
			
			sqlQ = "SELECT ID, Firstname "
					+ "FROM EMPLOYEES "
		            + "WHERE Firstname = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, RestaurantMain.username.getText());
			result = statement.executeQuery();
		
			if (result.next()) {
				
				String str_eid = result.getString("ID");
				stored_eid = Integer.parseInt(str_eid);
				
			}
			
			sqlQ = "SELECT MID, ITEM "
					+ "FROM MENU_ITEMS "
		            + "WHERE ITEM = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, FoodMenu.item);
			result = statement.executeQuery();
			
			if (result.next()) {
				
				String str_mid = result.getString("MID");
				stored_mid = Integer.parseInt(str_mid);
				
			}
			System.out.println(stored_mid);
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatted_datetime = datetime.format(format);
			
			Random r = new Random();
			String randomNumber = String.format("%04d", r.nextInt(10001));
			int bill = Integer.parseInt(randomNumber);
			
			sqlQ = "SELECT BILLID, BILL "
					+ "FROM TABLE_BILL "
		            + "WHERE BILL = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setInt(1, bill);
			result = statement.executeQuery();
			
			while (result.next()) {
				randomNumber = String.format("%04d", r.nextInt(10001));
				bill = Integer.parseInt(randomNumber);
				
				statement = conn.prepareStatement(sqlQ);
				result = statement.executeQuery();
				
			}
			
			String sql_table_bill = "{CALL P_TABLE_BILL(?,?)}"; //The insert procedure
			
			statement = conn.prepareStatement(sql_table_bill);
			statement.setInt(1, bill);
			statement.setString(2, billstatus);
			result = statement.executeQuery();
			
			statement = conn.prepareStatement(sqlQ); // Execute the same query(query before the insert procedure!) to get the bill ID!
			statement.setInt(1, bill);
			result = statement.executeQuery();
			
			if (result.next()) {
				String str_IDBILL = result.getString("BILLID");
				stored_IDbill = Integer.parseInt(str_IDBILL);
			}
			
			sql_order = "{CALL P_ORDERS(?,?,?,?,?)}"; //The insert procedure
			
			statement = conn.prepareStatement(sql_order);
			statement.setInt(1, Role.id_table);
			statement.setInt(2, stored_eid);
			statement.setInt(3, stored_mid);
			statement.setTimestamp(4, Timestamp.valueOf(formatted_datetime)); // TO_CHAR (o.TIMEORDERING, 'YYYY-MM-DD HH24:MI:SS')
			statement.setInt(5, stored_IDbill);
			result = statement.executeQuery(); 
			
			conn.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void addtable() {
//!!!!!! POPRAVI STRNUMBER!!!!!!!!!!!!!!!!!!!
		final String SAVE_DIR = "Restaurant_tables";
		int number;
		String strnumber = "1"; //The table number
		File imgfile = new File("table.png");
		File dir = new File(SAVE_DIR);
		int dotIndex;
		String fileNameToSearch;
		String status = "Свободна";
		
		if (dir.exists()) {
			for (File file : dir.listFiles() ) {
				
				dotIndex = file.getName().lastIndexOf(".");
				fileNameToSearch = file.getName().substring(0, dotIndex-1);
				
				if (fileNameToSearch.equals(strnumber)) {
					
					strnumber = fileNameToSearch;
					number = Integer.parseInt(strnumber);
					number +=1;
					strnumber = String.valueOf(number);
					}
				}
			}
			
			try {
				
				BufferedImage img = ImageIO.read(imgfile);
				newfile = new File(SAVE_DIR, strnumber + " .png");
				ImageIO.write(img, "png", newfile); //save the img in the new file!
				
				try {
					Connection conn = DriverManager.getConnection(idbcURL,user,password);
					
					sql = "{CALL P_RTABLES(?,?)}"; //The insert procedure
					
					statement = conn.prepareStatement(sql);
					statement.setString(1, strnumber);
					statement.setString(2, status);
					
					result = statement.executeQuery(); 
				
					conn.close();
					
				} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}

