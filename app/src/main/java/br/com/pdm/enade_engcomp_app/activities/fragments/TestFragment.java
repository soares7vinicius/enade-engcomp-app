package br.com.pdm.enade_engcomp_app.activities.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.activities.SimulatedActivity;
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

    public TestFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        fUser = mAuth.getCurrentUser();
    }

    public void onClickNewTest(View view){
        final User user = new User(fUser.getDisplayName(), fUser.getPhotoUrl().toString())
                .withId(fUser.getUid());
        final Test test = new Test(user);

        //getting random questions list sized 10
        db.collection("questions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<Question> questions = new ArrayList<>();
                    for(DocumentSnapshot doc : task.getResult()){
                        Question q = doc.toObject(Question.class).withId(doc.getId());
                        questions.add(q);
                    }

                    Collections.shuffle(questions);
                    questions = questions.subList(0, 10);
                    test.setQuestions(questions);

                    createTest(test);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), R.string.error_questions_query, Toast.LENGTH_SHORT).show();
            }
        });


        Intent intent = new Intent(getContext(), SimulatedActivity.class);
        intent.putExtra("IS_TEST", true);
        intent.putExtra("TEST_ID", "");
        startActivity(intent);
    }

    private void createTest(Test test){
        //TODO: create test on DB and call test activity
    }

}
