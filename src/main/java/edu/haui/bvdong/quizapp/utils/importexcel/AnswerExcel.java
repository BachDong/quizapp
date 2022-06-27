package edu.haui.bvdong.quizapp.utils.importexcel;

import java.io.Serializable;

public class AnswerExcel implements Serializable {

	private String answerText;
	private String sequence;
	private boolean correctAnswer;
	public String getAnswertext() {
		return answerText;
	}
	public void setAnswertext(String answertext) {
		this.answerText = answertext;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	public boolean getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public AnswerExcel(String answerText, String sequence, boolean correctAnswer) {
		this.answerText = answerText;
		this.sequence = sequence;
		this.correctAnswer = correctAnswer;
	}

	public AnswerExcel(String answertext, boolean correctAnswer) {
		super();
		this.answerText = answertext;
		this.correctAnswer = correctAnswer;
	}
	public AnswerExcel(String answertext) {
		this.answerText = answertext;
	}
	public AnswerExcel() {
		super();
	}

	@Override
	public String toString() {
		return "\n\tAnswer: " + answerText + ",correctAnswer=" + correctAnswer;
	}
	
}
