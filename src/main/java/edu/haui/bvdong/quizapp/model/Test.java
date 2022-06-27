package edu.haui.bvdong.quizapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "testId")
public class Test implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_id")
    private int testId;
    private String testText;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "course_id")
    private Course course;
    private int duration;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "level_id")
    private Level level;
    private int totalMark;

    @OneToMany(mappedBy = "test")
    @JsonBackReference
    private List<Exam> exams;

    @ManyToMany
    @JoinTable(
            name = "question_test",
            joinColumns = { @JoinColumn(name = "test_id") },
            inverseJoinColumns = { @JoinColumn(name = "question_id") }
    )
    @JsonManagedReference
    private List<Question> questions;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestText() {
        return testText;
    }

    public void setTestText(String testText) {
        this.testText = testText;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
