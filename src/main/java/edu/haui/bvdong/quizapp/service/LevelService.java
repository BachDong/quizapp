package edu.haui.bvdong.quizapp.service;

import java.util.List;

import edu.haui.bvdong.quizapp.model.Level;

public interface LevelService {
    /**
     *
     * @param level
     * @return a level saved
     */
    Level addLevel(Level level);

    /**
     *
     * @return list<Level>
     */
    List<Level> getAllLevel();

    /**
     *
     * @param levelId
     * @return a level by levelId
     */
    Level getLevelById(int levelId);
}
