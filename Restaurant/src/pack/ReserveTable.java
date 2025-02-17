package pack;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

class ReserveTable {
	
	public static void addtable() {

		final String SAVE_DIR = "Restaurant_tables";
		int number;
		String strnumber = "0"; //The table number
		File imgfile = new File("table.png");
		File dir = new File(SAVE_DIR);
		int dotIndex;
		String fileNameToSearch;
		
		try {
	
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
			
			BufferedImage img = ImageIO.read(imgfile);
			File newfile = new File(SAVE_DIR, strnumber + " .png");
			ImageIO.write(img, "png", newfile); //save the img in the new file!
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
