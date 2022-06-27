package edu.haui.bvdong.quizapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haui.bvdong.quizapp.common.LoggerSpringBoot;
import edu.haui.bvdong.quizapp.model.Answer;
import edu.haui.bvdong.quizapp.model.Question;
import edu.haui.bvdong.quizapp.service.AnswerService;
import edu.haui.bvdong.quizapp.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;

    /**
     *
     * @param questionId
     * @return question and StatusCode
     */
    @GetMapping(value = "/user/view-question", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Question> getQuestionById(@RequestParam(value = "question_id") String questionId) {

            return new ResponseEntity(questionService.findByQuestionId(Integer.valueOf(questionId)),HttpStatus.OK);
    }

    /**
     * @param questionId
     * @return List<answer> by question id and StatusCode
     */
    @GetMapping(value = "/user/question/getListAnswerByQuestionId", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Answer>> getListAnswerByQuestionId(@RequestParam(value = "question_id") String questionId) {
        return new ResponseEntity(answerService.getListAnswerByQuestionId(Integer.valueOf(questionId)), HttpStatus.OK);
    }

    /**
     * @param page
     * @param size
     * @param sort
     * @return all records of Question
     */
    @GetMapping(value = "/user/question/getAll")
    public @ResponseBody
    Page<Question> getAll(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                          @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {

        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("questionId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("questionId").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);
        return questionService.findAll(pageable);
    }

    /**
     * @param page
     * @param size
     * @param sort
     * @param subjectId
     * @param levelId
     * @param questionText
     * @return result of optional search by user.
     */
    @GetMapping(value = "/user/question/searchByCondition")
    public @ResponseBody
    Page<Question> searchQuestion(@RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
                                  @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                  @RequestParam(name = "subjectId", required = false, defaultValue = "0") int subjectId,
                                  @RequestParam(name = "levelId", required = false, defaultValue = "0") int levelId,
                                  @RequestParam(name = "questionText", required = false, defaultValue = "") String questionText
    ) {
        Sort sortable = null;
        if (sort.equals("ASC")) {
            sortable = Sort.by("questionId").ascending();
        }
        if (sort.equals("DESC")) {
            sortable = Sort.by("questionId").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        //Find by questionText & subjectId & levelId
        if (!questionText.equals("") && subjectId != 0 && levelId != 0) {
            LoggerSpringBoot.getLoggerSpringBoot().info("Find by questionText & subjectId & levelId");
            return questionService.findByQuestionTextLikeAndAndCourse_Subject_SubjectIdAndLevel_LevelId("%" + questionText + "%", subjectId, levelId, pageable);
        }
        //Find by questionText & subjectId
        if (!questionText.equals("") && subjectId != 0 && levelId == 0) {
            LoggerSpringBoot.getLoggerSpringBoot().info("Find by questionText & subjectId");
            return questionService.findByQuestionTextLikeAndAndCourse_Subject_SubjectId("%" + questionText + "%", subjectId, pageable);
        }
        //Find by  levelId & subjectId
        if (subjectId != 0 && levelId != 0 && questionText.equals("")) {
            LoggerSpringBoot.getLoggerSpringBoot().info("Find by  levelId & subjectId");
            return questionService.findByLevel_LevelIdAndCourse_Subject_SubjectId(levelId, subjectId, pageable);
        }
        //Find by  questionText & levelId
        if (!questionText.equals("") && levelId != 0 && subjectId == 0) {
            LoggerSpringBoot.getLoggerSpringBoot().info("Find by  questionText & levelId");
            return questionService.findByQuestionTextLikeAndLevel_LevelId("%" + questionText + "%", levelId, pageable);
        }
        //Find by questionText
        if (!questionText.equals("") && levelId == 0 && subjectId == 0) {
            LoggerSpringBoot.getLoggerSpringBoot().info("Find by questionText");
            return questionService.findByQuestionTextLike("%" + questionText + "%", pageable);
        }
        //Find by levelId
        if (questionText.equals("") && levelId != 0 && subjectId == 0) {
            LoggerSpringBoot.getLoggerSpringBoot().info("Find by levelId");
            return questionService.findByLevel_LevelId(levelId,pageable);
        }

        //Find by subjectId/
        if (questionText.equals("") && levelId == 0 && subjectId != 0) {
            LoggerSpringBoot.getLoggerSpringBoot().info("Find by subjectId");
            return questionService.findByCourse_Subject_SubjectId(subjectId,pageable);
        }
        return null;
    }

}
