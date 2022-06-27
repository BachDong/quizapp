package edu.haui.bvdong.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.haui.bvdong.quizapp.model.Level;

public interface LevelRepository extends JpaRepository<Level,Integer>{
    Level findByLevelName(String levelName);
}
