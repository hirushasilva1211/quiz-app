package com.telusko.question_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.telusko.question_service.domain.Question;
import com.telusko.question_service.domain.QuestionWrapper;
import com.telusko.question_service.domain.Response;
import com.telusko.question_service.service.QuestionService;

@RestController
@RequestMapping("/questions")
public class QuetionController {
	
	@Autowired
	private QuestionService questionService;
	
	public QuestionService getQuestionService() {
		return questionService;
	}
	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}
			

	// Get all questions
	@GetMapping("/allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions() {
		return questionService.getAllQuestions();
	}
	
	// Get questions by category
	@GetMapping("/{category}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("category") String category){
		return questionService.getQuestionsByCategory(category);
	}
	
	// Create questions 
	@PostMapping("/addQuestions")
	public ResponseEntity<String> createQuestions(@RequestBody List<Question> questionList){

		return questionService.createQuestions(questionList);
	}
	
	// Get questions for quiz
	@PostMapping("/getIds")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions){
		return questionService.getQuestionsForQuiz(categoryName, numQuestions);
	}
	
	// Get questions from ids
	@PostMapping("/getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsId){
		return questionService.getQuestionsFromId(questionsId);
	}
	
	//Get score
	@PostMapping("/getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses){
		return questionService.getScore(responses);
	}
	
}
