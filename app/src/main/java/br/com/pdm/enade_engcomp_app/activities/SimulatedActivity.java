package br.com.pdm.enade_engcomp_app.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.model.Question;

public class SimulatedActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db;

    private Toolbar toolbar;

    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulated);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent intent = new Intent(SimulatedActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        /*radioGroup = (RadioGroup) findViewById(R.id.radio_group_simulated);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.option1: {
                        Toast.makeText(SimulatedActivity.this, "Opção 1",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        */

        if(getIntent().getBooleanExtra("IS_TEST", false)){
            String id = getIntent().getStringExtra("TEST_ID");
            startTest(id);
        }else{
            String id = getIntent().getStringExtra("CATEGORY_ID");
            startTraining(id);
        }
    }

    private void startTraining(String catId){
        CollectionReference questionsRef = db.collection("questions");
        questionsRef.whereEqualTo("category", "categories/"+catId).limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                List<Question> questions = new ArrayList<>();
                for(DocumentSnapshot doc : documentSnapshots){
                    Question q = doc.toObject(Question.class).withId(doc.getId());
                    questions.add(q);
                }

                //lista de questoes pronta aqui
            }
        });
    }

    private void startTest(String testId){
        DocumentReference testRef = db.collection("tests").document(testId);
        testRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                //TODO: retrieve test
            }
        });
    }
}
