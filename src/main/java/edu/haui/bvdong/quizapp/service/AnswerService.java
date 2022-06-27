package edu.haui.bvdong.quizapp.service;

import java.util.List;

import edu.haui.bvdong.quizapp.dto.AnswerDto;
import edu.haui.bvdong.quizapp.model.Answer;
import edu.haui.bvdong.quizapp.model.Question;

public interface AnswerService {

     /**
      *
      * @param answerDto
      * @param questionCreated
      * @return a answer is saved
      */
     Answer addAnswer(AnswerDto answerDto, Question questionCreated);

     /**
      *
      * @param questionId
      * delete answer by question Id
      * @return true or false
      */
     boolean deleteAnswerByQuestionId (int questionId);

     /**
      *
      * @param questionId
      * @return list answer by question id
      */
     List<Answer> getListAnswerByQuestionId(int questionId);

     /**
      *
      * @param questionId
      * @param sequence
      * @return a answer
      */
     Answer getAnswerByQuestionIdAndSequence(int questionId,String sequence);
     boolean deleteAnswerById (int answerId);

     /**
      *
      * @param id
      * @param isCorrect
      * @return list answer
      */
     List<Answer> getAllByQuestionQuestionIdAndCorrectAnswer(int id,boolean isCorrect);
}
