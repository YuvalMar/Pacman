package test;
import controller.SysData;
import model.Question;
import exceptions.NotFourAnswersException;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

class tests {

	//Test readJson() returns JSONArray
	@Test
	void test() {
		JSONArray arrTest = new JSONArray();
		assertTrue(SysData.readJson().getClass().equals(arrTest.getClass()));
	}
	
	//Test there are 3 questions in the JSON file
	@Test
	void test1() {
		assertTrue(SysData.readJson().size() == 3);
	}
	
	//Add new question and check the JSON file was updated
	@Test
	void test2() {
		ArrayList<String> answers = new ArrayList<String>();
		answers.add("A1");
		answers.add("A2");
		answers.add("A3");
		answers.add("A4");
		SysData.addQuestion("What?",answers, "A3", "4");
		assertTrue(SysData.readJson().size() == 4);
	}
	
	//Delete a question and check it was deleted
	@Test
	void test3() {
		SysData.deleteQuestion("What?");
		assertTrue(SysData.readJson().size() == 3);
	}
	
	//Assert deleteQuestion() returns false if we try to delete a non existent questions from the file.
	@Test
	void test4() {
		assertFalse(SysData.deleteQuestion("WHY?"));
	}

}
