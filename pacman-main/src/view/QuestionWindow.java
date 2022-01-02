package view;
/**
 * When player eats a fruit(question's item), this window will display a random question on the screen.
 * Question level is dependent on the fruit's color. 
 */
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import controller.SysData;
import model.FancyButton;
import model.Question;
import java.awt.Font;
import javax.swing.SwingConstants;
//@author - Yuval & Dor
@SuppressWarnings("serial")
public class QuestionWindow extends JDialog {
	
	public boolean correct =false;
	public int level = 0;
	public QuestionWindow(int type) {
		
        setSize(736,335);
        getContentPane().setBackground(Color.black);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel buttonsC = new JPanel();
        buttonsC.setBackground(Color.black);
        
        getContentPane().add(buttonsC);
        List<Question> filteredQuestionsList= SysData.getInstance().getQuestionsList().stream().filter(question -> question.getLevel().equals(String.valueOf(type))).collect(Collectors.toList());
        int questionsNum = filteredQuestionsList.size();
        int randumNum = ThreadLocalRandom.current().nextInt(0, questionsNum);
        Question q = filteredQuestionsList.get(randumNum);
        buttonsC.setLayout(null);
        JLabel qbody = new JLabel("<html>" + q.getqBody() + "</html>", JLabel.CENTER);
        qbody.setHorizontalAlignment(SwingConstants.CENTER);
        qbody.setFont(new Font("Tahoma", Font.PLAIN, 20));
        qbody.setForeground(Color.ORANGE);
        qbody.setBounds(12, 43, 694, 41);
        setLevel(Integer.parseInt(q.getLevel()));
        buttonsC.add(qbody);
        
        
        
        FancyButton answer1 = new FancyButton(q.getAnswers().get(0));
        answer1.setHorizontalAlignment(SwingConstants.CENTER);
        answer1.setBounds(12, 97, 694, 22);
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
        answer2.setBounds(12, 132, 694, 22);
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
        answer3.setBounds(12, 167, 694, 22);
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
        answer4.setBounds(12, 202, 694, 22);
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
