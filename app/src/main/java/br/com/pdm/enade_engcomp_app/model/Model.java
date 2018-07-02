package br.com.pdm.enade_engcomp_app.model;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

/**
 * Created by vinicius on 01/07/18.
 */

@IgnoreExtraProperties
public class Model {

    private String id;

    public <T extends Model>T withId(@NonNull final String id){
        this.id = id;
        return (T)this;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;

        if(obj.getClass() == DocumentReference.class){
            DocumentReference dr = (DocumentReference)obj;
            return this.getId().equals(dr.getId());
        }else{
            Model m = (Model)obj;
            return this.getId().equals(m.getId());
        }
    }
}
