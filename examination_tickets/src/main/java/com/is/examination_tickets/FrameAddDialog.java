package com.is.examination_tickets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FrameAddDialog extends JDialog {
	private static final long serialVersionUID = 1L;
	protected static final Component D = null;
	JTextPane textPane = new JTextPane();
	String text = "";

	public FrameAddDialog(final String s) {
		setTitle("Добавление");
		setBounds(100, 100, 380, 151);
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		{
			JLabel label = new JLabel("\t\tВведите название");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			getContentPane().add(label, BorderLayout.NORTH);
		}
		{
			getContentPane().add(textPane, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			textPane.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					char c = e.getKeyChar();
					if (s == "Дисциплина") {
						if (Character.toString(c).matches("[^а-яА-Я]+") && c != ' ' && c != '-') {
							e.consume();
						}
					}
					if (s == "Группа") {
						if (Character.toString(c).matches("[^а-яА-Я0-9]+") && c != ' ' && c != '-') {
							e.consume();
						}
					}
				}
			});
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (textPane.getText().trim().length() != 0) {
							text = textPane.getText();
							setVisible(false);
						} else {
							JOptionPane.showMessageDialog(D,
									"Поле ввода пустое! \n Введите название или нажмите \"Cancel\"", "Ошибка",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
