package edu.haui.bvdong.quizapp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class PostDto implements Serializable{
    private int postId;
    @NotNull(message = "Title can't be null!")
    @NotEmpty(message = "Title can't be empty!")
    private String postTitle;
    @NotNull(message = "Content can't be null!")
    @NotEmpty(message = "Content can't be empty!")
    private String postContent;

    @Min(1)
    private int courseId;

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }
}
