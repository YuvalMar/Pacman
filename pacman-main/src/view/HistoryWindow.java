package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import controller.SysData;
import model.FancyButton;
import model.Question;

public class HistoryWindow extends JFrame{
	
	public HistoryWindow() {
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        buttonsC.setLayout(null);  
        FancyButton backBtn = new FancyButton("Back");
        backBtn.setBounds(488, 224, 54, 16);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartWindow sw = new StartWindow();
                dispose();
            }
        });
        getContentPane().add(buttonsC);
        SysData.getInstance().readJson();
		JList<String> list = new JList<String>(new Vector<String>(SysData.getInstance().getHistory()));
		System.out.println(SysData.getInstance().getHistory());
        list.setBackground(Color.ORANGE);
        list.setForeground(Color.RED);
        list.setBounds(12, 13, 311, 164);
        buttonsC.add(list);
        buttonsC.add(backBtn);
        

        setVisible(true);
	}

}
