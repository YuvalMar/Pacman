package model;

import java.awt.*;

public class Question {
    public enum difficulty {
        EASY, MEDIUM, HARD
    }
    public int id;
    public Point position;
    public difficulty diff;
    public String qBody;
    public Answer[] answers;

    public Question(int id, Point position, difficulty diff, String qBody, Answer[] answers) {
        this.id = id;
        this.position = position;
        this.diff = diff;
        this.qBody = qBody;
        this.answers = answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public difficulty getDiff() {
        return diff;
    }

    public void setDiff(difficulty diff) {
        this.diff = diff;
    }

    public String getqBody() {
        return qBody;
    }

    public void setqBody(String qBody) {
        this.qBody = qBody;
    }

    public Answer[] getAnswers() {
        return answers;
    }

    public void setAnswers(Answer[] answers) {
        this.answers = answers;
    }
}
