package com.example.expensemanager.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanager.Model.Expense;
import com.example.expensemanager.Repository.ExpenseRepository;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class ExpenseViewModel extends AndroidViewModel {
    private ExpenseRepository repository;
    private LiveData<List<Expense>> listLiveData;

    public ExpenseViewModel(@NotNull Application application) {
        super(application);
        repository = ExpenseRepository.getInstance(application);
        listLiveData = repository.getAllExpense();
    }

    public void insert (Expense expense) {
        repository.insert(expense);
    }

    public void update (Expense expense) {
        repository.update(expense);
    }

    public void delete (Expense expense) {
        repository.delete(expense);
    }

    public LiveData<List<Expense>> getAllExpense() {
        return repository.getAllExpense();
    }
}
