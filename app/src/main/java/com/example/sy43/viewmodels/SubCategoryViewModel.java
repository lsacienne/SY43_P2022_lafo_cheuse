package com.example.sy43.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sy43.db.entity.Categorydb;
import com.example.sy43.db.entity.SubCategory;
import com.example.sy43.repositories.SubCategoryRepository;

import java.util.List;

public class SubCategoryViewModel extends ViewModel {
    public SubCategoryRepository subCatRepo;

    public void init() {
        subCatRepo = SubCategoryRepository.getInstance();
    }

    public MutableLiveData<List<SubCategory>> getSubCategoriesByCatId(int catId){
        return subCatRepo.getSubCategoriesByCatId(catId);

    }

    public LiveData<SubCategory> getSubCategoryById(int id){
        return this.subCatRepo.getSubCategoryById(id);
    }
    public void createSubCategory(final SubCategory subCategory) {
        this.subCatRepo.createSubCategory(subCategory);
    }
    public LiveData<List<SubCategory>> getSubCategories() {
        return this.subCatRepo.getSubCategories();
    }


    public void delSubCategories(int id) {
        this.subCatRepo.deleteSubCategory(id);
    }
}