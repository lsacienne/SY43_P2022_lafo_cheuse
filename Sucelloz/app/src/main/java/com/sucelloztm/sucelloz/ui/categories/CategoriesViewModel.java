package com.sucelloztm.sucelloz.ui.categories;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sucelloztm.sucelloz.models.Categories;
import com.sucelloztm.sucelloz.repositories.CategoriesRepository;

import java.util.List;

public class CategoriesViewModel extends AndroidViewModel {
    private CategoriesRepository categoriesRepository;

    private LiveData<List<Categories>> currentCategories;

    public CategoriesViewModel(Application application) {
        super(application);
        this.categoriesRepository = new CategoriesRepository(application);
    }

    public LiveData<List<Categories>> getAllCategories() {
        if (this.currentCategories == null) {
            this.currentCategories = categoriesRepository.getAllCategories();
        }
        return this.currentCategories;
    }

}
