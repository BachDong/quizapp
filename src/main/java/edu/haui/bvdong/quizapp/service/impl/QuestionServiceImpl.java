package edu.haui.bvdong.quizapp.service.impl;


import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.haui.bvdong.quizapp.common.LoggerSpringBoot;
import edu.haui.bvdong.quizapp.dto.QuestionDto;
import edu.haui.bvdong.quizapp.model.Answer;
import edu.haui.bvdong.quizapp.model.Course;
import edu.haui.bvdong.quizapp.model.Level;
import edu.haui.bvdong.quizapp.model.Question;
import edu.haui.bvdong.quizapp.repository.AnswerRepository;
import edu.haui.bvdong.quizapp.repository.CourseRepository;
import edu.haui.bvdong.quizapp.repository.LevelRepository;
import edu.haui.bvdong.quizapp.repository.QuestionRepository;
import edu.haui.bvdong.quizapp.service.QuestionService;
import edu.haui.bvdong.quizapp.utils.importexcel.AnswerExcel;
import edu.haui.bvdong.quizapp.utils.importexcel.QuestionExcel;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private AnswerRepository answerRepository;

    /**
     * @param questionDto
     * @return question is saved
     */
    @Override
    @Transactional
    public Question addQuestion(QuestionDto questionDto) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"Start Add Question");
        Course course = new Course();
        course.setCourseName((questionDto.getCourseDto()).getCourseName());
        course.setCourseId((questionDto.getCourseDto()).getCourseId());
        Level level = new Level();
        level.setLevelId(questionDto.getLevelDto().getLevelId());
        level.setLevelName(questionDto.getLevelDto().getLevelName());

        Question question = new Question();
        if (questionDto.getQuestionId() != 0) {
            question.setQuestionId(questionDto.getQuestionId());
        }
        question.setQuestionText(questionDto.getQuestionText());
        question.setCourse(course);
        question.setLevel(level);
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"End Add Question");
        return questionRepository.save(question);
    }

    /**
     * @param question_id
     * @return question by id
     */
    @Override
    public Question findByQuestionId(int question_id) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"find Question By ID");
        return questionRepository.findByQuestionId(question_id);
    }


    /**
     * @param questionId
     * @return true or false
     */
    @Override
    @Transactional
    public boolean deleteQuestion(int questionId) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"Start delete Question");
        try {
            questionRepository.deleteById(questionId);
            LoggerSpringBoot.getLoggerSpringBoot().info(QuestionServiceImpl.class + "::Delete  Question completed ::");
            return true;
        } catch (HibernateException ex) {
            LoggerSpringBoot.getLoggerSpringBoot().error(QuestionServiceImpl.class + "::Delete error cause::" + ex);
            return false;
        }

    }

    /**
     * @param pageable
     * @return object Page - dataType :JSon
     * @Component {
     * content: list of question
     * first: true
     * last: false
     * number:
     * numberOfElements:
     * pageable: {sort: {sorted: true, unsorted: false}, offset: 0, pageSize: 5, pageNumber: 0, paged: true,…}
     * size: number size
     * sort: {sorted: true, unsorted: false}
     * totalElements:
     * totalPages:
     * }
     */
    @Override
    public Page<Question> findAll(Pageable pageable) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"Find all Question");
        return questionRepository.findAll(pageable);

    }

    /**
     * @param questionText
     * @param subjectId
     * @param levelId
     * @param pageable
     * @return Page question
     */
    @Override
    public Page<Question> findByQuestionTextLikeAndAndCourse_Subject_SubjectIdAndLevel_LevelId(String questionText, int subjectId, int levelId, Pageable pageable) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"findByQuestionTextLikeAndAndCourse_Subject_SubjectIdAndLevel_LevelId");
        return questionRepository.findByQuestionTextLikeAndAndCourse_Subject_SubjectIdAndLevel_LevelId(questionText, subjectId, levelId, pageable);
    }

    /**
     * @param questionText
     * @param subjectId
     * @param pageable
     * @return Page question
     */
    @Override
    public Page<Question> findByQuestionTextLikeAndAndCourse_Subject_SubjectId(String questionText, int subjectId, Pageable pageable) {

        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"findByQuestionTextLikeAndAndCourse_Subject_SubjectId");
        return questionRepository.findByQuestionTextLikeAndAndCourse_Subject_SubjectId(questionText, subjectId, pageable);
    }

    /**
     * @param questionText
     * @param levelId
     * @param pageable
     * @return Page question
     */
    @Override
    public Page<Question> findByQuestionTextLikeAndLevel_LevelId(String questionText, int levelId, Pageable pageable) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"findByQuestionTextLikeAndLevel_LevelId");
        return questionRepository.findByQuestionTextLikeAndLevel_LevelId(questionText, levelId, pageable);
    }

    /**
     * @param levelId
     * @param subjectId
     * @param pageable
     * @return Page question
     */
    @Override
    public Page<Question> findByLevel_LevelIdAndCourse_Subject_SubjectId(int levelId, int subjectId, Pageable pageable) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"findByLevel_LevelIdAndCourse_Subject_SubjectId");
        return questionRepository.findByLevel_LevelIdAndCourse_Subject_SubjectId(levelId, subjectId, pageable);
    }

    /**
     * @param questionText
     * @param pageable
     * @return Page question
     */
    @Override
    public Page<Question> findByQuestionTextLike(String questionText, Pageable pageable) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"findByQuestionTextLike");
        return questionRepository.findByQuestionTextLike(questionText, pageable);
    }

    /**
     * @param levelId
     * @param pageable
     * @return Page question
     */
    @Override
    public Page<Question> findByLevel_LevelId(int levelId, Pageable pageable) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"findByLevel_LevelId");
        return questionRepository.findByLevel_LevelId(levelId, pageable);
    }

    /**
     * @param subjectId
     * @param pageable
     * @return Page question
     */
    @Override
    public Page<Question> findByCourse_Subject_SubjectId(int subjectId, Pageable pageable) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"findByCourse_Subject_SubjectId");
        return questionRepository.findByCourse_Subject_SubjectId(subjectId, pageable);
    }

    /**
     *
     * @param questionExcelList
     * @do {
     *     import Excel for question
     * }
     * @return true or false
     */
    @Override
    @Transactional
    public boolean importExcelQuestion(List<QuestionExcel> questionExcelList) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"START IMPORT EXCEL QUESTIONS");
        try {
            for (QuestionExcel questionExcel : questionExcelList) {
                Question question = new Question();
                question.setQuestionText(questionExcel.getQuestionText());
                Course course = courseRepository.findByCourseName(questionExcel.getCourseText());
                Level level = levelRepository.findByLevelName(questionExcel.getLevelText());
                if(course.equals(null)|| level.equals(null) ){
                    LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"END IMPORT EXCEL QUESTIONS");
                    break;
                }
                question.setLevel(level);
                question.setCourse(course);
                Question questionSaved = questionRepository.save(question);
                for (AnswerExcel answerExcel : questionExcel.getListAnswer()) {
                    Answer answer = new Answer();
                    answer.setAnswerText(answerExcel.getAnswertext());
                    answer.setSequence(answerExcel.getSequence());
                    answer.setQuestion(questionSaved);
                    answer.setCorrectAnswer(answerExcel.getCorrectAnswer());
                    answerRepository.save(answer);
                }
            }
            LoggerSpringBoot.getLoggerSpringBoot().info("Import Excel Question OK".toUpperCase());
            return true;
        } catch (HibernateException e) {
            LoggerSpringBoot.getLoggerSpringBoot().error("Can't Import Excel" + e);
            return false;
        }

    }
}
