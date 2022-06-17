package com.example.budgetzeroapp.fragment.view;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.budgetzeroapp.R;
import com.example.budgetzeroapp.fragment.DataBaseFragment;
import com.example.budgetzeroapp.fragment.HomeFragment;
import com.example.budgetzeroapp.tool.ClickableListManager;
import com.example.budgetzeroapp.tool.DBHelper;
import com.example.budgetzeroapp.tool.item.CategoryItem;
import com.example.budgetzeroapp.tool.item.ExpenseItem;

import java.util.List;

//nom, budget
//liens cliquables vers sous catégories
//liste des dépenses de la catégorie
public class ViewExpenseCatFragment extends DataBaseFragment {

    private TextView name, budget;
    private ListView subCatList, expList;
    private String nameVal;
    private float budgetVal;
    private List<CategoryItem> subCatVal;
    private List<ExpenseItem> expVal;

    public ViewExpenseCatFragment(){ super(); }
    public ViewExpenseCatFragment(int id){ super(id); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_view_expense_cat, parent, false);
        return view;
    }

    public void getValues() {

        Cursor cat = database.getCatFromType(id, DBHelper.TYPE_EXP);
        cat.moveToFirst();
        if (cat.isAfterLast()) redirect(new HomeFragment(),id);
        else {
            nameVal = cat.getString(cat.getColumnIndexOrThrow(DBHelper.EXP_CAT_COL_NAME));
            budgetVal = cat.getFloat(cat.getColumnIndexOrThrow(DBHelper.EXP_CAT_COL_BUDGET));
            subCatVal = CategoryItem.initCategoryList(database, false);
            expVal = ExpenseItem.catExpensesToList(database, id, DBHelper.TYPE_EXP);
        }
    }

    public void setValues()   {
        name.setText(nameVal);
        budget.setText(String.valueOf(budgetVal));
        ClickableListManager.clickableBudgetList(subCatList, subCatVal);
        ClickableListManager.clickableExpenseList(expList, expVal);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(R.id.homeFragment, R.id.viewExpenseCatFragment).build();
        Toolbar toolbar = view.findViewById(R.id.toolbar_add_category);
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);


        /**Getting passed id**/
        id = ViewExpenseCatFragmentArgs.fromBundle(getArguments()).getIdExpenseCat();
        Toast.makeText(getActivity(),"id : " + id,Toast.LENGTH_SHORT).show();

        name = view.findViewById(R.id.textViewCatNameEntry);
        budget = view.findViewById(R.id.textViewCatBudgetEntry);
        expList = view.findViewById(R.id.listViewCatExpenses);
        subCatList = view.findViewById(R.id.listViewCatClickSub);
        getValues();
        setValues();
    }
}