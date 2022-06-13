package com.example.budgetzeroapp.tool.item;

import com.example.budgetzeroapp.MainActivity;
import com.example.budgetzeroapp.R;
import com.example.budgetzeroapp.fragment.BudgetFragment;
import com.example.budgetzeroapp.fragment.DataBaseFragment;
import com.example.budgetzeroapp.fragment.savings.SavingsFragment;
import com.example.budgetzeroapp.fragment.view.ViewExpenseCatFragment;
import com.google.android.material.tabs.TabLayout;

public class CategoryItem extends ListItem{

    private float budget, total, percentage;
    private DataBaseFragment frag;

    public CategoryItem(int id, String name, float total, float pOrB){
        super(id, name);
        percentage = pOrB;
        budget = pOrB;
        this.total = total;
    }

    public int getDrawable(){
        switch (name) {
            case "Health":
                return R.drawable.ic_heart;
            case "Shopping":
                return R.drawable.ic_shopping;
            case "Leisure":
                return R.drawable.ic_leisure;
            case "Vehicle":
                return R.drawable.ic_car;
            case "Miscellaneous":
                return R.drawable.ic_coffee;
            default:
                return R.drawable.ic_default_thumb;
        }
    }

    public void redirect(){
        if(id == -1 || id == 0) MainActivity.getActivity().bottomNavigationRedirect(R.id.Savings);
    }

    public float getTotal(){ return total; }
    public float getPercent(){ return percentage; }
    public float getBudget(){ return budget; }

    public DataBaseFragment getFragment(){
        if(id == 0) return new SavingsFragment(false);
        else if(id == -1) return new SavingsFragment();
        return new ViewExpenseCatFragment(id);
    }
}