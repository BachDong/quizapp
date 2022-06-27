package edu.haui.bvdong.quizapp.dto;

import java.util.List;

public class UserTestDto {
    private int testId;
    private List<UserQuestionDto> questionList;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public List<UserQuestionDto> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<UserQuestionDto> questionList) {
        this.questionList = questionList;
    }
}
