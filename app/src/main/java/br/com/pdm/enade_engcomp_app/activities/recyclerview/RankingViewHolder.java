package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import android.graphics.Color;
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

    public void bind(User user, String currentUserID, int position){
        this.user = user;
        TextView userName = (TextView) this.rankingCard.getChildAt(0).findViewById(R.id.userName);
        TextView userResult = (TextView) this.rankingCard.getChildAt(0).findViewById(R.id.userResult);
        TextView userPosition = (TextView) this.rankingCard.getChildAt(0).findViewById(R.id.userPosition);
        ImageView imgFirstPlace = (ImageView) this.rankingCard.findViewById(R.id.imgUser);

        String image = user.getPhoto();

        // Create glide request manager
        RequestManager requestManager = Glide.with(this.rankingCard);
        // Create request builder and load image.
        RequestBuilder requestBuilder = requestManager.load(image);
        // Show image into target imageview.
        requestBuilder.into(imgFirstPlace);

        userName.setText(this.user.getName());
        userResult.setText(this.user.getPoints() + " pontos");

        userPosition.setText(++position + "º");

        if(this.user.getId().equals(currentUserID)){
            int color = Color.parseColor("#43a047");
            this.rankingCard.setCardBackgroundColor(color);
        }
    }


    @Override
    public void onClick(View v) {
        //Toast.makeText(v.getContext(), "clicou", Toast.LENGTH_LONG).show();
    }
}
