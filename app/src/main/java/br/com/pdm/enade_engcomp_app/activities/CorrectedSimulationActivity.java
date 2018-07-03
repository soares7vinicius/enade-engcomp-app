package br.com.pdm.enade_engcomp_app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
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
import br.com.pdm.enade_engcomp_app.model.Test;
import br.com.pdm.enade_engcomp_app.model.User;

public class CorrectedSimulationActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db;

    private Toolbar toolbar;

    private ProgressDialog progressDialog;

    private int qtt_corrects;
    private int total_questions;

    private List<Question> questions;
    private List<Boolean> correct_questions = new ArrayList<Boolean>();

    private Boolean isTest;
    private String categoryID;
    private Test test;
    private String testID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrected_simulation);

        //ADD TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setTitle(getString(R.string.hold_on));

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent intent = new Intent(CorrectedSimulationActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        isTest = getIntent().getBooleanExtra("IS_TEST", false);

        correct_questions = (ArrayList<Boolean>) getIntent().getSerializableExtra("CORRECT_QUESTIONS");
        qtt_corrects = getIntent().getIntExtra("QTT_CORRECTS", 0);
        total_questions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);
        //CORRECT_QUESTIONS

        if(isTest){
            progressDialog.show();

            testID = getIntent().getStringExtra("TEST_ID");

            DocumentReference testRef = db.collection("tests").document(testID);
            testRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(final DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                    test = documentSnapshot.toObject(Test.class).withId(documentSnapshot.getId());
                    CollectionReference questionsRef = db.collection("questions");
                    questionsRef.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot documentSnapshots) {
                            questions = new ArrayList<>();
                            for(DocumentSnapshot doc : documentSnapshots){
                                Question q = doc.toObject(Question.class).withId(doc.getId());
                                if(test.getQuestions().contains(q)){
                                    questions.add(q);
                                }
                            }
                            progressDialog.dismiss();
                        }
                    });
                }
            });

        } else {
            categoryID = getIntent().getStringExtra("CATEGORY_ID");
            DocumentReference category = db.collection("categories").document(categoryID);
            CollectionReference questionsRef = db.collection("questions");
            questionsRef.whereEqualTo("category", category).limit(10)
                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                            questions = new ArrayList<>();
                            for(DocumentSnapshot doc : documentSnapshots){
                                Question q = doc.toObject(Question.class).withId(doc.getId());
                                questions.add(q);
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}
