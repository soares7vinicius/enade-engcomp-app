package br.com.pdm.enade_engcomp_app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.pdm.enade_engcomp_app.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView link = findViewById(R.id.aboutP3);
        link.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
