package br.com.pdm.enade_engcomp_app.activities;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
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
import br.com.pdm.enade_engcomp_app.model.Category;
import br.com.pdm.enade_engcomp_app.model.Question;

public class SimulatedActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db;

    private Toolbar toolbar;

    private RadioGroup radioGroup;

    private int countQuestion;

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

        this.countQuestion = 0;

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

    private void populaView(int countQuestion, List<Question> questions){

        String description_1 = questions.get(countQuestion).getDescription_1();
        String image = questions.get(countQuestion).getImage();
        String description_2 = questions.get(countQuestion).getDescription_2();
        String reference = questions.get(countQuestion).getReference();
        String year = questions.get(countQuestion).getYear();
        String alt_a = questions.get(countQuestion).getAlt_a();
        String alt_b = questions.get(countQuestion).getAlt_b();
        String alt_c = questions.get(countQuestion).getAlt_c();
        String alt_d = questions.get(countQuestion).getAlt_d();
        String alt_e = questions.get(countQuestion).getAlt_e();
        String alt_correct = questions.get(countQuestion).getAlt_correct();

        TextView question_number = (TextView) findViewById(R.id.question_number);
        question_number.setText("Questão " + countQuestion);

        if(description_1 != "" || description_1 != null){
            ((TextView) findViewById(R.id.question_description_1)).setText(description_1);
        }

        if(description_2 != "" || description_2 != null){
            ((TextView) findViewById(R.id.question_description_2)).setText(description_2);
        }

        if(image != "" || image != null){
            ((ImageView) findViewById(R.id.question_image)).setImageURI(Uri.parse(image));
        }

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_alternatives);
        for(int i = 0; i < radioGroup.getChildCount(); i++){
            switch (i) {
                case 0:
                    ((RadioButton) radioGroup.getChildAt(i)).setText(alt_a);
                    break;
                case 1:
                    ((RadioButton) radioGroup.getChildAt(i)).setText(alt_b);
                    break;
                case 2:
                    ((RadioButton) radioGroup.getChildAt(i)).setText(alt_c);
                    break;
                case 3:
                    ((RadioButton) radioGroup.getChildAt(i)).setText(alt_d);
                    break;
                case 4:
                    ((RadioButton) radioGroup.getChildAt(i)).setText(alt_e);
                    break;
            }
        }

        if(countQuestion != questions.size()){
            ((Button) findViewById(R.id.simulated_btn)).setText("Próximo");
            countQuestion++;
        } else {
            ((Button) findViewById(R.id.simulated_btn)).setText("Finalizar");
            //TODO lógica para finalizar o simulado
        }

    }

}
