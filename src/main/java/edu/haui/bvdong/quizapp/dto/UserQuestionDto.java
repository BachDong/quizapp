package edu.haui.bvdong.quizapp.dto;

import java.util.List;

public class UserQuestionDto {
    private int questionId;
    private List<String> sequenceList;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public List<String> getSequenceList() {
        return sequenceList;
    }

    public void setSequenceList(List<String> sequenceList) {
        this.sequenceList = sequenceList;
    }
}
