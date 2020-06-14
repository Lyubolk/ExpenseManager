package com.example.expensemanager.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Model.Expense;
import com.example.expensemanager.R;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {

    private List<Expense> expenses;
    final private OnListItemClickListener onListItemClickListener;

    @NonNull
    @Override
    public ExpenseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.expense_item, parent, false);
        return new ExpenseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ViewHolder holder, int position) {
        if (expenses != null) {
            Expense expense = expenses.get(position);
            String theAmount = String.valueOf(expense.getAmount());
            holder.amount.setText(theAmount);
            holder.type.setText(expense.getType());
            holder.note.setText(expense.getNote());
            holder.date.setText(expense.getDate());
        }
    }

    @Override
    public int getItemCount() {
        if (expenses == null) {
            return 0;
        }
        return expenses.size();
    }

    public interface OnListItemClickListener {
        void onListItemClick(Expense expense);
    }

    public ExpenseAdapter(ExpenseAdapter.OnListItemClickListener listener) {
        onListItemClickListener = listener;
    }

    public void setExpense(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    public Expense getExpenseAt(int position) {
        return expenses.get(position - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView amount;
        TextView type;
        TextView note;
        TextView date;

        private ViewHolder(@NotNull View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.expense_amount);
            type = itemView.findViewById(R.id.expense_type);
            note = itemView.findViewById(R.id.expense_note);
            date = itemView.findViewById(R.id.expense_date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onListItemClickListener.onListItemClick(expenses.get(getAdapterPosition()));
        }
    }
}