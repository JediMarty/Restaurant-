package pack;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// fix this public or something else ???
public class Login {

	String idbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "marti";
	String password = "marti";
	
	String resultHashedPass;
	
	Login() {
	try {
		
		MessageDigest digest;
		
		try {
			digest = MessageDigest.getInstance("SHA-256"); //Hash algorithm
		
			byte[] hashedBytes = digest.digest(RestaurantMain.password.getText().getBytes());
			
			StringBuilder hexString = new StringBuilder();
			for (byte b: hashedBytes) {
				hexString.append(String.format("%02x", b));
			}
			
			resultHashedPass = hexString.toString();
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
			System.out.print("Здрастиии");
			
			
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

}
