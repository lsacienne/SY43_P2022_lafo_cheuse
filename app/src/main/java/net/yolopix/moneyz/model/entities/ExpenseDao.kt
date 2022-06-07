package net.yolopix.moneyz.model.entities

import androidx.room.*

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM Expense where categoryUid == :categoryUid ")
    suspend fun getExpenseForCategory(categoryUid: Int): List<Expense>

    @Insert
    suspend fun insertExpense(expense: Expense)
}