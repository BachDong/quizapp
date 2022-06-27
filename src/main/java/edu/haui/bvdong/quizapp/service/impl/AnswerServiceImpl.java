package edu.haui.bvdong.quizapp.service.impl;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.haui.bvdong.quizapp.common.LoggerSpringBoot;
import edu.haui.bvdong.quizapp.common.query.SubjectQuery;
import edu.haui.bvdong.quizapp.dto.AnswerDto;
import edu.haui.bvdong.quizapp.model.Answer;
import edu.haui.bvdong.quizapp.model.Question;
import edu.haui.bvdong.quizapp.repository.AnswerRepository;
import edu.haui.bvdong.quizapp.service.AnswerService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {


    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private EntityManager entityManager;

    /**
     *
     * @param answerDto
     * @param questionCreated
     * @return answer is saved
     */
    @Override
    @Transactional
    public Answer addAnswer(AnswerDto answerDto, Question questionCreated) {
        Answer answer = new Answer();
        if (answerDto.getAnswerId() != 0) {
            answer.setAnswerId(answerDto.getAnswerId());
        }
        answer.setAnswerText(answerDto.getAnswerText());
        answer.setCorrectAnswer(answerDto.isCorrectAnswer());
        answer.setSequence(answerDto.getSequence());
        answer.setQuestion(questionCreated);
        LoggerSpringBoot.getLoggerSpringBoot().info("Saved Answer :" + answer);
        return answerRepository.save(answer);
    }

    /**
     *
     * @param questionId
     * delete answer by question Id
     * @return true or false
     */
    @Override
    @Transactional
    public boolean deleteAnswerByQuestionId(int questionId) {
        Query query = entityManager.createNativeQuery(SubjectQuery.DELETE_ANSWER_BY_QUESTION_ID);
        query.setParameter(1, questionId);
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"Delete Answer By Question ID");
        return query.executeUpdate() > 0;
    }

    /**
     *
     * @param questionId
     * @return list Answer By QuestionId
     */
    @Override
    public List<Answer> getListAnswerByQuestionId(int questionId) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"Get List Asnwer by Question Id");
        return answerRepository.findAllByQuestionQuestionId(questionId);
    }

    /**
     *
     * @param questionId
     * @param sequence
     * @return Answer
     */
    @Override
    public Answer getAnswerByQuestionIdAndSequence(int questionId, String sequence) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"Get Answer By QuestionId And Sequence");
        return answerRepository.findByQuestionQuestionIdAndSequence(questionId,sequence);
    }

    /**
     *
     * @param answerId
     * @return true or false
     */
    @Override
    public boolean deleteAnswerById(int answerId) {
        try {
            answerRepository.deleteById(answerId);
            return true;
        } catch (HibernateException e) {
            LoggerSpringBoot.getLoggerSpringBoot().error(this.getClass() + "Cant delete Answer");
            return false;
        }
    }

    /**
     *
     * @param id
     * @param isCorrect
     * @return list answer by question id
     */
    @Override
    public List<Answer> getAllByQuestionQuestionIdAndCorrectAnswer(int id, boolean isCorrect) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"getAllByQuestionQuestionIdAndCorrectAnswer");
        return answerRepository.findAllByQuestionQuestionIdAndCorrectAnswer(id,isCorrect);
    }
}
