package com.example.expensemanager.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensemanager.Adapter.ExpenseAdapter;
import com.example.expensemanager.Model.Expense;
import com.example.expensemanager.R;
import com.example.expensemanager.ViewModel.ExpenseViewModel;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class ExpenseFragment extends Fragment implements ExpenseAdapter.OnListItemClickListener{
    private ExpenseViewModel expenseViewModel;
    private RecyclerView recyclerView;
    private TextView totalExpense;
    private EditText editAmount;
    private EditText editType;
    private EditText editNote;
    private EditText editDate;
    private String amount;
    private String type;
    private String note;
    private String date;
    private ExpenseAdapter expenseAdapter;
    private Button update;
    private Button delete;

    public ExpenseFragment(MainActivity mainActivity, ExpenseViewModel expenseViewModel) {
        this.expenseViewModel = expenseViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.expense_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.expense_recycler_view);
        totalExpense = rootView.findViewById(R.id.expense_number);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        expenseAdapter = new ExpenseAdapter(this);
        recyclerView.setAdapter(expenseAdapter);

        expenseViewModel.getAllExpense().observe(this.getActivity(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                double totExpense = 0;

                for(int i = 0; i < expenses.size(); i++) {
                    totExpense += expenses.get(i).getAmount();
                }

                String result = String.valueOf(totExpense);
                totalExpense.setText(result);

                expenseAdapter.setExpense(expenses);
            }
        });
        return rootView;
    }

    @Override
    public void onListItemClick(final Expense expenses) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.edit_item, null);
        builder.setView(view);
        editAmount = view.findViewById(R.id.edit_item_amount);
        editType = view.findViewById(R.id.edit_item_type);
        editNote = view.findViewById(R.id.edit_item_note);

        editAmount.setText(String.valueOf(amount));
        editType.setText(type);
        editNote.setText(note);

        update = view.findViewById(R.id.save_button_edit);
        delete = view.findViewById(R.id.cancel_button_edit);

        final AlertDialog dialog = builder.create();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dAmount = editAmount.getText().toString();
                double theAmount = Double.parseDouble(dAmount);
                type = editType.getText().toString();
                note = editNote.getText().toString();
                String dDate = DateFormat.getDateInstance().format(new Date());
                int i = expenses.getId();
                Expense newExpense = new Expense(theAmount, type, note, dDate);
                newExpense.setId(i);
                expenseViewModel.update(newExpense);
                dialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = expenses.getId();
                expenseViewModel.delete(expenseAdapter.getExpenseAt(i));
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}