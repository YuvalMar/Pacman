package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	private JTextField qbody;
	private JTextField answer1;
	private JTextField answer2;
	private JTextField answer3;
	private JTextField answer4;
	private JTextField correctAnswer;
	private JTextField diff;
	public JList<Question> list;
	public ManageQuestionsWindow() {
		
        setSize(808,463);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        
		list = new JList<Question>(new Vector<Question>(SysData.getInstance().getQuestionsList()));
        list.setBackground(Color.ORANGE);
        list.setForeground(Color.RED);
        list.setBounds(12, 13, 311, 164);
        buttonsC.add(list);
        
        qbody = new JTextField();
        qbody.setBounds(383, 13, 116, 22);
        buttonsC.add(qbody);
        qbody.setColumns(10);
        
        
        answer1 = new JTextField();
        answer1.setColumns(10);
        answer1.setBounds(383, 48, 116, 22);
        buttonsC.add(answer1);
        
        answer2 = new JTextField();
        answer2.setColumns(10);
        answer2.setBounds(383, 83, 116, 22);
        buttonsC.add(answer2);
        
        answer3 = new JTextField();
        answer3.setColumns(10);
        answer3.setBounds(383, 118, 116, 22);
        buttonsC.add(answer3);
        
        answer4 = new JTextField();
        answer4.setColumns(10);
        answer4.setBounds(383, 155, 116, 22);
        buttonsC.add(answer4);
        
        correctAnswer = new JTextField();
        correctAnswer.setBounds(383, 187, 50, 22);
        buttonsC.add(correctAnswer);
        correctAnswer.setColumns(10);
        
        diff = new JTextField();
        diff.setColumns(10);
        diff.setBounds(449, 187, 50, 22);
        buttonsC.add(diff);
        
        FancyButton backBtn = new FancyButton("Back");
        FancyButton addBtn = new FancyButton("Add");
        FancyButton resetBtn = new FancyButton("Reset");
        addBtn.setBounds(197, 188, 50, 20);
        resetBtn.setBounds(22, 188, 50, 20);
        FancyButton editBtn = new FancyButton("Edit");
        editBtn.setLocation(150, 188);
        editBtn.setSize(45, 20);
        FancyButton deleteBtn = new FancyButton("Delete");
        deleteBtn.setLocation(82, 188);
        deleteBtn.setSize(45, 20);
        backBtn.setBounds(259, 190, 64, 16);
        resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManageQuestionsWindow();
				dispose();
				
			}
		});
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartWindow sw = new StartWindow();
                dispose();
            }
        });
        deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() != -1) {
					if(SysData.getInstance().deleteQuestion(list.getSelectedValue().qBody)) {
						JOptionPane.showMessageDialog(null, "Question Deleted");
						new ManageQuestionsWindow();
						dispose();
					}
					else
						JOptionPane.showMessageDialog(null, "Something went wrong :(");
				}else
					JOptionPane.showMessageDialog(null, "Select a question");
			}
		});
        
        editBtn.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				if (list.getSelectedIndex() != -1) {
					if(checkIfNotEmpty()) {
						ArrayList<String> answers = new ArrayList<String>();
						answers.add(answer1.getText());
						answers.add(answer2.getText());
						answers.add(answer3.getText());
						answers.add(answer4.getText());
						Question toAdd = new Question(diff.getText(), qbody.getText(), answers, correctAnswer.getText());
						SysData.getInstance().deleteQuestion(SysData.getInstance().getQuestionsList().get(list.getSelectedIndex()).getqBody());
						SysData.getInstance().addQuestion(toAdd.getqBody(), answers, correctAnswer.getText(), diff.getText());
						JOptionPane.showMessageDialog(null, "Question " + qbody.getText()+ " Edited");
						new ManageQuestionsWindow();
						dispose();
					}
				}	
			}
		});
        
        addBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex() == -1) {
					if(checkIfNotEmpty()) {
						ArrayList<String> answers = new ArrayList<String>();
						answers.add(answer1.getText());
						answers.add(answer2.getText());
						answers.add(answer3.getText());
						answers.add(answer4.getText());
						SysData.getInstance().addQuestion(qbody.getText(), answers, correctAnswer.getText(), diff.getText());
						JOptionPane.showMessageDialog(null, "Question " + qbody.getText()+ " Added");
						new ManageQuestionsWindow();
						dispose();
					}else
						JOptionPane.showMessageDialog(null, "Fill all fields!");
				}else
					JOptionPane.showMessageDialog(null, "Please reset fields to add a new question");
			}
		});
        buttonsC.setLayout(null);
        buttonsC.add(backBtn);
        buttonsC.add(addBtn);
        buttonsC.add(editBtn);
        buttonsC.add(deleteBtn);
        buttonsC.add(resetBtn);
        
        getContentPane().add(buttonsC);
                
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
	public boolean checkIfNotEmpty() {
		return !qbody.getText().isEmpty() && !answer1.getText().isEmpty() && !answer2.getText().isEmpty()
				&& !answer3.getText().isEmpty() && !answer4.getText().isEmpty() && !correctAnswer.getText().isEmpty()
				&& !diff.getText().isEmpty();
	}
}
