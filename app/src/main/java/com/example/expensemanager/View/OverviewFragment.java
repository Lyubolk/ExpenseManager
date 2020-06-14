package com.example.expensemanager.View;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.example.expensemanager.Model.Expense;
import com.example.expensemanager.Model.Income;
import com.example.expensemanager.R;
import com.example.expensemanager.ViewModel.ExpenseViewModel;
import com.example.expensemanager.ViewModel.IncomeViewModel;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class OverviewFragment extends Fragment {
    private TextView totalIncome;
    private TextView totalExpense;
    private FloatingActionButton fab;
    private FloatingActionButton income_fab;
    private FloatingActionButton expense_fab;
    private Boolean isFABOpen = false;
    private Animation fabOpen, fabClose;
    private IncomeViewModel incomeViewModel;
    private ExpenseViewModel expenseViewModel;

    public OverviewFragment(MainActivity mainActivity, IncomeViewModel incomeViewModel, ExpenseViewModel expenseViewModel) {
        this.incomeViewModel = incomeViewModel;
        this.expenseViewModel = expenseViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.overview_fragment, container, false);

        fab = rootView.findViewById(R.id.fab);
        income_fab = rootView.findViewById(R.id.fab_income);
        expense_fab = rootView.findViewById(R.id.fab_expense);
        //fab code taken from https://www.sitepoint.com/animating-android-floating-action-button/
        fabOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        totalIncome = rootView.findViewById(R.id.income_number);
        totalExpense = rootView.findViewById(R.id.expense_number);

        incomeViewModel.getAllIncome().observe(this.getActivity(), new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomes) {
                double totIncome = 0;

                for(int i = 0; i < incomes.size(); i++) {
                    totIncome += incomes.get(i).getAmount();
                }

                String result = String.valueOf(totIncome);
                totalIncome.setText(result);
            }
        });

        expenseViewModel.getAllExpense().observe(this.getActivity(), new Observer<List<Expense>>() {
            @Override
            public void onChanged(List<Expense> expenses) {
                double totExpense = 0;

                for(int i = 0; i < expenses.size(); i++) {
                    totExpense += expenses.get(i).getAmount();
                }

                String result = String.valueOf(totExpense);
                totalExpense.setText(result);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
                fabAnimation();
            }
        });
        return rootView;
    }

    private void addData() {
        income_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                insertIncome();
            }
        });

        expense_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                insertExpense();
            }
        });
    }

    public void insertIncome() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.add_item, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        dialog.setCancelable(false);

        final EditText editAmount = view.findViewById(R.id.add_item_amount);
        final EditText editType = view.findViewById(R.id.add_item_type);
        final EditText editNote = view.findViewById(R.id.add_item_note);

        Button save = view.findViewById(R.id.save_button_add);
        Button cancel = view.findViewById(R.id.cancel_button_add);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String type = editType.getText().toString();
                String amount = editAmount.getText().toString();
                String note = editNote.getText().toString();

                if (TextUtils.isEmpty(type)) {
                    editType.setError("Required field");
                    return;
                }

                if (TextUtils.isEmpty(amount)) {
                    editAmount.setError("Required field");
                    return;
                }

                double theAmount = Double.parseDouble(amount);

                if (TextUtils.isEmpty(note)) {
                    editNote.setError("Required firled");
                    return;
                }

                String date = DateFormat.getDateInstance().format(new Date());

                incomeViewModel.insert(new Income(theAmount, type, note, date));
                fabAnimation();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAnimation();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void insertExpense() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.add_item, null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();

        dialog.setCancelable(false);

        final EditText editAmount = view.findViewById(R.id.add_item_amount);
        final EditText editType = view.findViewById(R.id.add_item_type);
        final EditText editNote = view.findViewById(R.id.add_item_note);

        Button save = view.findViewById(R.id.save_button_add);
        Button cancel = view.findViewById(R.id.cancel_button_add);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String type = editType.getText().toString();
                String amount = editAmount.getText().toString();
                String note = editNote.getText().toString();

                if (TextUtils.isEmpty(type)) {
                    editType.setError("Required field");
                    return;
                }

                if (TextUtils.isEmpty(amount)) {
                    editAmount.setError("Required field");
                    return;
                }

                double theAmount = Double.parseDouble(amount);

                if (TextUtils.isEmpty(note)) {
                    editNote.setError("Required firled");
                    return;
                }

                String date = DateFormat.getDateInstance().format(new Date());

                expenseViewModel.insert(new Expense(theAmount, type, note, date));
                fabAnimation();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabAnimation();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void fabAnimation() {
        if (isFABOpen) {
            income_fab.startAnimation(fabClose);
            expense_fab.startAnimation(fabClose);
            income_fab.setClickable(false);
            expense_fab.setClickable(false);
            isFABOpen = false;
        } else {
            income_fab.startAnimation(fabOpen);
            expense_fab.startAnimation(fabOpen);
            income_fab.setClickable(true);
            expense_fab.setClickable(true);
            isFABOpen = true;
        }
    }
}
