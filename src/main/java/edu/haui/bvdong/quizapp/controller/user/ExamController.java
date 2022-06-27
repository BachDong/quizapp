package edu.haui.bvdong.quizapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import edu.haui.bvdong.quizapp.dto.JsonGeneratorDto;
import edu.haui.bvdong.quizapp.model.Exam;
import edu.haui.bvdong.quizapp.model.pk.ExamId;
import edu.haui.bvdong.quizapp.service.ExamService;
import edu.haui.bvdong.quizapp.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/exams")
public class ExamController {
    @Autowired
    private ExamService examService;

    @Autowired
    private UserService userService;

    @GetMapping(value = "/getAll/subjectId/{subjectId}")
    public @ResponseBody List<Exam> getAllByUserIdAndSubjectId(@PathVariable("subjectId")int subjectId){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        edu.haui.bvdong.quizapp.model.User userLogged = userService.findByUserName(user.getUsername());
        return examService.getAllByUserIdAndSubjectId(userLogged.getUserId(),subjectId);
    }

    @PostMapping(value = "/delete")
    public @ResponseBody JsonGeneratorDto<?> deleteExam(@RequestParam("testId")int testId,@RequestParam("dateTime")String dateTime){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = formatter.parse(dateTime);
        } catch (ParseException e) {
            return new JsonGeneratorDto(false,"Date time error!");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        edu.haui.bvdong.quizapp.model.User userLogged = userService.findByUserName(user.getUsername());

        ExamId examId = new ExamId();
        examId.setTestId(testId);
        examId.setDateTime(date);
        examId.setUserId(userLogged.getUserId());

        examService.deleteExamById(examId);


        return new JsonGeneratorDto(true,examId);
    }

}
