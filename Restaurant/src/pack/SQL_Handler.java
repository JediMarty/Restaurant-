package pack;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class SQL_Handler {

	private static String idbcURL = "";
	private static String user = "";
	private static String password = "";
	
	private static PreparedStatement statement;
	private static ResultSet result;
	
	static int flag;
	
	private static String sqlQ;
	private static String sql_order;
	private static String sql_employees;
	private static String sql_MENU_ITEMS;
	private static String sql_order_arch;
	
	private static double discountprice;
	private static Map<String, Double> discountedprice = new HashMap<>();
	
	static Boolean isloaded = false;
	
	private static DateTimeFormatter format;
	private static LocalDateTime datetime;
	
	static void loginsql(String resultHashedPass) {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password); //The connection
			
			sqlQ = "SELECT e.USERNAME, p.Pos_NAME "
					+ "FROM EMPLOYEES e "
					+ "JOIN positions p ON e.pos_id = p.pos_id "
					+ "WHERE USERNAME = ? AND Password = ? ";
			
			PreparedStatement statement = conn.prepareStatement(sqlQ);
			statement.setString(1, RestaurantMain.username.getText());
			statement.setString(2, resultHashedPass);
			
			ResultSet result = statement.executeQuery(); 
			
			if (result.next()) //Results crawling
			{
				System.out.print("Здрасти!"); 
				
				
			}
			
			else { // The data sent is invalid!
				System.out.print("Няма такъв потребител!");	
				RestaurantMain.username.setText("");
				RestaurantMain.password.setText("");
			}
			
			conn.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		
		}
		
	}
	
	static String loginrole() {
		
		String storedpostion = "";
			
			try {
				Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
				sqlQ = "SELECT e.USERNAME, p.Pos_NAME "
						+ "FROM EMPLOYEES e "
						+ "JOIN positions p ON e.pos_id = p.pos_id "
						+ "WHERE USERNAME = ? AND Password = ? ";
				
				statement = conn.prepareStatement(sqlQ);
				statement.setString(1, RestaurantMain.username.getText());
				statement.setString(2, Login.resultHashedPass);
				
				result = statement.executeQuery();
			
				if (result.next()) {
					
					storedpostion = result.getString("Pos_NAME");
					
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
			
			}
		
		return storedpostion;
		
	}
	
	
	static void addEmployee(JTextField name, JTextField lastname, JTextField egn, int selectedid, JTextField usern) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sql_employees = "{CALL P_EMPLOYEES(?, ?, ?, ?, ?, ?)}"; //The Registration/insert procedure
				
			statement = conn.prepareStatement(sql_employees);
			statement.setString(1, name.getText());
			statement.setString(2, lastname.getText());
			statement.setString(3, egn.getText());
			statement.setString(4, Role.hashedpassword());
			statement.setInt(5, selectedid);
			statement.setString(6, usern.getText());	
			
			result = statement.executeQuery(); 
			
			
			conn.close();
			
		} catch (SQLException er) {
			// TODO Auto-generated catch block
		
		} 
		
	}
	
	static void positions_combo() {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			String sqlQ = "SELECT pos_id, Pos_NAME FROM positions";
			
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery();
		
		
		while(result.next()) {
			Role.combo.addItem(result.getString("pos_id") + " " + result.getString("Pos_NAME")); //add all positions!
			
		}
		
		conn.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		
			JOptionPane.showMessageDialog(RestaurantMain.frame, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void updateEmployee(int id_emplo, JTextField name, JTextField lastname, JTextField egn, int selectedid, JTextField usern) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sql_employees = "{CALL UP_EMPLOYEES(?, ?, ?, ?, ?, ?, ?)}"; //The update procedure
				
			statement = conn.prepareStatement(sql_employees);
			statement.setInt(1, id_emplo);
			statement.setString(2, name.getText());
			statement.setString(3, lastname.getText());
			statement.setString(4, egn.getText());
			statement.setString(5, Role.hashedpassword());
			statement.setInt(6, selectedid);
			statement.setString(7, usern.getText());	
			
			result = statement.executeQuery(); 
			
			conn.close();
			
		} catch (SQLException er) {
			// TODO Auto-generated catch block
		
		} 
		
	}
	
	static void deleteEmployee(int id_emplo) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sql_employees = "{CALL D_EMPLOYEES(?)}"; //The delete procedure
				
			statement = conn.prepareStatement(sql_employees);
			statement.setInt(1, id_emplo);
			
		    result = statement.executeQuery(); 
		    
		    conn.close();
			
		} catch (SQLException er) {
			// TODO Auto-generated catch block
		
		} 
		
	}
	
	static void addItem(String filename, String money) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sql_MENU_ITEMS = "{CALL P_MENU_ITEMS(?, ?)}"; //The insert procedure
			
			statement = conn.prepareStatement(sql_MENU_ITEMS);
			statement.setString(1, filename);
			statement.setString(2, money);
			result = statement.executeQuery(); 
			
			conn.close();
			
		} catch (SQLException er) {
			// TODO Auto-generated catch block
		
		} 
		
	}
	
	static void deleteItem(String fileNameToSearch) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT * FROM MENU_ITEMS WHERE item = ?";
		    
		    sql_MENU_ITEMS = "{CALL D_MENU_ITEMS(?)}"; //The delete procedure
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, fileNameToSearch);
			result = statement.executeQuery(); 
	
			if (result.next()) //Results crawling
			{
				int storedpostion = result.getInt("mID"); //ID where is the item who needs to be deleted!
				
				statement = conn.prepareStatement(sql_MENU_ITEMS);
				statement.setInt(1, storedpostion);
				result = statement.executeQuery(); 
				
			}
			
			conn.close();
			
		} catch (SQLException er) {
			// TODO Auto-generated catch block
	
		} 
		
	}
	
	
	static void tablerelease() {
		
		try {
			Connection 	conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT TID FROM RTABLES WHERE TNAME = ?"; // Select table name to find ID of table!
			   
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
			result = statement.executeQuery();
			   
			int id_table = 0;
			
			if (result.next()) {
				id_table = result.getInt("TID");
			   }
			   
			sql_order = "DELETE FROM ORDERS WHERE TID = ? "; // DELETE ALL RECORDS OF CURRENT ORDER!
			   
			statement = conn.prepareStatement(sql_order);
			statement.setInt(1, id_table);
			result = statement.executeQuery();
			   
		    sqlQ = "{CALL PSTATUSA(?)}"; // The procedure for status of table = AVAILABLE!
			   
			statement = conn.prepareStatement(sqlQ);
		    statement.setInt(1, id_table);
		    result = statement.executeQuery();
			
		    sqlQ = "{CALL D_RESERVED_TABLE(?)}"; // The procedure for deleting the reservation!
			   
			statement = conn.prepareStatement(sqlQ);
		    statement.setInt(1, id_table);
		    result = statement.executeQuery();
		    
			conn.close();
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void order_menu_combo() {
		
		int id;
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT * FROM MENU_ITEMS";
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery(); 
			
			while(result.next()) {
				
				id = result.getInt("MID");
				ReserveTable.mealname_combo.addItem(result.getString("ITEM")); //add all items-food/drinks!
				ReserveTable.dataMap.put(result.getString("ITEM"),id);
				
			}
		
		conn.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	
	static void billOrder() {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT m.item AS Food, COUNT(m.item), SUM(m.price) AS Price "
				+ "FROM orders o "
				+ "JOIN menu_items m ON o.mid = m.mid "
				+ "JOIN rtables t ON o.tid = t.tid "
				+ "WHERE t.TNAME = ? "
				+ "GROUP BY m.item "
				+ "UNION ALL "
			    + "SELECT 'Обща Сума', COUNT(m.item), SUM(m.price) AS Price "
				+ "FROM orders o "
				+ "JOIN menu_items m ON o.mid = m.mid "
				+ "JOIN rtables t ON o.tid = t.tid "
				+ "WHERE t.TNAME = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
			statement.setString(2, Role.lastClicktable);
			result = statement.executeQuery();
	    
			ReserveTable.txtarea.setText("                                            СМЕТКА \n"
				                       + "---------------------------------------------------------------------\n"
				                       + "Сервитьор: " + RestaurantMain.username.getText() + "\n"
				                       + "---------------------------------------------------------------------\n"
				                       + "Поръчано: \n"
				                       + "---------------------------------------------------------------------\n");
				    
			LocalDateTime now = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
			String formattedDateTime = now.format(formatter);
			
			double totalbg = 0.0;
			double totaleuro = 0.0;
			double rate_euro = 1.9558;
			double euro = 0.0;
			
			flag = 0; // flag for the bill, if no things are purchased will not make a bill!
			
			while (result.next()) {
				String item = result.getString("food");
				String str_price = result.getString("price");
				double price = Double.valueOf(str_price);
				int count = result.getInt("COUNT(m.item)");
				
				if (ReserveTable.response != null && !ReserveTable.response.equals("")) {
					
					if (discountedprice.containsKey(item)) {
						
						price = discountedprice.get(item);
					
					}
				}
				
				euro = price / rate_euro;
				
				if (item.equals("Обща Сума")) {
					
					if (ReserveTable.response != null && ReserveTable.response != "") {
						ReserveTable.txtarea.append("с намаление("+ ReserveTable.response +"%) \n");
						price = discountedprice.get("Обща Сума (с намаление%)");
						
						totalbg = price;
						totaleuro = price / rate_euro;
					}
					
				    ReserveTable.txtarea.append("---------------------------------------------------------------------\n");
					ReserveTable.txtarea.append("Количество: " + count + "\n");
					ReserveTable.txtarea.append("Обща сума: " +  String.format("%.2f",totalbg) + " лв / €" + String.format("%.2f",totaleuro) + "\n");
					ReserveTable.txtarea.append("---------------------------------------------------------------------\n");
					ReserveTable.txtarea.append("Маса " + Role.lastClicktable + " " + formattedDateTime);
					continue;
					}
				
				ReserveTable.txtarea.append(item + " количество " +  count + " цена " + String.format("%.2f",price) + " лв / €" + String.format("%.2f",euro) + "\n");
				
				totalbg += price;
				totaleuro += euro;
				flag = 1;
			}
			
			conn.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
		} catch (NullPointerException e1) {
			// TODO Auto-generated catch block
			
		
	}
		
	}
	
	static void addOrder(int id_table, int stored_eid, int stored_mid) {
		
		int orderID = 0;
		String randomNumber = "";
		int billCount = 0;
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT TID, TNAME "
					+ "FROM RTABLES "
		            + "WHERE TNAME = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
			result = statement.executeQuery();
			
			if (result.next()) {
				id_table = result.getInt("TID");
				
			}
			
			sqlQ = "{CALL PSTATUSB(?)}"; // The procedure for status of table = BUSY!
			
			statement = conn.prepareStatement(sqlQ);
			statement.setInt(1, id_table);
			result = statement.executeQuery();
			
			sqlQ = "SELECT ID, USERNAME "
					+ "FROM EMPLOYEES "
		            + "WHERE USERNAME = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, RestaurantMain.username.getText());
			result = statement.executeQuery();
		
			if (result.next()) {
				stored_eid = result.getInt("ID");
				
			}
			
			sqlQ = "SELECT MID, ITEM "
					+ "FROM MENU_ITEMS "
		            + "WHERE ITEM = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, AbstractMenu.item); 
			result = statement.executeQuery();
			
			if (result.next()) {
				stored_mid = result.getInt("MID");
				
			}
	
			format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			datetime= LocalDateTime.now();
			String formatted_datetime = datetime.format(format);
			
			sqlQ = "SELECT t.TNAME, o.bill "
					+ "FROM ORDERS o "
					+ "JOIN rtables t ON o.tid = t.tid "
					+ "WHERE t.TNAME = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
			result = statement.executeQuery();
			
			if (result.next()) {
				randomNumber = result.getString("bill");
				
			}
			
			else {
			Random r = new Random();
			randomNumber = String.format("%04d", r.nextInt(10001)); //Bill code with four numbers!
			
			sqlQ = "SELECT BILL "
					+ "FROM ORDERS "
		            + "WHERE BILL = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, randomNumber);
			result = statement.executeQuery();
			
			while (result.next()) {
				randomNumber = String.format("%04d", r.nextInt(10001)); //Bill code with four numbers!
			
				statement = conn.prepareStatement(sqlQ);
				result = statement.executeQuery();
				
			}
			
			sqlQ = "SELECT COUNT(BILL) "
					+ "FROM ORDERS_ARCHIVE";
			
			statement = conn.prepareStatement(sqlQ);
		    result = statement.executeQuery();
		
		    if (result.next()) {
		    	billCount = result.getInt("COUNT(BILL)");
		    }
		    
			if (billCount==9999) {
				JOptionPane.showMessageDialog(ReserveTable.tableframe, "Достигнали сте 9999 сметка, нужно е да изтриете архива, за да може програмата да работи правилно!", "Предупреждение!", JOptionPane.OK_OPTION);
				ReserveTable.tableframe.dispose();
				new RestaurantMain();
			}
			
			}
			
			sql_order = "{CALL P_ORDERS(?,?,?,?,?)}"; //The insert procedure
			
			statement = conn.prepareStatement(sql_order);
			statement.setInt(1, id_table);  
			statement.setInt(2, stored_eid); 
			statement.setInt(3, stored_mid);  
		    statement.setTimestamp(4, Timestamp.valueOf(formatted_datetime)); 
			statement.setString(5, randomNumber); 
			result = statement.executeQuery(); 
			
            sql_order = "SELECT POS_ORDERS.NEXTVAL FROM ORDERS"; //To get ID of order
			
			statement = conn.prepareStatement(sql_order);
			result = statement.executeQuery(); 
			
			if (result.next()) {
				orderID = result.getInt(1) - 1; //SEQUENCE return next ID(next - 1 will get the current ID!)
			}
			
			SQL_Handler.addReservedTable(id_table,stored_eid);
			
			sql_order_arch = "{CALL P_ORDERS_ARCHIVE(?,?,?,?,?,?)}"; //The insert procedure of ORDERS_ARCHIVE!
			
			statement = conn.prepareStatement(sql_order_arch);
			statement.setInt(1, orderID);  
			statement.setInt(2, id_table);  
			statement.setInt(3, stored_eid); 
			statement.setInt(4, stored_mid);  
		    statement.setTimestamp(5, Timestamp.valueOf(formatted_datetime)); // TO_CHAR (o.TIMEORDERING, 'YYYY-MM-DD HH24:MI:SS')
			statement.setString(6, randomNumber); 
			result = statement.executeQuery(); 
			
			conn.close();
		
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void sql_addTable(String strnumber, String status) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "{CALL P_RTABLES(?,?)}"; //The insert procedure
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, strnumber);
			statement.setString(2, status);
			
			result = statement.executeQuery(); 
		
			conn.close();
			
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
	}
		
	}
	
	static void sql_deleteTable(int number) {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			String sql = "SELECT TID, TNAME FROM RTABLES "
					+ "WHERE TNAME = ? ";
			
			statement = conn.prepareStatement(sql);
			statement.setInt(1, number);
	        result = statement.executeQuery();
			
	        
	        if (result.next()) {
	        	String str_idTable = result.getString("TID");
	        	int idTable = Integer.parseInt(str_idTable);
	        	 
	            String sql_RTABLES = "{CALL D_RTABLES(?)}"; //The delete procedure
					
				statement = conn.prepareStatement(sql_RTABLES);
			    statement.setInt(1, idTable);
			    result = statement.executeQuery();
	        }
	        
	        
			conn.close();
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "File грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void music_discount(DefaultTableModel tableModel, int percentage) {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT m.item AS Food, COUNT(m.item), SUM(m.price) AS Price "
					+ "FROM orders o "
					+ "JOIN menu_items m ON o.mid = m.mid "
					+ "JOIN rtables t ON o.tid = t.tid "
					+ "WHERE t.TNAME = ? "
					+ "GROUP BY m.item "
					+ "UNION ALL "
				    + "SELECT 'Обща Сума (с намаление%)' AS Food, COUNT(m.item), SUM(m.price) * ( 1 - ( ? / 100)) AS Price "
					+ "FROM orders o "
					+ "JOIN menu_items m ON o.mid = m.mid "
					+ "JOIN rtables t ON o.tid = t.tid "
					+ "WHERE t.TNAME = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
			statement.setInt(2, percentage);
			statement.setString(3, Role.lastClicktable);
		    result = statement.executeQuery();
		    
		    while (result.next()) {
		    	
		    	String item = result.getString("food");
		    	String str_price = result.getString("price");
		    	discountprice = Double.valueOf(str_price);
		    	int count = result.getInt("COUNT(m.item)");
		    	
		    	discountedprice.put(item, discountprice);
		    	
		    	tableModel.addRow(new Object[] {item, count, String.format("%.2f",discountprice)});
		    }
		   
		   conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
		}
		
	}
	
	static String checkTableAvailable1() {
		
		String status = "";
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sqlQ = "SELECT STATUS FROM RTABLES "
					+ "WHERE TNAME = ? "; 
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
	        result = statement.executeQuery();
	        
	        if (result.next()) {
	        	status = result.getString("STATUS");
	        	
	        }
			
	        conn.close();
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		return status;
		
	}
	
	static String checkTableAvailable2() {
		
		String pass = "";
	
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sqlQ = "SELECT t.TNAME, t.status, e.password "
					+ "FROM reserved_table "
					+ "JOIN RTABLES t ON reserved_table.tid = t.tid "
					+ "JOIN employees e ON reserved_table.ID = e.ID "
					+ "WHERE t.TNAME = ? "; 
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
	        result = statement.executeQuery();
	        
	        if (result.next()) {
	        	pass = result.getString("password");
	        	
	        }
			
	        conn.close();
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		return pass;
		
	}
	
	static Boolean uploadprotection() {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			String sql = "SELECT e.USERNAME, p.Pos_NAME "
					+ "FROM EMPLOYEES e "
					+ "JOIN positions p ON e.pos_id = p.pos_id "
					+ "WHERE USERNAME = ? AND Password = ? ";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, RestaurantMain.username.getText());
		    statement.setString(2, Login.resultHashedPass);
			result = statement.executeQuery();
	        
			if (result.next()) {
	        	return true;
	        	
	        }
			
	       
	        
	        conn.close();
		} catch (SQLException e) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		return false;		
	}
	
	static void showOrder(DefaultTableModel tableModel) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sqlQ = "SELECT o.ORDERSID ,o.bill, t.TNAME, e.USERNAME, m.ITEM , o.TIMEORDERING "
					+ "FROM ORDERS o "
					+ "JOIN RTABLES t "
					+ "ON o.TID = t.TID "
					+ "JOIN EMPLOYEES e "
					+ "ON o.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON o.MID = m.MID "
					+ "WHERE t.TNAME = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
			result = statement.executeQuery();
			
			while (result.next()) {
				int num = result.getInt("ORDERSID");
		        String bill = result.getString("bill");
		    	String rtable = result.getString("TNAME");
		    	String waiter = result.getString("USERNAME");
		    	String item = result.getString("ITEM");
		    	String time = result.getString("TIMEORDERING");
		    	
		    	tableModel.addRow(new Object[] {num, bill, rtable, waiter, item, time});
		    }
		
			conn.close();
			
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void showArchiveOrders(DefaultTableModel tableModel) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			if (!Role.archive_text.getText().equals("")) {
			
			sqlQ = "SELECT a.bill, t.TNAME, e.USERNAME, m.ITEM , m.price ,a.TIMEORDERING "
					+ "FROM ORDERS_ARCHIVE a "
					+ "JOIN RTABLES t "
					+ "ON a.TID = t.TID "
					+ "JOIN EMPLOYEES e "
					+ "ON a.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON a.MID = m.MID "
					+ "WHERE TO_CHAR(t.TNAME) = ? OR TO_CHAR(a.TIMEORDERING ,'YYYY-MM-DD') = ? OR TO_CHAR(a.bill) = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.archive_text.getText());
			statement.setString(2, Role.archive_text.getText()); 
			statement.setString(3, Role.archive_text.getText());
		    result = statement.executeQuery();
			
			}
			
			else if (!Role.archive_textdate1.getText().equals("") && !Role.archive_textdate2.getText().equals("")) {
				
				sqlQ = "SELECT a.bill, t.TNAME, e.USERNAME, m.ITEM , m.price ,a.TIMEORDERING "
						+ "FROM ORDERS_ARCHIVE a "
						+ "JOIN RTABLES t "
						+ "ON a.TID = t.TID "
						+ "JOIN EMPLOYEES e "
						+ "ON a.ID = e.ID "
						+ "JOIN MENU_ITEMS m "
						+ "ON a.MID = m.MID "
						+ "WHERE TRUNC(a.TIMEORDERING) BETWEEN TO_DATE(? ,'YYYY-MM-DD') AND TO_DATE(? ,'YYYY-MM-DD') "
						+ "ORDER BY a.TIMEORDERING ASC ";
				
				statement = conn.prepareStatement(sqlQ);
			    statement.setString(1, Role.archive_textdate1.getText()); 
				statement.setString(2, Role.archive_textdate2.getText());
			    result = statement.executeQuery();
			}
			
			else {
				
				sqlQ = "SELECT a.bill, t.TNAME, e.USERNAME, m.ITEM , m.price ,a.TIMEORDERING "
						+ "FROM ORDERS_ARCHIVE a "
						+ "JOIN RTABLES t "
						+ "ON a.TID = t.TID "
						+ "JOIN EMPLOYEES e "
						+ "ON a.ID = e.ID "
						+ "JOIN MENU_ITEMS m "
						+ "ON a.MID = m.MID "
						+ "ORDER BY a.TIMEORDERING ASC";
						
				statement = conn.prepareStatement(sqlQ);
				result = statement.executeQuery();
				
			}
			
		    while (result.next()) {
		    	
		    	String bill = result.getString("bill");
		    	String rtable = result.getString("TNAME");
		    	String waiter = result.getString("USERNAME");
		    	String item = result.getString("ITEM");
		    	double prices = result.getDouble("price"); 
		    	String time = result.getString("TIMEORDERING");
		    	
		    	tableModel.addRow(new Object[] {bill, rtable, waiter, item, String.format("%.2f",prices), time});
		    }
		
			conn.close();
			
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(RestaurantMain.frame, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void showArchiveOrders_emplo(DefaultTableModel tableModel) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			if (Role.archive_text.getText().equals("")) {
			
			sqlQ = "SELECT o.bill, t.TNAME, e.USERNAME, m.ITEM , m.price ,o.TIMEORDERING "
					+ "FROM ORDERS o "
					+ "JOIN RTABLES t "
					+ "ON o.TID = t.TID "
					+ "JOIN EMPLOYEES e "
					+ "ON o.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON o.MID = m.MID "
					+ "WHERE e.USERNAME = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, RestaurantMain.username.getText());
			result = statement.executeQuery();
		    
			}
			
			else {
				
				sqlQ = "SELECT a.bill, t.TNAME, e.USERNAME, m.ITEM , m.price ,a.TIMEORDERING "
						+ "FROM ORDERS_ARCHIVE a "
						+ "JOIN RTABLES t "
						+ "ON a.TID = t.TID "
						+ "JOIN EMPLOYEES e "
						+ "ON a.ID = e.ID "
						+ "JOIN MENU_ITEMS m "
						+ "ON a.MID = m.MID "
						+ "WHERE e.USERNAME = ? AND a.bill = ? ";
				
				statement = conn.prepareStatement(sqlQ);
			    statement.setString(1, RestaurantMain.username.getText()); 
				statement.setString(2, Role.archive_text.getText());
				result = statement.executeQuery();
				
			}
			
			while (result.next()) {
				
				String bill = result.getString("bill");
		    	String rtable = result.getString("TNAME");
		    	String waiter = result.getString("USERNAME");
		    	String item = result.getString("ITEM");
		    	double prices = result.getDouble("price"); 
		    	String time = result.getString("TIMEORDERING");
		    	
		    	tableModel.addRow(new Object[] {bill, rtable, waiter, item, String.format("%.2f",prices), time});
		    }
		
			conn.close();
			
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(RestaurantMain.frame, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		
	}
	
	static void showTotalsum_emplos1(DefaultTableModel tableModel) {
		
		datetime = LocalDateTime.now();
		int currentMonth = datetime.getMonthValue();
		String month = String.format("%02d", currentMonth);
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT e.USERNAME, SUM(m.price) AS Общо "
					+ "FROM ORDERS_ARCHIVE a "
				    + "JOIN EMPLOYEES e "
					+ "ON a.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON a.MID = m.MID "
					+ "WHERE TO_CHAR(a.TIMEORDERING, 'MM') = ? "
					+ "GROUP BY e.USERNAME";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, month);
			result = statement.executeQuery();
			
			while (result.next()) {
				
				String waiter = result.getString("USERNAME");
		    	String total = result.getString("Общо");
		  
		    	tableModel.addRow(new Object[] {waiter, total});
		    }
		
			conn.close();
			
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(RestaurantMain.frame, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void showTotalsum_emplos2(DefaultTableModel tableModel) {
		
		datetime = LocalDateTime.now();
		format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatted_datetime = datetime.format(format);
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT e.USERNAME, SUM(m.price) AS Общо "
					+ "FROM ORDERS_ARCHIVE a "
				    + "JOIN EMPLOYEES e "
					+ "ON a.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON a.MID = m.MID "
					+ "WHERE TRUNC(a.TIMEORDERING) = TO_DATE(? ,'YYYY-MM-DD') "
					+ "GROUP BY e.USERNAME";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, formatted_datetime);
			result = statement.executeQuery();
			
			while (result.next()) {
				
				String waiter = result.getString("USERNAME");
		    	String total = result.getString("Общо");
		  
		    	tableModel.addRow(new Object[] {waiter, total});
		    }
		
			conn.close();
			
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(RestaurantMain.frame, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		
	}
	
	static void deleteArchive() {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT o.bill AS СМЕТКА, t.TNAME AS МАСА, e.USERNAME AS Сервитьор, m.ITEM AS поръчано, m.price AS ЦЕНА, o.TIMEORDERING AS ДАТА "
					+ "FROM ORDERS o "
					+ "JOIN RTABLES t "
					+ "ON o.TID = t.TID "
					+ "JOIN EMPLOYEES e "
					+ "ON o.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON o.MID = m.MID";
			
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery();
			
			if (result.next()) {
				JOptionPane.showMessageDialog(RestaurantMain.frame, "Нужно е да приключите сметките, за да продължите!", "Грешка", JOptionPane.OK_OPTION);
			}
			
			else {
			
			sqlQ = "SELECT a.bill AS СМЕТКА, t.TNAME AS МАСА, e.USERNAME AS Сервитьор, m.ITEM AS поръчано, m.price AS ЦЕНА, a.TIMEORDERING AS ДАТА "
					+ "FROM ORDERS_ARCHIVE a "
					+ "JOIN RTABLES t "
					+ "ON a.TID = t.TID "
					+ "JOIN EMPLOYEES e "
					+ "ON a.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON a.MID = m.MID ";
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery();
			
			String csvName = JOptionPane.showInputDialog("Въведете име на архива");
			
			try {
				OutputStreamWriter csvWriter = new OutputStreamWriter(new FileOutputStream("E:\\eclipse\\" + csvName + ".csv"), StandardCharsets.UTF_8);
				csvWriter.write('\uFEFF');
				ResultSetMetaData meta = result.getMetaData();
				DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
				DecimalFormat df = new DecimalFormat("0.00", symbols);
			    int columnCount = meta.getColumnCount();
			    
			    //title of table
			    for (int i = 1; i<= columnCount; i++) {
			    	csvWriter.append(meta.getColumnName(i));
			    	if (i <= columnCount) csvWriter.append(";");
			    }
			    
			    csvWriter.append("\n");
			    
			    //data
			    while (result.next()) {
			    	for (int i =1; i<= columnCount; i++) {
			    		String value;

			            int columnType = meta.getColumnType(i);
			            if (columnType == java.sql.Types.NUMERIC) {
			                double num = result.getDouble(i);
			                value = df.format(num);
			            } else {
			                value = result.getString(i);
			                if (value == null) value = "";
			            }

			            csvWriter.append("=\"").append(value).append("\"");
				    	if (i <= columnCount) csvWriter.append(";");
			    	}
			    	
			    	csvWriter.append("\n");
			    	
			    }
				
			    csvWriter.flush();
			    csvWriter.close();
			    
			} catch (IOException e) {
				// TODO Auto-generated catch block
			
			}
			
			sqlQ = "{CALL D_ALLORDERS_ARCHIVE()}"; // The procedure for DELETING THE ARCHIVE!!!
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery();
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
		}
		
	}
	
	static void waiterTable(DefaultTableModel tableModel) {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT e.ID, e.USERNAME, e.FIRSTNAME, e.LASTNAME, e.EGN, e.PASSWORD, p.POS_NAME "
					+ "FROM EMPLOYEES e "
					+ "JOIN positions p ON e.pos_id = p.pos_id ";
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery();
			
			while (result.next()) {
				
				int ID = result.getInt("ID");
				String username = result.getString("USERNAME");
				String FNAME = result.getString("FIRSTNAME");
				String LNAME = result.getString("LASTNAME");
				String EGN = result.getString("EGN");
				String PASS = result.getString("PASSWORD");
				String POSS = result.getString("POS_NAME");
				
				tableModel.addRow(new Object[] {ID,username,FNAME,LNAME,EGN,PASS,POSS});
				
			}
			
			conn.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			
		}
		
	}
	
	static void addReservedTable(int id_table,int stored_eid) {
		
		String table = "";
		
		try {
		
		Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
		sqlQ = "SELECT TNAME "
				+ "FROM reserved_table "
				+ "JOIN RTABLES t ON reserved_table.tid = t.tid "
				+ "WHERE t.TNAME = ? "; 
		
		statement = conn.prepareStatement(sqlQ);
		statement.setString(1, Role.lastClicktable);
        result = statement.executeQuery();
        
        if (result.next()) {
        	table = result.getString("TNAME");
        	
        }
		
		if (!(Role.lastClicktable.equals(table))) { //If the table is not in database!
			
			sqlQ = "{CALL P_RESERVED_TABLE(?,?)}"; // The procedure for add table and waiter!!!
			statement = conn.prepareStatement(sqlQ);
			statement.setInt(1, id_table);
			statement.setInt(2, stored_eid);
			result = statement.executeQuery();
			
		}
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
		
		}
		
	}
	
	static void updateOrder(String orderIdString) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			int id_order = Integer.parseInt(orderIdString);
		
			sql_order = "{CALL UP_ORDERS(?,?)}"; //The update procedure
			
			String selecteditem = ReserveTable.mealname_combo.getSelectedItem().toString();
			int id_item = ReserveTable.dataMap.get(selecteditem);
		
			statement = conn.prepareStatement(sql_order);
			statement.setInt(1, id_order);
			statement.setInt(2, id_item);
		    result = statement.executeQuery();
		   
	        sql_order_arch = "{CALL UP_ORDERS_ARCHIVE(?,?)}"; //The update procedure
		    
		    statement = conn.prepareStatement(sql_order_arch);
		    statement.setInt(1, id_order);
		    statement.setInt(2, id_item);
		    result = statement.executeQuery();
			
		    conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
		
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		} catch (Exception e1) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "Няма избрано ID!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void deleteOrder(String orderIdString) {
		
		try { 
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			int id_order = Integer.parseInt(orderIdString);
			
			sql_order = "{CALL D_ORDERS(?)}"; //The delete procedure for order!
			
			statement = conn.prepareStatement(sql_order);
			statement.setInt(1, id_order);
			result = statement.executeQuery();
			
			sql_order_arch = "{CALL D_ORDERS_ARCHIVE(?)}"; //The delete procedure for order archive!
			
			statement = conn.prepareStatement(sql_order_arch);
			statement.setInt(1, id_order);
			result = statement.executeQuery();
			
			conn.close();
			
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
	} 
	catch (Exception e1) {
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(ReserveTable.tableframe, "Няма избрано ID!", "Грешка", JOptionPane.OK_OPTION);
	} 
		
	}
	
	static List<String> topOrders() {
		
		List<String> list = new ArrayList<>();
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sqlQ = "SELECT * FROM "
					+ "(SELECT m.item AS item, COUNT(m.item) AS TOTAL_COUNT "
					+ "FROM ORDERS_ARCHIVE a "
					+ "JOIN menu_items m ON a.mid = m.mid "
					+ "GROUP BY m.item "
					+ "ORDER BY TOTAL_COUNT DESC) "
					+ "WHERE ROWNUM <= 3";
			
			statement = conn.prepareStatement(sqlQ);
			result = statement.executeQuery();
			
			while (result.next()) {
				list.add(result.getString("item"));
				
			}
		
			conn.close();
			
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		return list;
		
	}
	
	static int changesProtector() {
		
		int flag = 0;
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sqlQ = "SELECT o.ORDERSID, t.TNAME "
					+ "FROM ORDERS o "
				    + "JOIN RTABLES t "
				    + "ON o.TID = t.TID "
					+ "WHERE ORDERSID = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, ReserveTable.orderid.getText());
			result = statement.executeQuery();
			
			if (result.next()) {
				
				if (!(Role.lastClicktable.equals(result.getString("TNAME")))) {
					flag = 1;
					
				}
			}
		
			conn.close();
			
		} catch (SQLException e1) {
			
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		return flag;
		
	}
	
	static int upload_sameProtector() {
		
		int flag = 0;
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sqlQ = "SELECT item "
					+ "FROM MENU_ITEMS "
					+ "WHERE item = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, AbstractMenu.text.toLowerCase());
		
			result = statement.executeQuery();
			
			if (result.next()) {
				flag = 1;
				}
		
			conn.close();
			
		} catch (SQLException e1) {
			
		}
		
		catch (NullPointerException e1) {
			
		}
		
		return flag;
		
	}
	static int deleteitem_protector(String filename) {
		
		int flag = 0;
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sqlQ = "SELECT o.bill AS СМЕТКА, t.TNAME AS МАСА, e.USERNAME AS Сервитьор, m.ITEM AS поръчано, m.price AS ЦЕНА, o.TIMEORDERING AS ДАТА "
					+ "FROM ORDERS o "
					+ "JOIN RTABLES t "
					+ "ON o.TID = t.TID "
					+ "JOIN EMPLOYEES e "
					+ "ON o.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON o.MID = m.MID "
					+ "WHERE m.ITEM = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, filename);
			result = statement.executeQuery();
			
			if (result.next()) {
				flag = 1;
				}
		
			conn.close();
			
		} catch (SQLException e1) {
			
		}
		
		return flag;
		
	}
	
}
