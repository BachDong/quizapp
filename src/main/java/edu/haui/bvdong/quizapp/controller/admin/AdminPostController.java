package edu.haui.bvdong.quizapp.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import edu.haui.bvdong.quizapp.common.PageTitleConstrants;
import edu.haui.bvdong.quizapp.dto.JsonGeneratorDto;
import edu.haui.bvdong.quizapp.dto.PostDto;
import edu.haui.bvdong.quizapp.model.Course;
import edu.haui.bvdong.quizapp.model.Post;
import edu.haui.bvdong.quizapp.service.CourseService;
import edu.haui.bvdong.quizapp.service.PostService;
import edu.haui.bvdong.quizapp.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/admin/post-management")
public class AdminPostController {

    private final PostService postService;

    private final UserService userService;

    @Autowired
    public AdminPostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    /**
     * Display post management page when going to /admin/post-management/
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String showIndex(ModelMap modelMap){
        modelMap.addAttribute("title", PageTitleConstrants.POST_MANAGEMENT);
        return "admin/post-management";
    }

    /**
     * Delete a post
     * @param postId
     * @return an json object
     * {"status":true,"msg":"Delete success!"} meaning delete was successful
     * {"status":false,"msg":"Error"} meaning delete was unsuccessful
     */
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public @ResponseBody JsonGeneratorDto<?> deletePost(@PathVariable(value="id")int postId){
        if(postService.deletePost(postId)){
            return new JsonGeneratorDto<>(true,"Delete success!");
        }else{
            return new JsonGeneratorDto<>(false,"Error");
        }
    }

    /**
     * add a post
     * @param postDto
     * @param bindingResult
     * @param model
     * @return
     * {"status":false,"msg":"Course not found"} meaning : failed because course not found!
     * {"status":false,"msg":"@Some error"} meaning : validation was failed
     * {"status":true,"msg":"@newPost"} meaning : successful, return with a new post
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public @ResponseBody JsonGeneratorDto<?> addPost(@Valid final @ModelAttribute("postDto")PostDto postDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){ //validation was failed
            return new JsonGeneratorDto<>(false,bindingResult.getFieldError().getField()+" :: "+bindingResult.getFieldError().getDefaultMessage());
        }

        //Get current user logged
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User)authentication.getPrincipal();

        edu.haui.bvdong.quizapp.model.User currentUser = userService.findByUserName(user.getUsername());

        Post post = postService.addPost(postDto,currentUser);
        if(post==null){
            return new JsonGeneratorDto<>(false,"Course not found");
        }

        return new JsonGeneratorDto<>(true,post);
    }


}
