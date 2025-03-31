package pack;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

class SQL_Handler {

	private static String idbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String user = "marti";
	private static String password = "marti";
	
	private static PreparedStatement statement;
	private static ResultSet result;
	
	private static String sqlQ;
	private static String sql;
	private static String sql_order;
	private static String sql_employees;
	private static String sql_MENU_ITEMS;
	private static String sql_order_arch;
	
	static void loginsql(String resultHashedPass) {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password); //The connection
			
			String sql = "SELECT e.Firstname, p.Pos_NAME "
					+ "FROM EMPLOYEES e "
					+ "JOIN positions p ON e.pos_id = p.pos_id "
					+ "WHERE Firstname = ? AND Password = ? ";
			
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, RestaurantMain.username.getText());
			statement.setString(2, resultHashedPass);
			
			ResultSet result = statement.executeQuery(); 
			
			if (result.next()) //Results crawling
			{
				System.out.print("Здрасти!");
				
				
			}
			
			else { // The data sent is invalid!
				System.out.print("Няма такъв потребител!");	
			}
			
			conn.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	static void addEmployee(JTextField name, JTextField lastname, JTextField egn, int selectedid) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sql_employees = "{CALL P_EMPLOYEES(?, ?, ?, ?, ?)}"; //The Registration/insert procedure
				
			statement = conn.prepareStatement(sql_employees);
			statement.setString(1, name.getText());
			statement.setString(2, lastname.getText());
			statement.setString(3, egn.getText());
			statement.setString(4, Role.hashedpassword());
			statement.setInt(5, selectedid);
				
			result = statement.executeQuery(); 
			
			
			conn.close();
			
		} catch (SQLException er) {
			// TODO Auto-generated catch block
			er.printStackTrace();
		} 
		
	}
	
	static void updateEmployee(int id_emplo, JTextField name, JTextField lastname, JTextField egn,int selectedid) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sql_employees = "{CALL UP_EMPLOYEES(?, ?, ?, ?, ?, ?)}"; //The update procedure
				
			statement = conn.prepareStatement(sql_employees);
			statement.setInt(1, id_emplo);
			statement.setString(2, name.getText());
			statement.setString(3, lastname.getText());
			statement.setString(4, egn.getText());
			statement.setString(5, Role.hashedpassword());
			statement.setInt(6, selectedid);
				
			result = statement.executeQuery(); 
			
			conn.close();
			
		} catch (SQLException er) {
			// TODO Auto-generated catch block
			er.printStackTrace();
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
			er.printStackTrace();
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
			er.printStackTrace();
		} 
		
	}
	
	static void deleteItem(File file, String fileNameToSearch) {
		
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
			er.printStackTrace();
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
			   
		    sql = "{CALL PSTATUSA(?)}"; // The procedure for status of table = AVAILABLE!
			   
			statement = conn.prepareStatement(sql);
		    statement.setInt(1, id_table);
		    result = statement.executeQuery();
			
		    sql = "{CALL D_RESERVED_TABLE(?)}"; // The procedure for deleting the reservation!
			   
			statement = conn.prepareStatement(sql);
		    statement.setInt(1, id_table);
		    result = statement.executeQuery();
		    
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void billOrder(DefaultTableModel tableModel) {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT m.item AS Food, COUNT(m.item), SUM(m.price) AS Price "
				+ "FROM orders o "
				+ "JOIN menu_items m ON o.mid = m.mid "
				+ "JOIN rtables t ON o.tid = t.tid "
				+ "WHERE t.TNAME = ? "
				+ "GROUP BY m.item "
				+ "UNION ALL "
			    + "SELECT 'Обща сума' AS Food, COUNT(m.item), SUM(m.price) AS Price "
				+ "FROM orders o "
				+ "JOIN menu_items m ON o.mid = m.mid "
				+ "JOIN rtables t ON o.tid = t.tid "
				+ "WHERE t.TNAME = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
			statement.setString(2, Role.lastClicktable);
			result = statement.executeQuery();
	    
	    while (result.next()) {
	    	
	    	String item = result.getString("food");
	    	String str_price = result.getString("price");
	    	Double price = Double.valueOf(str_price);
	    	int count = result.getInt("COUNT(m.item)");
	    	tableModel.addRow(new Object[] {item, count, String.format("%.2f",price)});
	    }
	   
	   conn.close();
	   
		} catch (SQLException e1) {
		   // TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		
	}
	
	static void addOrder(int id_table, int stored_eid, int stored_mid, LocalDateTime datetime, int billName, int stored_IDbill) {
		
		int orderID = 0;
		
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
			
			sql = "{CALL PSTATUSB(?)}"; // The procedure for status of table = BUSY!
			
			statement = conn.prepareStatement(sql);
			statement.setInt(1, id_table);
			result = statement.executeQuery();
			
			sqlQ = "SELECT ID, Firstname "
					+ "FROM EMPLOYEES "
		            + "WHERE Firstname = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, RestaurantMain.username.getText());
			result = statement.executeQuery();
		
			if (result.next()) {
				stored_eid = result.getInt("ID");
				
			}
			
			sqlQ = "SELECT MID, ITEM "
					+ "FROM MENU_ITEMS "
		            + "WHERE ITEM = ? OR ITEM = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, FoodMenu.item); 
			statement.setString(2, DrinkMenu.item);
			result = statement.executeQuery();
			
			if (result.next()) {
				stored_mid = result.getInt("MID");
				
			}
			
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formatted_datetime = datetime.format(format);
			
			sqlQ = "SELECT t.TNAME, b.bill "
					+ "FROM ORDERS o "
					+ "JOIN rtables t ON o.tid = t.tid "
					+ "JOIN TABLE_BILL b  ON o.billid = b.billid "
					+ "WHERE TNAME = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.lastClicktable);
			result = statement.executeQuery();
			
			if (result.next()) {
				billName = result.getInt("bill");
				
			}
			
			else {
			Random r = new Random();
			String randomNumber = String.format("%04d", r.nextInt(10001)); //Bill code with four numbers!
			billName = Integer.parseInt(randomNumber);
			
			sqlQ = "SELECT BILLID, BILL "
					+ "FROM TABLE_BILL "
		            + "WHERE BILL = ?";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setInt(1, billName);
			result = statement.executeQuery();
			
			while (result.next()) {
				randomNumber = String.format("%04d", r.nextInt(10001)); //Bill code with four numbers!
				billName = Integer.parseInt(randomNumber);
				
				statement = conn.prepareStatement(sqlQ);
				result = statement.executeQuery();
				
			}
			
			String sql_table_bill = "{CALL P_TABLE_BILL(?)}"; //The insert procedure
			
			statement = conn.prepareStatement(sql_table_bill);
			statement.setInt(1, billName);
			result = statement.executeQuery();
			}
			
			sqlQ = "SELECT BILLID, BILL "
					+ "FROM TABLE_BILL "
		            + "WHERE BILL = ?";
			
			statement = conn.prepareStatement(sqlQ); // Execute the same query(query before the insert procedure!) to get the bill ID!
			statement.setInt(1, billName);
			result = statement.executeQuery();
			
			if (result.next()) {
				stored_IDbill = result.getInt("BILLID");
				
			}
			
			sql_order = "{CALL P_ORDERS(?,?,?,?,?)}"; //The insert procedure
			
			statement = conn.prepareStatement(sql_order);
			statement.setInt(1, id_table);  
			statement.setInt(2, stored_eid); 
			statement.setInt(3, stored_mid);  
		    statement.setTimestamp(4, Timestamp.valueOf(formatted_datetime)); // TO_CHAR (o.TIMEORDERING, 'YYYY-MM-DD HH24:MI:SS')
			statement.setInt(5, stored_IDbill); 
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
			statement.setInt(6, stored_IDbill); 
			result = statement.executeQuery(); 
			
			conn.close();
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void sql_addTable(String strnumber, String status) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sql = "{CALL P_RTABLES(?,?)}"; //The insert procedure
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, strnumber);
			statement.setString(2, status);
			
			result = statement.executeQuery(); 
		
			conn.close();
			
		} catch (SQLException e) {
		
		e.printStackTrace();
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
			
			e1.printStackTrace();
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
				    + "SELECT 'Обща сума (с намаление%)' AS Food, COUNT(m.item), SUM(m.price) * ( 1 - ( ? / 100)) AS Price "
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
		    	Double price = Double.valueOf(str_price);
		    	int count = result.getInt("COUNT(m.item)");
		    	tableModel.addRow(new Object[] {item, count, String.format("%.2f",price)});
		    }
		   
		   conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	static String checkTableAvailable1() {
		
		String status = "";
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sql = "SELECT STATUS FROM RTABLES "
					+ "WHERE TNAME = ? "; 
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, Role.lastClicktable);
	        result = statement.executeQuery();
	        
	        if (result.next()) {
	        	status = result.getString("STATUS");
	        	
	        }
			
	        conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		return status;
		
	}
	
	static String checkTableAvailable2() {
		
		String pass = "";
	
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sql = "SELECT t.TNAME, t.status, e.password "
					+ "FROM reserved_table "
					+ "JOIN RTABLES t ON reserved_table.tid = t.tid "
					+ "JOIN employees e ON reserved_table.ID = e.ID "
					+ "WHERE t.TNAME = ? "; 
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, Role.lastClicktable);
	        result = statement.executeQuery();
	        
	        if (result.next()) {
	        	pass = result.getString("password");
	        	
	        }
			
	        conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		return pass;
		
	}
	
	static void showOrder(DefaultTableModel tableModel) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sqlQ = "SELECT o.ORDERSID ,b.bill, t.TNAME, e.FIRSTNAME, m.ITEM , o.TIMEORDERING "
					+ "FROM ORDERS o "
					+ "JOIN table_bill b "
					+ "ON o.BILLID = b.BILLID "
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
		    	String waiter = result.getString("FIRSTNAME");
		    	String item = result.getString("ITEM");
		    	String time = result.getString("TIMEORDERING");
		    	
		    	tableModel.addRow(new Object[] {num, bill, rtable, waiter, item, time});
		    }
		
			conn.close();
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void showArchiveOrders(DefaultTableModel tableModel) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sqlQ = "SELECT b.bill, t.TNAME, e.FIRSTNAME, m.ITEM , a.TIMEORDERING "
					+ "FROM ORDERS_ARCHIVE a "
					+ "JOIN table_bill b "
					+ "ON a.BILLID = b.BILLID "
					+ "JOIN RTABLES t "
					+ "ON a.TID = t.TID "
					+ "JOIN EMPLOYEES e "
					+ "ON a.ID = e.ID "
					+ "JOIN MENU_ITEMS m "
					+ "ON a.MID = m.MID "
					+ "WHERE TO_CHAR(t.TNAME) = ? OR TO_CHAR(a.TIMEORDERING ,'YYYY-MM-DD') = ? OR TO_CHAR(b.bill) = ? ";
			
			statement = conn.prepareStatement(sqlQ);
			statement.setString(1, Role.archive_text.getText());
			statement.setString(2, Role.archive_text.getText()); 
			statement.setString(3, Role.archive_text.getText());
		    result = statement.executeQuery();
			
		    while (result.next()) {
		    	
		    	String bill = result.getString("bill");
		    	String rtable = result.getString("TNAME");
		    	String waiter = result.getString("FIRSTNAME");
		    	String item = result.getString("ITEM");
		    	String time = result.getString("TIMEORDERING");
		    	
		    	tableModel.addRow(new Object[] {bill, rtable, waiter, item, time});
		    }
		
			conn.close();
			
		} catch (SQLException e1) {
			
			e1.printStackTrace();
			JOptionPane.showMessageDialog(RestaurantMain.frame, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
	}
	
	static void deleteArchive() {
		
		try {
			
			Connection 	conn = DriverManager.getConnection(idbcURL,user,password);
			
			sql = "{CALL D_ALLORDERS_ARCHIVE()}"; // The procedure for DELETING THE ARCHIVE!!!
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			
			sql = "{CALL D_TABLE_BILL()}"; // The procedure for DELETING ALL BILLS!!!
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		
	}
	
	static void waiterTable(DefaultTableModel tableModel) {
		
		try {
			
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			sql = "SELECT e.ID, e.FIRSTNAME, e.LASTNAME, e.EGN, e.PASSWORD, p.POS_NAME "
					+ "FROM EMPLOYEES e "
					+ "JOIN positions p ON e.pos_id = p.pos_id ";
			statement = conn.prepareStatement(sql);
			result = statement.executeQuery();
			
			while (result.next()) {
				
				int ID = result.getInt("ID");
				String FNAME = result.getString("FIRSTNAME");
				String LNAME = result.getString("LASTNAME");
				String EGN = result.getString("EGN");
				String PASS = result.getString("PASSWORD");
				String POSS = result.getString("POS_NAME");
				
				tableModel.addRow(new Object[] {ID,FNAME,LNAME,EGN,PASS,POSS});
				
			}
			
			conn.close();
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		
	}
	
	static void addReservedTable(int id_table,int stored_eid) {
		
		String table = "";
		
		try {
		
		Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
		sql = "SELECT t.TNAME "
				+ "FROM reserved_table "
				+ "JOIN RTABLES t ON reserved_table.tid = t.tid "
				+ "WHERE t.TNAME = ? "; 
		
		statement = conn.prepareStatement(sql);
		statement.setString(1, Role.lastClicktable);
        result = statement.executeQuery();
        
        if (result.next()) {
        	table = result.getString("TNAME");
        	
        }
		
		if (!(Role.lastClicktable.equals(table))) { //If the table is not in database!
			
			sql = "{CALL P_RESERVED_TABLE(?,?)}"; // The procedure for add table and waiter!!!
			statement = conn.prepareStatement(sql);
			statement.setInt(1, id_table);
			statement.setInt(2, stored_eid);
			result = statement.executeQuery();
			
		}
		
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		    
		    sql = "SELECT TID FROM RTABLES "
					+ "WHERE TNAME = ? "; 
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, Role.lastClicktable);
	        result = statement.executeQuery();
	        
	        sql_order_arch = "{CALL UP_ORDERS_ARCHIVE(?,?)}"; //The update procedure
		    
		    statement = conn.prepareStatement(sql_order_arch);
		    statement.setInt(1, id_order);
		    statement.setInt(2, id_item);
		    result = statement.executeQuery();
			
		    conn.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
			e1.printStackTrace();
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
	} 
	catch (Exception e1) {
		// TODO Auto-generated catch block
		JOptionPane.showMessageDialog(ReserveTable.tableframe, "Няма избрано ID!", "Грешка", JOptionPane.OK_OPTION);
	} 
		
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
			
			e1.printStackTrace();
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		return flag;
		
	}
	
	
	
}
