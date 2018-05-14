package com.is.examination_tickets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class PassWin extends JDialog {
	protected static final Component D = null;
	private final JPanel contentPanel = new JPanel();
	private JPasswordField passwordField;

	public static void main(String[] args) {
		try {
			PassWin dialog = new PassWin();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public PassWin() {
		setResizable(false);
		setBounds(100, 100, 253, 145);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new MigLayout("", "[151px,grow]", "[14px,grow][20px,grow]"));
		{
			JLabel label = new JLabel("Введите пароль");
			contentPanel.add(label, "cell 0 0,alignx center,aligny center");
		}
		{
			passwordField = new JPasswordField();
			passwordField.setHorizontalAlignment(SwingConstants.CENTER);
			contentPanel.add(passwordField, "cell 0 1,grow");
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String pass = passwordField.getText();
						if (pass.equals("1234")) {
							MainWin frame2 = null;
							try {
								frame2 = new MainWin();
							} catch (ClassNotFoundException e1) {
								e1.printStackTrace();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							frame2.setVisible(true);
							setVisible(false);
						} else
							JOptionPane.showMessageDialog(D, "Пароль неверный !", "Ошибка",
									JOptionPane.WARNING_MESSAGE);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Назад");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						StartWin frame2 = null;
						frame2 = new StartWin();
						frame2.frame.setVisible(true);
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Назад");
				buttonPane.add(cancelButton);
			}
		}
	}
}