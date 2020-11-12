package com.suatkkrer.debtmanager.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.suatkkrer.debtmanager.R;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {

    View v;
    Context thisContext;
    CoordinatorLayout coordinatorLayout;
    AnyChartView anyChartView;
    PieChart pieChart;
    int totalIncome = 0;
    int totalOutcome = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisContext = container.getContext();
        v = inflater.inflate(R.layout.mainfragment,container,false);

        pieChart = v.findViewById(R.id.pieChart);

        totalIncome = 0;
        totalOutcome = 0;

        if (totalIncome == 0 && totalOutcome == 0) {

            try {
                SQLiteDatabase sqLiteDatabase = thisContext.openOrCreateDatabase("Income", MODE_PRIVATE, null);

                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS income(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


                Cursor cursor = sqLiteDatabase.rawQuery("SELECT amount FROM income", null);
                int incomeAmount = cursor.getColumnIndex("amount");


                while (cursor.moveToNext()) {
                    String income = cursor.getString(incomeAmount);
                    income = income.replaceAll("\\.","");
                    totalIncome += Integer.parseInt(income);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }


            try {
                SQLiteDatabase sqLiteDatabase = thisContext.openOrCreateDatabase("Outcome", MODE_PRIVATE, null);

                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS outcome(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


                Cursor cursor = sqLiteDatabase.rawQuery("SELECT amount FROM outcome", null);
                int OutcomeAmount = cursor.getColumnIndex("amount");


                while (cursor.moveToNext()) {
                    String outcome = cursor.getString(OutcomeAmount);
                    outcome = outcome.replaceAll("\\.","");
                    totalOutcome += Integer.parseInt(outcome);

                }

                System.out.println(totalOutcome);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


            ArrayList<PieEntry> pieChartArray = new ArrayList<>();
            pieChartArray.add(new PieEntry(totalIncome, getString(R.string.totalCredit)));
            pieChartArray.add(new PieEntry(totalOutcome, getString(R.string.totalDebt)));

            PieDataSet pieDataSet = new PieDataSet(pieChartArray, "");
            pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            pieDataSet.setValueTextColor(Color.BLACK);
            pieDataSet.setValueTextSize(16f);

            PieData pieData = new PieData(pieDataSet);
            if (totalOutcome == 0 && totalIncome == 0) {
                pieChart.setData(pieData);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText(getString(R.string.entervalie));
                pieChart.setCenterTextSize(18f);
                pieChart.animate();
            } else {
                pieChart.setData(pieData);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText(getString(R.string.debtCredit));
                pieChart.setCenterTextSize(18f);
                pieChart.animate();
            }


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        totalIncome = 0;
        totalOutcome = 0;
    }
}
