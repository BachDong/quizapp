package edu.haui.bvdong.quizapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import edu.haui.bvdong.quizapp.dto.PostDto;
import edu.haui.bvdong.quizapp.model.Post;
import edu.haui.bvdong.quizapp.model.User;

import java.util.List;


public interface PostService {
    Post addPost(PostDto postDto, User user);
    Page<Post> getAll(Pageable pageable);
    boolean deletePost(int id);
    Post getOneById(int id);
    Page<Post> getByTitleLikeAndCourse_Subject_SubjectId(String title,int subjectId,Pageable pageable);
    Page<Post> getByTitle(String title,Pageable pageable);
    List<Post> getByCourseId(int courseId);
}
