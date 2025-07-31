package pack;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

class Login {

	static String resultHashedPass;
	
	Login() {
		
		MessageDigest digest;
		
		try {
			digest = MessageDigest.getInstance("SHA-256"); //Hash algorithm
			String getPasswordText = new String(RestaurantMain.password.getPassword());
			byte[] hashedBytes = digest.digest(getPasswordText.getBytes());
			
			StringBuilder hexString = new StringBuilder();
			for (byte b: hashedBytes) {
				hexString.append(String.format("%02x", b));
			}
			
			resultHashedPass = hexString.toString();
		
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			
		}
		
		SQL_Handler.loginsql(resultHashedPass);
		
	}

}
