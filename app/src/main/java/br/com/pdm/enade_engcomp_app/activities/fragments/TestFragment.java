package br.com.pdm.enade_engcomp_app.activities.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.activities.SimulatedActivity;
import br.com.pdm.enade_engcomp_app.activities.recyclerview.TestAdapter;
import br.com.pdm.enade_engcomp_app.activities.recyclerview.TrainningAdapter;
import br.com.pdm.enade_engcomp_app.model.Question;
import br.com.pdm.enade_engcomp_app.model.Test;
import br.com.pdm.enade_engcomp_app.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser fUser;
    private RecyclerView recyclerView;
    private TestAdapter testAdapter;
    private List<Test> tests;
    private ProgressDialog progressDialog;

    public TestFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_test, container, false);
        //Implementação do RecyclerView
        this.recyclerView = fragmentView.findViewById(R.id.rv_tests_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(fragmentView.getContext());
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);

        return fragmentView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fUser = mAuth.getCurrentUser();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setTitle(getString(R.string.hold_on));
    }

    @Override
    public void onResume() {
        super.onResume();

        if(mAuth.getCurrentUser() != null){
            getUserTests();
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FloatingActionButton btnAddTest = (FloatingActionButton) getView().findViewById(R.id.btnAddTest);

        btnAddTest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(getContext(), SimulatedActivity.class);
                intent.putExtra("IS_TEST", true);
                startActivity(intent);
            }
        });
    }

    public void getUserTests(){
        CollectionReference testsRef = db.collection("tests");
        DocumentReference user = db.collection("users").document(fUser.getUid());

        testsRef.whereEqualTo("user", user)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        tests = new ArrayList<>();
                        for(DocumentSnapshot doc : documentSnapshots){
                            Test t = doc.toObject(Test.class).withId(doc.getId());
                            tests.add(t);
                        }
                        testAdapter = new TestAdapter(tests);
                        recyclerView.setAdapter(testAdapter);

                        //lista de tests pronta aqui
                        Log.d("tests size",tests.size()+"");
                    }

                });
    }
}
