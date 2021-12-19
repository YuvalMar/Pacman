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
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class ManageQuestionsWindow extends JFrame {
	private JTextArea qbody;
	private JTextField answer1;
	private JTextField answer2;
	private JTextField answer3;
	private JTextField answer4;
	private JComboBox<String> correctAnswer;
	private JComboBox<String> diff;
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
        list.setBounds(12, 13, 469, 270);
        buttonsC.add(list);
        
        qbody = new JTextArea();
//        qbody.setHorizontalAlignment(SwingConstants.LEFT);
        qbody.setLineWrap(true);
        qbody.setWrapStyleWord(true);
        qbody.setBounds(551, 13, 227, 72);
        buttonsC.add(qbody);
        qbody.setColumns(10);
        
        
        answer1 = new JTextField();
        answer1.setColumns(10);
        answer1.setBounds(601, 98, 116, 22);
        buttonsC.add(answer1);
        
        answer2 = new JTextField();
        answer2.setColumns(10);
        answer2.setBounds(601, 133, 116, 22);
        buttonsC.add(answer2);
        
        answer3 = new JTextField();
        answer3.setColumns(10);
        answer3.setBounds(601, 168, 116, 22);
        buttonsC.add(answer3);
        
        answer4 = new JTextField();
        answer4.setColumns(10);
        answer4.setBounds(601, 203, 116, 22);
        buttonsC.add(answer4);
        
        correctAnswer = new JComboBox<String>();
        correctAnswer.setBounds(601, 238, 50, 22);
        buttonsC.add(correctAnswer);
        correctAnswer.addItem("1");
        correctAnswer.addItem("2");
        correctAnswer.addItem("3");
        correctAnswer.addItem("4");
        correctAnswer.setSelectedIndex(-1);
        
        diff = new JComboBox<String>();
        diff.setBounds(601, 273, 50, 22);
        diff.addItem("1");
        diff.addItem("2");
        diff.addItem("3");
        diff.setSelectedIndex(-1);
        buttonsC.add(diff);
        
        FancyButton backBtn = new FancyButton("Back");
        FancyButton addBtn = new FancyButton("Add");
        FancyButton resetBtn = new FancyButton("Reset");
        addBtn.setBounds(22, 296, 50, 20);
        resetBtn.setBounds(663, 272, 50, 20);
        FancyButton editBtn = new FancyButton("Edit");
        editBtn.setLocation(142, 296);
        editBtn.setSize(45, 20);
        FancyButton deleteBtn = new FancyButton("Delete");
        deleteBtn.setLocation(82, 296);
        deleteBtn.setSize(45, 20);
        backBtn.setBounds(12, 387, 64, 16);
        resetBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new ManageQuestionsWindow();
				dispose();
				
			}
		});
        backBtn.addActionListener(new ActionListener() {
            @SuppressWarnings("unused")
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
						Question toAdd = new Question(diff.getSelectedItem().toString(), qbody.getText(), answers, correctAnswer.getSelectedItem().toString());
						SysData.getInstance().deleteQuestion(SysData.getInstance().getQuestionsList().get(list.getSelectedIndex()).getqBody());
						SysData.getInstance().addQuestion(toAdd.getqBody(), answers, correctAnswer.getSelectedItem().toString(), diff.getSelectedItem().toString());
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
						SysData.getInstance().addQuestion(qbody.getText(), answers, correctAnswer.getSelectedItem().toString(), diff.getSelectedItem().toString());
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
        
        JLabel lblNewLabel = new JLabel("Question");
        lblNewLabel.setForeground(Color.ORANGE);
        lblNewLabel.setBounds(493, 14, 56, 16);
        buttonsC.add(lblNewLabel);
        
        JLabel lblAnswer = new JLabel("Answer 1");
        lblAnswer.setForeground(Color.ORANGE);
        lblAnswer.setBounds(493, 98, 56, 16);
        buttonsC.add(lblAnswer);
        
        JLabel lblAnswer_2 = new JLabel("Answer 2");
        lblAnswer_2.setForeground(Color.ORANGE);
        lblAnswer_2.setBounds(493, 133, 56, 16);
        buttonsC.add(lblAnswer_2);
        
        JLabel lblAnswer_1_1 = new JLabel("Answer 3");
        lblAnswer_1_1.setForeground(Color.ORANGE);
        lblAnswer_1_1.setBounds(493, 168, 56, 16);
        buttonsC.add(lblAnswer_1_1);
        
        JLabel lblAnswer_1_1_1 = new JLabel("Answer 4");
        lblAnswer_1_1_1.setForeground(Color.ORANGE);
        lblAnswer_1_1_1.setBounds(493, 203, 56, 16);
        buttonsC.add(lblAnswer_1_1_1);
        
        JLabel lblAnswer_1_1_1_1 = new JLabel("Correct Answer");
        lblAnswer_1_1_1_1.setForeground(Color.ORANGE);
        lblAnswer_1_1_1_1.setBounds(493, 238, 89, 16);
        buttonsC.add(lblAnswer_1_1_1_1);
        
        JLabel lblAnswer_1_1_1_1_1 = new JLabel("Level");
        lblAnswer_1_1_1_1_1.setForeground(Color.ORANGE);
        lblAnswer_1_1_1_1_1.setBounds(493, 273, 89, 16);
        buttonsC.add(lblAnswer_1_1_1_1_1);
        

                       
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if (list.getSelectedIndex() != -1) {
					qbody.setText(list.getSelectedValue().toString());
					int ind = list.getSelectedIndex();
					answer1.setText(SysData.getInstance().getQuestionsList().get(ind).getAnswers().get(0));
					answer2.setText(SysData.getInstance().getQuestionsList().get(ind).getAnswers().get(1));
					answer3.setText(SysData.getInstance().getQuestionsList().get(ind).getAnswers().get(2));
					answer4.setText(SysData.getInstance().getQuestionsList().get(ind).getAnswers().get(3));
					correctAnswer.setSelectedItem(SysData.getInstance().getQuestionsList().get(ind).getCorrectAnswer());
					diff.setSelectedItem(SysData.getInstance().getQuestionsList().get(ind).getLevel());
				}
				
			}
		});
        
        setVisible(true);
        
	}
	public boolean checkIfNotEmpty() {
		return !qbody.getText().isEmpty() && !answer1.getText().isEmpty() && !answer2.getText().isEmpty()
				&& !answer3.getText().isEmpty() && !answer4.getText().isEmpty() && !(correctAnswer.getSelectedIndex() == -1)
				&& !(diff.getSelectedIndex() == -1);
	}
}
