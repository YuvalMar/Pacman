package view;
import javax.swing.ButtonGroup;
/*
 * In this window the player will enter his nickname.
 * Nickname must be between 2 and 10 characters, else there will be a message.
 */
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import model.FancyButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JRadioButton;

@SuppressWarnings("serial")
public class PlayerNameWindow extends JFrame {

	public PlayerNameWindow() {
		
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);

		JTextField playerName = new JTextField();
		playerName.setBounds(239, 75, 137, 22);
		buttonsC.add(playerName);
		playerName.setColumns(10);
		
		FancyButton btnNewButton = new FancyButton("Start Game");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 20));
		FancyButton backBtn = new FancyButton("Back");
		backBtn.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnNewButton.setBounds(239, 175, 137, 25);
		backBtn.setBounds(473, 215, 97, 25);
		
		JRadioButton rdbtnPacman = new JRadioButton("Pacman");
		rdbtnPacman.setForeground(Color.ORANGE);
		rdbtnPacman.setBounds(239, 109, 137, 25);
		rdbtnPacman.setOpaque(false);
		rdbtnPacman.setSelected(true);
		buttonsC.add(rdbtnPacman);
		
		JRadioButton rdbtnPacwoman = new JRadioButton("Pacwoman");
		rdbtnPacwoman.setForeground(Color.ORANGE);
		rdbtnPacwoman.setBounds(239, 141, 137, 25);
		rdbtnPacwoman.setOpaque(false);
		buttonsC.add(rdbtnPacwoman);
		
		ButtonGroup g = new ButtonGroup();
		g.add(rdbtnPacwoman);
		g.add(rdbtnPacman);
		
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unused")
			public void actionPerformed(ActionEvent arg0) {
				if(playerName.getText().length()<2 || playerName.getText().length()>10)
					JOptionPane.showMessageDialog(null, "Nickname must be between 2 and 10 characters");
				else {
					PacWindow pw = new PacWindow(playerName.getText(), rdbtnPacwoman.isSelected());
					dispose();
				}
			}
		});
		backBtn.addActionListener(new ActionListener() {
			
			@SuppressWarnings("unused")
			@Override
			public void actionPerformed(ActionEvent e) {
				StartWindow s = new StartWindow();
				dispose();
				
			}
		});
		buttonsC.setLayout(null);
		buttonsC.add(btnNewButton);
		buttonsC.add(backBtn);
		getContentPane().add(buttonsC, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("Nickname");
		lblNewLabel.setForeground(Color.ORANGE);
		lblNewLabel.setBounds(150, 78, 77, 16);
		buttonsC.add(lblNewLabel);
		

		setVisible(true);
	}
}
