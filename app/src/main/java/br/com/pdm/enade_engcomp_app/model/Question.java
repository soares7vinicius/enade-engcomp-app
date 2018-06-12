package br.com.pdm.enade_engcomp_app.model;

/**
 * Created by marco on 31/05/2018.
 */

public class Question {
    private long id;
    private String description;
    private int year;
    private String alt_a;
    private String alt_b;
    private String alt_c;
    private String alt_d;
    private String alt_e;
    private char alt_correct;
    private Category category;

    public Question(String description, int year, String alt_a, String alt_b,
                    String alt_c, String alt_d, String alt_e, char alt_correct, Category category) {
        this.description = description;
        this.year = year;
        this.alt_a = alt_a;
        this.alt_b = alt_b;
        this.alt_c = alt_c;
        this.alt_d = alt_d;
        this.alt_e = alt_e;
        this.alt_correct = alt_correct;
        this.category = category;
    }

    public Question(long id, String description, int year, String alt_a, String alt_b,
                    String alt_c, String alt_d, String alt_e, char alt_correct, Category category) {
        this.id = id;
        this.description = description;
        this.year = year;
        this.alt_a = alt_a;
        this.alt_b = alt_b;
        this.alt_c = alt_c;
        this.alt_d = alt_d;
        this.alt_e = alt_e;
        this.alt_correct = alt_correct;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getAlt_a() {
        return alt_a;
    }

    public void setAlt_a(String alt_a) {
        this.alt_a = alt_a;
    }

    public String getAlt_b() {
        return alt_b;
    }

    public void setAlt_b(String alt_b) {
        this.alt_b = alt_b;
    }

    public String getAlt_c() {
        return alt_c;
    }

    public void setAlt_c(String alt_c) {
        this.alt_c = alt_c;
    }

    public String getAlt_d() {
        return alt_d;
    }

    public void setAlt_d(String alt_d) {
        this.alt_d = alt_d;
    }

    public String getAlt_e() {
        return alt_e;
    }

    public void setAlt_e(String alt_e) {
        this.alt_e = alt_e;
    }

    public char getAlt_correct() {
        return alt_correct;
    }

    public void setAlt_correct(char alt_correct) {
        this.alt_correct = alt_correct;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }




}
