package com.suatkkrer.debtmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class IncomeFragment extends Fragment {

    View v;
    Context thisContext;
    FloatingActionButton floatingActionButton;
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        thisContext = container.getContext();
        v = inflater.inflate(R.layout.incomefragment,container,false);

        floatingActionButton = v.findViewById(R.id.incomeFloating);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thisContext,addIncome.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
