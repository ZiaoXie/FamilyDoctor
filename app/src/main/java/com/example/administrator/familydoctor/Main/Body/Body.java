package com.example.administrator.familydoctor.Main.Body;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.administrator.familydoctor.R;

import java.util.ArrayList;

public class Body extends AppCompatActivity {

    ImageView imageView;
    TabLayout tab;
    ViewPager viewPager;
    ArrayList<String> title=new ArrayList<String>();
    ArrayList<Fragment> fragments=new ArrayList<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);


        imageView=(ImageView)findViewById(R.id.back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tab=(TabLayout)findViewById(R.id.tab);
        viewPager=(ViewPager)findViewById(R.id.viewpager);
        title.add("经络");title.add("穴位");
        fragments.add(new JingLuo());fragments.add(new XueWei());

        viewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        tab.setupWithViewPager(viewPager);
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return title.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position%fragments.size());
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position%title.size());
        }
    }
}
