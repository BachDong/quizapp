package edu.haui.bvdong.quizapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.haui.bvdong.quizapp.model.Post;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {
    Page<Post> findAll(Pageable pageable);

    //Find by title and subjectId
    Page<Post> findByTitleLikeAndCourse_Subject_SubjectId(String title,int subjectId,Pageable pageable);

    Page<Post> findByTitleLike(String title,Pageable pageable);

    List<Post> findByCourseCourseId(int courseId);
}
