package com.suatkkrer.debtmanager.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.suatkkrer.debtmanager.Adapter.OutcomeAdapter;
import com.suatkkrer.debtmanager.Model.OutcomeClass;
import com.suatkkrer.debtmanager.R;
import com.suatkkrer.debtmanager.Activities.addOutCome;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.content.Context.MODE_PRIVATE;


public class OutcomeFragment extends Fragment implements OutcomeAdapter.OnNoteListener {

    View v;
    Context thisContext;
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
                    int convertedString = Integer.parseInt(cursor.getString(amount));
                    NumberFormat format = NumberFormat.getNumberInstance();
                    String s = String.valueOf(format.format(convertedString));
                    mOutcome.add(new OutcomeClass(cursor.getString(name), s, cursor.getString(desc), R.drawable.coloreddevaluation, cursor.getInt(idIx),cursor.getString(dateIx)));
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
                Intent intent = new Intent(getContext(), addOutCome.class);
                startActivity(intent);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return v;
    }

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            MainFragment mainFragment = new MainFragment();

            int position = viewHolder.getAdapterPosition();

            switch (direction){
                case ItemTouchHelper.LEFT:
                    SQLiteDatabase sqLiteDatabase = thisContext.openOrCreateDatabase("Outcome", MODE_PRIVATE, null);

                    sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS outcome(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


                    sqLiteDatabase.execSQL("DELETE FROM outcome WHERE id = " + mOutcome.get(position).getId() + "");
                    mOutcome.remove(position);
                    adapter.notifyItemRemoved(position);

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getContext(), R.color.red))
                    .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };


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
