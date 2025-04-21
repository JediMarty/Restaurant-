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
import java.io.IOException;
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

class FoodMenu {
	
	private JFrame foodframe;
	private JPanel imagePanel;
	private JPanel imageContainer;
	private JLabel imgLabel;
	private JTextField search;
	private final String SAVE_DIR = "Menu_food";
	private String text;
	private String price;
	private String fileNameToSearch;
	static String item;
	
	private int dotIndex;
	
	private JScrollPane scroll;
	
	FoodMenu() {
		init();
	}
	
	public void init() {
		
		imagePanel = new JPanel();
		imagePanel.setLayout(new GridLayout(0,4,10,10));
        imagePanel.setBackground(Color.orange);
	
        scroll = new JScrollPane(imagePanel);
        
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
		foodframe = new JFrame();
		foodframe.setTitle("Food-menu");
		foodframe.setSize(800,800);
		foodframe.setResizable(false);
		foodframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    
		foodframe.setLayout(new BorderLayout());
		foodframe.add(searchpanel, BorderLayout.NORTH);
		foodframe.add(scroll, BorderLayout.CENTER);
		foodframe.add(button,BorderLayout.SOUTH);
		foodframe.setVisible(true);
	}
	
	private void uploadImage() { 
		
		JFileChooser filechooser = new JFileChooser();
		filechooser.setCurrentDirectory(new File(".")); //The main directory 
		
		int result = filechooser.showOpenDialog(foodframe); //Return int number - whether the file is open or not
		
		if (result == JFileChooser.APPROVE_OPTION) { //If the user selects an image, and clicks OK!
			
			File file = filechooser.getSelectedFile();
			text = JOptionPane.showInputDialog("Въведете име на ястието");
			
			try {
				
				do {
					price = JOptionPane.showInputDialog("Въведете цена");
					
				} while (!price.matches("\\d+(\\.\\d*)?"));
				
			} catch (NullPointerException e){
				
			}
			
			addItemtoDB(text,price);
			
			if (text != null) {
				
				File savedfile = saveImage(file);
				
				if (savedfile != null) {
					displayImage(savedfile);
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
				displayImage(file);
			}
		}
	}
	
	private void addItemtoDB(String filename, String money) {
		
		SQL_Handler.addItem(filename, money);
		
	}
	
	private void delItemfromDB(File file) {
	
		int dotIndex = file.getName().lastIndexOf(".");
		String fileNameToSearch = file.getName().substring(0, dotIndex-1); //Get the file name without .png!
		
		SQL_Handler.deleteItem(file, fileNameToSearch);
		
	}
	
	private void delete(File file, JPanel panel) {
		
		int confirm = JOptionPane.showConfirmDialog(foodframe, "Сигурни ли сте, че искате да премахнете ястието от менюто ?", "Изтриване", JOptionPane.YES_NO_OPTION);
		
		if (confirm == JOptionPane.YES_OPTION) {
			
			delItemfromDB(file);
			file.delete(); 
			foodframe.dispose();
		    new FoodMenu();
		}
		
		else {
			JOptionPane.showMessageDialog(foodframe, "Изтриването е прекратено!", "Грешка", JOptionPane.OK_OPTION);
		}
	
	}
	
	private void search() {
		
		File dir = new File(SAVE_DIR);
		imagePanel.removeAll();
		
		for (File file : dir.listFiles() ) {
			
			dotIndex = file.getName().lastIndexOf(".");
			fileNameToSearch = file.getName().substring(0, dotIndex-1);
			// replaceAll("\\s+", "") - remove all spaces!
			if (fileNameToSearch.replaceAll("\\s+", "").toLowerCase().contains(search.getText().toLowerCase())) {
				displayImage(file);
				
			}
		}
		
	}
	
	public void orderFood(String fileNameToSearch) {
		
		imgLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
		imgLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				String status = "Свободна";
				
				try {
				
				if (status.equals(SQL_Handler.checkTableAvailable1()) || Login.resultHashedPass.equals(SQL_Handler.checkTableAvailable2())) {
					
					int confirm = JOptionPane.showConfirmDialog(foodframe, "Добавяне: " + fileNameToSearch, "Избор", JOptionPane.YES_NO_OPTION);
					
					if (confirm == JOptionPane.YES_OPTION) {
						item = fileNameToSearch;
						ReserveTable.order();
				}
					else {
						JOptionPane.showMessageDialog(foodframe, "Добавянето е прекратено!", "Грешка", JOptionPane.OK_OPTION);
						
					}
					
				}
				
				else {
					JOptionPane.showMessageDialog(foodframe, "Масата, която сте избрали е заета или не сте избрали!", "Грешка", JOptionPane.OK_OPTION);
				}
				
				} catch (NullPointerException e1){
					JOptionPane.showMessageDialog(foodframe, "Масата, която сте избрали е заета или не сте избрали!", "Грешка", JOptionPane.OK_OPTION);
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
		
		if (ReserveTable.tableframe != null) {
		ReserveTable.tableframe.dispose();
		
		}
	}
	
	private void displayImage(File file) {
		
		try {
			
			BufferedImage img = ImageIO.read(file);
			
			if (img == null) {
				JOptionPane.showMessageDialog(foodframe, "Невалидно изображение", "Error!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			ImageIcon image = new ImageIcon(img.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

			int dotIndex = file.getName().lastIndexOf(".");
			fileNameToSearch = file.getName().substring(0, dotIndex-1);
			
		    imgLabel = new JLabel(image);
		    imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			JLabel txtLabel = new JLabel(fileNameToSearch);
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
		
		orderFood(fileNameToSearch); //Method for ordering from the food menu, by clicking the image of the meal!
	}
}
