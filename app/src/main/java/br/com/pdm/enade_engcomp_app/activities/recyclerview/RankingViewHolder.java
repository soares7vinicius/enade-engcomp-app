package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.model.User;

/**
 * Created by marco on 01/07/2018.
 */

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private CardView rankingCard;
    private User user;

    public RankingViewHolder(View itemView) {
        super(itemView);
        this.rankingCard = (CardView) itemView.findViewById(R.id.rankingCard);
        this.rankingCard.setOnClickListener(this);
    }

    public void bind(User user){
        this.user = user;
        TextView userName = (TextView) this.rankingCard.getChildAt(0).findViewById(R.id.userName);
        TextView userResult = (TextView) this.rankingCard.getChildAt(0).findViewById(R.id.userResult);
        userName.setText("Simulado " + this.user.getId());
        //DESCOMENTAR
        //userResult.setText(this.user.getPoints());
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "clicou", Toast.LENGTH_LONG).show();
    }
}
