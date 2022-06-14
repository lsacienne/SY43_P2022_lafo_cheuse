package com.sucelloztm.sucelloz.ui.spendings;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpendingsTabsFragmentAdapter extends FragmentStateAdapter {

    private List<Fragment> spendingsFragmentList;
    private PositiveSpendingsFragment positiveSpendingsFragment;
    private NegativeSpendingsFragment negativeSpendingsFragment;

    public SpendingsTabsFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        spendingsFragmentList = new ArrayList<>();
        positiveSpendingsFragment = new PositiveSpendingsFragment();
        negativeSpendingsFragment = new NegativeSpendingsFragment();
        this.spendingsFragmentList.add(positiveSpendingsFragment);
        this.spendingsFragmentList.add(negativeSpendingsFragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return this.spendingsFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public PositiveSpendingsFragment getPositiveSpendingsFragment() {
        return positiveSpendingsFragment;
    }

    public NegativeSpendingsFragment getNegativeSpendingsFragment() {
        return negativeSpendingsFragment;
    }
}