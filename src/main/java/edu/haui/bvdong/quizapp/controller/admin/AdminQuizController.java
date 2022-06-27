package edu.haui.bvdong.quizapp.controller.admin;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfWriter;

import edu.haui.bvdong.quizapp.common.Note;
import edu.haui.bvdong.quizapp.common.PageTitleConstrants;
import edu.haui.bvdong.quizapp.dto.TestDto;
import edu.haui.bvdong.quizapp.model.Question;
import edu.haui.bvdong.quizapp.model.Test;
import edu.haui.bvdong.quizapp.service.QuestionService;
import edu.haui.bvdong.quizapp.service.TestService;
import edu.haui.bvdong.quizapp.utils.createPDF.ConvertWebPageToPdf;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/admin/quiz-management")
public class AdminQuizController {

    @Autowired
    private TestService testService;
    @Autowired
    private QuestionService questionService;

    /**
     * @param modelMap
     * @return:page(quiz-management)
     */
    @GetMapping(value = "/")
    public String showIndex(ModelMap modelMap) {
        modelMap.addAttribute("title", PageTitleConstrants.QUIZ_MANAGEMENT);
        return "admin/quiz-management";
    }

    /**
     * @param:quiz
     * @do:delete_a_Test_record
     * @return:StatusCode
     */
    @GetMapping(value = "/delete")
    public ResponseEntity deleteQuiz(@RequestParam("quizId") int quizId) {
        try {
            testService.deleteById(quizId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (HibernateException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     *
     * @param quizId
     * @do {
     *     create a file PDF and call Chomre open it now !.
     * }
     * @return
     */
    @GetMapping(value = "/print")
    public ResponseEntity readPDFQuiz(@RequestParam("quizId") int quizId) {
        try {
            Test test=testService.getById(quizId);
            ConvertWebPageToPdf.createPDF(test,PageTitleConstrants.PATH_SAVE_FILE_NAME_PDF);
        ProcessBuilder processBuilder= new ProcessBuilder();
            ProcessBuilder command = processBuilder.command(Note.CMD_RUN_GOOGLE_CHROME,PageTitleConstrants.PATH_SAVE_FILE_NAME_PDF);
            command.start();
            return new ResponseEntity<>("Created PDF ! Opening  PDF...",HttpStatus.OK);
        } catch (HibernateException | IOException e) {
            return new ResponseEntity<>("Sorry, Can't Open PDF on your PC ?",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * @param
     * @do:get_list_Test
     * @return_list<test>_and_Status_code
     */
    @GetMapping(value = "/loadTableQuiz", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Test>> loadTableQuiz() {
        return new ResponseEntity<>(testService.getAllTest(), HttpStatus.OK);
    }

    /**
     * @param testDto
     * @return Object(JSon) save or update and Status Code
     * @do {
     * save or update Test
     * }
     */
    @PostMapping(value = "/createQuiz", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Test> addOrUpdateTest(@RequestBody TestDto testDto) {
        Test test = testService.addOrUpdateTest(testDto);
        if (null != test) {
            return new ResponseEntity<>(test, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * @return get List<Question>
     * @param:testDto
     * @Do {
     * get question by Id and add to List
     * }
     */
    @PostMapping(value = "/demoQuiz")
    public String showDemoQuiz(@RequestBody TestDto testDto, ModelMap modelMap) {
        List<Question> questionList = new ArrayList<>();
        for (Integer integer : testDto.getQuestionIdList()) {
            questionList.add(questionService.findByQuestionId(integer));
        }
        modelMap.addAttribute("questionList", questionList);
        return "admin//base-quiz/demo-quiz";
    }

    /**
     * @param txtSearch
     * @return result of search from user
     */
    @GetMapping(value = "/searchQuiz", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Test>> searchQuiz(@RequestParam("txtSearch") String txtSearch) {
        return new ResponseEntity<>(testService.findByTestTextLike("%" + txtSearch + "%"), HttpStatus.OK);
    }

}
