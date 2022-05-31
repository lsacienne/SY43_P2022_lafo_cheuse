package net.yolopix.moneyz

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import net.yolopix.moneyz.model.entities.Category

class CategoryAdapter(private val categoryList : List<Category>)
    : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>()

{
    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryNameTextView: TextView = itemView.findViewById(R.id.categoryName)
        val categoryPriceTextView: TextView = itemView.findViewById(R.id.categoryPrice)
        val linearLayoutCategory: LinearLayout = itemView.findViewById(R.id.linearLayoutContainerCategory)
    }

    override fun onBindViewHolder(viewHolder: CategoryAdapter.CategoryViewHolder, position: Int) {
        viewHolder.categoryNameTextView.text  = categoryList[position].name
        viewHolder.categoryPriceTextView.text = categoryList[position].predictedAmount.toString()

    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view:View = inflater.inflate(R.layout.item_category_prevision, parent, false)
        return CategoryAdapter.CategoryViewHolder(view)
    }


    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountAdapter.AccountViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_account, parent, false)
        return AccountAdapter.AccountViewHolder(view)
    }*/
}