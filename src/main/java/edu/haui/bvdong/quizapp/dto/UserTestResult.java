package edu.haui.bvdong.quizapp.dto;

import java.io.Serializable;

public class UserTestResult<T,S> implements Serializable{
    private int questionId;
    private S userChoose;
    private T rightAnswer;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public S getUserChoose() {
        return userChoose;
    }

    public void setUserChoose(S userChoose) {
        this.userChoose = userChoose;
    }

    public T getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(T rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public UserTestResult(int questionId, S userChoose, T rightAnswer) {
        this.questionId = questionId;
        this.userChoose = userChoose;
        this.rightAnswer = rightAnswer;
    }
}

