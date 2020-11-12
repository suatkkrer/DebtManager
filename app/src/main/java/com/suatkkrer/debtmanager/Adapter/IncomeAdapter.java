package com.suatkkrer.debtmanager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suatkkrer.debtmanager.Model.IncomeClass;
import com.suatkkrer.debtmanager.R;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {
    private List<IncomeClass> mData;
    private OnNoteListener mOnNoteListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageView;
        public TextView name,date,amount;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iconViewAlacak);
            name = itemView.findViewById(R.id.nameAlacak);
            date = itemView.findViewById(R.id.tarihAlacak);
            amount = itemView.findViewById(R.id.paraAlacak);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    public IncomeAdapter(List<IncomeClass> incomeClasses, OnNoteListener onNoteListener) {
        this.mData = incomeClasses;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        ViewHolder viewHolder = new ViewHolder(v,mOnNoteListener);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IncomeClass currentItem = mData.get(position);
        holder.imageView.setImageResource(currentItem.getIcon());
        holder.name.setText(currentItem.getName());
        holder.date.setText(currentItem.getDate());
        holder.amount.setText(currentItem.getAmount());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
