
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FlatBalance extends JFrame {

	private JPanel contentPane;
	private JTable balanceTable;
	private JTable billsTable;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JLabel lblNewLabel;
	private JLabel lblBalance= new JLabel("");
	private int myid;
	private int myflatid;
	private Connection con;

	public FlatBalance(int myid,int myflatid) throws ClassNotFoundException, SQLException {
		this.myid=myid;
		this.myflatid=myflatid;
		con = dbConnection.instance().getConn();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 768);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(51, 204, 152));
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alon's PC\\eclipse-workspace\\PayTheBills\\Resources\\icons8-man-with-money-50.png"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton btnAdd = new JButton("Add  Bill");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        	NewBill nb = null;
//			        dispose();
					try {
						nb = new NewBill(myflatid,myid);
						System.out.println(myid);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			        nb.setVisible(true);
				}
		});
		btnAdd.setBounds(12, 683, 97, 25);
		contentPane.add(btnAdd);
		
		JButton btnNewButton = new JButton("Delete Bill");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteRow();
				JOptionPane.showMessageDialog(null, "The bill has been deleted!", "Bill Deleted", 1);
			}
		});
		btnNewButton.setBounds(140, 683, 97, 25);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Log out");
		btnNewButton_1.addActionListener(new ActionListener() {
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
		btnNewButton_1.setBounds(500, 683, 97, 25);
		contentPane.add(btnNewButton_1);
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(503, 32, 503, 614);
		contentPane.add(scrollPane_1);
		balanceTable = new JTable();
		scrollPane_1.setViewportView(balanceTable);
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 32, 503, 614);
		contentPane.add(scrollPane);
		billsTable = new JTable();
		scrollPane.setViewportView(billsTable);
		lblNewLabel = new JLabel("My balance:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel.setBounds(800, 683, 152, 20);
		contentPane.add(lblNewLabel);	
		JLabel lblNewLabel_1 = new JLabel("Payed Bills");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(180, 3, 97, 16);
		contentPane.add(lblNewLabel_1);
		JLabel lblRommatesBalance = new JLabel("My Transactions");
		lblRommatesBalance.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRommatesBalance.setBounds(684, 3, 178, 16);
		contentPane.add(lblRommatesBalance);
		
		
		trTable("SELECT Bill.Date,Bill.Comment,Transactions.Amount FROM PayTheBills.Transactions,PayTheBills.Bill "
				+ "WHERE Transactions.Amount!=0 && Account_id='"+myid+"' && Bill.Bill_id=Transactions.Bill_id",balanceTable);
		//***Creating bills table***
		trTable("Select Bill_id, Date,Name, LastName, Amount, Comment FROM PayTheBills.Bill,PayTheBills.Account "
				+ "WHERE Account.User_id=Bill.User_id && Bill.Flat_id='" + myflatid + "'",billsTable);
		mybalanceUpdate();
		contentPane.repaint();
		
		JButton btnRoommatesBalance = new JButton("Roommates Balance");
		btnRoommatesBalance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				RoomatesBalnace rb = null;
				try {
					rb = new RoomatesBalnace(myid,myflatid);
				} catch (ClassNotFoundException e3) {
					e3.printStackTrace();
				}
		        rb.setVisible(true);
			}
		});
		btnRoommatesBalance.setBounds(270, 683, 164, 25);
		contentPane.add(btnRoommatesBalance);
		
		JButton btnNewButton_2 = new JButton("Refresh");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//***Creating account's payments table***
				trTable("SELECT Bill.Date,Bill.Comment,Transactions.Amount FROM PayTheBills.Transactions,PayTheBills.Bill "
						+ "WHERE Transactions.Amount!=0 && Account_id='"+myid+"' && Bill.Bill_id=Transactions.Bill_id",balanceTable);
				//***Creating bills table***
				trTable("Select Bill_id, Date,Name, LastName, Amount, Comment FROM PayTheBills.Bill,PayTheBills.Account "
						+ "WHERE Account.User_id=Bill.User_id && Bill.Flat_id='" + myflatid + "'",billsTable);
				try {
					mybalanceUpdate();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				contentPane.repaint();
			}
		});
		btnNewButton_2.setBounds(640, 683, 97, 25);
		contentPane.add(btnNewButton_2);
	}
	private void trTable(String sql,JTable table) {
		PreparedStatement ps;
		ResultSet rs;
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void mybalanceUpdate() throws SQLException {
		String balancesql = "SELECT Balance From PayTheBills.Account WHERE User_id='" + myid + "'";
		PreparedStatement ps;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(balancesql);
			rs = ps.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs.next()) {
		lblBalance.setText(String.valueOf(rs.getInt(1)));
		lblBalance.setForeground(rs.getInt(1)>=0 ? Color.darkGray : Color.RED);
		}
		lblBalance.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBalance.setBounds(920, 687, 56, 16);
		contentPane.add(lblBalance);
	}
	
	private void deleteRow() {
		String query;
		PreparedStatement ps;
		int row = billsTable.getSelectedRow();
		int id;
		if(row>=0) {
			id=(int) billsTable.getValueAt(row, 0);
			query = "DELETE FROM  PayTheBills.Bill WHERE Bill_id='"+id+"'";
			try {
				ps = con.prepareStatement(query);
				ps.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			returnMyBalance(id);
		}
		else
			JOptionPane.showMessageDialog(null, "Please select row!", "Row not selected", 2);
			
	}
	
	private void returnMyBalance(int id) {
		String query = "SELECT Account_id, Amount From PayTheBills.Transactions WHERE Bill_id='" + id + "'";
		PreparedStatement ps;
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while(rs.next()) {
				delBalance(rs.getInt(1), rs.getInt(2));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	private void delBalance(int id,int amount) {
		String query = "UPDATE PayTheBills.Account SET Balance=Balance - '" + amount  + "' WHERE User_id='"+id+"'";
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(query);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
