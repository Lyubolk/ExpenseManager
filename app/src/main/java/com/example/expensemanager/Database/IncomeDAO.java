package com.example.expensemanager.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.expensemanager.Model.Income;

import java.util.List;

@Dao
public interface IncomeDAO {
    @Insert
    void insert(Income income);

    @Update
    void update(Income income);

    @Delete
    void delete(Income income);

    @Query("DELETE FROM income_table")
    void deleteAllIncome();

    @Query("SELECT * FROM income_table")
    LiveData<List<Income>> getAllIncome();
}
