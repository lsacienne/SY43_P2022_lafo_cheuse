package com.example.nomoola.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.nomoola.database.entity.SubCategory;

import java.util.List;

@Dao
public interface SubCategoryDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSubCategory(SubCategory...subCategories);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateSubCategory(SubCategory...subCategories);

    @Delete
    void deleteSubCategory(SubCategory subCategory);

    @Query("SELECT * FROM T_SUBCATEGORY")
    LiveData<List<SubCategory>> getAllSubCategories();

    @Query("SELECT * FROM T_SUBCATEGORY WHERE CAT_ID=:categoryID")
    LiveData<List<SubCategory>> getSubCategoriesOf(int categoryID);

    @Query("UPDATE T_SUBCATEGORY SET CAT_ID=:catID, SUBCAT_NAME=:subcatName WHERE SUBCAT_ID=:id")
    void updateSubCategory(int catID, String subcatName, int id);

}
