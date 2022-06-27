package edu.haui.bvdong.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.haui.bvdong.quizapp.model.Exam;
import edu.haui.bvdong.quizapp.model.pk.ExamId;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam,ExamId> {
    List<Exam> findAllByExamId_UserIdAndTestCourseSubjectSubjectId(int userId, int subjectId);
}
