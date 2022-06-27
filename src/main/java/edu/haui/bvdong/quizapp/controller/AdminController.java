package edu.haui.bvdong.quizapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.haui.bvdong.quizapp.common.PageTitleConstrants;

@Controller
public class AdminController {
    /**
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/admin",method = RequestMethod.GET)
    public String admin(ModelMap modelMap) {
        modelMap.addAttribute("title", PageTitleConstrants.HOME_PAGE);
        return "admin";
    }
}
