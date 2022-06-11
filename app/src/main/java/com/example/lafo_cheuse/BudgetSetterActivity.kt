package com.example.lafo_cheuse

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lafo_cheuse.material.ExpenseSetterAdapter
import com.example.lafo_cheuse.material.IncomeSetterAdapter
import com.example.lafo_cheuse.viewmodels.CategoryViewModel
import com.example.lafo_cheuse.viewmodels.ExpenseViewModel
import com.example.lafo_cheuse.viewmodels.IncomeViewModel


class BudgetSetterActivity : AppCompatActivity() {
    private var incomeAdapter: IncomeSetterAdapter? = null
    private var expenseAdapter: ExpenseSetterAdapter? = null
    private val incomeViewModel: IncomeViewModel by viewModels()
    private val expenseViewModel: ExpenseViewModel by viewModels()
    private val categoryViewModel: CategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_setter)

        val budgetIncomeSum : TextView = findViewById(R.id.total_regular_incomes)
        val budgetExpenseSum : TextView = findViewById(R.id.total_regular_expenses)



        initializeRecyclerViewIncome()
        initializeRecyclerViewExpense()

        categoryViewModel.getDefaultCategory()?.observe(this) { list ->
            incomeAdapter!!.setDefCategory(list[0])
            expenseAdapter!!.setDefCategory(list[0])
        }

        incomeViewModel.getIncomeSum().observe(this) { sumIncome ->
            budgetIncomeSum.text = if(sumIncome == null) {
                resources.getString(R.string.budget_total_empty)
            } else {
                resources.getString(R.string.budget_total_full,sumIncome)
            }
        }

        expenseViewModel.getMonthlyExpensesSum().observe(this) { sumExpenses ->
            budgetExpenseSum.text = if(sumExpenses == null) {
                resources.getString(R.string.budget_total_empty)
            } else {
                resources.getString(R.string.budget_total_full,-sumExpenses)
            }
        }

    }

    private fun initializeRecyclerViewIncome() {
        val recyclerViewIncome: RecyclerView = findViewById(R.id.income_list_recycler_view)
        recyclerViewIncome.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewIncome.setHasFixedSize(true)

        incomeAdapter = IncomeSetterAdapter(this, incomeViewModel)
        recyclerViewIncome.adapter = incomeAdapter

        incomeViewModel.getMonthlyIncome().observe(this) { list ->
            incomeAdapter!!.setIncomes(list)
        }
    }

    private fun initializeRecyclerViewExpense() {
        val recyclerViewExpense: RecyclerView = findViewById(R.id.expense_list_recycler_view)
        recyclerViewExpense.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewExpense.setHasFixedSize(true)

        expenseAdapter = ExpenseSetterAdapter(this, expenseViewModel, incomeViewModel)
        recyclerViewExpense.adapter = expenseAdapter

        expenseViewModel.getMonthlyExpense().observe(this) { list ->
            expenseAdapter!!.setExpenses(list)
        }
    }
}