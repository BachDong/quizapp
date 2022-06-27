package edu.haui.bvdong.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.haui.bvdong.quizapp.model.Test;

import java.util.List;

public interface TestRepository extends JpaRepository<Test,Integer>{
    List<Test> findAllByCourseCourseId(int courseId);

    @Query(value = "select test_text from test where test_id = ?1", nativeQuery = true)
    String getTestTextByTestId(int testId);
    List<Test> findAllByTestTextLike(String testText);

}
