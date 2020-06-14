package com.example.expensemanager.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.expensemanager.R;
import com.example.expensemanager.ViewModel.ExpenseViewModel;
import com.example.expensemanager.ViewModel.IncomeViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigation;
    private FirebaseAuth fbAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Expense Manager");
        setSupportActionBar(toolbar);

        fbAuth = FirebaseAuth.getInstance();

        bottomNavigation = findViewById(R.id.bottom_navigation);
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

       NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new OverviewFragment(MainActivity.this, new ViewModelProvider(this).get(IncomeViewModel.class),
                        new ViewModelProvider(MainActivity.this).get(ExpenseViewModel.class))).commit();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()){
                    case R.id.nav_overview:
                        fragment = new OverviewFragment(MainActivity.this, new ViewModelProvider(MainActivity.this).get(IncomeViewModel.class),
                                new ViewModelProvider(MainActivity.this).get(ExpenseViewModel.class));
                        break;

                    case R.id.nav_income:
                        fragment = new IncomeFragment(MainActivity.this, new ViewModelProvider(MainActivity.this).get(IncomeViewModel.class));
                        break;

                    case R.id.nav_expense:
                        fragment = new ExpenseFragment(MainActivity.this, new ViewModelProvider(MainActivity.this).get(ExpenseViewModel.class));
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        fragment).commit();
                return true;
            }
        });

    }

    public void displaySelectedListener(int itemId){
        Fragment fragment = null;

        switch (itemId){
            case R.id.overview:
                fragment = new OverviewFragment(MainActivity.this, new ViewModelProvider(MainActivity.this).get(IncomeViewModel.class),
                        new ViewModelProvider(MainActivity.this).get(ExpenseViewModel.class));
                break;

            case R.id.income:
                fragment = new IncomeFragment(MainActivity.this, new ViewModelProvider(MainActivity.this).get(IncomeViewModel.class));
                break;

            case R.id.expense:
                fragment = new ExpenseFragment(MainActivity.this, new ViewModelProvider(MainActivity.this).get(ExpenseViewModel.class));
                break;

            case R.id.logout:
                fbAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    fragment).commit();
        }

        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedListener(item.getItemId());
        return true;
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);

        if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME );
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}
