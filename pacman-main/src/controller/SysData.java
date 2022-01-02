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
import model.Question;


/**
 *This class defines reading from JSON files and writing to it.
 */

public class SysData {
	
	private ArrayList<Question> questions = new ArrayList<Question>();
	private ArrayList<String> players = new ArrayList<String>();
	private ArrayList<String> scores = new ArrayList<String>();
	public String curDir = System.getProperty("user.dir");
	public String path = "";
	public String playersPath = "";

	private static SysData instance;

	public static SysData getInstance() {
		if (instance == null)
			instance = new SysData();
		return instance;
	}

	/**
	 * Read JSON file and parse to string variables.
	 * Every iterations adds the question to the questions ArrayList declared in line 31(ArrayList<Question>).
	 * Every iteration adds the player and his score to  players and scores ArrayList declared in line 32 & 33 (ArrayList<String>).
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public  void readJson() {
		path = "/controller/q.json";
		playersPath = "/controller/gamesHistory.json";
		try {
			if(SysData.class.getResource("SysData.class").getContent().toString().contains("jar")) {
				String temp = path;
				path = curDir + temp;
				temp = playersPath;
				playersPath = curDir + temp;
			}else {
				String temp = path;
				path = "src" + temp;
				temp = playersPath;
				playersPath = "src" + temp;
			}
			ArrayList<String> answers = new ArrayList<String>();
			ArrayList<String> ans = new ArrayList<String>();
			ans.add("1");
			ans.add("2");
			ans.add("3");
			ans.add("4");
			String question = "";
			String correctAnswer = "";
			String level = "";

			JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(path));
			JSONArray arr = (JSONArray) obj.get("questions");
			Iterator<JSONObject> iterator = arr.iterator();
			while (iterator.hasNext()) {
				JSONObject temp = iterator.next();
				question = (String) temp.get("question");
				correctAnswer = (String) temp.get("correct_ans");
				level = (String) temp.get("level");
				JSONArray tempAnswers = (JSONArray) temp.get("answers");
				for( int i=0; i<tempAnswers.size(); i++) {
					answers.add((String) tempAnswers.get(i));	
				}
				Question q = new Question(level, question, answers, correctAnswer);
				questions.add(q);
				answers.clear();
			}
			JSONObject objPlayers = (JSONObject) new JSONParser().parse(new FileReader(playersPath));
			JSONArray arrPlayers = (JSONArray) objPlayers.get("players");
			String player = "";
			String score = "";
			Iterator<JSONObject> playersIterator = arrPlayers.iterator();
			while(playersIterator.hasNext()) {
				JSONObject temp = playersIterator.next();
				player = (String) temp.get("playerName");
				score = (String) temp.get("Score");
//				String toAdd = player + "           " + score;
				players.add(player);
				scores.add(score);
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
	@SuppressWarnings("unchecked")
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
	
	// Loop through all the questions until it matches the wanted question to delete.
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
	//Read from gameHistory file and add players to players list and scores to scores list.
	public boolean addPlayersToHistory(String player, String score) {	
		try {
			JSONObject history = (JSONObject) new JSONParser().parse(new FileReader(playersPath));
			JSONArray arr = (JSONArray) history.get("players");
			JSONObject obj = new JSONObject();
			obj.put("playerName", player);
			obj.put("Score", score);
			// create the new question
			arr.add(obj);
			// create a new json object to hold the array and the array key
			JSONObject toAdd = new JSONObject();
			toAdd.put("players", arr);
			// write json object to file
			FileWriter file = new FileWriter(playersPath, false);
			file.write(toAdd.toJSONString());
			file.flush();
			file.close();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
			return false;
		} 
		return true;
		
	}

	public  ArrayList<Question> getQuestionsList(){
		questions.clear();
		readJson();
		return questions;
	}
	public  String getQuestions(int index) {
		return getQuestionsList().get(index).getAnswers().get(0);
	}
	
	public ArrayList<String> getHistory(){
		return this.players;
	}
	public ArrayList<String> getScores(){
		return this.scores;
	}
	
	
}







