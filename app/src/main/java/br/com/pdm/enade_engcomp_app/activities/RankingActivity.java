package br.com.pdm.enade_engcomp_app.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import br.com.pdm.enade_engcomp_app.R;
import br.com.pdm.enade_engcomp_app.activities.recyclerview.RankingAdapter;
import br.com.pdm.enade_engcomp_app.model.User;

public class RankingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RankingAdapter rankingAdapter;

    private FirebaseFirestore db;

    private List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        //Implementação do RecyclerView
        this.recyclerView = findViewById(R.id.rv_ranking_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setHasFixedSize(true);

        db = FirebaseFirestore.getInstance();
        getUsersOrderByPoints();
    }

    private void getUsersOrderByPoints(){

        CollectionReference usersRef = db.collection("users");
        usersRef.orderBy("points", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        users = new ArrayList<>();
                        for(DocumentSnapshot doc : documentSnapshots){
                            User u = doc.toObject(User.class).withId(doc.getId());
                            users.add(u);
                        }
                        setFirstPlace(users.get(0));
                        users.remove(0);
                        rankingAdapter = new RankingAdapter(users);
                        recyclerView.setAdapter(rankingAdapter);
                    }
                });
    }

    private void setFirstPlace(User user){
        TextView nameFirstPlace = (TextView) findViewById(R.id.nameFirstPlace);
        TextView pointsFirstPlace = (TextView) findViewById(R.id.pointsFirstPlace);
        ImageView imgFirstPlace = (ImageView) findViewById(R.id.imgFirstPlace);

        String image = user.getPhoto();
        nameFirstPlace.setText(user.getName());
        pointsFirstPlace.setText(user.getPoints() + " pontos");

        // Create glide request manager
        RequestManager requestManager = Glide.with(this);
        // Create request builder and load image.
        RequestBuilder requestBuilder = requestManager.load(image);
        // Show image into target imageview.
        requestBuilder.into(imgFirstPlace);
    }
}
