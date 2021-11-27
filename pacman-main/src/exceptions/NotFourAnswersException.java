package exceptions;

public class NotFourAnswersException extends Exception {
	public NotFourAnswersException() {
		super("You need 4 answers!!!");
	}

}
