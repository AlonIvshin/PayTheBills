
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.Button;
import java.awt.Color;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;

public class NewBill extends JFrame {

	private JPanel contentPane, panel = new JPanel();
	private JScrollPane scroll;
	private int x = 93;
	private int y = 0;
	private int myflatid;
	private int myid;
	private JTextField noticetf;
	private JTextField amounttf;
	private JLabel lblNewLabel;
	private Connection con = dbConnection.instance().getConn();
	private ArrayList<Integer> accountids = new ArrayList<Integer>();

	public NewBill(int myflatid, int myid) throws SQLException, ClassNotFoundException {
		setTitle("New Bill");
		this.myflatid = myflatid;
		this.myid = myid;
		int rows = 0;
		panel.setBackground(new Color(51, 204, 152));
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alon's PC\\eclipse-workspace\\PayTheBills\\Resources\\icons8-man-with-money-50.png"));
		panel.setBounds(-1, 103, 355, 350);
		PreparedStatement ps;
		ResultSet rs = null;
		String query = "SELECT Name, LastName, User_id FROM PayTheBills.Account WHERE Flat_id='" + myflatid + "'";
		try {
			ps = con.prepareStatement(query);

			rs = ps.executeQuery();

			while (rs.next()) {
				rows++;
				;
			}
			rs = ps.executeQuery();
		} catch (SQLException ex) {
			System.out.println(ex);
		}
		JTextField jt[] = new JTextField[rows];
		JLabel jl[] = new JLabel[rows];
		panel.setLayout(new GridLayout(rows, 2, 0, 10));
		insert(rows, jt, jl, rs);

		JLabel lblNotice = new JLabel("Comment:");
		lblNotice.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNotice.setBounds(12, 29, 97, 16);
		contentPane.add(lblNotice);

		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAmount.setBounds(12, 58, 73, 16);
		contentPane.add(lblAmount);

		noticetf = new JTextField();
		noticetf.setBounds(101, 27, 116, 22);
		contentPane.add(noticetf);
		noticetf.setColumns(10);

		amounttf = new JTextField();
		amounttf.setColumns(10);
		amounttf.setBounds(101, 56, 116, 22);
		amounttf.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (((c < '0') || (c > '9'))&&(c!='-') && (c != KeyEvent.VK_BACK_SPACE)) {
					e.consume(); // ignore event
				}
			}
		});

		contentPane.add(amounttf);

		JCheckBox cbox = new JCheckBox("Split even");
		cbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbox.isSelected())
					cBoxChecked(jt, amounttf);
				else
					cBoxUnchecked(jt, amounttf);
			}
		});
		cbox.setBounds(229, 55, 113, 25);
		contentPane.add(cbox);

		lblNewLabel = new JLabel("Insert how much every roommate should pay:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(12, 87, 318, 16);
		contentPane.add(lblNewLabel);

		Button btAdd = new Button("Add");
		btAdd.setBounds(10, 209, 79, 24);
		btAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) { //adds new bill to system
				PreparedStatement pst = null;
				try {
					if(amountTest(jt)) {
					addToBills(pst, jt);
					JOptionPane.showMessageDialog(null, "Bill has been added successfully", "Bill inseration", 1);
					}
					else
						JOptionPane.showMessageDialog(null, "Roommates bill split doesn't match to the inserted amount!", "Amount Error Error", 2);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		contentPane.add(btAdd);


		JButton btCancel = new JButton("Close");
		btCancel.setBounds(245, 240, 97, 25);
		btCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		contentPane.add(btCancel);
		setLocationRelativeTo(null);

	}

	public void insert(int rows, JTextField[] jt, JLabel[] jl, ResultSet rs) throws SQLException // update please
	{
		for (int i = 0; i < rows; i++) {
			if (rs.next())
			jl[i] = new JLabel(rs.getString(1) + " " + rs.getString(2));
			jl[i].setFont(new Font("Tahoma", Font.BOLD, 16));
			jl[i].setBounds(12, y, 150, 30);
			panel.add(jl[i]);
			jt[i] = new JTextField();
			jt[i].setBounds(x, y, 116, 22);
			jt[i].setText("0");
			jt[i].addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (((c < '0') || (c > '9'))&& c!='-' && (c != KeyEvent.VK_BACK_SPACE)) {
						e.consume(); // ignore event
					}
				}
			});
			panel.add(jt[i]);
			accountids.add(Integer.parseInt(rs.getString(3)));
			revalidate();
			repaint();
			y += 30;
		}
		scroll = new JScrollPane(panel);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(12, 103, 300, 100);
		contentPane = new JPanel(null);
		contentPane.setBackground(new Color(51, 204, 152));
		contentPane.setPreferredSize(new Dimension(345, 280));
		contentPane.add(scroll);
		setContentPane(contentPane);
		pack();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	public void cBoxChecked(JTextField[] jt, JTextField amounttf2) { // Splits amount to all textfields of roommates
		for (JTextField tf : jt) {
			tf.setText(String.valueOf(Float.parseFloat(amounttf2.getText()) / jt.length));
		}
	}

	public void cBoxUnchecked(JTextField[] jt, JTextField amounttf2) { // Erase amount from all textfields of roommates
		for (JTextField tf : jt) {
			tf.setText("0");
		}
	}

	// *** Inserts new row to Bills table. ***
	public void addToBills(PreparedStatement ps, JTextField[] jt) throws SQLException {
		String query = "INSERT INTO PayTheBills.Bill ( Flat_id, User_id, Amount, Comment) values(?,?,?,?) ";
		String amount = amounttf.getText();
		int billid;
		boolean flag = amount.equals("") ? false : true;
		if (!flag)
			JOptionPane.showMessageDialog(null, "Amount field is empty!", "Bill insert Error", 2);
		try {
			ps = con.prepareStatement(query);
			ps.setString(1, String.valueOf(myflatid));
			ps.setString(2, String.valueOf(myid));
			ps.setString(3, amounttf.getText());
			ps.setString(4, noticetf.getText());
			ps.executeUpdate();
		} catch (Exception e1) {
			System.out.println(e1);
		}

		updateTrascations( jt, lastBillId(ps));
	}

	// *** finds last bill id ***
	public int lastBillId(PreparedStatement ps) throws SQLException {
		String query = "SELECT MAX(bill_id) From PayTheBills.Bill";
		ResultSet rs = null;
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (rs.next()) {
			return rs.getInt(1);
		}
		return -1;
	}

	// ***Inserts new row to Transactions***
	public void updateTrascations(JTextField[] jt, int billid)
			throws NumberFormatException, SQLException {
		PreparedStatement ps;
		String query = "INSERT INTO PayTheBills.Transactions ( Bill_id, Account_id, Amount) values(?,?,?) ";
		for (int i = 0; i < accountids.size(); i++) {
			try {
				ps = con.prepareStatement(query);
				ps.setString(1, String.valueOf(billid));
				ps.setString(2, String.valueOf(accountids.get(i)));
				if(accountids.get(i)!=myid)
				ps.setString(3, String.valueOf(Float.parseFloat(jt[i].getText())*-1)); //fix it!
				else
					ps.setString(3, String.valueOf(Float.parseFloat(amounttf.getText().trim())-Float.parseFloat(jt[i].getText())));
				ps.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			updateAccount(accountids.get(i), Float.parseFloat(jt[i].getText().trim()));
		}
	}

	// ***update account's balance***
	public void updateAccount(int accountid, float f) throws SQLException {
		String query = "SELECT Balance FROM PayTheBills.Account WHERE User_id='" + accountid + "'";
		PreparedStatement ps = con.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		int mybalance = 0;
		if (rs.next())
			mybalance = rs.getInt(1);
		query = "UPDATE PayTheBills.Account SET Balance = ? WHERE User_id = ?";
		PreparedStatement pst = con.prepareStatement(query);
		if(accountid==myid)
			pst.setString(1,String.valueOf(mybalance+Float.parseFloat(amounttf.getText().trim())-f));
		else
		pst.setString(1, String.valueOf(mybalance + f * -1).trim());
		pst.setString(2, String.valueOf(accountid));
		pst.executeUpdate();
	}
	
	public boolean amountTest(JTextField[] jt) {
		float sum = 0;
		for (JTextField balance : jt) {
			sum+=Float.parseFloat(balance.getText());
		}
		if(sum!=Float.parseFloat(amounttf.getText())) {
			return false;
		}
		return true;
	}
}
