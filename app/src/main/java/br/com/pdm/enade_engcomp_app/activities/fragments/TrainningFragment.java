package br.com.pdm.enade_engcomp_app.activities.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.activities.LoginActivity;
import br.com.pdm.enade_engcomp_app.activities.SimulatedActivity;
import br.com.pdm.enade_engcomp_app.model.Category;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainningFragment extends Fragment {

    private FirebaseFirestore db;
    private CollectionReference categoriesReference;
    private List<Category> categories;

    public TrainningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = FirebaseFirestore.getInstance();
        categoriesReference = db.collection("categories");
        getCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trainning, container, false);
    }

    public void onClickNewTraining(View view){

        //getViewById do item q clicou
        String catId = "id da categoria"; //nao esquecer
        Intent intent = new Intent(getContext(), SimulatedActivity.class);
        intent.putExtra("IS_TEST", false);
        intent.putExtra("CATEGORY_ID", catId);
        startActivity(intent);
    }

    private void getCategories(){
        categoriesReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    List<Category> categories = new ArrayList<>();
                    for(DocumentSnapshot doc : task.getResult()){
                        Category cat = doc.toObject(Category.class).withId(doc.getId());
                        categories.add(cat);
                    }
                    inflateCategories(categories);
                }
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), R.string.error_categories_query, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void inflateCategories(List<Category> categories){
        Log.d("categories size", categories.size()+"");
        Log.d("category 0", categories.get(0).getId()+ " " + categories.get(0).getName());

        this.categories = categories;
        // preencher a activity com as categorias aqui
    }

}
