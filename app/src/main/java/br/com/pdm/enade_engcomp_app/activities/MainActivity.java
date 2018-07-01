package br.com.pdm.enade_engcomp_app.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.pdm.enade_engcomp_app.R;
import java.util.ArrayList;

import br.com.pdm.enade_engcomp_app.activities.fragments.TestFragment;
import br.com.pdm.enade_engcomp_app.activities.fragments.TrainningFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
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


}
