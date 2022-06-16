package com.sy43.savecur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    List<Category> categories;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.leaveBtn) {
            mAuth.signOut();
            startActivity(new Intent(HomeActivity.this, FirstActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
        }

        db = FirebaseFirestore.getInstance();
        setCategories();

        db.collection("users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    MaterialTextView helloNameText = (MaterialTextView) findViewById(R.id.helloNameText);
                    helloNameText.setText("Hello, " + task.getResult().getData().get("name"));
                }
            }
        });

        MaterialButton newCategoryBtn = (MaterialButton) findViewById(R.id.newCategoryBtn);
        newCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CategoryEditActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setCategories() {
        List<Category> categories = new ArrayList<Category>();
        db.collection("users").document(currentUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                task.getResult().getReference().collection("categories").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            float total_money_spent = 0;
                            float total_money_limit = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("req", document.getId() + " => " + document.getData());
                                Map<String, Object> data = document.getData();
                                String id = document.getId();
                                float moneySpent = Float.parseFloat(data.get("moneySpent").toString());
                                float moneyLimit = Float.parseFloat(data.get("moneyLimit").toString());
                                categories.add(new Category(data.get("name").toString(), moneyLimit, moneySpent, id));

                                total_money_limit += moneyLimit;
                                total_money_spent += moneySpent;
                            }

                            ProgressBar totalProgressBar = (ProgressBar) findViewById(R.id.total_progress_bar);
                            totalProgressBar.setProgress((int)((total_money_spent * 100) / total_money_limit));
                            MaterialTextView totalMoneySpentTextView = (MaterialTextView) findViewById(R.id.total_money_spent);
                            MaterialTextView totalMoneyLimitTextView = (MaterialTextView) findViewById(R.id.total_money_limit);
                            totalMoneySpentTextView.setText(String.valueOf(total_money_spent)+"€");
                            totalMoneyLimitTextView.setText(String.valueOf(total_money_limit)+"€");

                            GridView categoriesGridView = (GridView) findViewById(R.id.categories_grid_view);
                            categoriesGridView.setAdapter(new CategoriesGridAdapter(HomeActivity.this, categories));

                            Intent editCategoryIntent = new Intent(HomeActivity.this, CategoryEditActivity.class);
                            categoriesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    editCategoryIntent.putExtra("name", categories.get(position).getName());
                                    editCategoryIntent.putExtra("moneySpent", categories.get(position).getMoneySpent());
                                    editCategoryIntent.putExtra("moneyLimit", categories.get(position).getMoneyLimit());
                                    editCategoryIntent.putExtra("categoryID", categories.get(position).getId());

                                    startActivity(editCategoryIntent);
                                }
                            });

                            FloatingActionButton addExpenseBtn = (FloatingActionButton) findViewById(R.id.addExpenseBtn);
                            Intent addExpenseIntent = new Intent(HomeActivity.this, AddExpenseActivity.class);

                            ArrayList<String> categoryNameList = new ArrayList<>();
                            ArrayList<String> categoryIdList = new ArrayList<>();
                            for (int i = 0; i < categories.size(); i++) {
                                categoryNameList.add(categories.get(i).getName());
                                categoryIdList.add(categories.get(i).getId());
                            }
                            addExpenseIntent.putStringArrayListExtra("categories", categoryNameList);
                            addExpenseIntent.putStringArrayListExtra("categoriesIDs", categoryIdList);

                            addExpenseBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(addExpenseIntent);
                                }
                            });
                        } else {
                            Log.w("req", "Error getting documents.", task.getException());
                        }
                    }
                });
            }
        });
    }
}