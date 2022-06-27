package edu.haui.bvdong.quizapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haui.bvdong.quizapp.model.Course;
import edu.haui.bvdong.quizapp.service.CourseService;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping("/showAllCourse/subject/{id}")
    public @ResponseBody List<Course> showCourseBySubjectId(@PathVariable(value="id")int subjectId){
        return courseService.getAllCourseBySubjectId(subjectId);
    }

}
