package edu.haui.bvdong.quizapp.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.haui.bvdong.quizapp.dto.JsonGeneratorDto;
import edu.haui.bvdong.quizapp.dto.PostDto;
import edu.haui.bvdong.quizapp.exception.UserExistException;
import edu.haui.bvdong.quizapp.model.Course;
import edu.haui.bvdong.quizapp.model.Post;
import edu.haui.bvdong.quizapp.model.User;
import edu.haui.bvdong.quizapp.repository.PostRepository;
import edu.haui.bvdong.quizapp.service.CourseService;
import edu.haui.bvdong.quizapp.service.PostService;
import edu.haui.bvdong.quizapp.service.UserService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private static final Logger LOGGER = LogManager.getLogger(PostServiceImpl.class);

    private final PostRepository postRepository;

    private final CourseService courseService;

    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CourseService courseService, UserService userService) {
        this.postRepository = postRepository;
        this.courseService = courseService;
        this.userService = userService;
    }


    @Override
    @Transactional
    public Post addPost(PostDto postDto,User user) {
        //Get a course of post
        Course course = courseService.getCourseById(postDto.getCourseId());
        if(course==null){
           return null;
        }

        //Convert postDto to Post
        Post post = new Post();
        post.setTitle(postDto.getPostTitle());
        post.setContent(postDto.getPostContent());
        post.setCourse(course);
        post.setCreateDate(new Date());

        //postId==0 meaning Add new post :: postId!=0 meaning update post which has id = postId
        if(postDto.getPostId()!=0){
            post.setPostId(postDto.getPostId());
            post.setUpdateDate(new Date());
        }


        //Set user to post
        post.setUser(user);

        return postRepository.save(post);
    }

    @Override
    public Page<Post> getAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public boolean deletePost(int id) {
        Optional<Post> post = postRepository.findById(id);
        boolean flag = false;

        //Check post is exist or not
        if(post.isPresent()){
            flag = true;
            postRepository.delete(post.get());
        }
        return flag;
    }

    @Override
    public Post getOneById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Post> getByTitleLikeAndCourse_Subject_SubjectId(String title,int subjectId,Pageable pageable) {
        return postRepository.findByTitleLikeAndCourse_Subject_SubjectId(title,subjectId,pageable);
    }

    @Override
    public Page<Post> getByTitle(String title, Pageable pageable) {
        return postRepository.findByTitleLike(title,pageable);
    }

    @Override
    public List<Post> getByCourseId(int courseId) {
        return postRepository.findByCourseCourseId(courseId);
    }
}
