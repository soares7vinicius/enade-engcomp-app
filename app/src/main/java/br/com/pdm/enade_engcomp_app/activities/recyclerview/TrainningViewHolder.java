package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.model.Category;

/**
 * Created by marco on 01/07/2018.
 */

public class TrainningViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private CardView trainningCard;
    private Category category;

    public TrainningViewHolder(View itemView) {
        super(itemView);
        this.trainningCard = (CardView) itemView.findViewById(R.id.trainningCard);
        trainningCard.setOnClickListener(this);
    }

    public void bind(Category category){
        this.category = category;
        TextView categoryName = (TextView) this.trainningCard.getChildAt(0).findViewById(R.id.categoryName);
        categoryName.setText(this.category.getName());
    }


    @Override
    public void onClick(View v) {
        //quando clicar na categoria
        Toast.makeText(v.getContext(), "clicou", Toast.LENGTH_LONG).show();
    }
}
