package com.example.budgetzeroapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.budgetzeroapp.MainActivity;
import com.example.budgetzeroapp.R;
import com.example.budgetzeroapp.fragment.cashflow.CashFlowEvolutionFragment;
import com.example.budgetzeroapp.fragment.cashflow.CashFlowGeneralFragment;
import com.example.budgetzeroapp.fragment.cashflow.CashFlowStableFragment;
import com.example.budgetzeroapp.tool.ToolBar;
import com.example.budgetzeroapp.tool.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class CashFlowFragment extends DataBaseFragment {
    TabLayout tablayout;
    ViewPager viewPager;
    public CashFlowFragment() {
        // Required empty public constructor
    }

    public static CashFlowFragment newInstance(String param1, String param2) {
        CashFlowFragment fragment = new CashFlowFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cash_flow, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addFragment(view);

        ToolBar.getInstance().initToolBar(view, R.id.toolbar_cashflow);
        /**Navigation
        Toolbar toolbar = view.findViewById(R.id.toolbar_cashflow);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                NavController navController = Navigation.findNavController(view);
                switch(item.getItemId()){
                    case R.id.next_day:
                        //change day
                        break;
                }
                return true;
            }
        });**/

    }

    private void addFragment(View view)
    {
        tablayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.viewPager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new CashFlowGeneralFragment(), "General");
        adapter.addFragment(new CashFlowStableFragment(), "Stable");
        adapter.addFragment(new CashFlowEvolutionFragment(), "Evolution");
        viewPager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewPager);
    }

    public static void redirectToViewExpense(int exp_id)
    {
        NavController navController= Navigation.findNavController(MainActivity.getActivity(), R.id.nav_host_fragment);
        NavDirections action = CashFlowFragmentDirections.navigateToViewExpenseFragmentFromCashflow(exp_id);
        navController.navigate(action);
    }

}