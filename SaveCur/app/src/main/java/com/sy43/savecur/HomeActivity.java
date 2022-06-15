package com.sy43.savecur;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    List<Category> categories = getCategories();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        GridView categoriesGridView = (GridView) findViewById(R.id.categories_grid_view);
        categoriesGridView.setAdapter(new CategoriesGridAdapter(this, categories));

        Intent editCategoryIntent = new Intent(this, CategoryEditActivity.class);
        categoriesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                editCategoryIntent.putExtra("name", categories.get(position).getName());
                editCategoryIntent.putExtra("moneySpent", categories.get(position).getMoneySpent());
                editCategoryIntent.putExtra("moneyLimit", categories.get(position).getMoneyLimit());

                startActivity(editCategoryIntent);
            }
        });

        FloatingActionButton addExpenseBtn = (FloatingActionButton) findViewById(R.id.addExpenseBtn);
        Intent addExpenseIntent = new Intent(this, AddExpenseActivity.class);

        ArrayList<String> categoryNameList = new ArrayList<>();
        for (int i = 0; i < categories.size(); i++) {
            categoryNameList.add(categories.get(i).getName());
        }
        addExpenseIntent.putStringArrayListExtra("categories", categoryNameList);

        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(addExpenseIntent);
            }
        });
    }

    private void addExpense(String name, int amount, int categoryId) {

    }

    private List<Category> getCategories() {
        List<Category> categories = new ArrayList<Category>();
        categories.add(new Category("Food",150,0, 0));
        categories.add(new Category("Party",80,15, 1));
        categories.add(new Category("Other",180,70, 2));
        categories.add(new Category("Other2",30,25, 3));
        categories.add(new Category("Other3",90,16, 4));
        categories.add(new Category("Other4",90,16, 5));

        return categories;
    }
}