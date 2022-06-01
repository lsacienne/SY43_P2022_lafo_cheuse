package com.example.nomoola.database.entity;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "T_SUBCATEGORY", primaryKeys = {"SUBCAT_NAME", "CAT_NAME"})
public class SubCategory {

    /**
     * ATTRIBUTE
     */

    @NonNull
    @ColumnInfo(name = "CAT_NAME")
    private String m_CAT_NAME;

    @NonNull
    @ColumnInfo(name = "SUBCAT_NAME")
    private String m_SUBCAT_NAME;


    /**
     * CONSTRUCTOR
     */

    public SubCategory(){
    }

    public SubCategory(@NonNull String categoryName, @NonNull String SubCategory){
        Log.d("CREATION", "Instantiation of UnderCategory = "+SubCategory);
        this.m_CAT_NAME = categoryName;
        this.m_SUBCAT_NAME = SubCategory;
    }

    /**
     * GETTER / SETTER
     */
    public String getM_CAT_NAME() {
        return m_CAT_NAME;
    }

    public void setM_CAT_NAME(String m_CAT_NAME) {
        this.m_CAT_NAME = m_CAT_NAME;
    }

    @NonNull
    public String getM_SUBCAT_NAME() {
        return m_SUBCAT_NAME;
    }

    public void setM_SUBCAT_NAME(@NonNull String m_SUBCAT_NAME) {
        this.m_SUBCAT_NAME = m_SUBCAT_NAME;
    }
}