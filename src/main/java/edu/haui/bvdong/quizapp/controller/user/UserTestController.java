package edu.haui.bvdong.quizapp.controller.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import edu.haui.bvdong.quizapp.dto.*;
import edu.haui.bvdong.quizapp.model.Answer;
import edu.haui.bvdong.quizapp.model.Exam;
import edu.haui.bvdong.quizapp.model.Question;
import edu.haui.bvdong.quizapp.model.Test;
import edu.haui.bvdong.quizapp.model.pk.ExamId;
import edu.haui.bvdong.quizapp.service.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tests/")
public class UserTestController {

    private static final Logger LOGGER = LogManager.getLogger(UserTestController.class);

    private final TestService testService;

    private final ExamService examService;

    @Autowired
    public UserTestController(TestService testService, ExamService examService) {
        this.testService = testService;
        this.examService = examService;
    }


    @RequestMapping("/courseId/{id}")
    public @ResponseBody List<Test> getTestByCourseId(@PathVariable("id") final int id){
        return testService.getAllByCourseId(id);
    }

    @RequestMapping(value = "/getById/{id}",method = RequestMethod.GET)
    public @ResponseBody Test getTestByTestId(@PathVariable("id") final int id){
        return testService.getById(id);

    }

    @RequestMapping(value = "/doQuiz",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody JsonGeneratorDto<?> doQuiz(@RequestBody final UserTestDto userTestDto){
        final boolean status;
        ExamResultDto examResultDto = testService.doTest(userTestDto);
        if(examResultDto!=null){
            status = true;
            examService.addExam(examResultDto,testService.getById(userTestDto.getTestId()));
        }else{
            status = false;
        }

        return new JsonGeneratorDto<>(status,examResultDto);
    }

    @GetMapping(value = "/testText/{id}")
    public @ResponseBody JsonGeneratorDto<String> getTestTextByTestId(@PathVariable("id") final int testId){
        return new JsonGeneratorDto<>(true,testService.getTestTextByTestId(testId));
    }

}
