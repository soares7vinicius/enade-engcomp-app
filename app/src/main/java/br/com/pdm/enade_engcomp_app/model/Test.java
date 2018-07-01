package br.com.pdm.enade_engcomp_app.model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

import java.util.List;

/**
 * Created by marco on 31/05/2018.
 */

public class Test extends Model {
    @Exclude
    private final int questions_qtt = 10;

    private int correct_qtt = 0;
    private int points = 0;
    private List<DocumentReference> questions;
    private  DocumentReference user;

    public Test(){}

    public Test(DocumentReference user){
        this.user = user;
    }

    public Test(int correct_qtt, int points, DocumentReference user) {
        this.correct_qtt = correct_qtt;
        this.points = points;
        this.user = user;
    }

    public Test(int correct_qtt, int points, List<DocumentReference> questions, DocumentReference user) {
        this.correct_qtt = correct_qtt;
        this.points = points;
        this.questions = questions;
        this.user = user;
    }

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }

    public int getQuestions_qtt() {
        return questions_qtt;
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

    public List<DocumentReference> getQuestions() {
        return questions;
    }

    public void setQuestions(List<DocumentReference> questions) {
        this.questions = questions;
    }
}
