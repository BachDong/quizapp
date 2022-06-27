package edu.haui.bvdong.quizapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.haui.bvdong.quizapp.common.LoggerSpringBoot;
import edu.haui.bvdong.quizapp.model.Course;
import edu.haui.bvdong.quizapp.repository.CourseRepository;
import edu.haui.bvdong.quizapp.service.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    /**
     *
     * @param course
     * @return course is saved
     */
    @Override
    @Transactional
    public Course addCourse(Course course) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"ADD COURSE");
        return courseRepository.save(course);
    }

    /**
     *
     * @return List all course
     */
    @Override
    public List<Course> getAllCourse() {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"GET ALL COURSE");
        return courseRepository.findAll();
    }

    /**
     *
     * @param subjectId
     * @return list course
     */
    @Override
    public List<Course> getAllCourseBySubjectId(int subjectId) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"Get All Course By Subject ID");
        return courseRepository.findAllBySubjectSubjectId(subjectId);
    }

    /**
     *
     * @param id
     * @return course by id
     */
    @Override
    public Course getCourseById(int id) {
        LoggerSpringBoot.getLoggerSpringBoot().info(this.getClass()+"Get CourseBy ID");
        return courseRepository.findByCourseId(id);
    }
}
