package com.suatkkrer.debtmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private MainFragment mainFragment;
    private OutcomeFragment outcomeFragment;
    private IncomeFragment incomeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        String fragment = intent.getStringExtra("income");



        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        mainFragment = new MainFragment();
        outcomeFragment = new OutcomeFragment();
        incomeFragment = new IncomeFragment();

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),0);

        viewPagerAdapter.addFragment(mainFragment,"Home Page");
        viewPagerAdapter.addFragment(incomeFragment,"Income");
        viewPagerAdapter.addFragment(outcomeFragment,"Outcome");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.getTabAt(0).setIcon(R.drawable.home);
        tabLayout.getTabAt(1).setIcon(R.drawable.income);
        tabLayout.getTabAt(2).setIcon(R.drawable.outcome);

        if (fragment != null) {
            if (fragment.equals("income")) {
                viewPager.setCurrentItem(1);
            }
        }

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments = new ArrayList<>();
        private List<String> fragmentTitle = new ArrayList<>();


        public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        public void addFragment(Fragment fragment, String title){
            fragments.add(fragment);
            fragmentTitle.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitle.get(position);
        }
    }
}