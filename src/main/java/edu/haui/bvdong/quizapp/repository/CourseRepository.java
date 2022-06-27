package edu.haui.bvdong.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.haui.bvdong.quizapp.model.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Integer> {
    List<Course> findAllBySubjectSubjectId(int subjectId);
    Course findByCourseId(int id);
    Course findByCourseName(String courseName);
}
