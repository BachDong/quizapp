package edu.haui.bvdong.quizapp.utils.importexcel;

import java.io.Serializable;
import java.util.List;

public class QuestionExcel  implements Serializable {
	private String questionText;
	private List<AnswerExcel> listAnswer ;
	private String levelText;
	private String courseText;
	
	public QuestionExcel() {
		super();
	}

	public List<AnswerExcel> getListAnswer() {
		return listAnswer;
	}

	public void setListAnswer(List<AnswerExcel> listAnswer) {
		this.listAnswer = listAnswer;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getLevelText() {
		return levelText;
	}

	public void setLevelText(String levelText) {
		this.levelText = levelText;
	}

	public String getCourseText() {
		return courseText;
	}

	public void setCourseText(String courseText) {
		this.courseText = courseText;
	}

	public QuestionExcel(String questionText, List<AnswerExcel> listAnswer, String levelText, String courseText) {
		super();
		this.questionText = questionText;
		this.listAnswer = listAnswer;
		this.levelText = levelText;
		this.courseText = courseText;
	}

	public QuestionExcel(String questionText) {
		super();
		this.questionText = questionText;
	}


	@Override
	public String toString() {
		return "QuestionExcel " + courseText + ",  " + levelText + ", "  + questionText
				+ ",\nlistAnswer=" + listAnswer ;
	}



}