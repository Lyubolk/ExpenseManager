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

import com.example.expensemanager.Adapter.IncomeAdapter;
import com.example.expensemanager.Model.Income;
import com.example.expensemanager.R;
import com.example.expensemanager.ViewModel.IncomeViewModel;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class IncomeFragment extends Fragment implements IncomeAdapter.OnListItemClickListener{
    private IncomeViewModel incomeViewModel;
    private RecyclerView recyclerView;
    private TextView totalIncome;
    private EditText editAmount;
    private EditText editType;
    private EditText editNote;
    private EditText editDate;
    private String amount;
    private String type;
    private String note;
    private String date;
    private IncomeAdapter incomeAdapter;
    private Button update;
    private Button delete;

    public IncomeFragment(MainActivity mainActivity, IncomeViewModel incomeViewModel) {
        this.incomeViewModel = incomeViewModel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.income_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.income_recycler_view);
        totalIncome = rootView.findViewById(R.id.income_number);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        incomeAdapter = new IncomeAdapter(this);
        recyclerView.setAdapter(incomeAdapter);

        incomeViewModel.getAllIncome().observe(this.getActivity(), new Observer<List<Income>>() {
            @Override
            public void onChanged(List<Income> incomes) {
                double totIncome = 0;

                for(int i = 0; i < incomes.size(); i++) {
                    totIncome += incomes.get(i).getAmount();
                }

                String result = String.valueOf(totIncome);
                totalIncome.setText(result);

                incomeAdapter.setIncome(incomes);
            }
        });
        return rootView;
    }

    @Override
    public void onListItemClick(final Income incomes) {
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
                double theAmmount = Double.parseDouble(dAmount);
                type = editType.getText().toString();
                note = editNote.getText().toString();
                String dDate = DateFormat.getDateInstance().format(new Date());
                int i = incomes.getId();
                Income newIncome = new Income(theAmmount, type, note, dDate);
                newIncome.setId(i);
                incomeViewModel.update(newIncome);
                dialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = incomes.getId();
                incomeViewModel.delete(incomeAdapter.getIncomeAt(i));
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
