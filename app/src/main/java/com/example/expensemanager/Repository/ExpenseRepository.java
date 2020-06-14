package com.example.expensemanager.Repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Database;

import com.example.expensemanager.Database.ExpenseDAO;
import com.example.expensemanager.Database.ExpenseDatabase;
import com.example.expensemanager.Database.IncomeDAO;
import com.example.expensemanager.Database.IncomeDatabase;
import com.example.expensemanager.Model.Expense;
import com.example.expensemanager.Model.Income;

import java.util.List;

public class ExpenseRepository {
    private ExpenseDAO expenseDAO;
    private static ExpenseRepository instance;
    private LiveData<List<Expense>> allExpense;

    private ExpenseRepository(Application application) {
        ExpenseDatabase expenseDatabase = ExpenseDatabase.getInstance(application);
        expenseDAO = expenseDatabase.expenseDAO();
        allExpense = expenseDAO.getAllExpense();
    }

    public static synchronized ExpenseRepository getInstance(Application application) {
        if (instance == null)
            instance = new ExpenseRepository(application);

        return instance;
    }

    public LiveData<List<Expense>>getAllExpense() {
        return allExpense;
    }

    public void insert(Expense expense) {
        new ExpenseRepository.InsertExpenseAsync(expenseDAO).execute(expense);
    }

    private static class InsertExpenseAsync extends AsyncTask<Expense, Void, Void> {
        private ExpenseDAO expenseDAO;

        private InsertExpenseAsync(ExpenseDAO expenseDAO) {
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDAO.insert(expenses[0]);
            return null;
        }
    }

    public void delete (Expense expense) {
        new ExpenseRepository.DeleteExpenseAsync(expenseDAO).execute(expense);
    }

    private static class DeleteExpenseAsync extends AsyncTask<Expense, Void, Void> {
        private ExpenseDAO expenseDAO;

        private DeleteExpenseAsync(ExpenseDAO expenseDAO) {
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDAO.delete(expenses[0]);
            return null;
        }
    }

    public void update(Expense expense) {
        new ExpenseRepository.UpdateExpenseAsyncTask(expenseDAO).execute(expense);
    }

    private static class UpdateExpenseAsyncTask extends AsyncTask<Expense, Void, Void> {
        private ExpenseDAO expenseDAO;

        private UpdateExpenseAsyncTask(ExpenseDAO expenseDAO) {
            this.expenseDAO = expenseDAO;
        }

        @Override
        protected Void doInBackground(Expense... expenses) {
            expenseDAO.update(expenses[0]);
            return null;
        }
    }
}
