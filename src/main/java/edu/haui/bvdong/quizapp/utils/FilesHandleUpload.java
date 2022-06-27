package edu.haui.bvdong.quizapp.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import edu.haui.bvdong.quizapp.common.LoggerSpringBoot;
import edu.haui.bvdong.quizapp.common.PageTitleConstrants;
import edu.haui.bvdong.quizapp.utils.importexcel.FileUploads;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
public class FilesHandleUpload {


    /**
     *
     * @param fileUploads
     * @Do {
     *     GetFileUploads
     * }
     * @return
     */
    @ResponseBody
    @PostMapping("/rest/uploadMultiFiles")
    public ResponseEntity< List<String>> multiUploadFileModel(@ModelAttribute FileUploads fileUploads) {

       LoggerSpringBoot.getLoggerSpringBoot().info("Description:" + fileUploads.getDescription());
        LoggerSpringBoot.getLoggerSpringBoot().info(PageTitleConstrants.UPLOAD_DIR);
        List<String> listPath = null;
        try {

            listPath = this.saveUploadedFiles(fileUploads.getFiles());
        }
        // Here Catch IOException only.
        // Other Exceptions catch by RestGlobalExceptionHandler class.
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(listPath, HttpStatus.OK);
    }
    /**
     *
     * @param files
     * @do {
     *     Save Files
     * }
     * @return
     * @throws IOException
     */
    @ResponseBody
    private List<String> saveUploadedFiles(MultipartFile[] files) throws IOException {
        // Make sure directory exists!
        File uploadDir = new File(PageTitleConstrants.UPLOAD_DIR);
        uploadDir.mkdirs();
        List<String> listPath  =new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            String uploadFilePath = PageTitleConstrants.UPLOAD_DIR + "/" + file.getOriginalFilename();
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadFilePath);
            Files.write(path, bytes);
            listPath.add(uploadFilePath);
        }
        return listPath;
    }
}
