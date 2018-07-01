package br.com.pdm.enade_engcomp_app.model;

/**
 * Created by marco on 31/05/2018.
 */

public class Question {
    private String id;
    private String description_1;
    private String description_2;
    private String image;
    private String reference;
    private int year;
    private String alt_a;
    private String alt_b;
    private String alt_c;
    private String alt_d;
    private String alt_e;
    private String alt_correct;
    private Category category;

    public Question(String description_1, String description_2, String image, String reference, int year, String alt_a, String alt_b, String alt_c, String alt_d, String alt_e, String alt_correct, Category category) {
        this.description_1 = description_1;
        this.description_2 = description_2;
        this.image = image;
        this.reference = reference;
        this.year = year;
        this.alt_a = alt_a;
        this.alt_b = alt_b;
        this.alt_c = alt_c;
        this.alt_d = alt_d;
        this.alt_e = alt_e;
        this.alt_correct = alt_correct;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription_1() {
        return description_1;
    }

    public void setDescription_1(String description_1) {
        this.description_1 = description_1;
    }

    public String getDescription_2() {
        return description_2;
    }

    public void setDescription_2(String description_2) {
        this.description_2 = description_2;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
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

    public String getAlt_correct() {
        return alt_correct;
    }

    public void setAlt_correct(String alt_correct) {
        this.alt_correct = alt_correct;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
