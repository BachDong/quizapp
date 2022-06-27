package edu.haui.bvdong.quizapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import edu.haui.bvdong.quizapp.model.Course;

public class CourseDto{

    private int courseId;
    @NotNull(message = "Name Course can't be null!")
    @NotEmpty(message = "Name Course can't be empty!")
    private String courseName;

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public String toString() {
        return "CourseDto{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
