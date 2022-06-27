package edu.haui.bvdong.quizapp.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.haui.bvdong.quizapp.dto.QuestionDto;
import edu.haui.bvdong.quizapp.model.Question;
import edu.haui.bvdong.quizapp.utils.importexcel.QuestionExcel;

import java.util.List;

public interface QuestionService {
    /**
     *
     * @param questionDto
     * @return
     */
    Question addQuestion(QuestionDto questionDto);

    /**
     *
     * @param question_id
     * @return
     */
    Question findByQuestionId(int question_id);

    /**
     *
     * @param questionId
     * @return
     */
    boolean deleteQuestion(int questionId);
    /**
     *
     * @param pageable
     * Find All
     * @return
     */
    Page<Question> findAll(Pageable pageable);
    /**
     *
     * @param questionText
     * @param subjectId
     * @param levelId
     * @param pageable
     * Find by questionText & subjectId & levelId
     * @return
     */
    Page<Question> findByQuestionTextLikeAndAndCourse_Subject_SubjectIdAndLevel_LevelId(String questionText,int subjectId,int levelId,Pageable pageable);

    /**
     *
     * @param questionText
     * @param subjectId
     * @param pageable
     * Find by questionText & subjectId
     * @return
     */
    Page<Question> findByQuestionTextLikeAndAndCourse_Subject_SubjectId(String questionText,int subjectId,Pageable pageable);

    /**
     *
     * @param questionText
     * @param levelId
     * @param pageable
     * Find by questionText & levelId
     * @return
     */
    Page<Question> findByQuestionTextLikeAndLevel_LevelId(String questionText,int levelId,Pageable pageable);

    /**
     *
     * @param levelId
     * @param subjectId
     * @param pageable
     * Find by   levelId & subjectId
     * @return
     */
    Page<Question> findByLevel_LevelIdAndCourse_Subject_SubjectId(int levelId,int subjectId,Pageable pageable);

    /**
     *
     * @param questionText
     * @param pageable
     * Find by questionText
     * @return
     */
    Page<Question> findByQuestionTextLike(String questionText,Pageable pageable);

    /**
     *
     * @param levelId
     * @param pageable
     * Find by LevelID
     * @return
     */
    Page<Question> findByLevel_LevelId(int levelId,Pageable pageable);

    /**
     *
     * @param subjectId
     * @param pageable
     * Find by SubjectID
     * @return
     */
    Page<Question> findByCourse_Subject_SubjectId(int subjectId,Pageable pageable);

    /**
     *
     * @param questionExcelList
     * @return true or false
     */
    boolean importExcelQuestion(List<QuestionExcel> questionExcelList);

}
