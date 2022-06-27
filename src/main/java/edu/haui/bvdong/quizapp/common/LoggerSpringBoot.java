package edu.haui.bvdong.quizapp.common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.haui.bvdong.quizapp.service.impl.PostServiceImpl;

public class LoggerSpringBoot {

    public static final  Logger getLoggerSpringBoot(){
        return LogManager.getLogger(LoggerSpringBoot.class);
    }

}
