package com.example.lafo_cheuse.fragment.view

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.lafo_cheuse.MainActivity
import com.example.lafo_cheuse.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.DelicateCoroutinesApi

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment() : Fragment() {
    var chartView : PieChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.slide_in_left)
        enterTransition = inflater.inflateTransition(R.transition.slide_in_right)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_home, container, false)
        val chartButton : FloatingActionButton = view.findViewById(R.id.floatingActionButton)
        chartButton.setOnClickListener { v ->
            displayChart(v)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)
        chartView = view.findViewById<PieChart>(R.id.piechart)
        chartView?.setTouchEnabled(false);

        val entries = ArrayList<PieEntry>()

        for (i in 0..3) {
            entries.add(PieEntry((Math.random() * 70 + 30).toFloat(), "\uD83D\uDED2 Quarter " + (i + 1)))
        }
        val dataSet = PieDataSet(entries, "")

        dataSet.colors = ColorTemplate.PASTEL_COLORS.toList()
        dataSet.valueTextColor = Color.WHITE
        dataSet.valueTextSize = 12f

        val data = PieData(dataSet)

        chartView!!.data = data
        val description = Description()
        description.textColor = Color.RED
        description.text = "Bootleg graph"

        chartView!!.legend.isEnabled = false
        chartView!!.description = description
        chartView!!.animateX(2000)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun displayChart(view : View) {
        (this.activity as MainActivity).navigationView!!.selectedItemId = R.id.chartFragment
    }

}