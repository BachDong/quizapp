package edu.haui.bvdong.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.haui.bvdong.quizapp.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject,Integer> {

}
