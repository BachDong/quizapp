package edu.haui.bvdong.quizapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import edu.haui.bvdong.quizapp.common.PageTitleConstrants;
import edu.haui.bvdong.quizapp.dto.UserDto;
import edu.haui.bvdong.quizapp.exception.PasswordMismatchException;
import edu.haui.bvdong.quizapp.exception.UserExistException;
import edu.haui.bvdong.quizapp.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserService userService;

    /**
     *
     * @param userService
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        model.addAttribute("title", PageTitleConstrants.REGISTER_PAGE);
        return "register";
    }

    /**
     *
     * @param userDto
     * @param bindingResult
     * @param model
     * @return
     * @throws UserExistException
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String doRegistration(@Valid final @ModelAttribute("userDto")UserDto userDto,BindingResult bindingResult, Model model) throws UserExistException {
        if(bindingResult.hasErrors()){
            return "register";
        }

        try{
            userService.add(userDto);
        }catch (PasswordMismatchException e){
            bindingResult.rejectValue("password","password.mismatch",e.toString());
        }catch (UserExistException e){
            bindingResult.rejectValue("userName","userName.taken",e.toString());
        }

        if(bindingResult.hasErrors()){
            return "register";
        }

       return "redirect:/login?register=true";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping(value = "/user/examHistory")
    public String showExamHistory(Model model){
        model.addAttribute("title",PageTitleConstrants.USER_EXAM_PAGE);
        return "user/userExam";
    }
}
