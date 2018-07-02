package br.com.pdm.enade_engcomp_app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.model.Question;

public class CorrectedSimulationActivity extends AppCompatActivity {

    private List<Boolean> correct_questions;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrected_simulation);

        ArrayList<Boolean> correct_questions = (ArrayList<Boolean>) getIntent().getSerializableExtra("CORRECT_QUESTIONS");
        int qtt_corrects = getIntent().getIntExtra("QTT_CORRECTS", 0);
        int total_questions = getIntent().getIntExtra("TOTAL_QUESTIONS", 0);

        //ADD TOOLBAR
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}
