package controller;

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


import model.Question;

public class SysData {
	
	
	
	private static ArrayList<Question> questions = new ArrayList<>();
	private ArrayList<Question> currentGameQuestions = new ArrayList<>();


	private static SysData instance;

	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}

	/**
	 * Read json file
	 */
	@SuppressWarnings("unchecked")
	public static void readJson() {
		try {
			ArrayList<String> answers = new ArrayList<>();
			String question = "";
			String correctAnswer = "";
			String level = "";
			String team = "";
			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader("src\\controller\\q.json"));
			JSONArray arr = (JSONArray) obj.get("questions");
			Iterator<JSONObject> iterator = arr.iterator();
			while (iterator.hasNext()) {
				JSONObject temp = iterator.next();
				question = (String) temp.get("question");
				correctAnswer = (String) temp.get("correct_ans");
				level = (String) temp.get("level");
				team = (String) temp.get("team");
				JSONArray tempAnswers = (JSONArray) temp.get("answers");
				Iterator<String> iterator2 = tempAnswers.iterator();
				while (iterator2.hasNext())
					answers.add(iterator2.next());
				questions = arr;
				answers.clear();
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static boolean addQuestion(String question, ArrayList<String> answers, String correctAnswer, String level) {
		try {
			JSONObject q = (JSONObject) new JSONParser().parse(new FileReader("src\\controller\\q.json"));
			JSONArray arr = (JSONArray) q.get("questions");
			JSONObject obj = new JSONObject();
			obj.put("question", question);
			// create the new question
			JSONArray temp = new JSONArray();
			for (String a : answers)
				temp.add(a);
			obj.put("answers", temp);
			obj.put("correct_ans", correctAnswer);
			obj.put("level", level);
			obj.put("team", "Zebra");
			arr.add(obj);
			// create a new json object to hold the array and the array key
			JSONObject toAdd = new JSONObject();
			toAdd.put("questions", arr);
			// write json object to file
			FileWriter file = new FileWriter("src\\controller\\q.json", false);
			file.write(toAdd.toJSONString());
			file.flush();
			file.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		ArrayList<String> answers2 = new ArrayList();
	
		readJson();
		System.out.println(questions);
		addQuestion("q3",answers2,"1","3");
		readJson();
		System.out.println(questions);
	}

}


