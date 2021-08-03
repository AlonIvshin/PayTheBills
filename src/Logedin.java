
import java.awt.Color;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Logedin extends JFrame {
	private JPanel contentPane;
	private int myid;
	private int myflatid;
	private String myname;
	private JLabel lblname;
	private JButton btnNewButton_1;
	
	//INNER class
	class FlatDialog extends JDialog {
		private JPasswordField pasf;
		private int myid;
		private int myflatid;

		public FlatDialog(int myid,int myflatid) throws ClassNotFoundException {
			this.myid=myid;
			this.myflatid=myflatid;
			Connection con = dbConnection.instance().getConn();
			setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alon's PC\\eclipse-workspace\\PayTheBills\\Resources\\icons8-man-with-money-50.png"));
			setBounds(100, 100, 428, 167);
			getContentPane().setLayout(null);
			
			contentPane.setBackground(new Color(51, 204, 152));
			JLabel lblNewLabel = new JLabel("Please enter yours flat password");
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblNewLabel.setBounds(73, 23, 267, 16);
			getContentPane().add(lblNewLabel);
			
			pasf = new JPasswordField();
			pasf.setBounds(77, 52, 263, 22);
			getContentPane().add(pasf);
			
			JButton btnNewButton = new JButton("Ok");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					PreparedStatement ps;
					ResultSet rs;
			        String pas = new String(pasf.getPassword()); 
			        String query ="SELECT * FROM PayTheBills.Flat WHERE Flat_id='" + myflatid + "' && Flat_password='" + pas + "'";
			        if(pas.equals(""))
			        	JOptionPane.showMessageDialog(null, "No passwored has enterd!", "Password Error", 2);
			        else
			       try {
			    	   ps = con.prepareStatement(query);

			        rs = ps.executeQuery();
			        if (rs.next()) { //User entered correct flat password
			        	dispose();
			        	FlatBalance fb = null;
						fb = new FlatBalance(myid,myflatid);
				        fb.setVisible(true);
				        //con.close();
			        }
			        else {
			        	JOptionPane.showMessageDialog(null, "Incorrect flat Password, please try again", "Login Failed", 2);
			        }
				} catch (SQLException ex) {
					System.out.println(ex);
		        } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			});
			btnNewButton.setBounds(174, 82, 54, 25);
			getContentPane().add(btnNewButton);
			setLocationRelativeTo(null);
		}
	}
	//End of inner class
	
	public Logedin(int myid,String myname,int myflatid) {
		setTitle("Pay The Bills");
		this.myid=myid;
		this.myname=myname;
		this.myflatid=myflatid;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alon's PC\\eclipse-workspace\\PayTheBills\\Resources\\icons8-man-with-money-50.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 354, 183);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 204, 152));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		lblname = new JLabel("Hello "+myname);
		//System.out.println(this.myname);
		lblname.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblname.setBounds(12, 34, 293, 16);
		contentPane.add(lblname);
		
		JButton btnNewButton = new JButton("My flat");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					FlatDialog fd = new FlatDialog(myid, myflatid);
					fd.setVisible(true);
					dispose();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(12, 90, 97, 25);
		contentPane.add(btnNewButton);
		
		btnNewButton_1 = new JButton("Change flat");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SwapFlat sf = new SwapFlat(myid);
					sf.setVisible(true);
				}catch (ClassNotFoundException e3) {
					e3.printStackTrace();
				}
			}
		});
		btnNewButton_1.setBounds(210, 90, 114, 25);
		contentPane.add(btnNewButton_1);
	}
}
