package edu.haui.bvdong.quizapp.service;

import java.util.List;

import edu.haui.bvdong.quizapp.model.Course;

public interface CourseService {
    /**
     *
     * @param course
     * @return  a course is saved
     */
    Course addCourse(Course course);

    /**
     *
     * @return list all course
     */
    List<Course> getAllCourse();

    /**
     *
     * @param subjectId
     * @return list course by subjectId
     */
    List<Course> getAllCourseBySubjectId(int subjectId);

    /**
     *
     * @param id
     * @return a course by id
     */
    Course getCourseById(int id);
}
