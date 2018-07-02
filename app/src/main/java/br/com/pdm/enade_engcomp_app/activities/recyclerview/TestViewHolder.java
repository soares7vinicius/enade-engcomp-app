package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.model.Test;

/**
 * Created by marco on 01/07/2018.
 */

public class TestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private CardView testCard;
    private Test test;

    public TestViewHolder(View itemView) {
        super(itemView);
        this.testCard = (CardView) itemView.findViewById(R.id.testCard);
        this.testCard.setOnClickListener(this);
    }

    public void bind(Test test, int position){
        this.test = test;
        TextView testName = (TextView) this.testCard.getChildAt(0).findViewById(R.id.testName);
        TextView testResult = (TextView) this.testCard.getChildAt(0).findViewById(R.id.testResult);
        testName.setText("Simulado " + position);
        double pontPercent = (this.test.getCorrect_qtt()/this.test.getQuestions_qtt())/100.0;
        testResult.setText(pontPercent + "% - " + this.test.getPoints());
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "CHAMAR TELA DE CORREÇÃO", Toast.LENGTH_LONG).show();
    }
}
