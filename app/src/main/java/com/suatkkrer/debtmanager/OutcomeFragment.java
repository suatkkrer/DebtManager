package com.suatkkrer.debtmanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class OutcomeFragment extends Fragment implements OutcomeAdapter.OnNoteListener{

    View v;
    Context thisContext;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton floatingActionButton;
    List<OutcomeClass> mOutcome;
    RecyclerView recyclerView;
    OutcomeAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisContext = container.getContext();
        v = inflater.inflate(R.layout.outcomefragment,container,false);

        floatingActionButton = v.findViewById(R.id.fb_button);
        recyclerView = v.findViewById(R.id.debtRecycler);

        if (mOutcome.size() == 0) {
            try {
                SQLiteDatabase sqLiteDatabase = thisContext.openOrCreateDatabase("Outcome", MODE_PRIVATE, null);

                sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS outcome(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM outcome", null);

                int name = cursor.getColumnIndex("name");
                int amount = cursor.getColumnIndex("amount");
                int idIx = cursor.getColumnIndex("id");
                int dateIx = cursor.getColumnIndex("date");
                int desc = cursor.getColumnIndex("description");

                while (cursor.moveToNext()) {
                    mOutcome.add(new OutcomeClass(cursor.getString(name), cursor.getString(amount), cursor.getString(desc), R.drawable.outcome, cursor.getInt(idIx),cursor.getString(dateIx)));
                }

                cursor.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new OutcomeAdapter(mOutcome,this);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),addOutCome.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOutcome = new ArrayList<>();

    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(getContext(),addOutCome.class);
        intent.putExtra("nameOutcome",mOutcome.get(position).getName());
        intent.putExtra("descriptionOutcome",mOutcome.get(position).getDescription());
        intent.putExtra("amountOutcome",mOutcome.get(position).getAmount());
        intent.putExtra("idOutcome",mOutcome.get(position).getId());
        startActivity(intent);
    }
}
