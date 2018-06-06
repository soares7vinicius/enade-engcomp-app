package com.example.marco.enade_engcomp_app.model;

import java.util.List;

/**
 * Created by marco on 31/05/2018.
 */

public class Test {
    private long id;
    private int questions_qtt;
    private int correct_qtt;
    private int points;
    List<Question> questions;
    User user;

    public Test(int questions_qtt, int correct_qtt, int points, User user) {
        this.questions_qtt = questions_qtt;
        this.correct_qtt = correct_qtt;
        this.points = points;
        this.user = user;
    }

    public Test(int questions_qtt, int correct_qtt, int points, List<Question> questions, User user) {
        this.questions_qtt = questions_qtt;
        this.correct_qtt = correct_qtt;
        this.points = points;
        this.questions = questions;
        this.user = user;
    }

    public Test(long id, int questions_qtt, int correct_qtt, int points, List<Question> questions, User user) {
        this.id = id;
        this.questions_qtt = questions_qtt;
        this.correct_qtt = correct_qtt;
        this.points = points;
        this.questions = questions;
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuestions_qtt() {
        return questions_qtt;
    }

    public void setQuestions_qtt(int questions_qtt) {
        this.questions_qtt = questions_qtt;
    }

    public int getCorrect_qtt() {
        return correct_qtt;
    }

    public void setCorrect_qtt(int correct_qtt) {
        this.correct_qtt = correct_qtt;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
