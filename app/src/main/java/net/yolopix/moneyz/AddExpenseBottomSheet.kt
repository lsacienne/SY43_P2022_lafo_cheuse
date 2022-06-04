package net.yolopix.moneyz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import net.yolopix.moneyz.model.AppDatabase

class AddExpenseBottomSheet(private val db: AppDatabase, private val accountUid: Int) :
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_add_expense, container, false)
    }

    private lateinit var editTextExpenseName: EditText

    // When the bottom sheet is created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val buttonAdd =
            view.findViewById<com.google.android.material.button.MaterialButton>(R.id.button_add)
        editTextExpenseName = view.findViewById(R.id.edit_text_expense_name)

        // Add click listener to the add button
        buttonAdd.setOnClickListener {
            addExpense()
        }

        // When the done button is pressed on the soft keyboard
        editTextExpenseName.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                addExpense()
            true
        }

        // Date picker
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.expense_date)
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        // Category selection spinner
        val spinner: Spinner = view.findViewById(R.id.spinner_category)
        //val adapter = ArrayAdapter.createFromResource(requireContext(), android.R.string ,android.R.layout.item_category)
        lifecycleScope.launch {
            spinner.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                db.categoryDao().getAll()
            )
        }


    }

    private fun addExpense() {
        // Fetch the account name in the EditText and add it to the database
        val expenseName = editTextExpenseName.text.toString()

        dismiss()
    }

}