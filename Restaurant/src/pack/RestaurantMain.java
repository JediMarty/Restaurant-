package pack;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class RestaurantMain {
	
	static JFrame frame = new JFrame(); //Creating the main window
	ImageIcon logo;
	ImageIcon menufr;
	ImageIcon img_food1; 
	ImageIcon img_food2;
	ImageIcon img_drinks1;
	ImageIcon img_drinks2;
	ImageIcon img_waiter1;
	ImageIcon img_waiter2;
	ImageIcon img_addtable1;
	ImageIcon img_addtable2;
	static ImageIcon delIcon;
	static Font font;
	static JTextField username;
	static JPasswordField password;
	JButton FoodIcon;
	JButton DrinkIcon;
	JButton WaiterIcon;
	JButton loginbtn;
	static JButton button_del_table;
	static JPanel Homepanel;
	JPanel panel1;
	JPanel panel_login;
	JLabel logoframe;
	JLabel label_user;
	JLabel label_pass;
	JLabel menuframe;
	JButton addtable; 
	static JPanel imagePanel;
	
    static JScrollPane scroll;
	
	public static JPanel cardPanel = new JPanel(new CardLayout());
	
	RestaurantMain() {
		init();
	}
	
	public void init() {
		//initialize
		panel1 = new JPanel();
		panel_login = new JPanel();
		logoframe = new JLabel();
		label_user = new JLabel();
		label_pass = new JLabel();
		menuframe = new JLabel();
		username = new JTextField();
		password = new JPasswordField();
		logo = new ImageIcon("logo.png");
		img_food1 = new ImageIcon("food.png");
		img_food2 = new ImageIcon("food2.png");
		img_drinks1 = new ImageIcon("drinks1.png");
		img_drinks2= new ImageIcon("drinks2.png");
		img_waiter1 = new ImageIcon("waiter1.png");
		img_waiter2 = new ImageIcon("waiter2.png");
		img_addtable1 = new ImageIcon("iconaddtable1.png");
		img_addtable2 = new ImageIcon("iconaddtable2.png");
		menufr = new ImageIcon("menuframe.png");
		delIcon = new ImageIcon("icondeltable1.png");
		FoodIcon = new JButton();
		DrinkIcon = new JButton();
		WaiterIcon = new JButton();
		addtable = new JButton();
		button_del_table = new JButton();
		font = new Font("Calibri", Font.PLAIN , 30);
		//...............................//
		
		logoframe.setIcon(logo);
		logoframe.setBounds(650, 100, 500, 200);
		
		panel1.setBackground(Color.orange);
		panel1.setBounds(0,0,1520,100);
		
		label_user.setText("ИМЕ");
		label_user.setFont(new Font("Calibri", Font.BOLD, 30));
		label_user.setBounds(150, 150, 100,30);
		label_pass.setText("ПАРОЛА");
		label_pass.setFont(new Font("Calibri", Font.BOLD, 30));
		label_pass.setBounds(130, 355, 110,30);
		
		username.setBounds(70, 190, 220, 50); 
		username.setFont(font);
		password.setBounds(70, 400, 220, 50);
		password.setFont(font);
		
		loginbtn = new JButton();
		loginbtn.setText("ВЛЕЗ");
		loginbtn.setBounds(70,480,220,50);
		loginbtn.setFocusPainted(false);
		loginbtn.addActionListener(new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Login();
			new Role();
			
		}
		
	});
		
		panel_login.setBackground(Color.orange);
		panel_login.setLayout(null);
		panel_login.setBounds(0,110,350,550);
		panel_login.add(label_user);
		panel_login.add(username);
		panel_login.add(label_pass);
		panel_login.add(password);
		panel_login.add(loginbtn);
		
		menuframe.setIcon(menufr);
		menuframe.setBounds(500,0,500,100);
		
		FoodIcon.setIcon(img_food1);
		FoodIcon.setBorderPainted(false);
		FoodIcon.setBounds(590, 15, 70, 70);
		FoodIcon.setContentAreaFilled(false);
		FoodIcon.setFocusPainted(false);
		FoodIcon.addMouseListener(click_FoodIcon);
		FoodIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		DrinkIcon.setIcon(img_drinks1);
		DrinkIcon.setBorderPainted(false);
		DrinkIcon.setBounds(720, 15, 70, 70);
		DrinkIcon.setContentAreaFilled(false);
		DrinkIcon.addMouseListener(click_DrinkIcon);
		DrinkIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		WaiterIcon.setIcon(img_waiter1);
		WaiterIcon.setBorderPainted(false);
		WaiterIcon.setBounds(850, 15, 70, 70);
		WaiterIcon.setContentAreaFilled(false);
		WaiterIcon.addMouseListener(click_WaiterIcon);
		WaiterIcon.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		addtable.setIcon(img_addtable1);
		addtable.setBorderPainted(false);
		addtable.setBounds(1400, 900, 70, 70);
		addtable.setContentAreaFilled(false);
		addtable.addMouseListener(click_tableicon);
		addtable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		button_del_table.setBounds(1200, 700, 70, 70);
		button_del_table.setIcon(delIcon);
		button_del_table.setBorderPainted(false);
		button_del_table.setContentAreaFilled(false);
		button_del_table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button_del_table.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				ReserveTable.deltable();
				
			}
			
		});
		
		Homepanel = new JPanel();
		Homepanel.add(button_del_table);
		Homepanel.add(addtable); 
		Homepanel.add(WaiterIcon);
		Homepanel.add(DrinkIcon);
		Homepanel.add(FoodIcon);
		Homepanel.add(menuframe);
		Homepanel.add(panel1);
		Homepanel.add(panel_login);
		Homepanel.add(logoframe);
        Homepanel.setLayout(null);
		Homepanel.setBackground(Color.WHITE);
		cardPanel.add(Homepanel);
		cardPanel.add(Role.Profilepanel);
		
		//The Window
		frame.setTitle("Table Reserved");
		frame.setSize(1520,1040);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    
		frame.setLayout(null);
		cardPanel.setBounds(0, 0, 1520, 1040);
		frame.add(cardPanel);
		frame.setVisible(true);
	
	}
	
	MouseListener click_FoodIcon = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			new FoodMenu();
			
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
			FoodIcon.setIcon(img_food2);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			FoodIcon.setIcon(img_food1);
			
		}
	
	};
	
	MouseListener click_DrinkIcon = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			new DrinkMenu();
			
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
			DrinkIcon.setIcon(img_drinks2);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			DrinkIcon.setIcon(img_drinks1);
			
		}
		
	};
	
	MouseListener click_WaiterIcon = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			switch_the_scene();
			
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
			WaiterIcon.setIcon(img_waiter2);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			WaiterIcon.setIcon(img_waiter1);
			
		}
	
	};
	
	MouseListener click_tableicon = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if (Role.flagtable == 1) { //Only the Boss have privilege to add more tables!
				
				ReserveTable.addtable();
			    Homepanel.remove(Role.scroll);
				Role.displaytables();
			    Role.imagePanel.revalidate();
			    Role.imagePanel.repaint();
			    Role.scroll.revalidate();
			    Role.scroll.repaint();
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
			addtable.setIcon(img_addtable2);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			addtable.setIcon(img_addtable1);
			
		}
		
	};

	public static void switch_the_scene() {
		CardLayout cl = (CardLayout) (cardPanel.getLayout());
		cl.next(cardPanel);
		}

}

