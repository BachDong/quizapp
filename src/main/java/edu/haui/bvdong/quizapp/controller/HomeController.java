package edu.haui.bvdong.quizapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haui.bvdong.quizapp.common.PageTitleConstrants;

@Controller
public class HomeController {
    private static final Logger LOGGER = LogManager.getLogger(HomeController.class);

    /**
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("title", PageTitleConstrants.HOME_PAGE);
        return "index";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/403",method = RequestMethod.GET)
    public @ResponseBody String er403() {
        LOGGER.info("403");
        return "403 err";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/404")
    public String er404(Model model) {
        model.addAttribute("title","Sorry Page Not Found");
        LOGGER.info("404");
        return "error/404";
    }
}
