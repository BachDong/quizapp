package edu.haui.bvdong.quizapp.dto;

import java.io.Serializable;
import java.util.List;

public class Paths implements Serializable {
    List<String> listPath;

    public List<String> getListPath() {
        return listPath;
    }

    public void setListPath(List<String> listPath) {
        this.listPath = listPath;
    }

    public Paths(List<String> listPath) {
        this.listPath = listPath;
    }

    public Paths() {
    }
}
