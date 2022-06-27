package edu.haui.bvdong.quizapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haui.bvdong.quizapp.model.Level;
import edu.haui.bvdong.quizapp.service.LevelService;

import java.util.List;
@Controller
@RequestMapping(value = "/level")
public class LevelController {

    @Autowired
    private LevelService levelService;

    @GetMapping(value = "/getAllLevel")
    @ResponseBody
    public List<Level> getAllLevel(){
        return levelService.getAllLevel();
    }
}
