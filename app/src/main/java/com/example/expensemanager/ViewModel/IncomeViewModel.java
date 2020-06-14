package com.example.expensemanager.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.expensemanager.Model.Income;
import com.example.expensemanager.Repository.IncomeRepository;
import com.google.firebase.database.annotations.NotNull;

import java.util.List;

public class IncomeViewModel extends AndroidViewModel {
    private IncomeRepository repository;
    private LiveData<List<Income>> listLiveData;

    public IncomeViewModel(@NotNull Application application) {
        super(application);
        repository = IncomeRepository.getInstance(application);
        listLiveData = repository.getAllIncome();
    }

    public void insert (Income income) {
        repository.insert(income);
    }

    public void update (Income income) {
        repository.update(income);
    }

    public void delete (Income income) {
        repository.delete(income);
    }

    public LiveData<List<Income>> getAllIncome() {
        return repository.getAllIncome();
    }
}
