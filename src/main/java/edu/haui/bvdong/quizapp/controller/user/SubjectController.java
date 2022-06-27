package edu.haui.bvdong.quizapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import edu.haui.bvdong.quizapp.common.PageTitleConstrants;
import edu.haui.bvdong.quizapp.model.Subject;
import edu.haui.bvdong.quizapp.service.SubjectService;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @RequestMapping(value = "/view/{id}",method = RequestMethod.GET)
    public String showViewSubject(ModelMap modelMap,@PathVariable("id")int subjectId){
        modelMap.addAttribute("title", PageTitleConstrants.SUBJECT_PAGE);
        return "subject";
    }

    @RequestMapping(value = "/getAllSubject",method = RequestMethod.GET)
    public @ResponseBody List<Subject> getAllSubject(){
        return subjectService.getAllSubject();
    }

    @RequestMapping(value = "/getSubject/{id}")
    public @ResponseBody Subject getSubjectById(@PathVariable("id") int subjectId){
        return subjectService.getSubjectById(subjectId);
    }


}
