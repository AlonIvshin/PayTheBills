import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class SwapFlat extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTextField flatidtf;
	private JPasswordField passwordField;
	private Connection con ;
	private int myid;

	public SwapFlat(int myid) throws ClassNotFoundException {
		this.myid=myid;
		con = dbConnection.instance().getConn();
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alon's PC\\eclipse-workspace\\PayTheBills\\Resources\\icons8-man-with-money-50.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 581, 313);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 204, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton btOK = new JButton("OK");
		btOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(flatidtf.getText().equals("")||passwordField.getPassword().equals(""))
					JOptionPane.showMessageDialog(null, "Please fill all the filds", "Flat Error", 2);
				else {
					if(flatexist()) {
						if(balanceIsZero()) {
						changeFlat();
						dispose();
						}
					}
					else
						JOptionPane.showMessageDialog(null, "Flat id or password are incorect!", "Flat Error", 2);
				}
			}
		});
		btOK.setBounds(119, 221, 97, 25);
		contentPane.add(btOK);
		
		JButton btnNewButton = new JButton("Cancel");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnNewButton.setBounds(10, 221, 97, 25);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(251, 13, 285, 212);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("Flat id:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(12, 35, 57, 16);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(10, 102, 84, 16);
		contentPane.add(lblPassword);
		
		flatidtf = new JTextField();
		flatidtf.setBounds(123, 33, 116, 22);
		contentPane.add(flatidtf);
		flatidtf.setColumns(10);
		flatidtf.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
				      char c = e.getKeyChar();
				      if ( (!(c>='0' && c<='9') && (c != KeyEvent.VK_BACK_SPACE))) {
				         e.consume();  // ignore event
				      }
				   }
				});
		
		passwordField = new JPasswordField();
		passwordField.setBounds(123, 100, 116, 22);
		contentPane.add(passwordField);
		
		bringTable();
	}
	private void bringTable() {
		String query = "Select Flat_id, City , Addr  FROM PayTheBills.Flat";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs)); //DbUtils is class from rs2xml. Table display
			
			JButton btnNewButton = new JButton("Close");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			btnNewButton.setBounds(323, 200, 97, 25);
			contentPane.add(btnNewButton);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private boolean flatexist() {
		String query = "SELECT  Flat_id, Flat_password From PayTheBills.Flat WHERE Flat_id='"
	+flatidtf.getText()+"' && Flat_password='"+passwordField.getText()+"'";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private void changeFlat() {
		String query = "UPDATE  PayTheBills.Account SET Flat_id='"+flatidtf.getText()+"' WHERE User_id='"+myid+"'";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private boolean balanceIsZero() {
		String query = "SELECT  Balance From PayTheBills.Account WHERE User_id='"+myid+"'";
		try {
			PreparedStatement ps = con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			if (rs.next()&&rs.getInt(1)==0)
				return true;
		}catch (SQLException e) {
		}
		JOptionPane.showMessageDialog(null, "Your account balance should be zero before switcing flat!", "Balance Error", 2);
		return false;
	}
}
