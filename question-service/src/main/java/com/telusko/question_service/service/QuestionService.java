package com.telusko.question_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.telusko.question_service.domain.Question;
import com.telusko.question_service.domain.QuestionWrapper;
import com.telusko.question_service.domain.Response;
import com.telusko.question_service.repository.QuestionRepository;


@Service
public class QuestionService {
	
	@Autowired
	private QuestionRepository questionRepository;
	
	public QuestionRepository getQuestionRepository() {
		return questionRepository;
	}

	public void setQuestionRepository(QuestionRepository questionRepository) {
		this.questionRepository = questionRepository;
	}

	// Get all questions
	public ResponseEntity<List<Question>> getAllQuestions() {
		try {
			return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}
	
	// Get questions by category
	public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
		
		try {
			return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
	}

	// Create questions
	public ResponseEntity<String> createQuestions(List<Question> questionList) {
		try {
			questionRepository.saveAll(questionList);
			return new ResponseEntity<>("Success", HttpStatus.CREATED);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Not success", HttpStatus.BAD_REQUEST);
	}
	
	// Get question ids for quiz
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
		List<Integer> questions = questionRepository.findRandomQuestionsByCategory(categoryName ,numQuestions);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionsId) {
		List<QuestionWrapper> qwList = new ArrayList<>();
		List<Question> questionsList = new ArrayList<>();
		
		for(Integer i : questionsId) {
			questionsList.add(questionRepository.findById(i).get());
		}
		
		for(Question q : questionsList){
			QuestionWrapper qw = new QuestionWrapper(
							q.getId(),
							q.getQuestionTitle(),
							q.getOption1(),
							q.getOption2(),
							q.getOption3(),
							q.getOption4()
					);
			qwList.add(qw); 
			
		}
		return new ResponseEntity<>(qwList, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {	
		int rightAnswer = 0;
		
		for(Response response : responses) {
			if (response.getAnswer().equals(questionRepository.findById(response.getId()).get().getRightAnswer())) {
				rightAnswer++;
			}
		}
		
		return new ResponseEntity<>(rightAnswer, HttpStatus.OK );
	}
	
	

	
	
	
}
