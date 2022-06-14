package net.yolopix.moneyz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import net.yolopix.moneyz.model.AppDatabase
import net.yolopix.moneyz.model.entities.Category

/**
 * Adapter for the category recyclerview
 * @param categoryList List of categories to display
 * @param context Activity context
 * @param expenseMode If true, display only categories with expenses and nest them under each item. If false, display all categories in prevision mode.
 * @param db The database to let each category build its list of expenses
 */
class CategoryAdapter(
    private val categoryList: List<Category>,
    private val context: AppCompatActivity,
    private val expenseMode: Boolean,
    private val db: AppDatabase,
    private val monthNumber: Int? = null,
    private val yearNumber: Int? = null,

    ) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    /**
     * A nested class for the view holder
     */
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryNameTextView: TextView = itemView.findViewById(R.id.categoryName)
        val categoryPriceTextView: TextView = itemView.findViewById(R.id.categoryPrice)
        val expandButton: View = itemView.findViewById(R.id.button_expand)
        val expensesRecyclerView: RecyclerView = itemView.findViewById(R.id.recyclerview_expenses)
        val categoryHeaderLayout: View = itemView.findViewById(R.id.layout_category_header)
    }

    override fun onBindViewHolder(viewHolder: CategoryViewHolder, position: Int) {
        // For expense view
        if (expenseMode) {
            viewHolder.categoryPriceTextView.visibility = View.GONE
            viewHolder.expensesRecyclerView.layoutManager = LinearLayoutManager(context)
            context.lifecycleScope.launch {
                viewHolder.expensesRecyclerView.adapter = ExpensesAdapter(
                    db.expenseDao().getExpenseForCategory(categoryList[position].uid),
                    monthNumber!!, yearNumber!!,db, context
                )
            }
            // Expand/collapse the expenses nested under the category
            viewHolder.categoryHeaderLayout.setOnClickListener {
                if (viewHolder.expensesRecyclerView.visibility == View.GONE) {
                    viewHolder.expensesRecyclerView.visibility = View.VISIBLE
                    viewHolder.expandButton.rotation = 180f
                } else {
                    viewHolder.expensesRecyclerView.visibility = View.GONE
                    viewHolder.expandButton.rotation = 0f
                }
            }
        } else { // For prevision view
            viewHolder.expandButton.visibility = View.GONE
            viewHolder.expensesRecyclerView.visibility = View.GONE
            viewHolder.categoryPriceTextView.text = context.getString(
                R.string.money_format,
                String.format("%.2f", categoryList[position].predictedAmount)
            )
        }
        // Common to both prevision/expenses view
        viewHolder.categoryNameTextView.text = categoryList[position].name
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

}
