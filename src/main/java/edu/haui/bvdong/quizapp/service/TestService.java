package edu.haui.bvdong.quizapp.service;

import java.util.List;

import edu.haui.bvdong.quizapp.dto.ExamResultDto;
import edu.haui.bvdong.quizapp.dto.TestDto;
import edu.haui.bvdong.quizapp.dto.UserTestDto;
import edu.haui.bvdong.quizapp.dto.UserTestResult;
import edu.haui.bvdong.quizapp.model.Test;
import edu.haui.bvdong.quizapp.model.pk.ExamId;

public interface TestService {
    /**
     *
     * @param testId
     * @return a test by id
     */
    Test getById(int testId);

    /**
     *
     * @param testDto
     * @return object save or update
     */
    Test addOrUpdateTest(TestDto testDto);

    /**
     *
     * @param id
     * @return list test by course id
     */
    List<Test> getAllByCourseId(int id);

    /**
     *
     * @param testId
     * @return testText by testId
     */
    String getTestTextByTestId(int testId);

    /**
     *
     * @param userTestDto
     * @return examResultDto
     */
    ExamResultDto doTest(UserTestDto userTestDto);

    /**
     *
     * @return list all test
     */
    List<Test> getAllTest();

    /**
     *
     * @param testId
     * @do {
     *     delete test by id
     * }
     * @return true or false
     */
    boolean deleteById(int testId);

    /**
     *
     * @param testText
     * @return
     */
    List<Test> findByTestTextLike(String testText);
}
