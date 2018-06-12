package br.com.pdm.enade_engcomp_app.model;

/**
 * Created by marco on 31/05/2018.
 */

public class Category {
    private long id;
    private String name;

    public Category(long id){
        this.id = id;
    }

    public Category(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
