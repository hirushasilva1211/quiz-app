package com.telusko.quiz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.telusko.quiz_service.domain.QuestionWrapper;
import com.telusko.quiz_service.domain.Response;



@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
	
	// Get questions for quiz
		@PostMapping("/questions/getIds")
		public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions);
		
		// Get questions from ids
		@PostMapping("/questions/getQuestions")
		public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionsId);
		
		//Get score
		@PostMapping("/questions/getScore")
		public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);

}
