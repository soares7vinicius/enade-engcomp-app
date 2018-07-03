package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.activities.CorrectionActivity;
import br.com.pdm.enade_engcomp_app.activities.CorrectionQuestionActivity;
import br.com.pdm.enade_engcomp_app.model.Question;

public class CorrectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private CardView correctionCard;
    private Question question;
    private boolean isTest;


    public CorrectionViewHolder(View itemView) {
        super(itemView);
        this.correctionCard = (CardView) itemView.findViewById(R.id.correctionCard);
        this.correctionCard.setOnClickListener(this);
    }

    public void bind(Question question, Boolean correct, boolean isTest, int position){
        this.question = question;
        this.isTest = isTest;
        TextView questionCorrection = (TextView) this.correctionCard.getChildAt(0).findViewById(R.id.questionCorrection);
        ImageView imgCorrect = (ImageView) this.correctionCard.getChildAt(0).findViewById(R.id.imgCorrect);
        questionCorrection.setText("Questão " + position);
        if(correct){
            imgCorrect.setImageResource(R.drawable.ic_check_circle_green_36dp);
        }else{
            imgCorrect.setImageResource(R.drawable.ic_highlight_off_green_36dp);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), CorrectionQuestionActivity.class);
        intent.putExtra("QUESTION_ID", this.question.getId());
        v.getContext().startActivity(intent);
    }
}
