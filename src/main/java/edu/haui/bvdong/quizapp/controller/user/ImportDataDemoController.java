package edu.haui.bvdong.quizapp.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.haui.bvdong.quizapp.dto.TestDto;
import edu.haui.bvdong.quizapp.model.*;
import edu.haui.bvdong.quizapp.repository.*;
import edu.haui.bvdong.quizapp.service.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ImportDataDemoController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private LevelService levelService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestService testService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @RequestMapping("/import")
    @Transactional
    public @ResponseBody
    boolean hello() {
        Role role = new Role();
        role.setAuthority("ROLE_ADMIN");
        roleRepository.save(role);

        Role role1 = new Role();
        role1.setAuthority("ROLE_MEMBER");
        roleRepository.save(role1);

        Set<Role> roles = new HashSet<>();
        roles.add(role);
        roles.add(role1);

        User user = new User();
        user.setUserName("admin");
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRoles(roles);

        userRepository.save(user);

        Subject subject = new Subject();
        subject.setSubjectName("Java");

        Subject subject1 = new Subject();
        subject1.setSubjectName("Jsp");

        Subject subject2 = new Subject();
        subject2.setSubjectName("Hibernate");

        Subject subject3 = new Subject();
        subject3.setSubjectName("Servlet");

        Subject subject4 = new Subject();
        subject4.setSubjectName("Subject 12345");

        subjectService.addSubject(subject);
        subjectService.addSubject(subject1);
        subjectService.addSubject(subject2);
        subjectService.addSubject(subject3);
        subjectService.addSubject(subject4);


        Course course = new Course();
        course.setCourseName("OOP");
        course.setSubject(subject);

        Course course1 = new Course();
        course1.setCourseName("JDBC");
        course1.setSubject(subject);

        Course course2 = new Course();
        course2.setCourseName("JSP-01");
        course2.setSubject(subject1);

        Course course3 = new Course();
        course3.setCourseName("JSP-02");
        course3.setSubject(subject1);

        courseService.addCourse(course);
        courseService.addCourse(course1);
        courseService.addCourse(course2);
        courseService.addCourse(course3);

        Level level= new Level();
        level.setLevelName("Easy");
        Level level1= new Level();
        level1.setLevelName("Medium");
        Level level2= new Level();
        level2.setLevelName("Hard");
        Level level3= new Level();
        level3.setLevelName("Very Hard");
        levelService.addLevel(level);
        levelService.addLevel(level1);
        levelService.addLevel(level2);
        levelService.addLevel(level3);


        return true;


    }

    @GetMapping("/import/test/{id}")
    public @ResponseBody Test getTestByIdDemo(@PathVariable("id")int id){
        return testService.getById(id);
    }

    @GetMapping("/import/demo/a")
    @Transactional
    public @ResponseBody boolean adddTeao(){
        Answer a1 = new Answer();
        a1.setSequence("A");
        a1.setAnswerText("Dung");
        a1.setCorrectAnswer(true);
        answerRepository.save(a1);

        Answer a2 = new Answer();
        a2.setSequence("B");
        a2.setAnswerText("Sai");
        a2.setCorrectAnswer(false);
        answerRepository.save(a2);

        return true;
    }

    @GetMapping("/import/demo/q")
    @Transactional
    public @ResponseBody Question addTeao(){

        Question q1 = new Question();
        q1.setQuestionText("Ha noi co phai thu do Viet Nam?");
        q1.setLevel(levelService.getLevelById(1));
        q1.setCourse(courseService.getCourseById(1));
        questionRepository.save(q1);
        answerRepository.findById(5).orElse(null).setQuestion(q1);
        answerRepository.findById(6).orElse(null).setQuestion(q1);
        return questionRepository.save(q1);
//
//        Question q2 = new Question();
//        q2.setQuestionText("Ha noi co phai thu do Viet Nam 2?");
//        q2.setLevel(levelService.getLevelById(1));
//        q2.setCourse(courseService.getCourseById(1));
//        q2.setAnswers(answerList);
//
//
//
//        return questionRepository.save(q2)!=null;
    }

    @GetMapping("/import/demo/test")
    @Transactional
    public @ResponseBody Test addTestByIdDemo(){

        TestDto testDto= new TestDto();
        testDto.setCourseId(1);
        testDto.setLevelId(2);
        testDto.setDuration(90);
        testDto.setTotalMark(100);
        testDto.setTestText("BAI TEST Demo");
        Set<Integer> integerList=new HashSet<>();
        integerList.add(14);
        integerList.add(14);
        integerList.add(16);
        integerList.add(17);
        testDto.setQuestionIdList(integerList);
        return testService.addOrUpdateTest(testDto);
    }

    @GetMapping("/demo")
    public @ResponseBody
    List<Subject> listAllWithCourse() {
        return subjectRepository.findAll();
    }
}
