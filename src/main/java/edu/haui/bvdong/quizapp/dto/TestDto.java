package edu.haui.bvdong.quizapp.dto;

import java.util.List;
import java.util.Set;

import edu.haui.bvdong.quizapp.model.Question;

public class TestDto {

    private int testId;
    private String testText;
    private int duration;
    private int totalMark;
    private int courseId;
    private int levelId;
    private Set<Integer> questionIdList;

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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTotalMark() {
        return totalMark;
    }

    public void setTotalMark(int totalMark) {
        this.totalMark = totalMark;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public Set<Integer> getQuestionIdList() {
        return questionIdList;
    }

    public void setQuestionIdList(Set<Integer> questionIdList) {
        this.questionIdList = questionIdList;
    }

    @Override
    public String toString() {
        return "TestDto{" +
                "testId=" + testId +
                ", testText='" + testText + '\'' +
                ", duration=" + duration +
                ", totalMark=" + totalMark +
                ", courseId=" + courseId +
                ", levelId=" + levelId +
                ", questionIdList=" + questionIdList +
                '}';
    }
}
