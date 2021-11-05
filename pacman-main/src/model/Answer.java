package model;

public class Answer {
    public int id;
    public String aBody;
    public boolean isCorrect;

    public Answer(int id, String aBody, boolean isCorrect) {
        this.id = id;
        this.aBody = aBody;
        this.isCorrect = isCorrect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getaBody() {
        return aBody;
    }

    public void setaBody(String aBody) {
        this.aBody = aBody;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
