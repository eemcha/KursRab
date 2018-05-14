package com.is.examination_tickets;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import bd.conn;
import net.miginfocom.swing.MigLayout;

public class MainWin extends JFrame {
	private static final long serialVersionUID = 1L;
	protected static final Component D = null;
	private JPanel contentPane;
	private conn db = new conn();
	private String disciplin = null;
	private JComboBox<String> comboBox = new JComboBox<String>();
	private JComboBox<String> comboBox_1 = new JComboBox<String>();
	private JList<String> list = new JList<String>();
	private JList<String> list_1 = new JList<String>();
	private JEditorPane editorPane = new JEditorPane();
	private JTextField textField;
	private JTextField textField_1;

	static ArrayList<String> readDisciplin(conn db) throws ClassNotFoundException, SQLException {
		ArrayList<String[]> list = db.ReadDisciplin();
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			res.add(list.get(i)[1]);
		}
		return res;
	}

	static int serchID(conn db, String tabel, String d) throws ClassNotFoundException, SQLException {
		int id = 0;
		ArrayList<String[]> list;
		if ("Disciplin".equals(tabel))
			list = db.ReadDisciplin();
		else if ("Group".equals(tabel))
			list = db.ReadGroup();
		else if ("Question".equals(tabel))
			list = db.ReadQuestion();
		else
			return id = -1;
		for (int i = 0; i < list.size(); i++) {
			if (!"Question".equals(tabel)) {
				if (d.equals(list.get(i)[1]))
					id = Integer.parseInt(list.get(i)[0]);
			} else {
				if (d.equals(list.get(i)[2]))
					id = Integer.parseInt(list.get(i)[0]);
			}
		}
		return id;
	}

	static ArrayList<String> readGroup(conn db, String disciplin) throws ClassNotFoundException, SQLException {
		ArrayList<String[]> list = db.ReadGroup();
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			res.add(list.get(i)[1]);
		}
		return res;
	}

	static ArrayList<String> readQuestionWHERE(conn db, int Disciplin) throws ClassNotFoundException, SQLException {
		ArrayList<String[]> list = db.ReadQuestionWHERE(Disciplin);
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			res.add("Вопрос " + (i + 1) + " - " + list.get(i)[2]);
		}
		return res;
	}

	static ArrayList<String> readQuestionTextWHERE(conn db, int Disciplin) throws ClassNotFoundException, SQLException {
		ArrayList<String[]> list = db.ReadQuestionWHERE(Disciplin);
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			res.add(list.get(i)[2]);
		}
		return res;
	}

	static ArrayList<String> readTicketWHERE(conn db, int Disciplin, int Group)
			throws ClassNotFoundException, SQLException {
		ArrayList<String[]> list = db.ReadTicketWHERE(Disciplin, Group);
		ArrayList<String> res = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			res.add(list.get(i)[0] + " " + list.get(i)[1] + " " + list.get(i)[2] + " " + list.get(i)[3]);
		}
		return res;
	}

	static ArrayList<ArrayList<String>> readTicketWHERE2(conn db, int Disciplin, int Group)
			throws ClassNotFoundException, SQLException {
		ArrayList<String[]> list = db.ReadTicketWHERE(Disciplin, Group);
		ArrayList<String> res;
		ArrayList<ArrayList<String>> tmp = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < list.size(); i++) {
			String d = db.ReadQuestionWHEREidQuestion(Integer.parseInt(list.get(i)[3]))[2];
			res = new ArrayList<String>();
			res.add(d);
			res.add(list.get(i)[4]);
			tmp.add(res);
		}
		return tmp;
	}

	void question() throws ClassNotFoundException, SQLException {
		int idDisciplin = serchID(db, "Disciplin", (String) (comboBox.getSelectedItem()));
		ArrayList<String> listTicket = readQuestionWHERE(db, idDisciplin);
		DefaultListModel<String> listModelTicket = new DefaultListModel<String>();
		for (int i = 0; i < listTicket.size(); i++) {
			listModelTicket.addElement(listTicket.get(i));
		}
		list.setModel(listModelTicket);
	}

	void ticket() throws ClassNotFoundException, SQLException {
		int idDisciplin = serchID(db, "Disciplin", (String) (comboBox.getSelectedItem()));
		int idGroup = serchID(db, "Group", (String) (comboBox_1.getSelectedItem()));
		ArrayList<ArrayList<String>> ticket = readTicketWHERE2(db, idDisciplin, idGroup);
		DefaultListModel<String> listModelTicket2 = new DefaultListModel<String>();
		String bilet = "";
		list_1.clearSelection();
		int j = 0;
		for (int i = 0; i < ticket.size(); i++) {
			if (bilet.equals(ticket.get(i).get(1))) {
				listModelTicket2.addElement("Вопрос " + (j + 1) + " - " + ticket.get(i).get(0));
				j++;
			} else {
				bilet = ticket.get(i).get(1);
				j = 0;
				listModelTicket2.addElement("БИЛЕТ " + (Integer.parseInt(bilet) + 1));
				listModelTicket2.addElement("Вопрос " + (j + 1) + " - " + ticket.get(i).get(0));
				j++;
			}
		}
		list_1.setModel(listModelTicket2);
	}

	protected void SaveTicketToDb(conn db2, ArrayList<ArrayList<String>> ticket)
			throws ClassNotFoundException, SQLException {
		int idDisciplin = serchID(db, "Disciplin", (String) (comboBox.getSelectedItem()));
		int idGroup = serchID(db, "Group", (String) (comboBox_1.getSelectedItem()));
		db2.DelTicket(idDisciplin, idGroup);
		for (int i = 0; i < ticket.size(); i++) {
			for (int j = 0; j < ticket.get(i).size(); j++) {
				int idQuestion = serchID(db, "Question", ticket.get(i).get(j));
				db2.AddTicket(null, idDisciplin, idGroup, idQuestion, i);
			}
		}
	}

	protected void TicketOutput(ArrayList<ArrayList<String>> ticket) {
		DefaultListModel<String> listModelTicket = new DefaultListModel<String>();
		list_1.clearSelection();
		for (int i = 0; i < ticket.size(); i++) {
			listModelTicket.addElement("БИЛЕТ " + (i + 1));
			for (int j = 0; j < ticket.get(i).size(); j++) {
				listModelTicket.addElement("Вопрос " + (j + 1) + " - " + ticket.get(i).get(j));
			}
		}
		list_1.setModel(listModelTicket);
	}

	private void TicketOutputException(String s) {
		list_1.clearSelection();
		DefaultListModel<String> listModelTicket2 = new DefaultListModel<String>();
		listModelTicket2.addElement(s);
		list_1.setModel(listModelTicket2);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public MainWin() throws ClassNotFoundException, SQLException {
		setResizable(false);
		db.Conn();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 806, 474);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		JPanel panel = new JPanel();
		JPanel panel_4 = new JPanel();
		JLabel lblNewLabel = new JLabel("Дисциплины");
		JLabel label = new JLabel("Группы");
		JButton button_1 = new JButton("Добавить ");
		JButton button_2 = new JButton("Удалить");
		JButton button_3 = new JButton("Добавить");
		JButton button_4 = new JButton("Удалить");
		ArrayList<String> listDisciplin = readDisciplin(db);
		ArrayList<String> listGroup = readGroup(db, disciplin);
		editorPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				editorPane.setText("");
			}
		});
		editorPane.setText("Введите вопрос тут");
		editorPane.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (Character.toString(c).matches("[^а-яА-Я]+") && c != ' ' && c != '-' && c != '?') {
					e.consume();
				}
			}
		});
		comboBox.setModel(new DefaultComboBoxModel(listDisciplin.toArray()));
		comboBox.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent arg0) {
				disciplin = (String) (comboBox.getSelectedItem());
				try {
					question();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					ticket();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					question();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					ticket();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		comboBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					question();
					ticket();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		comboBox_1.addHierarchyListener(new HierarchyListener() {
			public void hierarchyChanged(HierarchyEvent arg0) {
				try {
					ticket();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		comboBox_1.setModel(new DefaultComboBoxModel(listGroup.toArray()));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FrameAddDialog dialog = new FrameAddDialog("Дисциплина");
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				try {
					if (dialog.text.trim().length() != 0) {
						db.AddDisciplin(null, dialog.text);
						comboBox.addItem(dialog.text);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		button_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					db.DelDisciplin(null, (String) (comboBox.getSelectedItem()));
					comboBox.removeItem(comboBox.getSelectedItem());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});
		button_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				FrameAddDialog dialog = new FrameAddDialog("Группа");
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
				try {
					if (dialog.text.trim().length() != 0) {
						db.AddGroup(null, dialog.text);
						comboBox_1.addItem(dialog.text);
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		button_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					db.DelGroup(null, (String) (comboBox_1.getSelectedItem()));
					comboBox_1.removeItem(comboBox_1.getSelectedItem());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_1, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
						.addComponent(button_2, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
						.addComponent(button_4, GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE))
				.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(lblNewLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(button_1)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(button_2)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(label)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(button_3)
						.addPreferredGap(ComponentPlacement.RELATED).addComponent(button_4)
						.addContainerGap(105, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		panel_4.setLayout(new BorderLayout(0, 0));
		question();
		contentPane.setLayout(new MigLayout("", "[113px,fill][14.00px][337.00px][5px][10px]", "[322.00px][51.00px]"));
		contentPane.add(panel, "flowx,cell 0 0,alignx left,growy");
		JPanel panel_1 = new JPanel();
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportView(list_1);
		contentPane.add(panel_1, "cell 2 0,grow");
		panel_1.setLayout(new MigLayout("", "[326px]", "[322px]"));
		panel_1.add(scrollPane_1, "cell 0 0,grow");
		JLabel label_1 = new JLabel("Билеты");
		scrollPane_1.setColumnHeaderView(label_1);
		JPanel panel_2 = new JPanel();
		JButton button = new JButton("Удалить");
		JButton btnNewButton_1 = new JButton("Добавить");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int idDisciplin = 0;
				try {
					idDisciplin = serchID(db, "Disciplin", (String) (comboBox.getSelectedItem()));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				String TextQuestion = editorPane.getText();
				try {
					if (TextQuestion.trim().length() != 0) {
						db.AddQuestion(null, idDisciplin, TextQuestion);
						question();
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String TextQuestion = "";
				int length = (list.getSelectedValue().split(" ")).length;
				for (int i = 3; i < length; i++)
					if (i == length - 1)
						TextQuestion = TextQuestion + (list.getSelectedValue().split(" "))[i];
					else
						TextQuestion = TextQuestion + (list.getSelectedValue().split(" "))[i] + " ";
				try {
					db.DelQuestion(null, TextQuestion);
					question();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		JLabel label_4 = new JLabel("Вопросы");
		JButton btnNewButton = new JButton("Удалить все вопросы");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idDisciplin = serchID(db, "Disciplin", (String) (comboBox.getSelectedItem()));
					db.DelQuestionD(idDisciplin);
				} catch (SQLException e2) {
					e2.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
				try {
					question();
					ticket();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup().addGap(3).addComponent(label_4,
								GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup().addContainerGap()
								.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
										.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(button, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup().addComponent(label_4)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(editorPane, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
								.addGroup(gl_panel_2.createSequentialGroup().addComponent(btnNewButton_1)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(button)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnNewButton)))
						.addContainerGap()));
		panel_2.setLayout(gl_panel_2);
		contentPane.add(panel_2, "flowx,cell 0 1,alignx left,aligny center");
		contentPane.add(panel_4, "flowx,cell 0 1,alignx right,aligny top");
		JLabel lblNewLabel_1 = new JLabel("Вопросы");
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 0,grow");
		scrollPane.setViewportView(list);
		list.setSelectedIndex(0);
		scrollPane.setColumnHeaderView(lblNewLabel_1);
		JPanel panel_5 = new JPanel();
		JLabel label_2 = new JLabel("Колличесво билетов");
		JLabel label_3 = new JLabel("Колличесво вопросов в билете");
		JButton button_5 = new JButton("Сгенерировать билеты");
		button_5.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (textField.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(D, "Вы должны ввести число!", "Ошибка", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (textField_1.getText().trim().length() == 0) {
					JOptionPane.showMessageDialog(D, "Вы должны ввести число!", "Ошибка", JOptionPane.WARNING_MESSAGE);
					return;
				}
				int numberOfTickets;
				int questionsInTickets;
				TicketOutputException("Билеты генерируются...");
				Integer.parseInt(textField.getText());
				Integer.parseInt(textField_1.getText());
				ArrayList<String> questions = null;
				ArrayList<ArrayList<String>> Ticket = null;
				try {
					questions = readQuestionTextWHERE(db,
							serchID(db, "Disciplin", (String) (comboBox.getSelectedItem())));
				} catch (ClassNotFoundException e2) {
					e2.printStackTrace();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
				numberOfTickets = Integer.parseInt(textField.getText());
				questionsInTickets = Integer.parseInt(textField_1.getText());
				Ticket = TicketGenerator.generator(questions, numberOfTickets, questionsInTickets);
				if (Ticket != null) {
					try {
						SaveTicketToDb(db, Ticket);
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					TicketOutput(Ticket);
				} else {
					JOptionPane.showMessageDialog(D, "Слишком мало вопросов!", "Ошибка", JOptionPane.WARNING_MESSAGE);
					try {
						ticket();
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		textField = new JTextField();
		textField.setColumns(10);
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (Character.toString(c).matches("[^0-9]+")) {
					e.consume();
				}
			}
		});
		textField_1.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (Character.toString(c).matches("[^0-9]+")) {
					e.consume();
				} else {
					if (!(textField_1.getText() + c).equals("")) {
						int A = Integer.parseInt(textField_1.getText() + c);
						int B = list.getModel().getSize();
						if (A > B) {
							JOptionPane.showMessageDialog(D, "Слишком мало вопросов!", "Ошибка",
									JOptionPane.WARNING_MESSAGE);
							e.consume();
						}
					}
				}
			}
		});
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_5
				.createSequentialGroup().addContainerGap()
				.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addComponent(button_5)
						.addGroup(Alignment.TRAILING, gl_panel_5.createSequentialGroup()
								.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addComponent(label_2)
										.addComponent(label_3))
								.addPreferredGap(ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING, false)
										.addComponent(textField_1, 0, 0, Short.MAX_VALUE)
										.addComponent(textField, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE))))
				.addContainerGap()));
		gl_panel_5.setVerticalGroup(gl_panel_5.createParallelGroup(Alignment.LEADING).addGroup(gl_panel_5
				.createSequentialGroup().addGap(6).addComponent(button_5).addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE).addComponent(label_2).addComponent(
						textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGap(6)
				.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE).addComponent(label_3).addComponent(
						textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		panel_5.setLayout(gl_panel_5);
		contentPane.add(panel_5, "cell 2 1,grow");
	}
}
