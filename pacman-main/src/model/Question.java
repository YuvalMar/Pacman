package model;

import java.util.ArrayList;

public class Question {
    
    public String level;
    public String qBody;
    public ArrayList<String> answers = new ArrayList<String>();
    public String correctAns;
    

    public Question(String level, String qBody, ArrayList<String> answers, String correct) {
        this.level = level;
        this.qBody = qBody;
        for(String a : answers)
        	this.answers.add(a);
        this.correctAns = correct;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getqBody() {
        return qBody;
    }

    public void setqBody(String qBody) {
        this.qBody = qBody;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

	@Override
	public String toString() {
		return  qBody;
	}
	
	public String getCorrectAnswer() {
		return this.correctAns;
	}
    
}
