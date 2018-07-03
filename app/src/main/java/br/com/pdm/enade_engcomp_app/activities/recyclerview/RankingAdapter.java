package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.model.User;

/**
 * Created by marco on 01/07/2018.
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingViewHolder>{
    private List<User> users;
    private String currentUserID;

    public RankingAdapter(List<User> users, String currentUserID){
        this.users = users;
        this.currentUserID = currentUserID;
    }



    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context contextRV = parent.getContext();
        int layoutID = R.layout.view_holder_ranking;

        LayoutInflater inflater = LayoutInflater.from(contextRV);

        Boolean attachToParent = false;
        View view = inflater.inflate(layoutID, parent, attachToParent);

        RankingViewHolder rankingVHolder = new RankingViewHolder(view);
        return rankingVHolder;
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        holder.bind(this.users.get(position), this.currentUserID, position+1);
    }

    @Override
    public int getItemCount() {
        return this.users != null? this.users.size():1;
    }
}
