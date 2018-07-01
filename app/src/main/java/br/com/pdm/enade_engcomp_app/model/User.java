package br.com.pdm.enade_engcomp_app.model;

/**
 * Created by marco on 31/05/2018.
 */

public class User extends Model{
    private String name;
    private String photo;
    private long points;

    public User() {
    }

    public User(String name, String photo, long points) {
        this.name = name;
        this.photo = photo;
        this.points = points;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() { return photo; }

    public void setPhoto(String photo) { this.photo = photo; }
}

