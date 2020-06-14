package com.example.expensemanager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Model.Income;
import com.example.expensemanager.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {

    private List<Income> incomes;
    final private OnListItemClickListener onListItemClickListener;

    @NonNull
    @Override
    public IncomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.income_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeAdapter.ViewHolder holder, int position) {
        if(incomes != null) {
            Income income = incomes.get(position);
            String theAmount = String.valueOf(income.getAmount());
            holder.amount.setText(theAmount);
            holder.type.setText(income.getType());
            holder.note.setText(income.getNote());
            holder.date.setText(income.getDate());
        }
    }

    @Override
    public int getItemCount() {
        if(incomes == null) {
            return 0;
        }
        return incomes.size();
    }

    public interface OnListItemClickListener {
        void onListItemClick(Income incomes);
    }

    public IncomeAdapter(OnListItemClickListener listener) {
        onListItemClickListener = listener;
    }

    public void setIncome(List<Income> incomes) {
        this.incomes = incomes;
        notifyDataSetChanged();
    }

    public Income getIncomeAt (int position) {
        return incomes.get(position-1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView amount;
        TextView type;
        TextView note;
        TextView date;

        private ViewHolder(@NotNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.income_amount);
            type = itemView.findViewById(R.id.income_type);
            note = itemView.findViewById(R.id.income_note);
            date = itemView.findViewById(R.id.income_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListItemClickListener.onListItemClick(incomes.get(getAdapterPosition()));
        }
    }
}
