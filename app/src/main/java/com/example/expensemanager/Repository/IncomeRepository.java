package com.example.expensemanager.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.expensemanager.Database.IncomeDAO;
import com.example.expensemanager.Database.IncomeDatabase;
import com.example.expensemanager.Model.Income;

import java.util.List;

public class IncomeRepository {
    private IncomeDAO incomeDAO;
    private static IncomeRepository instance;
    private LiveData<List<Income>> allIncome;

    private IncomeRepository(Application application) {
        IncomeDatabase incomeDatabase = IncomeDatabase.getInstance(application);
        incomeDAO = incomeDatabase.incomeDAO();
        allIncome = incomeDAO.getAllIncome();
    }

    public static synchronized IncomeRepository getInstance(Application application) {
        if (instance == null)
            instance = new IncomeRepository(application);

        return instance;
    }

    public LiveData<List<Income>>getAllIncome() {
        return allIncome;
    }

    public void insert(Income income) {
        new InsertIncomeAsync(incomeDAO).execute(income);
    }

    private static class InsertIncomeAsync extends AsyncTask<Income, Void, Void> {
        private IncomeDAO incomeDAO;

        private InsertIncomeAsync(IncomeDAO incomeDAO) {
            this.incomeDAO = incomeDAO;
        }

        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDAO.insert(incomes[0]);
            return null;
        }
    }

    public void delete (Income income) {
        new DeleteIncomeAsync(incomeDAO).execute(income);
    }

    private static class DeleteIncomeAsync extends AsyncTask<Income, Void, Void> {
        private IncomeDAO incomeDAO;

        private DeleteIncomeAsync(IncomeDAO incomeDAO) {
            this.incomeDAO = incomeDAO;
        }

        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDAO.delete(incomes[0]);
            return null;
        }
    }

    public void update(Income income) {
        new UpdateIncomeAsyncTask(incomeDAO).execute(income);
    }

    private static class UpdateIncomeAsyncTask extends AsyncTask<Income, Void, Void> {
        private IncomeDAO incomeDAO;

        private UpdateIncomeAsyncTask(IncomeDAO incomeDAO) {
            this.incomeDAO = incomeDAO;
        }

        @Override
        protected Void doInBackground(Income... incomes) {
            incomeDAO.update(incomes[0]);
            return null;
        }
    }
}
