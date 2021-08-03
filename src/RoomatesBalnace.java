
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class RoomatesBalnace extends JFrame {

	private JPanel contentPane;
	private JPanel panel = new JPanel();
	private int myid;
	private int myflatid;
	private int rows = 0;
	private JLabel[] lbName;
	private JLabel[] lbBalnace;
	private int x = 93;
	private int y = 0;

	public RoomatesBalnace(int myid, int myflatid) throws ClassNotFoundException {
		this.myid = myid;
		this.myflatid = myflatid;
		setTitle("Rommates Balance");
		setResizable(false);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Alon's PC\\eclipse-workspace\\PayTheBills\\Resources\\icons8-man-with-money-50.png"));
		ResultSet rs;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 619, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(51, 204, 152));
		setContentPane(contentPane);

		JButton btnNewButton = new JButton("Close");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton.setBounds(12, 352, 97, 25);
		contentPane.add(btnNewButton);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(32, 32, 526, 307);
		contentPane.add(scrollPane);
		panel.setBackground(new Color(0, 204, 153));

		panel.setLayout(new GridLayout(rows, 2, 0, 10));
		scrollPane.setViewportView(panel);

		rs = calcRows();
		lbName = new JLabel[rows];
		lbBalnace = new JLabel[rows];
		myRoommates();

	}

	private ResultSet calcRows() throws ClassNotFoundException {
		PreparedStatement ps;
		ResultSet rs = null;
		Connection con = dbConnection.instance().getConn();
		String query = "SELECT Name,LastName,Balance FROM PayTheBills.Account WHERE Flat_id='" + myflatid
				+ "' && Flat_id !='" + myid + "'";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			while (rs.next()) {
				rows++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	private void myRoommates() throws ClassNotFoundException {
		PreparedStatement ps;
		ResultSet rs = null;
		Connection con = dbConnection.instance().getConn();
		String query = "SELECT Name,LastName,Balance FROM PayTheBills.Account WHERE Flat_id='" + myflatid
				+ "' && Flat_id !='" + myid + "'";
		try {
			ps = con.prepareStatement(query);
			rs = ps.executeQuery();
			for (int i = 0; i < rows; i++) {
				if (rs.next()) {
					lbName[i] = new JLabel(rs.getString(1) + " " + rs.getString(2));
				lbName[i].setFont(new Font("Tahoma", Font.BOLD, 18));
				lbName[i].setBounds(25, y, 150, 30);
				panel.add(lbName[i]);
				lbBalnace[i] = new JLabel(rs.getString(3));
				lbBalnace[i].setFont(new Font("Tahoma", Font.BOLD, 18));
				lbBalnace[i].setForeground(rs.getInt(3)>=0 ? Color.GREEN : Color.RED);
				lbBalnace[i].setBounds(50, y, 150, 30);
				panel.add(lbBalnace[i]);
				revalidate();
				repaint();
				y += 30;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
