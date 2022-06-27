package edu.haui.bvdong.quizapp.utils;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import edu.haui.bvdong.quizapp.common.LoggerSpringBoot;


public class FileDowload {

    public static void downloadFileFromURL(String urlString, String destination) {
        try {
            LoggerSpringBoot.getLoggerSpringBoot().info("Start DOWNLOAD EXCEL");
            File file=new File(destination);
            URL website = new URL(urlString);
            ReadableByteChannel rbc;
            rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(file);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
            LoggerSpringBoot.getLoggerSpringBoot().info("Finished DOWNLOAD EXCEL");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
