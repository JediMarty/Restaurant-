package pack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

class DrinkMenu {

	private JFrame drinkframe;
	private JPanel imagePanel;
	JPanel imageContainer;
	JLabel imgLabel;
	JTextField search;
	private String SAVE_DIR = "Menu_drink";
	File pricesfile = new File("prices.txt");
	String text;
	String money;
	Double price;
	
	String idbcURL = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "marti";
	String password = "marti";
	
	DrinkMenu() {
		init();
	}
	
	public void init() {
		
		imagePanel = new JPanel();
		imagePanel.setLayout(new GridLayout(0,4,10,10));
        imagePanel.setBackground(Color.orange);
	
        JScrollPane scroll = new JScrollPane(imagePanel);
        
        search = new JTextField();
        
        JButton searchbutton = new JButton("Търси");
        searchbutton.setPreferredSize(new Dimension(10,50));
        searchbutton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				search(); //Method for searching in food menu! 
				
			}
		});
        
        JPanel searchpanel = new JPanel();
        searchpanel.setBackground(Color.orange);
        searchpanel.setLayout((new BoxLayout(searchpanel, BoxLayout.Y_AXIS)));
        searchpanel.add(Box.createVerticalStrut(5)); //Spacing before
        searchpanel.add(search);
        
        searchpanel.add(searchbutton);
        searchbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton button = new JButton("Качи");
		button.setPreferredSize(new Dimension(150,50));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				uploadImage();
				
			}
			
		});
		
		loadsaveImage();
	
		//The Window
		drinkframe = new JFrame();
		drinkframe.setTitle("Food-menu");
		drinkframe.setSize(800,800);
		drinkframe.setResizable(false);
		drinkframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    
		drinkframe.setLayout(new BorderLayout());
		drinkframe.add(searchpanel, BorderLayout.NORTH);
		drinkframe.add(scroll, BorderLayout.CENTER);
		drinkframe.add(button,BorderLayout.SOUTH);
		drinkframe.setVisible(true);
	}
	
	private void uploadImage() { 
		
		JFileChooser filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(new File(".")); //The main directory 
		
		int result = filechooser.showOpenDialog(drinkframe); //Return int number - whether the file is open or not
		
		if (result == JFileChooser.APPROVE_OPTION) { //If the user selects an image, and clicks OK!
			
			File file = filechooser.getSelectedFile();
			text = JOptionPane.showInputDialog("Въведете име на ястието");
			money = JOptionPane.showInputDialog("Въведете цена");
			
			dataPrice(text,money);
			
			if (text != null) {
				
				File savedfile = saveImage(file);
				
				if (savedfile != null) {
					displayImage(savedfile, text);
				}
			}
		}
	}
	
	private File saveImage(File file) {
		
		try {
			
			BufferedImage img = ImageIO.read(file);
			File newfile = new File(SAVE_DIR, text + " .png");
			ImageIO.write(img, "png", newfile); //save the img in the new file!
			return newfile;
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	private void loadsaveImage() {
		
		File dir = new File(SAVE_DIR);
		
		if (dir.exists()) {
			for (File file : dir.listFiles() ) {
				displayImage(file, file.getName());
			}
		}
	}
	
private void dataPrice(String filename, String money) {
		
		try {
			Connection conn = DriverManager.getConnection(idbcURL,user,password);
			
			PreparedStatement statement;
		    ResultSet result;
		    
		    String sql = "{CALL P_MENU_ITEMS(?, ?)}"; //The insert procedure
			
			statement = conn.prepareStatement(sql);
			statement.setString(1, filename);
			statement.setString(2, money);
			result = statement.executeQuery(); 
			
			conn.close();
			
		} catch (SQLException er) {
			// TODO Auto-generated catch block
			er.printStackTrace();
		} 
		
}
	
private void deldataPrice(File file) {
	
	try {
		Connection conn = DriverManager.getConnection(idbcURL,user,password);
	
		PreparedStatement statement;
	    ResultSet result;

	    int dotIndex = file.getName().lastIndexOf(".");
		String fileNameToSearch = file.getName().substring(0, dotIndex-1); //Get the file name without .png!
	    
	    String sqlQ = "SELECT * FROM MENU_ITEMS";
	    
	    String sql = "{CALL D_MENU_ITEMS(?)}"; //The delete procedure
		
		statement = conn.prepareStatement(sqlQ);
		result = statement.executeQuery(); 

		if (result.next()) //Results crawling
		{
			String storedname = result.getString("item"); 
			
			if (fileNameToSearch.contains(storedname)) {
				int storedpostion = result.getInt("mID"); //ID where is the item who needs to be deleted!
				
				statement = conn.prepareStatement(sql);
				statement.setInt(1, storedpostion);
				result = statement.executeQuery(); 
			}
			
		}
	
		
		conn.close();
		
	} catch (SQLException er) {
		// TODO Auto-generated catch block
		er.printStackTrace();
	} 
	
}
	
	private void delete(File file, JPanel panel) {
		
		int confirm = JOptionPane.showConfirmDialog(drinkframe, "Сигурни ли сте, че искате да премахнете ястието от менюто ?", "Изтриване", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			
			file.delete(); 
			imagePanel.remove(panel); //remove from the interface
			deldataPrice(file);
			drinkframe.dispose();
			new DrinkMenu();
			
		}
		
		else {
			JOptionPane.showMessageDialog(drinkframe, "Изтриването е прекратено!", "Грешка", JOptionPane.OK_OPTION);
		}
	
	}
	
	private void search() {
		
		File dir = new File(SAVE_DIR);
		imagePanel.removeAll();
		
		for (File file : dir.listFiles() ) {
			
			if (file.getName().toLowerCase().contains(search.getText().toLowerCase())) {
				displayImage(file,file.getName());
				
			}
			
			else {
				System.out.println("Not found");
			}
		}
	}
	
	public void orderFood() {
		
		imgLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		imgLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int confirm = JOptionPane.showConfirmDialog(drinkframe, "Добавено - " + text, "Добавяне", JOptionPane.YES_NO_OPTION);
				
				if (confirm == JOptionPane.YES_OPTION) {
					
				}
				
				else {
					JOptionPane.showMessageDialog(drinkframe, "Добавянето е прекратено!", "Грешка", JOptionPane.OK_OPTION);
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	private void displayImage(File file, String text) {
		
		try {
			
			BufferedImage img = ImageIO.read(file);
			
			if (img == null) {
				JOptionPane.showMessageDialog(drinkframe, "Невалидно изображение", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			ImageIcon image = new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
		
		    imgLabel = new JLabel(image);
		    imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			JLabel txtLabel = new JLabel(text);
			txtLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			
			ImageIcon delIcon = new ImageIcon("delete.png");
			ImageIcon image_delIcon = new ImageIcon(delIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
			
			imageContainer = new JPanel(); //For the image, text and delete button
			imageContainer.setLayout(new BoxLayout(imageContainer, BoxLayout.Y_AXIS)); //Vertical arrangement);
		
			JButton delButton = new JButton();
			delButton.setIcon(image_delIcon);
			delButton.setBorderPainted(false);
			delButton.setContentAreaFilled(false);
			delButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			delButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			delButton.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					delete(file, imageContainer);
					
				}
				
			});
			imageContainer.add(Box.createVerticalGlue()); //Spacing before
			imageContainer.add(imgLabel);
			imageContainer.add(txtLabel);
			imageContainer.add(delButton);
			imageContainer.add(Box.createVerticalGlue()); //Spacing after
		
			imagePanel.add(imageContainer);
			imagePanel.revalidate();
			imagePanel.repaint();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		orderFood(); //Method for ordering for the food menu, by clicking the image of the meal!
	}
}



	

	

