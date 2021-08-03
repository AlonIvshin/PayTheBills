import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class registration extends JFrame {

	private JPanel contentPane;
	private JTextField City;
	private JTextField Addr;
	private JLabel lblCity;
	private JLabel lblPassword;
	private JTextField Pass;
	private Connection con=null;

	public registration() throws ClassNotFoundException {
		setTitle("Flat Registration");
		setLocationRelativeTo(null);
		con = dbConnection.instance().getConn();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 387, 386);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alon's PC\\eclipse-workspace\\PayTheBills\\Resources\\icons8-man-with-money-50.png"));
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 204, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		City = new JTextField();
		City.setBounds(12, 59, 345, 42);
		City.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
				      char c = e.getKeyChar();
				      if ( (!(c>='a' && c<='z')&&!(c>='A' && c<='Z')) && (c != KeyEvent.VK_BACK_SPACE)) {
				         e.consume();  // ignore event
				      }
				   }
				});

		contentPane.add(City);
		City.setColumns(10);
		
		Addr = new JTextField();
		Addr.setColumns(10);
		Addr.setBounds(12, 137, 345, 42);
		contentPane.add(Addr);
		
		lblCity = new JLabel("City");
		lblCity.setBounds(12, 34, 85, 16);
		contentPane.add(lblCity);
		
		lblPassword = new JLabel("Address");
		lblPassword.setBounds(12, 114, 85, 16);
		contentPane.add(lblPassword);
		
		JButton btnReg = new JButton("Submit");
		btnReg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				insertToTable();
			}
		});
		btnReg.setBounds(12, 277, 97, 25);
		contentPane.add(btnReg);
		
		Pass = new JTextField();
		Pass.setColumns(10);
		Pass.setBounds(12, 214, 345, 42);
		contentPane.add(Pass);
		
		JLabel lblPass = new JLabel("Password");
		lblPass.setBounds(12, 192, 85, 16);
		contentPane.add(lblPass);
		
		JButton btnNewButton = new JButton("Home");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        login blogin = null;
		        dispose();
				try {
					blogin = new login();
				} catch (ClassNotFoundException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
		        blogin.setVisible(true);
			}
		});
		btnNewButton.setBounds(260, 277, 97, 25);
		contentPane.add(btnNewButton);
		setLocationRelativeTo(null);
	}
	private void insertToTable() {
		String query = "INSERT INTO PayTheBills.Flat ( City, Addr, Flat_password) values(?,?,?) ";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1, (String)City.getText());
			ps.setString(2,(String) Addr.getText());
			ps.setString(3, (String)Pass.getText());
			int x = ps.executeUpdate();
			if(x > 0) {
				System.out.println("Flat has been submited sucessfully!");
		        login blogin = null;
		        dispose();
				try {
					blogin = new login();
				} catch (ClassNotFoundException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
		        blogin.setVisible(true);
			}
			else
				System.out.println("Flat submition Failed.");
		}catch(Exception e1) {
			System.out.println(e1);
		}
	}
}
