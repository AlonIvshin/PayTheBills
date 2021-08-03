
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;

import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class sginUp extends JFrame {

	private JPanel contentPane;
	private JFormattedTextField user;
	private JFormattedTextField flatid;
	private JFormattedTextField namef;
	private JFormattedTextField lastnamef;
	private JPasswordField pasf;
	private boolean flatflag;
	private boolean usernameflag;

	public sginUp() throws ClassNotFoundException {
		setTitle("Sign up");
		setResizable(false);
		Connection con = dbConnection.instance().getConn();
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alon's PC\\eclipse-workspace\\PayTheBills\\Resources\\icons8-man-with-money-50.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 342, 301);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 204, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		user = new JFormattedTextField();
		user.setBounds(113, 31, 116, 22);
		contentPane.add(user);
		user.setColumns(10);
		user.addKeyListener(new KeyAdapter() { //only numbers and chars are allowed.
			   public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if ( (!(c>='a' && c<='z')&&!(c>='A' && c<='Z')&&!(c>='0' && c<='9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			         e.consume();  // ignore event
			      }
			   }
			});

		flatid = new JFormattedTextField();
		flatid.setColumns(10);
		flatid.setBounds(113, 106, 116, 22);
		contentPane.add(flatid);
		flatid.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if ( ((c < '0') || (c > '9')) && (c != KeyEvent.VK_BACK_SPACE)) {
			         e.consume();  // ignore event
			      }
			   }
			});

		namef = new JFormattedTextField();
		namef.setColumns(10);
		namef.setBounds(113, 141, 116, 22);
		contentPane.add(namef);
		namef.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if ( (!(c>='a' && c<='z')&&!(c>='A' && c<='Z')) && (c != KeyEvent.VK_BACK_SPACE)) {
			         e.consume();  // ignore event
			      }
			   }
			});

		lastnamef = new JFormattedTextField();
		lastnamef.setColumns(10);
		lastnamef.setBounds(113, 176, 116, 22);
		contentPane.add(lastnamef);
		lastnamef.addKeyListener(new KeyAdapter() {
			   public void keyTyped(KeyEvent e) {
			      char c = e.getKeyChar();
			      if ( (!(c>='a' && c<='z')&&!(c>='A' && c<='Z')) && (c != KeyEvent.VK_BACK_SPACE)) {
			         e.consume();  // ignore event
			      }
			   }
			});

		JLabel lblNewLabel = new JLabel("User name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(12, 30, 102, 16);
		contentPane.add(lblNewLabel);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblPassword.setBounds(12, 69, 102, 16);
		contentPane.add(lblPassword);

		JLabel lblFlatId = new JLabel("Flat ID:");
		lblFlatId.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblFlatId.setBounds(12, 109, 102, 16);
		contentPane.add(lblFlatId);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblName.setBounds(12, 144, 102, 16);
		contentPane.add(lblName);

		JLabel lblLastName = new JLabel("Last name:");
		lblLastName.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblLastName.setBounds(12, 179, 102, 16);
		contentPane.add(lblLastName);

		pasf = new JPasswordField();
		pasf.setBounds(113, 66, 116, 22);
		contentPane.add(pasf);

		JButton btnNewButton = new JButton("Sign up");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				signQ(con);
			}
		});
		btnNewButton.setBounds(232, 216, 80, 25);
		contentPane.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Flats");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				flatsDisplay sflats = null;
				try {
					sflats = new flatsDisplay();
				} catch (ClassNotFoundException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				sflats.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(241, 105, 66, 25);
		contentPane.add(btnNewButton_1);
		
		JButton Cancelbt = new JButton("Cancel");
		Cancelbt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login lg = null;
				try {
					lg = new login();
					lg.setVisible(true);
					dispose();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		Cancelbt.setBounds(12, 216, 80, 25);
		contentPane.add(Cancelbt);
		setLocationRelativeTo(null);
	}

	private boolean checkfalt(String fid, Connection con) { // Check if flat id valid
		PreparedStatement ps;
		ResultSet rs;
		String query = "SELECT * FROM PayTheBills.Flat WHERE Flat_id='" + fid + "'";

		try {
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();
			if (!rs.next())
				return false;
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return true;
	}

	private boolean checkUsername(String uname, Connection con) { // Check if user name is already taken
		PreparedStatement ps;
		ResultSet rs;
		String query = "SELECT * FROM PayTheBills.Account WHERE UserName='" + uname + "'";

		try {
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();
			if (rs.next())
				return false;
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		return true;
	}

	private void fildstest(String uname, String pas, String fid, String name, String lastname)
			throws ClassNotFoundException {
		Connection con = dbConnection.instance().getConn();
		flatflag = checkfalt(fid, con);
		usernameflag = checkUsername(uname, con);
		if (uname.equals("") || pas.equals("") || fid.equals("") || name.equals("") || lastname.equals("")) // Some fields are empty
			JOptionPane.showMessageDialog(null, "Please fill all the filds", "Flat Error", 2);
		else {
			if (!flatflag)
				JOptionPane.showMessageDialog(null, "Incorrect Flat id", "Flat Error", 2);
			if (!usernameflag)
				JOptionPane.showMessageDialog(null, "This user name is already exist!", "Flat Error", 2);
		}
	}
	private void signQ(Connection con) {
		PreparedStatement ps;
		String uname = user.getText();
		String pas = new String(pasf.getPassword());
		String fid = flatid.getText();
		String name = namef.getText();
		String lastname = lastnamef.getText();
		try {
			fildstest(uname,pas,fid,name,lastname);
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		if (flatflag && usernameflag) {
			String query = "INSERT INTO PayTheBills.Account ( UserName, Password, Flat_id, Name, LastName) values(?,?,?,?,?) ";
			try {
				ps = con.prepareStatement(query);
				ps.setString(1, uname);
				ps.setString(2, pas);
				ps.setString(3, fid);
				ps.setString(4, name);
				ps.setString(5, lastname);
				int x = ps.executeUpdate();
				if (x > 0) {
					System.out.println("Acoount has been signedup sucessfully!");
					login blogin = null;
					dispose();
					try {
						blogin = new login();
					} catch (ClassNotFoundException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
					blogin.setVisible(true);
				} else
					System.out.println("Flat signup Failed.");
			} catch (Exception e1) {
				System.out.println(e1);
			}
		}
	}
}
