package br.com.pdm.enade_engcomp_app.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import br.com.pdm.enade_engcomp_app.R;
import java.util.ArrayList;

import br.com.pdm.enade_engcomp_app.activities.fragments.TestFragment;
import br.com.pdm.enade_engcomp_app.activities.fragments.TrainningFragment;
import br.com.pdm.enade_engcomp_app.model.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private ImageView userImage;
    private TextView userName;
    private TextView userPoints;

    private User user;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        mAuth.addAuthStateListener(mAuthListener);
        super.onStart();
    }

    @Override
    protected void onResume() {
        populateDrawbar();
        super.onResume();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Log.d("nav", "inside");
        Intent intent;

        switch(item.getItemId()){
            case(R.id.menu_ranking):
                intent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(intent);
                break;

            case(R.id.menu_about):
                intent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(intent);
                break;

            case(R.id.menu_logout):
                Log.d("nav logout", "true");
                mAuth.signOut();
                break;
        }
        return true;
    }

    private void populateDrawbar(){
        CollectionReference usersRef = db.collection("users");
        usersRef.document(mAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                user = documentSnapshot.toObject(User.class).withId(documentSnapshot.getId());

                userImage = findViewById(R.id.userImage);
                userName = findViewById(R.id.userName);
                userPoints = findViewById(R.id.userPoints);

                Glide.with(MainActivity.this).load(user.getPhoto()).into(userImage);
                userName.setText(user.getName());
                userPoints.setText(user.getPoints()+"");
            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter {
        private final ArrayList<Fragment> fragments;
        private final ArrayList<String> titles;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<Fragment>(2);
            fragments.add(new TestFragment());
            fragments.add(new TrainningFragment());

            titles = new ArrayList<String>();
            titles.add(getString(R.string.test_tab));
            titles.add(getString(R.string.trainning_tab));
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(toggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
