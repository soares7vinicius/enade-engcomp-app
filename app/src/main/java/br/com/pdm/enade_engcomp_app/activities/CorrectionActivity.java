package br.com.pdm.enade_engcomp_app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioGroup;

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
import br.com.pdm.enade_engcomp_app.activities.recyclerview.CorrectionAdapter;
import br.com.pdm.enade_engcomp_app.model.Question;
import br.com.pdm.enade_engcomp_app.model.Test;
import br.com.pdm.enade_engcomp_app.model.User;

public class CorrectionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CorrectionAdapter correctionAdapter;

    //VARIABLES FOR AUTHENTICATION
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db;

    private Toolbar toolbar;
    private RadioGroup radioGroup;

    private List<Question> questions;
    private List<Boolean> correct_questions = new ArrayList<Boolean>();
    private int countQuestion = 0;
    private int qtt_corrects = 0;

    private String categoryID;

    private Boolean isTest;
    private Test test;
    private String testID;

    private User user;
    private String userID;

    private Boolean next;
    private Boolean finish;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction);

        //Implementação do RecyclerView
        this.recyclerView = findViewById(R.id.rv_correction_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);

        correct_questions = (ArrayList<Boolean>) getIntent().getSerializableExtra("CORRECT_QUESTIONS");
        qtt_corrects = getIntent().getIntExtra("QTT_CORRECTS", 0);
        countQuestion = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

        //ADD TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.setTitle(getString(R.string.hold_on));

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Intent intent = new Intent(CorrectionActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        isTest = getIntent().getBooleanExtra("IS_TEST", false);

        if (isTest) {
            testID = getIntent().getStringExtra("TEST_ID");
            getTestByUser(testID);
        } else {
            categoryID = getIntent().getStringExtra("CATEGORY_ID");
            getTraining(categoryID);
        }
    }


    private void getTraining(String catId){
        DocumentReference category = db.collection("categories").document(catId);
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
                        setParamsRecyclerView(questions);
                    }
                });
    }

    private void getTestByUser(String testId){
        progressDialog.show();

        DocumentReference testRef = db.collection("tests").document(testId);
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

                        CollectionReference usersRef = db.collection("users");
                        usersRef.document(test.getUser().getId())
                                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                    @Override
                                    public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                                        user = documentSnapshot.toObject(User.class).withId(documentSnapshot.getId());

                                        progressDialog.dismiss();
                                        setParamsRecyclerView(questions);
                                    }
                                });
                    }
                });

            }
        });
    }

    private void setParamsRecyclerView(List<Question> questions){
        correctionAdapter = new CorrectionAdapter(questions, this.correct_questions, this.isTest);
        recyclerView.setAdapter(correctionAdapter);
    }


}
