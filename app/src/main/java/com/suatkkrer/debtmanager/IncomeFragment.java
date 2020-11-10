package com.suatkkrer.debtmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class IncomeFragment extends Fragment implements IncomeAdapter.OnNoteListener {

    View v;
    Context thisContext;
    FloatingActionButton floatingActionButton;
    CoordinatorLayout coordinatorLayout;
    List<IncomeClass> mIncome;
    RecyclerView recyclerView;
    IncomeAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisContext = container.getContext();
        v = inflater.inflate(R.layout.incomefragment,container,false);

        floatingActionButton = v.findViewById(R.id.incomeFloating);
        recyclerView = v.findViewById(R.id.incomeRecycler);


        if (mIncome.size() == 0) {
            try {
                SQLiteDatabase sqLiteDatabase = thisContext.openOrCreateDatabase("Income", MODE_PRIVATE, null);

                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS income(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM income", null);

                int name = cursor.getColumnIndex("name");
                int amount = cursor.getColumnIndex("amount");
                int idIx = cursor.getColumnIndex("id");
                int dateIx = cursor.getColumnIndex("date");
                int desc = cursor.getColumnIndex("description");

                while (cursor.moveToNext()) {
                    int convertedString = Integer.parseInt(cursor.getString(amount));
                    NumberFormat format = NumberFormat.getNumberInstance();
                    String s = String.valueOf(format.format(convertedString));
                    mIncome.add(new IncomeClass(cursor.getString(name), s, cursor.getString(desc), R.drawable.coloredprofit, cursor.getInt(idIx),cursor.getString(dateIx)));
                }

                cursor.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new IncomeAdapter(mIncome,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisContext,addIncome.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIncome = new ArrayList<>();

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getContext(),addIncome.class);
        intent.putExtra("nameIncome",mIncome.get(position).getName());
        intent.putExtra("descriptionIncome",mIncome.get(position).getDescription());
        intent.putExtra("amountIncome",mIncome.get(position).getAmount());
        intent.putExtra("idIncome",mIncome.get(position).getId());
        startActivity(intent);
    }
}
