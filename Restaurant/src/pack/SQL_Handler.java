package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTable;
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
	
	
	static void tablerelease() {
		
		try {
			Connection 	conn = DriverManager.getConnection(idbcURL,user,password);
			
			sqlQ = "SELECT TID FROM RTABLES WHERE TNAME = ?"; // Select table name to find ID of table!
			   
			statement = conn.prepareStatement(sqlQ);
			statement.setInt(1, Role.lastClicktable);
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
			
			conn.close();
		} catch (SQLException e) {
			
			e.printStackTrace();
			JOptionPane.showMessageDialog(ReserveTable.tableframe, "SQL грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
		
		
	}
	
	static String checkTableAvailable1() {
		
		String status = "";
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
		
			sql = "SELECT STATUS FROM RTABLES "
					+ "WHERE TNAME = ? "; 
			
			statement = conn.prepareStatement(sql);
			statement.setInt(1, Role.lastClicktable);
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
		
			sql = "SELECT t.TNAME, e.password "
					+ "FROM ORDERS_ARCHIVE o "
					+ "JOIN RTABLES t "
					+ "ON o.tid = t.tid "
					+ "JOIN employees e "
					+ "ON o.ID = e.ID "
					+ "WHERE TNAME = ? "; 
			
			statement = conn.prepareStatement(sql);
			statement.setInt(1, Role.lastClicktable);
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
	
	static void showOrder(JTable table, DefaultTableModel tableModel) {
		
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
			statement.setInt(1, Role.lastClicktable);
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
	
	static void showArchiveOrders(JTable table, DefaultTableModel tableModel) {
		
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
				
				if (Role.lastClicktable != result.getInt("TNAME")) {
					int a = result.getInt("TNAME");
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

