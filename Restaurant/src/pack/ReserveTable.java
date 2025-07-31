package pack;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


class ReserveTable {
	
	static JFrame tableframe;
	
	static File newfile;
	
	private static int stored_mid;
	private static int stored_eid;
	private static int id_table;

	static String response;
	
	private static JPanel panel = new JPanel();
	private static JLabel label_orderid = new JLabel();
	private static JLabel label_mealname = new JLabel();
	public static JTextField orderid = new JTextField();
	static JComboBox<String> mealname_combo = new JComboBox<>();
	static JTextArea txtarea = new JTextArea();
	private JButton button_update = new JButton();
	private JButton button_delete = new JButton();
	private JButton button_get_bill = new JButton();
	private JButton button_final = new JButton();
	private JButton button_music = new JButton();
	
	static Map<String, Integer> dataMap = new HashMap<>();
	
    private static DefaultTableModel tableModel;
	private static JTable table;
	private static JScrollPane scroll;
	static JScrollPane scroll2;
	
	private ImageIcon img_music1 = new ImageIcon("E:\\eclipse\\eclipse workspace\\Restaurant\\src\\music1.png");
	private ImageIcon img_music2 = new ImageIcon("E:\\eclipse\\eclipse workspace\\Restaurant\\src\\music2.png");
	
	public void table() {
		
		String status = "Свободна";
		//if the selected table is not taken by other waiter!
		if (status.equals(SQL_Handler.checkTableAvailable1()) || Login.resultHashedPass.equals(SQL_Handler.checkTableAvailable2())) {
		
			panel = new JPanel();
			mealname_combo = new JComboBox<>(); 
			
			SQL_Handler.order_menu_combo();
			
			label_orderid.setText("ID");
			label_orderid.setFont(new Font("Calibri", Font.BOLD, 30));
			label_orderid.setBounds(145, 30, 100,50);
			orderid.setBounds(45, 80, 220, 50); 
			orderid.setFont(RestaurantMain.font);
			label_mealname.setText("Поръчано");
			label_mealname.setFont(new Font("Calibri", Font.BOLD, 30));
			label_mealname.setBounds(90, 185, 150,30);
			mealname_combo.setBounds(45, 220, 220, 50); 
			mealname_combo.setFont(RestaurantMain.font);
			
			button_update.setText("Промени");
			button_update.setBounds(45,360,220,50);
			button_update.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if (SQL_Handler.changesProtector() != 1) {
						
						String orderIdString = orderid.getText();
						
						SQL_Handler.updateOrder(orderIdString);
						
						tableModel.setRowCount(0); //delete rows for the new ones with the changes!
						SQL_Handler.showOrder(tableModel);
						
					}
					
				}
				
			});
			
			button_delete.setText("Изтрий поръчка");
			button_delete.setBounds(45,420,220,50);
			button_delete.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					if (SQL_Handler.changesProtector() != 1) {
						
						String orderIdString = orderid.getText();
						
						SQL_Handler.deleteOrder(orderIdString);
						
						tableModel.setRowCount(0); //delete rows!
						SQL_Handler.showOrder(tableModel);
						
					}
					
				}
				
			});
			
			button_get_bill.setText("СМЕТКА");
			button_get_bill.setBounds(45,480,220,50);
			button_get_bill.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					SQL_Handler.billOrder();
					
					if (SQL_Handler.flag !=0) { // flag for the bill, if no things are purchased will not make a bill!
					
					try {
						
						FileWriter writer = new FileWriter("E:\\eclipse\\bill.txt");
						writer.write(txtarea.getText()); //The text goes from method SQL_Handler.billOrder!
						
						writer.close();
						
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						
						}
					}
				}
				
			});
			
			button_final.setText("Приключване");
			button_final.setBounds(45,540,220,50);
			button_final.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					tableModel.setRowCount(0); //delete rows!
					SQL_Handler.tablerelease();
					txtarea.setText("");
					}
				});
			
			tableModel = new DefaultTableModel();
			table = new JTable(tableModel);
			tableModel.addColumn("ID");
			tableModel.addColumn("Сметка");
			tableModel.addColumn("МАСА");
			tableModel.addColumn("Сервитьор");
			tableModel.addColumn("ястие/напитка");
			tableModel.addColumn("Дата");
			
			button_music.setBounds(115,590, 70, 70);
			button_music.setIcon(img_music1);
			button_music.setBorderPainted(false);
			button_music.setContentAreaFilled(false);
			button_music.setCursor(new Cursor(Cursor.HAND_CURSOR));
			button_music.addMouseListener(click_musicicon);
			
			SQL_Handler.showOrder(tableModel);
			
			panel.setBackground(Color.orange);
			panel.setLayout(null);
			panel.setBounds(483,100,300,715);
			panel.add(label_orderid);
			panel.add(orderid);
			panel.add(label_mealname);
			panel.add(mealname_combo);
			panel.add(button_update);
			panel.add(button_delete);
			panel.add(button_get_bill);
			panel.add(button_final);
			panel.add(button_music);
			
			txtarea.setFont(new Font("Calibri", Font.PLAIN, 18)); //For the bill!
			txtarea.setEditable(false);
			
			scroll2 = new JScrollPane(txtarea);
			scroll2.setBounds(40,300,422,330);
			
			ImageIcon frameicon = new ImageIcon("E:\\eclipse\\eclipse workspace\\Restaurant\\src\\iconaddtable1.png");
			
			tableframe = new JFrame();
			tableframe.setTitle("Table");
			tableframe.setSize(800,800);
			tableframe.setResizable(false);
			tableframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);   
			tableframe.setIconImage(frameicon.getImage());
			tableframe.setLocationRelativeTo(null); //To center the frame!
			tableframe.setLayout(null);
			scroll = new JScrollPane(table);
			scroll.setBounds(0, 0, 800, 120);
			tableframe.add(scroll2);
			tableframe.add(scroll);
			tableframe.add(panel);
			
			tableframe.setVisible(true);
			
			//Clear the fields!
			txtarea.setText("");
			orderid.setText("");
			response = "";
		}
		
		else {
			JOptionPane.showMessageDialog(RestaurantMain.frame, "Масата е заета!", "Грешка", JOptionPane.OK_OPTION);
			}
		
	}
	
	public static void order() {
		
		String str_number = "";
	
		try {
			
			do {
				str_number = JOptionPane.showInputDialog("Въведете брой:");
				} while(!str_number.matches("\\d+(\\.\\d*)?"));
			
		} catch(NullPointerException e) {
			
		}
		
		int number = Integer.valueOf(str_number);
		
		//How many times this query to be executed, depends of the order! 
		//(if are purchased more than one same item, will be executed more times for the same item!) 
		
		while (number > 0) {  
			SQL_Handler.addOrder(id_table, stored_eid, stored_mid); 
			number-=1;
		}
		
		AbstractMenu.item = ""; //Clear the DrinkMenu and FoodMenu item
		
	}
	
	public static void addtable() {

		final String SAVE_DIR = "E:\\eclipse\\eclipse workspace\\Restaurant\\src\\Restaurant_tables";
		int number = 0;
		String strnumber; //The table name
		File imgfile = new File("E:\\eclipse\\eclipse workspace\\Restaurant\\src\\table.png");
		File dir = new File(SAVE_DIR);
		int dotIndex;
		String fileNameToSearch;
		String status = "Свободна";
		
		if (dir.exists()) {
			for (File file : dir.listFiles() ) {
				
				dotIndex = file.getName().lastIndexOf(".");
				fileNameToSearch = file.getName().substring(0, dotIndex-1);
				int e = Integer.parseInt(fileNameToSearch);
				
				if (e > number) {
					number = e;
				}
				
			}
			
		}
			number +=1;
			strnumber = String.valueOf(number);
			try {
				
				BufferedImage img = ImageIO.read(imgfile);
				newfile = new File(SAVE_DIR, strnumber + " .png");
				ImageIO.write(img, "png", newfile); //save the img in the new file!
				
				SQL_Handler.sql_addTable(strnumber, status);
				
			} catch (IOException e) {
			
			JOptionPane.showMessageDialog(tableframe, "File грешка!", "Грешка", JOptionPane.OK_OPTION);
		}
	}
	
	public static void deltable() {
		
		final String SAVE_DIR = "E:\\eclipse\\eclipse workspace\\Restaurant\\src\\Restaurant_tables";
		File dir = new File(SAVE_DIR);
		int number = 0;
		int filenumber = 0;
		
		File lastFile = new File("");
		
		for (File file : dir.listFiles() ) {
			
			int dotIndex = file.getName().lastIndexOf(".");
			String fileName = file.getName().substring(0, dotIndex-1);
			filenumber = Integer.parseInt(fileName);
			
			if (filenumber > number) {
				number = filenumber;
				lastFile = file;
			}
			
		}
		
		lastFile.delete(); 
		RestaurantMain.Homepanel.remove(Role.scroll);
		
		SQL_Handler.sql_deleteTable(number);
		
		Role.displaytables();
		Role.imagePanel.revalidate();
		Role.imagePanel.repaint();
		Role.scroll.revalidate();
		Role.scroll.repaint();
		
	}
	
	MouseListener click_musicicon = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			button_music.setIcon(img_music2);
			
			try {
			// Discount % for the orchestra!
			response = JOptionPane.showInputDialog("НАМАЛЕНИЕ с:");
			
			int percentage = Integer.parseInt(response);// Converting String to int!
			
			if (tableModel.getRowCount() !=0) {
				
				tableModel.setColumnCount(0); //delete columns for the new ones!
				tableModel.setRowCount(0); //delete rows for the new ones!
				
				tableModel.addColumn("ястие/напитка");
				tableModel.addColumn("Брой");
				tableModel.addColumn("Сума");
				
				SQL_Handler.music_discount(tableModel, percentage);
				
				}

			}catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(tableframe, "Моля въвдете намалението!", "Грешка", JOptionPane.OK_OPTION);
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
			button_music.setIcon(img_music2);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			button_music.setIcon(img_music1);
			
		}
		
	};
	
}
