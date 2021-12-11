package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import controller.SysData;
import misc.MapEditor;
import model.FancyButton;
import model.Question;
import javax.swing.JTextField;

public class ManageQuestionsWindow extends JFrame {
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	public ManageQuestionsWindow() {
		
        setSize(600,300);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        
        FancyButton backBtn = new FancyButton("Back");
        backBtn.setBounds(259, 190, 64, 16);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartWindow sw = new StartWindow();
                dispose();
            }
        });
        buttonsC.setLayout(null);
        buttonsC.add(backBtn);
        
        getContentPane().add(buttonsC);
        

		//        JList<Question> list = new JList<Question>();
//        list.setBackground(Color.ORANGE);
//        list.setForeground(Color.RED);
//        list.setBounds(12, 13, 311, 164);
//        buttonsC.add(list);
//        
//        DefaultListModel<Question> listModel = new DefaultListModel<Question>();
//        SysData.getInstance();
//		for(Question q : SysData.readJson()) {
//        	listModel.addElement(q);
//        }
//        
//        list.setModel(listModel);
        
		JList<Question> list = new JList<Question>(new Vector<Question>(SysData.getInstance().getQuestionsList()));
        list.setBackground(Color.ORANGE);
        list.setForeground(Color.RED);
        list.setBounds(12, 13, 311, 164);
        buttonsC.add(list);
        
        JTextField qbody = new JTextField();
        qbody.setBounds(383, 13, 116, 22);
        buttonsC.add(qbody);
        qbody.setColumns(10);
        
        
        JTextField answer1 = new JTextField();
        answer1.setColumns(10);
        answer1.setBounds(383, 48, 116, 22);
        buttonsC.add(answer1);
        
        JTextField answer2 = new JTextField();
        answer2.setColumns(10);
        answer2.setBounds(383, 83, 116, 22);
        buttonsC.add(answer2);
        
        JTextField answer3 = new JTextField();
        answer3.setColumns(10);
        answer3.setBounds(383, 118, 116, 22);
        buttonsC.add(answer3);
        
        JTextField answer4 = new JTextField();
        answer4.setColumns(10);
        answer4.setBounds(383, 155, 116, 22);
        buttonsC.add(answer4);
        
        JTextField correctAnswer = new JTextField();
        correctAnswer.setBounds(383, 187, 50, 22);
        buttonsC.add(correctAnswer);
        correctAnswer.setColumns(10);
        
        JTextField diff = new JTextField();
        diff.setColumns(10);
        diff.setBounds(449, 187, 50, 22);
        buttonsC.add(diff);
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedIndex() != -1) {
					qbody.setText(list.getSelectedValue().toString());
					int ind = list.getSelectedIndex();
					answer1.setText(SysData.getInstance().getQuestionsList().get(ind).getAnswers().get(0));
					answer2.setText(SysData.getInstance().getQuestionsList().get(ind).getAnswers().get(1));
					answer3.setText(SysData.getInstance().getQuestionsList().get(ind).getAnswers().get(2));
					answer4.setText(SysData.getInstance().getQuestionsList().get(ind).getAnswers().get(3));
					correctAnswer.setText(SysData.getInstance().getQuestionsList().get(ind).getCorrectAnswer());
					diff.setText(SysData.getInstance().getQuestionsList().get(ind).getLevel());
				}
				
			}
		});
        
        setVisible(true);
        
	}
}
