package exceptions;

/**
 * Custom exception when building a question without 4 answers.
 * @author Berko
 *
 */

public class NotFourAnswersException extends Exception {
	public NotFourAnswersException() {
		super("You need 4 answers!!!");
	}

}
