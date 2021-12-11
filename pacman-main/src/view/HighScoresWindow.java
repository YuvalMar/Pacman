package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import model.FancyButton;

public class HighScoresWindow extends JFrame{

	public HighScoresWindow() {
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        
        FancyButton backBtn = new FancyButton("Back");
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartWindow sw = new StartWindow();
                dispose();
            }
        });
        buttonsC.add(backBtn);
        
        getContentPane().add(buttonsC);
        
        setVisible(true);
	}

}
