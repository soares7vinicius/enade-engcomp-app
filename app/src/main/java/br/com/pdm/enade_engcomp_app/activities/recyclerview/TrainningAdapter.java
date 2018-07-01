package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import br.com.pdm.enade_engcomp_app.R;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.pdm.enade_engcomp_app.model.Category;

/**
 * Created by marco on 01/07/2018.
 */

public class TrainningAdapter extends RecyclerView.Adapter<TrainningViewHolder>{
    private List<Category> categories;
    private int count;

    public TrainningAdapter(List<Category> categories){
        this.categories = categories;
        this.count = this.categories.size();
    }

    @Override
    public TrainningViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context contextRV = parent.getContext();
        int layoutID = R.layout.view_holder_trainning;

        LayoutInflater inflater = LayoutInflater.from(contextRV);

        Boolean attachToParent = false;
        View view = inflater.inflate(layoutID, parent, attachToParent);

        TrainningViewHolder trainningVHolder = new TrainningViewHolder(view);
        this.count++;
        return trainningVHolder;
    }

    @Override
    public void onBindViewHolder(TrainningViewHolder holder, int position) {
        holder.bind(this.categories.get(position));
    }

    @Override
    public int getItemCount() {
        return this.count;
    }
}
