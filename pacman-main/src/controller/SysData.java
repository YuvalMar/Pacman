package controller;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import exceptions.NotFourAnswersException;
import model.Answer;
import model.Question;


/**
 *This class defines reading to JSON files and writing to it.
 */

public class SysData {
	
	private ArrayList<Question> questions = new ArrayList<Question>();
	private ArrayList<Question> currentGameQuestions = new ArrayList<>();
	public String path = "src/controller/q.json";


	private static SysData instance;

	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}

	/**
	 * Read JSON questions file and parse to string variables.
	 * Every iterations adds the question to the questions ArrayList declared in line 29(ArrayList<Question>).
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public  void readJson() {
		try {
			ArrayList<String> answers = new ArrayList<String>();
			ArrayList<String> ans = new ArrayList<String>();
			ans.add("1");
			ans.add("2");
			ans.add("3");
			ans.add("4");
			String question = "";
			String correctAnswer = "";
			String level = "";
			String team = "";
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(path));
			JSONArray arr = (JSONArray) obj.get("questions");
			ArrayList<Question> q2 = new ArrayList<>();
			Iterator<JSONObject> iterator = arr.iterator();
			while (iterator.hasNext()) {
				JSONObject temp = iterator.next();
				question = (String) temp.get("question");
				correctAnswer = (String) temp.get("correct_ans");
				level = (String) temp.get("level");
				team = (String) temp.get("team");
				JSONArray tempAnswers = (JSONArray) temp.get("answers");
				for( int i=0; i<tempAnswers.size(); i++) {
					answers.add((String) tempAnswers.get(i));
					
				}
//				Iterator<String> iterator2 = tempAnswers.iterator();
//				while (iterator2.hasNext()) {
//					answers.add(iterator2.next());
//				}
//				
//				questions = arr;
				Question q = new Question(new Point(0,0), level, question, answers, correctAnswer);
				questions.add(q);
				answers.clear();
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
		return;
	}
	
	
	/**
	 * @param question
	 * @param answers
	 * @param correctAnswer
	 * @param level
	 * @return
	 * Convert the question, answers list, correct answer and level from String to JSON.
	 */
	public  boolean addQuestion(String question, ArrayList<String> answers, String correctAnswer, String level) {
		try {
			if(answers.size() != 4)
				throw new NotFourAnswersException();
			JSONObject q = (JSONObject) new JSONParser().parse(new FileReader(path));
			JSONArray arr = (JSONArray) q.get("questions");
			JSONObject obj = new JSONObject();
			obj.put("question", question);
			// create the new question
			JSONArray temp = new JSONArray(); //answers list
			for (String a : answers)
				temp.add(a);
			obj.put("answers", temp);
			obj.put("correct_ans", correctAnswer);
			obj.put("level", level);
			obj.put("team", "Wolf");
			arr.add(obj);
			// create a new json object to hold the array and the array key
			JSONObject toAdd = new JSONObject();
			toAdd.put("questions", arr);
			// write json object to file
			FileWriter file = new FileWriter(path, false);
			file.write(toAdd.toJSONString());
			file.flush();
			file.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return false;
		} catch (NotFourAnswersException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	// Loop through all the questions untill it matches the wanted question to delete.
	public  boolean deleteQuestion(String question) {
		boolean bool = false;
		try {
			JSONObject q = (JSONObject) new JSONParser().parse(new FileReader(path));
			JSONArray arr = (JSONArray) q.get("questions");
			for(int i=0; i< arr.size(); i++) {
				JSONObject toDelete = (JSONObject) arr.get(i);
				String qToDelete = (String) toDelete.get("question");
				if(qToDelete.equals(question)) {
					bool = true;
					arr.remove(i);
					// create a new json object to hold the array and the array key
					JSONObject toAdd = new JSONObject();
					toAdd.put("questions", arr);
					// write json object to file
					FileWriter file = new FileWriter(path, false);
					file.write(toAdd.toJSONString());
					file.flush();
					file.close();
				}
			}	
		}catch (IOException | ParseException e) {
			e.printStackTrace();
			return false;
		}
		return bool;
		
	}

	public  ArrayList<Question> getQuestionsList(){
		questions.clear();
		readJson();
		return questions;
	}
	
	
	public  String getQuestions(int index) {
		return getQuestionsList().get(index).getAnswers().get(0);
	}
	
	
}







