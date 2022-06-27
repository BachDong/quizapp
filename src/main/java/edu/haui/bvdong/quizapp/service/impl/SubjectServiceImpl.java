package edu.haui.bvdong.quizapp.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.haui.bvdong.quizapp.model.Subject;
import edu.haui.bvdong.quizapp.repository.SubjectRepository;
import edu.haui.bvdong.quizapp.service.SubjectService;

import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService{
    private static final Logger LOGGER = LogManager.getLogger(SubjectServiceImpl.class);
    @Autowired
    private SubjectRepository subjectRepository;


    @Override
    @Transactional
    public Subject addSubject(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public List<Subject> getAllSubject() {
        LOGGER.info("getAllSubject");
        return subjectRepository.findAll();
    }

    @Override
    public Subject getSubjectById(int subjectId) {
        return subjectRepository.findById(subjectId).orElse(null);
    }
}
