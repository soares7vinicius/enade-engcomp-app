package br.com.pdm.enade_engcomp_app.model;

/**
 * Created by marco on 31/05/2018.
 */

public class Category extends Model{

    private String name;

    public Category(){}

    public Category(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
