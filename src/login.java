
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Toolkit;

public class login extends JFrame {

	private JPanel contentPane;
	private JTextField user;
	private JPasswordField pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login frame = new login();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws ClassNotFoundException 
	 */
	public login() throws ClassNotFoundException {
		setBackground(new Color(0, 51, 153));
		setTitle("Login");
		setIconImage(Toolkit.getDefaultToolkit().getImage("Resources\\icons8-man-with-money-50.png"));
		Connection con = dbConnection.instance().getConn();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 225);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 204, 152));
		contentPane.setForeground(Color.ORANGE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		user = new JTextField();
		user.setBounds(120, 59, 251, 22);
		contentPane.add(user);
		user.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User Name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(12, 52, 117, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(12, 96, 117, 16);
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("Sign up");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        sginUp sign = null;
		        dispose();
				try {
					sign = new sginUp();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        sign.setVisible(true);
			}
		});
		btnNewButton.setBounds(12, 136, 97, 25);
		contentPane.add(btnNewButton);
		
		JButton btnCreateNewFlat = new JButton("New flat");
		btnCreateNewFlat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {;
		        registration reg = null;
		        dispose();
				try {
					reg = new registration();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        reg.setVisible(true);
			}
		});
		btnCreateNewFlat.setBounds(322, 136, 97, 25);
		contentPane.add(btnCreateNewFlat);
		
		JButton btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PreparedStatement ps;
		        ResultSet rs;
		        String uname = user.getText();
		        String pas = new String(pass.getPassword()); 
		        String query ="SELECT * FROM PayTheBills.Account WHERE UserName='" + uname + "' && Password='" + pas + "'";
		        if(uname.equals("")||pas.equals(""))
		        	JOptionPane.showMessageDialog(null, "Please fill all fields!", "Login Failed", 2);
		        else
		       try {
		    	   ps = con.prepareStatement(query);

		        rs = ps.executeQuery();
		        if (rs.next()) { //User loged in.
		        	Logedin lg = null;
			        dispose();
					lg = new Logedin(rs.getInt(1),rs.getString(6),rs.getInt(4));
			        lg.setVisible(true);
				}
		        else {
		        	JOptionPane.showMessageDialog(null, "Incorect username or password", "Login Failed", 2);
		        }
			} catch (SQLException ex) {
				System.out.println(ex);
	        }
			}
		});
		btnLogIn.setBounds(170, 136, 97, 25);
		contentPane.add(btnLogIn);
		
		pass = new JPasswordField();
		pass.setBounds(120, 94, 251, 22);
		contentPane.add(pass);
		setLocationRelativeTo(null);
	}
}
