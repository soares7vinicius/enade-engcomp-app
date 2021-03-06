package br.com.pdm.enade_engcomp_app.activities.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.activities.LoginActivity;
import br.com.pdm.enade_engcomp_app.activities.recyclerview.TrainningAdapter;
import br.com.pdm.enade_engcomp_app.activities.SimulatedActivity;
import br.com.pdm.enade_engcomp_app.model.Category;
import br.com.pdm.enade_engcomp_app.model.Question;
import br.com.pdm.enade_engcomp_app.model.Test;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrainningFragment extends Fragment {

    private FirebaseFirestore db;
    private CollectionReference categoriesReference;
    private RecyclerView recyclerView;
    private TrainningAdapter trainningAdapter;
    private List<Category> categories;

    public TrainningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_trainning, container, false);
        //Implementação do RecyclerView
        this.recyclerView = fragmentView.findViewById(R.id.rv_categories_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentView.getContext());
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();
        categoriesReference = db.collection("categories");
        getCategories();


        return fragmentView;
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
        this.trainningAdapter = new TrainningAdapter(categories);
        this.recyclerView.setAdapter(this.trainningAdapter);
    }

}
