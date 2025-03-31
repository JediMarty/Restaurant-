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
import javax.swing.JTextField;

public class RestaurantMain {
	
	static JFrame frame = new JFrame(); //Creating the main window
	private ImageIcon logo;
	private ImageIcon menufr;
	private ImageIcon img_food1; 
	private ImageIcon img_food2;
	private ImageIcon img_drinks1;
	private ImageIcon img_drinks2;
	private ImageIcon img_waiter1;
	private ImageIcon img_waiter2;
	private ImageIcon img_addtable1;
	private ImageIcon img_addtable2;
	private ImageIcon delIcon;
	static Font font;
	static JTextField username;
	static JPasswordField password;
	private JButton FoodIcon;
	private JButton DrinkIcon;
	private JButton WaiterIcon;
	private JButton loginbtn;
	private JButton logoutbtn;
	private JButton addtable; 
	private JButton button_del_table;
	static JPanel Homepanel;
	private JPanel panel1;
	private JPanel panel_login;
	private JLabel logoframe;
	private JLabel label_user;
	private JLabel label_pass;
	private JLabel menuframe;
	
	Login login;
	Role role;
    
	private static JPanel cardPanel = new JPanel(new CardLayout());
	
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
		delIcon = new ImageIcon("icondeltable2.png");
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
			
			login = new Login();
			role = new Role();
			cardPanel.add(Role.Profilepanel);
			
		}
		
	});
		
		logoutbtn = new JButton();
		logoutbtn.setText("ИЗЛЕЗ");
		logoutbtn.setBounds(70,520,220,50);
		logoutbtn.setFocusPainted(false);
		logoutbtn.setBorderPainted(false);
		logoutbtn.setContentAreaFilled(false);
		logoutbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		logoutbtn.addMouseListener(click_logoutbtn);
		
		panel_login.setBackground(Color.orange);
		panel_login.setLayout(null);
		panel_login.setBounds(0,110,350,590);
		panel_login.add(label_user);
		panel_login.add(username);
		panel_login.add(label_pass);
		panel_login.add(password);
		panel_login.add(loginbtn);
		panel_login.add(logoutbtn);
		
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
		addtable.setBounds(1400, 820, 70, 70);
		addtable.setContentAreaFilled(false);
		addtable.addMouseListener(click_tableicon);
		addtable.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		button_del_table.setBounds(1400, 900, 70, 70);
		button_del_table.setIcon(delIcon);
		button_del_table.setBorderPainted(false);
		button_del_table.setContentAreaFilled(false);
		button_del_table.setCursor(new Cursor(Cursor.HAND_CURSOR));
		button_del_table.addMouseListener(click_deltableicon);
	
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
			
			if (role.flagtable == 1) { //Only the Boss have privilege to add more tables!
				
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

	MouseListener click_deltableicon = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if (role.flagtable == 1) { //Only the Boss have privilege to delete tables!
			ReserveTable.deltable();
			
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
		
	};
	
	MouseListener click_logoutbtn = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent e) {
			
			username.setText("");
			password.setText("");
	
			try {
			
			cardPanel.remove(Role.Profilepanel);
			Homepanel.remove(Role.scroll);		
			Role.lastClicktable = "0";
			
			
			} catch (NullPointerException e1){
				
			}
			
			frame.revalidate();
			frame.repaint();
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
			logoutbtn.setForeground(new Color (240,255,255)); //The color is azure
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			logoutbtn.setForeground(Color.black); 
			
		}
		
	};
	
	public static void switch_the_scene() {
		CardLayout cl = (CardLayout) (cardPanel.getLayout());
		cl.next(cardPanel);
		}

}
