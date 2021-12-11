package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.FancyButton;

import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;

public class PlayerNameWindow extends JFrame {
	private JTextField textField;
	public PlayerNameWindow() {
		
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);

		JTextField playerName = new JTextField();
		playerName.setBounds(239, 75, 116, 22);
		buttonsC.add(playerName);
		playerName.setColumns(10);
		FancyButton btnNewButton = new FancyButton("Start Game");
		btnNewButton.setBounds(256, 143, 97, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PacWindow pw = new PacWindow(playerName.getText());
				dispose();
			}
		});
		buttonsC.setLayout(null);
		buttonsC.add(btnNewButton);
		getContentPane().add(buttonsC, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Player Name:");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setBounds(135, 78, 77, 16);
		buttonsC.add(lblNewLabel);
		setVisible(true);
	}
}
