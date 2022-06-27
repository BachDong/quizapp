package edu.haui.bvdong.quizapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "questionId")
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private int questionId;

    @Type(type = "text")
    private String questionText;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "level_id")

    private Level level;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name="course_id")
    private Course course;

    @OneToMany(mappedBy = "question",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Answer> answers;

    @ManyToMany
    @JoinTable(
            name = "question_test",
            joinColumns = { @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "test_id") }
    )
    @JsonBackReference
    private List<Test> tests;

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Question() {
    }

    public Question(String questionText, Level level, Course course, List<Answer> answers) {

        this.questionText = questionText;
        this.level = level;
        this.course = course;
        this.answers = answers;
    }

}
