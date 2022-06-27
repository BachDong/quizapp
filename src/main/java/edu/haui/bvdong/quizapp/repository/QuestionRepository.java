package edu.haui.bvdong.quizapp.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.haui.bvdong.quizapp.model.Question;

public interface QuestionRepository extends JpaRepository<Question,Integer> {

    //Find A Question
    Question findByQuestionId(int question_id);

    //Find All
    Page<Question> findAll(Pageable pageable);

    //Find by questionText & subjectId & levelId
    Page<Question> findByQuestionTextLikeAndAndCourse_Subject_SubjectIdAndLevel_LevelId(String questionText,int subjectId,int levelId,Pageable pageable);

    //Find by questionText & subjectId
    Page<Question> findByQuestionTextLikeAndAndCourse_Subject_SubjectId(String questionText,int subjectId,Pageable pageable);

    //Find by questionText & levelId
    Page<Question> findByQuestionTextLikeAndLevel_LevelId(String questionText,int levelId,Pageable pageable);

    //Find by   levelId & subjectId
    Page<Question> findByLevel_LevelIdAndCourse_Subject_SubjectId(int levelId,int subjectId,Pageable pageable);

    //Find by questionText
    Page<Question> findByQuestionTextLike(String questionText,Pageable pageable);
    //Find by LevelID
    Page<Question> findByLevel_LevelId(int levelId,Pageable pageable);
    //Find by SubjectID
    Page<Question> findByCourse_Subject_SubjectId(int subjectId,Pageable pageable);

}