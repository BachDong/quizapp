package edu.haui.bvdong.quizapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AnswerDto {


    private int answerId;
    @NotNull(message = "Name Answer can't be null!")
    @NotEmpty(message = "Name Answer can't be empty!")
    private String answerText;
    @NotNull(message = "sequence Answer can't be null!")
    @NotEmpty(message = "sequence Answer can't be empty!")
    private String sequence;
    @NotNull(message = "correctAnswer  can't be null!")
    @NotEmpty(message = "correctAnswer  can't be empty!")
    private boolean correctAnswer;

    private QuestionDto questionDto;


    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }



    public void setQuestionDto(QuestionDto questionDto) {
        this.questionDto = questionDto;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public QuestionDto getQuestionDto() {
        return questionDto;
    }

    @Override
    public String toString() {
        return "AnswerDto{" +
                "answerId=" + answerId +
                ", answerText='" + answerText + '\'' +
                ", sequence='" + sequence + '\'' +
                ", correctAnswer=" + correctAnswer +
                ", questionDto=" + questionDto +
                '}';
    }
}
