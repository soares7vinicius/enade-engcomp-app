package br.com.pdm.enade_engcomp_app.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.model.Question;

public class CorrectionQuestionActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db;

    private Toolbar toolbar;

    private String questionID;
    private Question question;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_correction_question);

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
                    Intent intent = new Intent(CorrectionQuestionActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        //questionID = getIntent().getStringExtra("QUESTION_ID");
        questionID = "qFgwF6yeIsZZiGmvTpbm";

        progressDialog.show();

        DocumentReference questionRef = db.collection("questions").document(questionID);
        questionRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(final DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                question = documentSnapshot.toObject(Question.class).withId(documentSnapshot.getId());

                String description_1 = question.getDescription_1();
                String image = question.getImage();
                String description_2 = question.getDescription_2();
                String alt_correct = question.getAlt_correct();
                String alt_a = question.getAlt_a();
                String alt_b = question.getAlt_b();
                String alt_c = question.getAlt_c();
                String alt_d = question.getAlt_d();
                String alt_e = question.getAlt_e();

                if(description_1 != "" && description_1 != null){
                    description_1 = description_1.replaceAll("\\\\n", "\n");
                    ((TextView) findViewById(R.id.question_description_1_correction)).setText(description_1);
                }

                TextView question_description_2 = (TextView) findViewById(R.id.question_description_2_correction);
                if(description_2 != "" && description_2 != null){
                    description_2 = description_2.replaceAll("\\\\n", "\n");
                    question_description_2.setText(description_2);
                } else {
                    question_description_2.setText("");
                }

                ImageView imageView = (ImageView) findViewById(R.id.question_image_correction);
                if(image != "" && image != null){
                    progressDialog.show();
                    // Create glide request manager
                    RequestManager requestManager = Glide.with(CorrectionQuestionActivity.this);
                    // Create request builder and load image.
                    RequestBuilder requestBuilder = requestManager.load(image);
                    // Show image into target imageview.
                    requestBuilder.into(imageView);
                    progressDialog.dismiss();
                } else {
                    imageView.setImageResource(0);
                }

                switch (alt_correct) {
                    case "A":
                        ((TextView) findViewById(R.id.resolution)).setText(alt_a);
                        break;
                    case "B":
                        ((TextView) findViewById(R.id.resolution)).setText(alt_b);
                        break;
                    case "C":
                        ((TextView) findViewById(R.id.resolution)).setText(alt_c);
                        break;
                    case "D":
                        ((TextView) findViewById(R.id.resolution)).setText(alt_d);
                        break;
                    case "E":
                        ((TextView) findViewById(R.id.resolution)).setText(alt_e);
                        break;
                }

                progressDialog.dismiss();
            }
        });

    }
}
