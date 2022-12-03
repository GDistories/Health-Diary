package com.healthdiary.ui.activity

import android.R
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityDashboardBinding
import kotlinx.android.synthetic.main.activity_dashboard.*


class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        set_Data_to_Lines();

        //setDataToLineChart();
        sleep_lineChart2
        wrokout_lineChart1

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun set_lineChart() {
        with(sleep_lineChart1){
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
        }
    }


//    private fun setUpLineChart() {
//        with(lineChart1) {
//            animateX(1200, Easing.EaseInSine)
//            description.isEnabled = false
//
//            xAxis.setDrawGridLines(false)
//            xAxis.position = XAxis.XAxisPosition.BOTTOM
//            xAxis.granularity = 1F
//            //xAxis.valueFormatter = MyAxisFormatter()
//
//            axisRight.isEnabled = false
//            extraRightOffset = 30f
//
//            legend.orientation = Legend.LegendOrientation.VERTICAL
//            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//            legend.textSize = 15F
//            legend.form = Legend.LegendForm.LINE
//        }
//    }


    class MyAxisFormatter : IndexAxisValueFormatter() {

        private var items = arrayListOf("Milk", "Butter", "Cheese", "Ice cream", "Milkshake")

        override fun getAxisLabel(value: Float, axis: AxisBase?): String? {
            val index = value.toInt()
            return if (index < items.size) {
                items[index]
            } else {
                null
            }
        }
    }

    fun sleep_data_1(): ArrayList<Entry>{
        val sleep = ArrayList<Entry>()
        // float from 0.0 to 2.0
        sleep.add(Entry(0f, 1.1f))
        sleep.add(Entry(1f, 1.3f))
        sleep.add(Entry(2f, 1.8f))
        sleep.add(Entry(3f, 1.6f))
        sleep.add(Entry(4f, 1.5f))
        return sleep
    }

    fun sleep_data_2(): ArrayList<Entry>{
        val sleep = ArrayList<Entry>()
        // float from 0.0 to 2.0
        sleep.add(Entry(0f, 1.1f))
        sleep.add(Entry(1f, 1.3f))
        sleep.add(Entry(2f, 1.8f))
        sleep.add(Entry(3f, 1.6f))
        sleep.add(Entry(4f, 1.5f))
        return sleep
    }

    fun workout_data_1(): ArrayList<Entry>{
        val workout = ArrayList<Entry>()
        // float from 0.0 to 2.0
        workout.add(Entry(0f, 0f))
        workout.add(Entry(1f, 0.25f))
        workout.add(Entry(2f, 1.75f))
        workout.add(Entry(3f, 0.35f))
        workout.add(Entry(4f, 1.5f))
        workout.add(Entry(4f, 1.0f))
        return workout
    }


//    fun set_sleep_chart(){
//        sleep_lineChart1
//    }

    fun set_Data_to_Lines(){
        val weekSleepData = LineDataSet(sleep_data_1(), "Week 1")
        val dataSet = ArrayList<ILineDataSet>()
        dataSet.add(weekSleepData)
        val lineData = LineData(dataSet)
        sleep_lineChart1.data = lineData

        val weekSleepData2 = LineDataSet(sleep_data_2(), "Week 2")
        val dataSet2 = ArrayList<ILineDataSet>()
        dataSet2.add(weekSleepData)
        val lineData2 = LineData(dataSet)
        sleep_lineChart2.data = lineData2

        val weekWorkoutData = LineDataSet(workout_data_1(), "Workout")
        val dataSet3 = ArrayList<ILineDataSet>()
        dataSet3.add(weekSleepData)
        val lineData3 = LineData(dataSet)
        wrokout_lineChart1.data = lineData3


        //sleep_lineChart1.invalidate();
    }

    fun week2(): ArrayList<Entry> {
        val sales = ArrayList<Entry>()
        sales.add(Entry(0f, 11f))
        sales.add(Entry(1f, 13f))
        sales.add(Entry(2f, 18f))
        sales.add(Entry(3f, 16f))
        sales.add(Entry(4f, 22f))
        return sales
    }


//    fun setDataToLineChart() {
//
//        val weekOneSales = LineDataSet(week1(), "Week 1")
//        weekOneSales.lineWidth = 3f
//        weekOneSales.valueTextSize = 15f
//        weekOneSales.mode = LineDataSet.Mode.CUBIC_BEZIER
//        weekOneSales.color = ContextCompat.getColor(this, R.color.holo_red_dark)
//        weekOneSales.valueTextColor = ContextCompat.getColor(this, R.color.holo_red_dark)
//        weekOneSales.enableDashedLine(20F, 10F, 0F)
//
//        val weekTwoSales = LineDataSet(week2(), "Week 2")
//        weekTwoSales.lineWidth = 3f
//        weekTwoSales.valueTextSize = 15f
//        weekTwoSales.mode = LineDataSet.Mode.CUBIC_BEZIER
//        weekTwoSales.color = ContextCompat.getColor(this, R.color.holo_red_dark)
//        weekTwoSales.valueTextColor = ContextCompat.getColor(this, R.color.holo_red_dark)
//        weekTwoSales.enableDashedLine(20F, 10F, 0F)
//
//
//        val dataSet = ArrayList<ILineDataSet>()
//        dataSet.add(weekOneSales)
//        dataSet.add(weekTwoSales)
//
//        val lineData = LineData(dataSet)
//        lineChart1.data = lineData
//
//        lineChart1.invalidate()
//    }










}


