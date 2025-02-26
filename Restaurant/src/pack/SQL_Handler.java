package pack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class SQL_Handler {

	private static String idbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
	private static String user = "marti";
	private static String password = "marti";
	
	private static PreparedStatement statement;
	private static ResultSet result;
	
	private static String sqlQ;
	
	public static void ShowOrder(JTable table, DefaultTableModel tableModel) {
		
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
					+ "WHERE TNAME = ? ";
			
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
}
