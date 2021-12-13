package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

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
	
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	public QuestionWindow() {
		
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        
        getContentPane().add(buttonsC);
         
        Question q = SysData.getInstance().getQuestionsList().get(1);
        buttonsC.setLayout(null);
        JLabel qbody = new JLabel(q.getqBody());
        qbody.setHorizontalAlignment(SwingConstants.CENTER);
        qbody.setFont(new Font("Tahoma", Font.PLAIN, 20));
        qbody.setForeground(Color.ORANGE);
        qbody.setBounds(193, 13, 198, 41);
        buttonsC.add(qbody);
        
        
        
        FancyButton answer1 = new FancyButton(q.getAnswers().get(0));
        answer1.setHorizontalAlignment(SwingConstants.CENTER);
        answer1.setBounds(219, 67, 116, 22);
        buttonsC.add(answer1);
        
        FancyButton answer2 = new FancyButton(q.getAnswers().get(1));
        answer2.setHorizontalAlignment(SwingConstants.CENTER);
        answer2.setBounds(219, 102, 116, 22);
        buttonsC.add(answer2);
        
        FancyButton answer3 = new FancyButton(q.getAnswers().get(2));
        answer3.setHorizontalAlignment(SwingConstants.CENTER);
        answer3.setBounds(219, 137, 116, 22);
        buttonsC.add(answer3);
        
        FancyButton answer4 = new FancyButton(q.getAnswers().get(3));
        answer4.setHorizontalAlignment(SwingConstants.CENTER);
        answer4.setBounds(219, 172, 116, 22);
        buttonsC.add(answer4);

        this.setModalityType(ModalityType.APPLICATION_MODAL);
        setVisible(true);    
	}

}
