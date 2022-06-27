package edu.haui.bvdong.quizapp.dto;

import java.io.Serializable;
import java.util.List;

public class ExamResultDto implements Serializable {
    private double score;
    private int rank;
    private int correct;
    private int wrong;
    private boolean status;
    private List<UserTestResult> userTestResults;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void setWrong(int wrong) {
        this.wrong = wrong;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<UserTestResult> getUserTestResults() {
        return userTestResults;
    }

    public void setUserTestResults(List<UserTestResult> userTestResults) {
        this.userTestResults = userTestResults;
    }


}
