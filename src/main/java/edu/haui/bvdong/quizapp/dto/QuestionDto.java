package edu.haui.bvdong.quizapp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import edu.haui.bvdong.quizapp.model.Answer;
import edu.haui.bvdong.quizapp.model.Course;
import edu.haui.bvdong.quizapp.model.Level;
import edu.haui.bvdong.quizapp.model.Test;

import java.io.Serializable;
import java.util.List;

public class QuestionDto implements Serializable {

    private int questionId;
    @NotNull(message = "Name Question can't be null!")
    @NotEmpty(message = "Name Question can't be empty!")
    private String questionText;
    @NotNull(message = "Question can't have level is null!")
    @NotEmpty(message = "Question can't have level is empty!")
    private LevelDto levelDto;
    @NotNull(message = "Question can't have course is null!")
    @NotEmpty(message = "Question can't have course is empty!")
    private CourseDto courseDto;
    @NotNull(message = "Question can't have Answer is null!")
    @NotEmpty(message = "Question can't have Answer is empty!")
    private List<AnswerDto> answersDto;
    private List<AnswerDto> answersDelete;

    public List<AnswerDto> getAnswersDelete() {
        return answersDelete;
    }

    public void setAnswersDelete(List<AnswerDto> answersDelete) {
        this.answersDelete = answersDelete;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public LevelDto getLevelDto() {
        return levelDto;
    }

    public void setLevelDto(LevelDto levelDto) {
        this.levelDto = levelDto;
    }

    public CourseDto getCourseDto() {
        return courseDto;
    }

    public void setCourseDto(CourseDto courseDto) {
        this.courseDto = courseDto;
    }

    public List<AnswerDto> getAnswersDto() {
        return answersDto;
    }

    public void setAnswersDto(List<AnswerDto> answersDto) {
        this.answersDto = answersDto;
    }

    @Override
    public String toString() {
        return "QuestionDto{" +
                "questionId=" + questionId +
                ", questionText='" + questionText + '\'' +
                ", levelDto=" + levelDto +
                ", courseDto=" + courseDto +
                ", answersDto=" + answersDto +
                '}';
    }
}
