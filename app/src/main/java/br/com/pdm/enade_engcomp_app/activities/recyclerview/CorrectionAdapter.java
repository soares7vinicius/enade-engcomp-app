package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.model.Question;

/**
 * Created by marco on 02/07/2018.
 */

public class CorrectionAdapter extends RecyclerView.Adapter<CorrectionViewHolder>{
    private List<Question> questions;
    private List<Boolean> responses;
    private boolean isTest;

    public CorrectionAdapter(List<Question> questions, List<Boolean> responses, boolean isTest){
        this.questions = questions;
        this.responses = responses;
        this.isTest = isTest;
    }

    @Override
    public CorrectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context contextRV = parent.getContext();
        int layoutID = R.layout.view_holder_correction;

        LayoutInflater inflater = LayoutInflater.from(contextRV);

        Boolean attachToParent = false;
        View view = inflater.inflate(layoutID, parent, attachToParent);

        CorrectionViewHolder correctionVHolder = new CorrectionViewHolder(view);
        return correctionVHolder;
    }

    @Override
    public void onBindViewHolder(CorrectionViewHolder holder, int position) {
        holder.bind(this.questions.get(position), this.responses.get(position), this.isTest, position+1);
    }

    @Override
    public int getItemCount() {
        return this.questions !=null ? this.questions.size():0;
    }
}
