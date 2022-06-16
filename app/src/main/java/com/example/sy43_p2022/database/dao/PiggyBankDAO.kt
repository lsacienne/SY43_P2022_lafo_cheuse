package com.example.sy43_p2022.database.dao

import androidx.room.*
import com.example.sy43_p2022.database.entities.Category
import com.example.sy43_p2022.database.entities.SubCategory

@Dao
interface PiggyBankDAO {
    @Insert(entity = Category::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(vararg name: Category)

    @Insert(entity = SubCategory::class, onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSubCategory(vararg name: SubCategory)

    @Transaction
    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>

    @Transaction
    @Query("SELECT * FROM category WHERE catid = :id")
    suspend fun getCategoryById(id: String): Category

    @Transaction
    @Query("SELECT * FROM category WHERE name = :name")
    suspend fun getCategoryByName(name: String): Category

    @Transaction
    @Query("SELECT * FROM subcategory WHERE categoryId = :id")
    suspend fun getSubCategoriesByCategoryId(id: Int): List<SubCategory>

    @Transaction
    @Query("DELETE FROM category")
    fun nukeCategoryTable()

    @Transaction
    @Query("DELETE FROM subcategory")
    fun nukeSubCategoryTable()


}