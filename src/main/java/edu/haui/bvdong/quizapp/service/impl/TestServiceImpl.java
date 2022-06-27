package edu.haui.bvdong.quizapp.service.impl;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.haui.bvdong.quizapp.common.LoggerSpringBoot;
import edu.haui.bvdong.quizapp.dto.*;
import edu.haui.bvdong.quizapp.model.*;
import edu.haui.bvdong.quizapp.model.pk.ExamId;
import edu.haui.bvdong.quizapp.repository.QuestionRepository;
import edu.haui.bvdong.quizapp.repository.TestRepository;
import edu.haui.bvdong.quizapp.service.AnswerService;
import edu.haui.bvdong.quizapp.service.TestService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class TestServiceImpl implements TestService {


    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private TestRepository testRepository;
    @Autowired
    private AnswerService answerService;

    /**
     * @Constructor
     * @param answerService
     * @param testRepository
     */
    public TestServiceImpl(AnswerService answerService, TestRepository testRepository) {
        this.answerService = answerService;
        this.testRepository = testRepository;
    }

    /**
     *
     * @param testId
     * @return test by id
     */
    @Override
    public Test getById(int testId) {
        return testRepository.findById(testId).orElse(null);
    }

    /**
     *
     * @param testDto
     * @return test is saved or update
     */
    @Override
    @Transactional
    public Test addOrUpdateTest(TestDto testDto) {
        Test test = new Test();
        if (testDto.getTestId() != 0) {
            test.setTestId(testDto.getTestId());
        }
        test.setTestText(testDto.getTestText());
        test.setTotalMark(testDto.getTotalMark());
        test.setDuration(testDto.getDuration());
        Course course = new Course();
        course.setCourseId(testDto.getCourseId());
        test.setCourse(course);
        Level level = new Level();
        level.setLevelId(testDto.getLevelId());
        test.setLevel(level);
        List<Question> questionList = new ArrayList<>();
        for (Integer integer : testDto.getQuestionIdList()) {
            questionList.add(questionRepository.findByQuestionId(integer));
        }
        test.setQuestions(questionList);
        return testRepository.save(test);
    }

    /**
     *
     * @param id
     * @return list test by course id
     */
    @Override
    public List<Test> getAllByCourseId(int id) {
        return testRepository.findAllByCourseCourseId(id);
    }

    /**
     *
     * @param testId
     * @return testText by id
     */
    @Override
    public String getTestTextByTestId(int testId) {
        return testRepository.getTestTextByTestId(testId);
    }

    /**
     *
     * @return list all Test
     */
    @Override
    public List<Test> getAllTest() {
        return testRepository.findAll();
    }

    /**
     *
     * @param userTestDto
     * @return ExamResultDto
     */
    @Override
    @Transactional
    public ExamResultDto doTest(UserTestDto userTestDto) {
        Test test = getById(userTestDto.getTestId());
        List<UserTestResult> userTestResultList = new ArrayList<>();
        int numberOfQuestion = test.getQuestions().size();
        if (numberOfQuestion == 0) {
            return null;
        }

        int totalMark = test.getTotalMark();
        double mark = 0.0;
        double markPerQuestion = totalMark / numberOfQuestion;
        int numberOfCorrentQuestion = 0;
        int rank = 0;

        List<Answer> correctAnswerList = null;
        Set<String> correctAnswerSequences;
        UserTestResult userTestResult;
        Set<String> userChooseList = null;
        for (UserQuestionDto userQuestionDto : userTestDto.getQuestionList()) {
            userTestResult = null;
            correctAnswerList = answerService.getAllByQuestionQuestionIdAndCorrectAnswer(userQuestionDto.getQuestionId(), true);
            correctAnswerSequences = new TreeSet<>();
            for (Answer answer : correctAnswerList) {
                correctAnswerSequences.add(answer.getSequence());
            }
            userChooseList = new TreeSet<String>(userQuestionDto.getSequenceList());
            if (correctAnswerSequences.size() != userChooseList.size()) {
                userTestResult = new UserTestResult<>(userQuestionDto.getQuestionId(), userChooseList, correctAnswerList);
            }

            if (!correctAnswerSequences.equals(userChooseList)) {
                userTestResult = new UserTestResult<>(userQuestionDto.getQuestionId(), userChooseList, correctAnswerList);
            }

            if (userTestResult == null) {
                mark += markPerQuestion;
                numberOfCorrentQuestion++;
            } else {
                userTestResultList.add(userTestResult);
            }

        }

        ExamResultDto examResultDto = new ExamResultDto();
        examResultDto.setUserTestResults(userTestResultList);
        examResultDto.setScore(mark);
        examResultDto.setCorrect(numberOfCorrentQuestion);
        examResultDto.setWrong(numberOfQuestion - numberOfCorrentQuestion);
        if (mark / totalMark * 100 > 70) {
            examResultDto.setRank(1);
            examResultDto.setStatus(true);
        } else {
            examResultDto.setRank(0);
            examResultDto.setStatus(false);
        }

        return examResultDto;


    }

    /**
     *
     * @param testId
     * @return true or false
     */
    @Override
    @Transactional
    public boolean deleteById(int testId) {
        try {
            testRepository.deleteById(testId);
            LoggerSpringBoot.getLoggerSpringBoot().info("Delete Test compplete by id "+testId);
            return  true;
        } catch (DataIntegrityViolationException e) {
            LoggerSpringBoot.getLoggerSpringBoot().error("Delete failed"+e);
            return  false;
        }
    }

    @Override
    public List<Test> findByTestTextLike(String testText) {
        return testRepository.findAllByTestTextLike(testText);
    }
}
