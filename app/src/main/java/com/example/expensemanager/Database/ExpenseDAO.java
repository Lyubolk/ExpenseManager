package com.example.expensemanager.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensemanager.Model.Expense;
import com.example.expensemanager.Model.Income;

import java.util.List;

@Dao
public interface ExpenseDAO {
    @Insert
    void insert(Expense expense);

    @Update
    void update(Expense expense);

    @Delete
    void delete(Expense expense);

    @Query("DELETE FROM expense_table")
    void deleteAllExpense();

    @Query("SELECT * FROM expense_table")
    LiveData<List<Expense>> getAllExpense();
}
