package edu.haui.bvdong.quizapp.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.web.multipart.MultipartFile;

import edu.haui.bvdong.quizapp.common.LoggerSpringBoot;
import edu.haui.bvdong.quizapp.utils.importexcel.AnswerExcel;
import edu.haui.bvdong.quizapp.utils.importexcel.QuestionExcel;

public class ExcelReader {
    /**
     * @param PATH
     * @return List<QuestionExcel>
     * @throws EncryptedDocumentException
     * @throws InvalidFormatException
     * @do {
     * Convert ExcelQuestion To Question Entity and Answer Entity
     * }
     */
    public static List<QuestionExcel> readExcelQuestion(final String PATH, final MultipartFile file) throws EncryptedDocumentException, InvalidFormatException {
        List<QuestionExcel> listQuestionExcel = new ArrayList<QuestionExcel>();
        LoggerSpringBoot.getLoggerSpringBoot().info("START readExcelQuestion");
        try {
            Workbook workbook;
            if ("".equals(PATH)&& null!=file) {
                LoggerSpringBoot.getLoggerSpringBoot().info(":::ReadExcelQuestion Via FILE:::");
                workbook = WorkbookFactory.create(file.getInputStream());
            } else {
                LoggerSpringBoot.getLoggerSpringBoot().info(":::ReadExcelQuestion Via URL:::");
                workbook = WorkbookFactory.create(new File(PATH));
            }
            Sheet datatypeSheet = workbook.getSheetAt(0);
            DataFormatter fmt = new DataFormatter();
            Iterator<Row> iterator = datatypeSheet.iterator();
            Row firstRow = iterator.next();
            Cell firstCell = firstRow.getCell(0);
            LoggerSpringBoot.getLoggerSpringBoot().info("Header: " + firstCell.getStringCellValue());
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if ("".equals(fmt.formatCellValue(currentRow.getCell(0)))) {
                    break;
                }
                List<AnswerExcel> listAnswer = new ArrayList<>();
                boolean correctA = false;
                boolean correctB = false;
                boolean correctC = false;
                boolean correctD = false;
                if (fmt.formatCellValue(currentRow.getCell(1)).equals(fmt.formatCellValue(currentRow.getCell(5)))) {
                    correctA = true;
                }
                if (fmt.formatCellValue(currentRow.getCell(2)).equals(fmt.formatCellValue(currentRow.getCell(5)))) {
                    correctB = true;
                }
                if (fmt.formatCellValue(currentRow.getCell(3)).equals(fmt.formatCellValue(currentRow.getCell(5)))) {
                    correctC = true;
                }
                if (fmt.formatCellValue(currentRow.getCell(4)).equals(fmt.formatCellValue(currentRow.getCell(5)))) {
                    correctD = true;
                }
                QuestionExcel questionExcel = new QuestionExcel(fmt.formatCellValue(currentRow.getCell(0)));
                listAnswer.add(new AnswerExcel(fmt.formatCellValue(currentRow.getCell(1)), "A", correctA));
                listAnswer.add(new AnswerExcel(fmt.formatCellValue(currentRow.getCell(2)), "B", correctB));
                listAnswer.add(new AnswerExcel(fmt.formatCellValue(currentRow.getCell(3)), "C", correctC));
                listAnswer.add(new AnswerExcel(fmt.formatCellValue(currentRow.getCell(4)), "D", correctD));
                questionExcel.setLevelText(fmt.formatCellValue(currentRow.getCell(6)));
                questionExcel.setCourseText(fmt.formatCellValue(currentRow.getCell(7)));
                questionExcel.setListAnswer(listAnswer);
                listQuestionExcel.add(questionExcel);
            }
            for (QuestionExcel questionExcel1 : listQuestionExcel) {
                LoggerSpringBoot.getLoggerSpringBoot().info(questionExcel1.toString());
                LoggerSpringBoot.getLoggerSpringBoot().info("\n");
            }
            workbook.close();
        } catch (FileNotFoundException e) {
            LoggerSpringBoot.getLoggerSpringBoot().error("Can't Find file:::::: " + e);
        } catch (IOException e) {
            LoggerSpringBoot.getLoggerSpringBoot().error("Can't Read file:::::: " + e);
        }
        LoggerSpringBoot.getLoggerSpringBoot().info(":::ReadExcelQuestion Success:::");
        return listQuestionExcel;
    }
}