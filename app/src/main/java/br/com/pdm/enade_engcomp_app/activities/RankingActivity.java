package br.com.pdm.enade_engcomp_app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.activities.recyclerview.RankingAdapter;

public class RankingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RankingAdapter rankingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //Implementação do RecyclerView
        this.recyclerView = findViewById(R.id.rv_categories_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);

        /*IMPLEMENTAR FUNÇÃO QUE RECUCUPERA UMA LISTA DE TESTES E COMPLETAR O RECYCLER VIEW NELA
        * IGUAL O IMPLEMENTADO NO TRAININGFRAGMENT*/
    }
}
