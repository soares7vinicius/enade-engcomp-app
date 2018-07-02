package br.com.pdm.enade_engcomp_app.activities.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.model.Test;

/**
 * Created by marco on 01/07/2018.
 */

public class TestAdapter extends RecyclerView.Adapter<TestViewHolder>{
    private List<Test> tests;
    public TestAdapter(List<Test> tests){
        this.tests = tests;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context contextRV = parent.getContext();
        int layoutID = R.layout.view_holder_test;

        LayoutInflater inflater = LayoutInflater.from(contextRV);

        Boolean attachToParent = false;
        View view = inflater.inflate(layoutID, parent, attachToParent);

        TestViewHolder testVHolder = new TestViewHolder(view);
        return testVHolder;
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        holder.bind(this.tests.get(position), position+1);
    }

    @Override
    public int getItemCount() {
        return this.tests != null? this.tests.size():1;
    }
}
