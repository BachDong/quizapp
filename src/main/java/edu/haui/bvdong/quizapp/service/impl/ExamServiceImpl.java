package edu.haui.bvdong.quizapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import edu.haui.bvdong.quizapp.dto.ExamResultDto;
import edu.haui.bvdong.quizapp.model.Exam;
import edu.haui.bvdong.quizapp.model.Test;
import edu.haui.bvdong.quizapp.model.pk.ExamId;
import edu.haui.bvdong.quizapp.repository.ExamRepository;
import edu.haui.bvdong.quizapp.service.ExamService;
import edu.haui.bvdong.quizapp.service.UserService;

import java.util.Date;
import java.util.List;

@Service
public class ExamServiceImpl implements ExamService {
    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private UserService userService;

    @Override
    public Exam addExam(Exam exam) {
        return examRepository.save(exam);
    }

    @Override
    public void deleteExamById(ExamId examId) {
        examRepository.deleteById(examId);
    }

    @Override
    public List<Exam> getAllByUserIdAndSubjectId(int userId, int subjectId) {
        return examRepository.findAllByExamId_UserIdAndTestCourseSubjectSubjectId(userId,subjectId);
    }

    @Override
    public Exam addExam(ExamResultDto examResultDto,Test test) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        edu.haui.bvdong.quizapp.model.User userLogged = userService.findByUserName(user.getUsername());
        ExamId examId = new ExamId();
        examId.setUserId(userLogged.getUserId());
        examId.setDateTime(new Date());
        examId.setTestId(test.getTestId());

        Exam exam = new Exam();
        exam.setExamId(examId);
        exam.setMark(examResultDto.getScore());
        exam.setRanks(examResultDto.getRank());
        exam.setTest(test);
        exam.setUser(userLogged);

        return examRepository.save(exam);
    }
}
