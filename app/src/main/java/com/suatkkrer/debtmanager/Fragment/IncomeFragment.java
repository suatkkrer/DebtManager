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
import com.suatkkrer.debtmanager.Adapter.IncomeAdapter;
import com.suatkkrer.debtmanager.Model.IncomeClass;
import com.suatkkrer.debtmanager.R;
import com.suatkkrer.debtmanager.Activities.addIncome;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

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
                Intent intent = new Intent(thisContext, addIncome.class);
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
                        SQLiteDatabase sqLiteDatabase = thisContext.openOrCreateDatabase("Income", MODE_PRIVATE, null);

                        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS income(id INTEGER PRIMARY KEY,name TEXT, amount TEXT, description TEXT,date TEXT)");


                        sqLiteDatabase.execSQL("DELETE FROM income WHERE id = " + mIncome.get(position).getId() + "");
                        mIncome.remove(position);
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
