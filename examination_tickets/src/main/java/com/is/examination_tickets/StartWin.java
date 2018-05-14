package com.is.examination_tickets;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import net.miginfocom.swing.MigLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class StartWin extends JFrame {
	JFrame frame;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartWin window = new StartWin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public StartWin() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 256, 166);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JFrame.isDefaultLookAndFeelDecorated();
		JButton button = new JButton("Преподователь");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PassWin frame2 = null;
				frame2 = new PassWin();
				frame2.setVisible(true);
				frame.setVisible(false);
			}
		});
		frame.getContentPane().setLayout(new MigLayout("", "[241.00px]", "[56.00px][71px]"));
		JButton button_1 = new JButton("Студент");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StudentWin frame2 = null;
				try {
					frame2 = new StudentWin();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame2.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		frame.getContentPane().add(button_1, "cell 0 1,alignx center,aligny center");
		frame.getContentPane().add(button, "cell 0 0,alignx center,aligny center");
	}
}
