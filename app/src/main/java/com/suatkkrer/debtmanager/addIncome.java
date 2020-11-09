package com.suatkkrer.debtmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class addIncome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void cancelIncome(View view) {
//       IncomeFragment incomeFragment = new IncomeFragment();
//        FragmentManager manager = getSupportFragmentManager();
//        manager.beginTransaction()
//                .replace(R.id.container,incomeFragment)
//                .commit();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.putExtra("income","income");
        startActivity(intent);
    }

    public void deleteIncome(View view) {
    }

    public void saveIncome(View view) {
    }
}