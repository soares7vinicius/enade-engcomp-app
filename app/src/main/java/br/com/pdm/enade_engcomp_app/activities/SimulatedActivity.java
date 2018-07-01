package br.com.pdm.enade_engcomp_app.activities;

import android.content.Intent;
import android.net.Uri;
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
import br.com.pdm.enade_engcomp_app.model.Question;
import br.com.pdm.enade_engcomp_app.model.Test;

public class SimulatedActivity extends AppCompatActivity {

    //VARIABLES FOR AUTHENTICATION
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db;

    private Toolbar toolbar;
    private RadioGroup radioGroup;

    private List<Question> questions = new ArrayList<Question>();
    private List<Boolean> correct_questions = new ArrayList<Boolean>();
    private int countQuestion = 0;
    private int qtt_corrects = 0;
    private Boolean isTest;

    private Boolean next;
    private Boolean finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulated);

        //ADD TOOLBAR
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

        isTest = getIntent().getBooleanExtra("IS_TEST", false);

        if(isTest){
            String testID = getIntent().getStringExtra("TEST_ID");
            startTest(testID);
        }else{
            //String categoryID = getIntent().getStringExtra("CATEGORY_ID");
            String categoryID = "BPv0Z1XUzt73JtPVwIj8";
            startTraining(categoryID);
        }
    }

    private void startTraining(String catId){
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
                        popularSimulatedView(countQuestion, questions);
                    }
                });
    }

    private void startTest(String testId){
        DocumentReference testRef = db.collection("tests").document(testId);
        testRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                Test test = documentSnapshot.toObject(Test.class).withId(documentSnapshot.getId());


                //objeto test disponivel aqui
                Log.d("test id", test.getId()+"");
                Log.d("test questions size", test.getQuestions().size()+"");
            }
        });
    }

    private void popularSimulatedView(int countQuestion, List<Question> questions){

        String description_1 = questions.get(countQuestion).getDescription_1();
        String image = questions.get(countQuestion).getImage();
        String description_2 = questions.get(countQuestion).getDescription_2();
        String alt_a = questions.get(countQuestion).getAlt_a();
        String alt_b = questions.get(countQuestion).getAlt_b();
        String alt_c = questions.get(countQuestion).getAlt_c();
        String alt_d = questions.get(countQuestion).getAlt_d();
        String alt_e = questions.get(countQuestion).getAlt_e();
        String reference = questions.get(countQuestion).getReference();
        String year = questions.get(countQuestion).getYear();

        TextView question_number = (TextView) findViewById(R.id.question_number);
        question_number.setText("Questão " + (countQuestion+1));

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

        if( (countQuestion+1) != questions.size()){
            ((Button) findViewById(R.id.simulated_btn)).setText("Próxima");
            this.countQuestion++;
            next = true;
            finish = false;
        } else if( (countQuestion+1) == questions.size()){
            ((Button) findViewById(R.id.simulated_btn)).setText("Finalizar");
            next = false;
            finish = true;
        }

    }

    public void onClickSimulatedBtn(View view){

        radioGroup = (RadioGroup) findViewById(R.id.radio_group_alternatives);
        int selected_alt = radioGroup.getCheckedRadioButtonId();

        if(selected_alt > 0) {

            String alt_correct = questions.get(countQuestion).getAlt_correct();

            switch (alt_correct) {
                case "A":
                    if (selected_alt == R.id.alt_a) {
                        correct_questions.add(true);
                        qtt_corrects++;
                    }
                    else { correct_questions.add(false); }
                    break;
                case "B":
                    if (selected_alt == R.id.alt_b) {
                        correct_questions.add(true);
                        qtt_corrects++;
                    }
                    else { correct_questions.add(false); }
                    break;
                case "C":
                    if (selected_alt == R.id.alt_c) {
                        correct_questions.add(true);
                        qtt_corrects++;
                    }
                    else { correct_questions.add(false); }
                    break;
                case "D":
                    if (selected_alt == R.id.alt_d) {
                        correct_questions.add(true);
                        qtt_corrects++;
                    } else { correct_questions.add(false); }
                    break;
                case "E":
                    if (selected_alt == R.id.alt_e) {
                        correct_questions.add(true);
                        qtt_corrects++;
                    } else { correct_questions.add(false); }
                    break;
                }
            radioGroup.clearCheck();

            if(next){
                popularSimulatedView(countQuestion, questions);
            } else if(finish){
                Intent intent = new Intent(this, CorrectedSimulationActivity.class);
                //intent.putExtra("QUESTIONS", (ArrayList<Question>) questions);
                //intent.putExtra("CORRECT_QUESTIONS", (ArrayList<Boolean>) correct_questions);
                intent.putExtra("QTT_CORRECTS", qtt_corrects);
                startActivity(intent);
            }

        } else {
            Toast.makeText(this, "Selecione uma alternativa!", Toast.LENGTH_SHORT).show();
        }
    }

}
