package edu.haui.bvdong.quizapp.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.haui.bvdong.quizapp.model.Level;
import edu.haui.bvdong.quizapp.repository.LevelRepository;
import edu.haui.bvdong.quizapp.service.LevelService;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {
    private static final Logger LOGGER = LogManager.getLogger(LevelServiceImpl.class);
    @Autowired
    private LevelRepository levelRepository;
    @Autowired
    private EntityManager em;

    @Override
    public List<Level> getAllLevel() {
        LOGGER.info("getAllLevel");
        return levelRepository.findAll();
    }


    @Override
    @Transactional
    public Level addLevel(Level level) {

        return levelRepository.save(level);
    }

    @Override
    public Level getLevelById(int levelId) {
        return levelRepository.findById(levelId).orElse(null);
    }
}
