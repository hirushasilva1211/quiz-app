package com.telusko.quiz_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.telusko.quiz_service.domain.QuestionWrapper;
import com.telusko.quiz_service.domain.Quiz;
import com.telusko.quiz_service.domain.Response;
import com.telusko.quiz_service.feign.QuizInterface;
import com.telusko.quiz_service.repository.QuizRepository;



@Service
public class QuizService {
	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private QuizInterface quizInterface;
	

	public QuizInterface getQuizInterface() {
		return quizInterface;
	}

	public void setQuizInterface(QuizInterface quizInterface) {
		this.quizInterface = quizInterface;
	}

	public QuizRepository getQuizRepository() {
		return quizRepository;
	}

	public void setQuizRepository(QuizRepository quizRepository) {
		this.quizRepository = quizRepository;
	}

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		
		List<Integer> questionIds = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
		
		Quiz quiz = new Quiz(title, questionIds);
		quizRepository.save(quiz);
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		Quiz quiz = quizRepository.findById(id).get();
		
		ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(quiz.getQuestionIds());
		
		return questions;
	}
	
	// Get score for the answers
	public ResponseEntity<Integer> getScore(Integer id, List<Response> responseList) {
		
		return quizInterface.getScore(responseList);
	}
	
	
}
