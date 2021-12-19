package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.SysData;
import model.FancyButton;
import model.Question;
import java.awt.Font;
import javax.swing.SwingConstants;

public class QuestionWindow extends JDialog {
	
	public boolean correct =false;
	public int level = 0;
	public QuestionWindow() {
		
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        
        getContentPane().add(buttonsC);
        
        int randumNum = ThreadLocalRandom.current().nextInt(0, 3);
        Question q = SysData.getInstance().getQuestionsList().get(randumNum);
        buttonsC.setLayout(null);
        JLabel qbody = new JLabel(q.getqBody());
        qbody.setHorizontalAlignment(SwingConstants.CENTER);
        qbody.setFont(new Font("Tahoma", Font.PLAIN, 20));
        qbody.setForeground(Color.ORANGE);
        qbody.setBounds(193, 13, 198, 41);
        setLevel(Integer.parseInt(q.getLevel()));
        buttonsC.add(qbody);
        
        
        
        FancyButton answer1 = new FancyButton(q.getAnswers().get(0));
        answer1.setHorizontalAlignment(SwingConstants.CENTER);
        answer1.setBounds(219, 67, 116, 22);
        answer1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCorrect(answer1.getText().equals(q.getAnswers().get(Integer.valueOf(q.getCorrectAnswer())-1)));
                dispose();
            }
        });
        buttonsC.add(answer1);
        
        FancyButton answer2 = new FancyButton(q.getAnswers().get(1));
        answer2.setHorizontalAlignment(SwingConstants.CENTER);
        answer2.setBounds(219, 102, 116, 22);
        answer2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setCorrect(answer2.getText().equals(q.getAnswers().get(Integer.valueOf(q.getCorrectAnswer())-1)));
                dispose();
            }
        });
        buttonsC.add(answer2);
        
        FancyButton answer3 = new FancyButton(q.getAnswers().get(2));
        answer3.setHorizontalAlignment(SwingConstants.CENTER);
        answer3.setBounds(219, 137, 116, 22);
        answer3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setCorrect(answer3.getText().equals(q.getAnswers().get(Integer.valueOf(q.getCorrectAnswer())-1)));
                dispose();
            }
        });
        buttonsC.add(answer3);
        
        FancyButton answer4 = new FancyButton(q.getAnswers().get(3));
        answer4.setHorizontalAlignment(SwingConstants.CENTER);
        answer4.setBounds(219, 172, 116, 22);
        answer4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setCorrect(answer4.getText().equals(q.getAnswers().get(Integer.valueOf(q.getCorrectAnswer())-1)));
                dispose();
            }
        });
        buttonsC.add(answer4);

        this.setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);    
	}
	private void setCorrect(boolean c) {
		this.correct = c;
	}
	public boolean getCorrect() {
		return this.correct;
	}
	public void setLevel(int l) {
		this.level = l;
	}
	public int getLevel() {
		return this.level;
	}

}
