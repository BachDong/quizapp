package edu.haui.bvdong.quizapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haui.bvdong.quizapp.common.PageTitleConstrants;
import edu.haui.bvdong.quizapp.model.Role;
import edu.haui.bvdong.quizapp.model.User;
import edu.haui.bvdong.quizapp.repository.RoleRepository;
import edu.haui.bvdong.quizapp.repository.UserRepository;
import edu.haui.bvdong.quizapp.service.impl.UserDetailsServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AuthenticationController {

    private static final Logger LOGGER = LogManager.getLogger(AuthenticationController.class);

    /**
     *
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String getLogin(ModelMap modelMap) {
        LOGGER.info("Start Login:::::::::::");
        modelMap.addAttribute("title", PageTitleConstrants.LOGIN_PAGE);
        return "login";
    }
}
