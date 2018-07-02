package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.activities.SimulatedActivity;
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
        this.trainningCard.setOnClickListener(this);
    }

    public void bind(Category category){
        this.category = category;
        TextView categoryName = (TextView) this.trainningCard.getChildAt(0).findViewById(R.id.categoryName);
        categoryName.setText(this.category.getName());
        int t = R.id.iconTrainning;
        ImageView iconCategory = (ImageView)this.trainningCard.getChildAt(0).findViewById(t);
        iconCategory.setImageResource(R.mipmap.ic_launcher);
    }


    @Override
    public void onClick(View v) {
        //quando clicar na categoria
        //getViewById do item q clicou
        String catId = this.category.getId(); //nao esquecer
        Intent intent = new Intent(v.getContext(), SimulatedActivity.class);
        intent.putExtra("IS_TEST", false);
        intent.putExtra("CATEGORY_ID", catId);
        v.getContext().startActivity(intent);
    }
}
