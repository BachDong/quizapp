package edu.haui.bvdong.quizapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import edu.haui.bvdong.quizapp.model.Post;
import edu.haui.bvdong.quizapp.service.PostService;

import java.util.List;

@Controller
@RequestMapping(value = "/posts")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    /**
     *
     * @param page
     * @param size
     * @param sort
     * @return All posts with pagination
     */
    @GetMapping(value = "/getAll")
    public @ResponseBody
    Page<Post> getAll(@RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
                      @RequestParam(name = "size", required = false, defaultValue = "5") final Integer size,
                      @RequestParam(name = "sort", required = false, defaultValue = "ASC") final String sort) {

        Sort sortable = Sort.by("postId").ascending();

        if (sort.equals("DESC")) {
            sortable = Sort.by("postId").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);
        return postService.getAll(pageable);
    }

    /**
     *
     * @param page
     * @param size
     * @param sort
     * @param subjectId
     * @param title
     * @return all posts have subjectId and title Like
     */
    @GetMapping(value = "/search")
    public @ResponseBody
    Page<Post> searchPost(@RequestParam(name = "page", required = false, defaultValue = "0") final Integer page,
                          @RequestParam(name = "size", required = false, defaultValue = "5") final Integer size,
                          @RequestParam(name = "sort", required = false, defaultValue = "ASC") final String sort,
                          @RequestParam(name = "subjectId", required = false, defaultValue = "0") final int subjectId,
                          @RequestParam(name = "title", required = false, defaultValue = "") final String title) {

        Sort sortable = Sort.by("postId").ascending();
        if (sort.equals("DESC")) {
            sortable = Sort.by("postId").descending();
        }

        Pageable pageable = PageRequest.of(page, size, sortable);
        if (subjectId != 0) {
            return postService.getByTitleLikeAndCourse_Subject_SubjectId("%" + title + "%", subjectId, pageable);
        } else {
            return postService.getByTitle("%" + title + "%", pageable);
        }

    }

    /**
     *
     * @param postId
     * @return One post with id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Post getOneById(@PathVariable(value = "id") final int postId) {
        return postService.getOneById(postId);
    }

    @RequestMapping(value = "/courseId/{id}")
    public @ResponseBody
    List<Post> getAllByCourseId(@PathVariable(value = "id") final int courseId) {
        return postService.getByCourseId(courseId);
    }
}
