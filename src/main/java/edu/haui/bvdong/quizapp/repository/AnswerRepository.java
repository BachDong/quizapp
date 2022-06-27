package edu.haui.bvdong.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.haui.bvdong.quizapp.model.Answer;
import edu.haui.bvdong.quizapp.model.Question;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer,Integer>{
    List<Answer> findAllByQuestionQuestionId(int id);
    Answer findByQuestionQuestionIdAndSequence(int questionId,String sequence);
    List<Answer> findAllByQuestionQuestionIdAndCorrectAnswer(int id,boolean isCorrect);
}
