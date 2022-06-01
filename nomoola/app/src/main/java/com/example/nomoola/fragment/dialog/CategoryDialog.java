package com.example.nomoola.fragment.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.nomoola.R;
import com.example.nomoola.database.entity.Category;

public class CategoryDialog extends DialogFragment {

    private TextView view_CategoryTitle, view_CategoryName, view_CategoryBudgetAmount;
    private EditText edit_CategoryName, edit_CategoryBudgetAmount;
    private ImageButton exitButton;

    private Category category;

    public CategoryDialog(Category category){
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.dialog_category, container, false);
        this.view_CategoryTitle = view.findViewById(R.id.textViewCategoryTitle);
        this.view_CategoryName = view.findViewById(R.id.text_view_category_name);
        this.view_CategoryBudgetAmount = view.findViewById(R.id.textViewCategoryBudget);
        this.edit_CategoryName = view.findViewById(R.id.editTextCategoryName);
        this.edit_CategoryBudgetAmount = view.findViewById(R.id.editTextCatBudgetAmount);
        this.exitButton = view.findViewById(R.id.exitButton);

        this.view_CategoryTitle.setText(category.getM_CAT_NAME());


        this.exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return view;
    }
}