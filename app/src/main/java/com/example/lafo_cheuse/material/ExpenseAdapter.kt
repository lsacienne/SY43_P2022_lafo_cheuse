package com.example.lafo_cheuse.material

import android.app.Activity
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lafo_cheuse.R
import com.example.lafo_cheuse.models.Expense
import java.util.*
import kotlin.math.exp

/**
 * Adapter that handle the RecyclerView in charge of expenses
 *
 * @property context - the [Activity] in which the RecyclerView is used
 * @property itemClickListener - an [EventListener] to check if the delete button is clicked
 * @property resources - a parameter to access the resources
 */
class ExpenseAdapter (
    var context : Activity,
    private val itemClickListener: DeleteButtonClickListener,
    val resources: Resources
    ) : RecyclerView.Adapter<ExpenseAdapter.ViewHolder>() {
    private var mExpense: List<Expense> = ArrayList<Expense>()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ieName: TextView = itemView.findViewById(R.id.ie_name)
        val categoryEmojiButton: Button = itemView.findViewById(R.id.emojiButton)
        val ieValue: TextView = itemView.findViewById(R.id.ie_value)
        val ieDate: TextView = itemView.findViewById(R.id.ie_date)
        val deleteButton: FloatingActionButton = itemView.findViewById(R.id.deleteButton2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val incomeExpenseItemView = inflater.inflate(R.layout.income_expense_item, parent, false)
        return ViewHolder(incomeExpenseItemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val expense: Expense = mExpense[position]
        holder.ieName.text = expense.name
        holder.categoryEmojiButton.text = expense.category?.categoryEmoji
        holder.ieValue.text = resources.getString(R.string.sum,-expense.amount)
        holder.ieDate.text = resources.getString(R.string.date,expense.dateDay,expense.dateMonth,expense.dateYear)

        holder.deleteButton.setOnClickListener {
            itemClickListener.onDeleteButtonClick(position)
            notifyDataSetChanged()
        }
    }

    /**
     * Function that calls the onDeleteButtonClick function in case of a click on the delete button
     */
    interface DeleteButtonClickListener {
        fun onDeleteButtonClick(position: Int)
    }

    /**
     * Function that counts the number of expenses in the DB
     *
     * @return the size of the Expense list
     */
    override fun getItemCount(): Int {
        return mExpense.size
    }

    /**
     * Function that changes an expense in the DB
     */
    fun setExpenses(mExpenses: List<Expense>) {
        this.mExpense = mExpenses
        notifyDataSetChanged()
    }

    /**
     * Function that gets an expense in the DB
     */
    fun getItemAt(position: Int): Expense = mExpense[position]
}