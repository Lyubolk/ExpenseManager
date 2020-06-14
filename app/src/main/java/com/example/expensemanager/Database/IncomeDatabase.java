package com.example.expensemanager.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.expensemanager.Model.Income;

@Database(entities = {Income.class}, version = 1)
public abstract class IncomeDatabase extends RoomDatabase {
    private static IncomeDatabase instance;

    public abstract IncomeDAO incomeDAO();

    public static synchronized IncomeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    IncomeDatabase.class, "income_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
