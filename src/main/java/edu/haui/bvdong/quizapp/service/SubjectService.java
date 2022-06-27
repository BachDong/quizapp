package edu.haui.bvdong.quizapp.service;

import java.util.List;

import edu.haui.bvdong.quizapp.model.Subject;

public interface SubjectService {
    /**
     *
     * @param subject
     * @return subject is saved
     */
    Subject addSubject(Subject subject);

    /**
     *
     * @return list  all subject
     */
    List<Subject> getAllSubject();

    /**
     *
     * @param subjectId
     * @return subject by id
     */
    Subject getSubjectById(int subjectId);
}
