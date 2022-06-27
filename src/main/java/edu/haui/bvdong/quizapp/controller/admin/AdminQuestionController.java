package edu.haui.bvdong.quizapp.controller.admin;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import edu.haui.bvdong.quizapp.common.LoggerSpringBoot;
import edu.haui.bvdong.quizapp.common.PageTitleConstrants;
import edu.haui.bvdong.quizapp.dto.*;
import edu.haui.bvdong.quizapp.model.Answer;
import edu.haui.bvdong.quizapp.model.Question;
import edu.haui.bvdong.quizapp.service.AnswerService;
import edu.haui.bvdong.quizapp.service.QuestionService;
import edu.haui.bvdong.quizapp.service.impl.QuestionServiceImpl;
import edu.haui.bvdong.quizapp.utils.ExcelReader;
import edu.haui.bvdong.quizapp.utils.FileDowload;
import edu.haui.bvdong.quizapp.utils.importexcel.QuestionExcel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Controller
@RequestMapping(value = "/admin/question-management")
public class AdminQuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;

    /**
     * @param request
     * @return username of user logged
     * @do {
     * getName user logged
     * }
     */
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }

    /**
     * @param:modelMap
     * @return:page(question_managemnet)
     */
    @GetMapping(value = "/")
    public String showIndex(ModelMap modelMap) {
        modelMap.addAttribute("title", PageTitleConstrants.QUESTION_MANAGEMENT);
        return "admin/question-management";
    }


    /**
     * @param questionDto
     * @return questionDto and status code
     * @Do {
     * save Question
     * update Question
     * delete or Update Answer in a Question
     * }
     */
    @ResponseBody
    @PostMapping(value = "/createQuestion", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Question> addQuestion(final @RequestBody QuestionDto questionDto) {
        //Get question to save to DB;
        Question questionCreated = questionService.addQuestion(questionDto);
        List<Answer> answerList = new ArrayList<>();
        /*Browse list AnswerDto to remove invalid  element and SAVE or UPDATE the valid element.*/
        Iterator iterator = questionDto.getAnswersDto().iterator();
        while (iterator.hasNext()) {
            AnswerDto answerDto = new AnswerDto();
            answerDto = (AnswerDto) iterator.next();
            if (null != answerDto.getAnswerText()) {
                answerList.add(answerService.addAnswer(answerDto, questionCreated));
            } else {
                iterator.remove();
            }
        }
        //Delete answer by answerId
        try {
            for (AnswerDto answerDto : questionDto.getAnswersDelete()) {
                if (answerDto.getAnswerId() != 0) {
                    answerService.deleteAnswerById(answerDto.getAnswerId());
                }
            }
        } catch (NullPointerException ex) {
            LoggerSpringBoot.getLoggerSpringBoot().error(this.getClass() + " :::Don't Delete");
        }
        //control result to user
        if (null != questionCreated && answerList.size() != 0) {
            return new ResponseEntity<>(questionCreated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(questionCreated, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param questionId
     * @return status Code
     * @Do deleteQuestionAndAnswer
     */
    @GetMapping(value = "/deleteQuestion")
    public ResponseEntity deleteQuestionAndAnswer(@RequestParam(value = "question_id") String questionId) {
        boolean flag = false, flag1 = false;
        flag = answerService.deleteAnswerByQuestionId(Integer.valueOf(questionId));
        flag1 = questionService.deleteQuestion(Integer.valueOf(questionId));

        if (flag && flag1) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param paths
     * @return
     */
    @PostMapping (value = "/importExcelQuestion")
    public ResponseEntity<Integer> importExcelQuestion(@RequestBody Paths paths
    ) {
        List<QuestionExcel> questionImportExcels=new ArrayList<>();
        List<String> listPath=paths.getListPath();
        try {
            String urlFile = PageTitleConstrants.LINK_FILE;
            for ( String path : listPath) {
                if (path.contains("https://")) {
                    FileDowload.downloadFileFromURL(path, urlFile);
                } else {
                    urlFile = path;
                }
                questionImportExcels.addAll(ExcelReader.readExcelQuestion(urlFile, null));
            }
            Integer row = questionImportExcels.size();
            if (null != questionImportExcels && questionImportExcels.size() != 0) {
                questionService.importExcelQuestion(questionImportExcels);
                return new ResponseEntity(row, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (InvalidFormatException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}