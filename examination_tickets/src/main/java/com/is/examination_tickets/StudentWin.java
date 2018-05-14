package com.is.examination_tickets;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import bd.conn;
import net.miginfocom.swing.MigLayout;
import java.awt.Dialog.ModalExclusionType;

public class StudentWin extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JList<String> list = new JList<String>();
	private JComboBox<String> comboBox = new JComboBox<String>();
	private JComboBox<String> comboBox_1 = new JComboBox<String>();
	JFrame frame;
	private conn db = new conn();

	void question() throws ClassNotFoundException, SQLException {
		int idDisciplin = MainWin.serchID(db, "Disciplin", (String) (comboBox.getSelectedItem()));
		ArrayList<String> listTicket = MainWin.readQuestionWHERE(db, idDisciplin);
		DefaultListModel<String> listModelTicket = new DefaultListModel<String>();
		for (int i = 0; i < listTicket.size(); i++) {
			listModelTicket.addElement(listTicket.get(i));
		}
		list.setModel(listModelTicket);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentWin window = new StudentWin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StudentWin() throws ClassNotFoundException, SQLException {
		initialize();
	}

	private String disciplin = null;

	private void initialize() throws ClassNotFoundException, SQLException {
		frame = new JFrame();
		frame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[111.00][120.00,grow]", "[142.00,grow,baseline]"));
		db.Conn();
		JScrollPane scrollPane = new JScrollPane();
		frame.getContentPane().add(scrollPane, "cell 1 0,grow");
		scrollPane.setViewportView(list);
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, "cell 0 0,grow");
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 28, 0, 42, 0 };
		gbl_panel.rowHeights = new int[] { 22, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		JLabel label = new JLabel("Предмет ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 1;
		gbc_label.gridy = 0;
		panel.add(label, gbc_label);
		ArrayList<String> listDisciplin = MainWin.readDisciplin(db);
		ArrayList<String> listGroup = MainWin.readGroup(db, disciplin);
		comboBox.setModel(new DefaultComboBoxModel(listDisciplin.toArray()));
		comboBox_1.setModel(new DefaultComboBoxModel(listGroup.toArray()));
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					question();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.gridwidth = 3;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 1;
		panel.add(comboBox, gbc_comboBox);
		try {
			question();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
