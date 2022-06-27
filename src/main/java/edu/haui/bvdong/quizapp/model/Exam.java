package edu.haui.bvdong.quizapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.haui.bvdong.quizapp.model.pk.ExamId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Exam implements Serializable {
    @Id
    @EmbeddedId
    private ExamId examId;
    private double mark;
    private int ranks;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @ManyToOne
    @MapsId("testId")
    private Test test;

    public ExamId getExamId() {
        return examId;
    }

    public void setExamId(ExamId examId) {
        this.examId = examId;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getRanks() {
        return ranks;
    }

    public void setRanks(int ranks) {
        this.ranks = ranks;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
