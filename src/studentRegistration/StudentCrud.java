package studentRegistration;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StudentCrud {

	private static Connection con;
	private PreparedStatement ps;
	private JFrame frmRegistration;
	private JTable table;
	private JTextField txtname;
	private JTextField txtmobile;
	private JTextField txtcourse;
	private ResultSet rest;
	private ResultSetMetaData rmd;
	private int col;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentCrud window = new StudentCrud();
					window.frmRegistration.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public StudentCrud() {
		initialize();
		creatConn();
		table_load();
	}

	public static Connection creatConn() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/SkillLync";
			String user = "root";
			String pass = "V2169@gpatil";

			con = DriverManager.getConnection(url, user, pass);

		} catch (ClassNotFoundException | SQLException e) {

			e.printStackTrace();
		}
		return con;
	}

	public void table_load() {

		try {
			ps = con.prepareStatement("select * from courseregistration");
			rest = ps.executeQuery();

//			rmd = rest.getMetaData();
//			col = rmd.getColumnCount();
//			DefaultTableModel dft = (DefaultTableModel) table.getModel();
//
//			dft.setRowCount(0);
//			Vector<String> v = new Vector<String>();
//			while (rest.next()) {
//				
//				for (int i = 1; i <= col; i++) 
//				{
//
//					v.add(rest.getString("Id"));
//					v.add(rest.getString("Name"));
//					v.add(rest.getString("Mobile"));
//					v.add(rest.getString("Course"));
//				}
//				dft.addRow(v);
//			}

			table.setModel(DbUtils.resultSetToTableModel(rest));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmRegistration = new JFrame();
		frmRegistration.setFont(new Font("Dialog", Font.BOLD, 16));
		frmRegistration.setTitle("Registration Form");
		frmRegistration.setBounds(100, 100, 920, 476);
		frmRegistration.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegistration.getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Student Registration");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Verdana Pro Semibold", Font.BOLD, 24));
		lblNewLabel.setBounds(230, 11, 391, 50);
		frmRegistration.getContentPane().add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Course Registration", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(20, 72, 446, 338);
		frmRegistration.getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Student Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1.setBounds(47, 45, 148, 39);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Mobile No.");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_1.setBounds(47, 104, 148, 39);
		panel.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_2 = new JLabel("Course");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel_1_2.setBounds(47, 160, 148, 39);
		panel.add(lblNewLabel_1_2);

		txtname = new JTextField();
		txtname.setFont(new Font("Verdana Pro Semibold", Font.PLAIN, 14));
		txtname.setBounds(204, 47, 216, 39);
		panel.add(txtname);
		txtname.setColumns(10);

		txtmobile = new JTextField();
		txtmobile.setFont(new Font("Verdana Pro Semibold", Font.BOLD, 14));
		txtmobile.setColumns(10);
		txtmobile.setBounds(204, 104, 216, 39);
		panel.add(txtmobile);

		txtcourse = new JTextField();
		txtcourse.setFont(new Font("Verdana Pro Semibold", Font.BOLD, 14));
		txtcourse.setColumns(10);
		txtcourse.setBounds(204, 162, 216, 39);
		panel.add(txtcourse);

		JButton btnadd = new JButton("Add");
		btnadd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String name = txtname.getText();
				String mobile = txtmobile.getText();
				String course = txtcourse.getText();

				if (name.equals("") && mobile.equals("") && course.equals("")) {
					JOptionPane.showMessageDialog(null, "Please Fill all the fields ...!");
				} else {
					try {
						String query = "insert into courseregistration (name, mobile, course) values(?, ?, ?)";
						ps = con.prepareStatement(query);
						ps.setString(1, name);
						ps.setString(2, mobile);
						ps.setString(3, course);
						ps.executeUpdate();

						JOptionPane.showMessageDialog(null, "Record Added");
						table_load();
						txtname.setText("");
						txtmobile.setText("");
						txtcourse.setText("");

						txtname.requestFocus();

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Mobile No Must be Numbers and Less than 11");
//						e1.printStackTrace();
					}
				}
			}
		});
		btnadd.setFont(new Font("Verdana Pro Cond Semibold", Font.BOLD, 16));
		btnadd.setBounds(47, 259, 89, 33);
		panel.add(btnadd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel dft = (DefaultTableModel) table.getModel();
				int index = table.getSelectedRow();
				if (index >= 0) {

					try {
						int id = Integer.parseInt(dft.getValueAt(index, 0).toString());
						int dialogresult = JOptionPane.showConfirmDialog(null, "Do you want to Delete the Record ?",
								"Warning", JOptionPane.YES_NO_OPTION);

						if (dialogresult == JOptionPane.YES_OPTION) {

							String query = "delete from courseregistration where id=? ";
							ps = con.prepareStatement(query);
							ps.setInt(1, id);
							ps.executeUpdate();

							JOptionPane.showMessageDialog(null, "Record Deleted ...!");
							table_load();
							txtname.setText("");
							txtmobile.setText("");
							txtcourse.setText("");
							txtname.requestFocus();
						}

					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} else
					JOptionPane.showMessageDialog(null, "Please Select Data to Delete First ...!");

			}
		});
		btnDelete.setFont(new Font("Verdana Pro Cond Semibold", Font.BOLD, 16));
		btnDelete.setBounds(191, 259, 89, 33);
		panel.add(btnDelete);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel dft = (DefaultTableModel) table.getModel();
				int index = table.getSelectedRow();
				if (index >= 0) {
					try {
						int id = Integer.parseInt(dft.getValueAt(index, 0).toString());
						String name = txtname.getText();
						String mobile = txtmobile.getText();
						String course = txtcourse.getText();

						String query = "update courseregistration set name=?, mobile=?, course=? where id=? ";
						ps = con.prepareStatement(query);
						ps.setString(1, name);
						ps.setString(2, mobile);
						ps.setString(3, course);
						ps.setInt(4, id);
						ps.executeUpdate();

						JOptionPane.showMessageDialog(null, "Record Updated ...!");
						table_load();
						txtname.setText("");
						txtmobile.setText("");
						txtcourse.setText("");

						txtname.requestFocus();

					} catch (SQLException e1) {
						JOptionPane.showMessageDialog(null, "Please Enter All Data ...!");
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please Select Data to Edit First ...!");
				}

			}
		});
		btnEdit.setFont(new Font("Verdana Pro Cond Semibold", Font.BOLD, 16));
		btnEdit.setBounds(331, 259, 89, 33);
		panel.add(btnEdit);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(487, 104, 407, 306);
		frmRegistration.getContentPane().add(scrollPane);

		table = new JTable();
		table.setFont(new Font("Verdana Pro Semibold", Font.BOLD, 13));
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel dft = (DefaultTableModel) table.getModel();
				int index = table.getSelectedRow();

				txtname.setText(dft.getValueAt(index, 1).toString());
				txtmobile.setText(dft.getValueAt(index, 2).toString());
				txtcourse.setText(dft.getValueAt(index, 3).toString());

			}
		});
		scrollPane.setViewportView(table);
	}
}
