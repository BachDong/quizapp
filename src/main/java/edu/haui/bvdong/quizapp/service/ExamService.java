package edu.haui.bvdong.quizapp.service;

import java.util.List;

import edu.haui.bvdong.quizapp.dto.ExamResultDto;
import edu.haui.bvdong.quizapp.model.Exam;
import edu.haui.bvdong.quizapp.model.Test;
import edu.haui.bvdong.quizapp.model.pk.ExamId;

public interface ExamService {
    Exam addExam(Exam exam);
    void deleteExamById(ExamId examId);
    List<Exam> getAllByUserIdAndSubjectId(int userId,int subjectId);
    Exam addExam(ExamResultDto examResultDto,Test test);
}
