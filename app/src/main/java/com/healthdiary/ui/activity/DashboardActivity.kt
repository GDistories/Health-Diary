package com.healthdiary.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
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

        set_lineCharts()
        set_Data_to_Lines()

        //setDataToLineChart();
//        sleep_lineChart2
//        workout_lineChart1

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnGoCommunity.setOnClickListener {
            startActivity(Intent(this, DoctorListActivity::class.java))
        }
    }

    private fun set_lineCharts() {
        with(sleep_lineChart1){
            description.isEnabled = false
            setDrawGridBackground(false)
            setBorderWidth(2.0f)
            axisLeft.isEnabled = false
            axisLeft.spaceTop = 40f
            axisLeft.spaceBottom = 40f
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            //setBackgroundColor(Color.rgb(137, 230, 81));
            animateX(500)

        }

        with(sleep_lineChart2){
            description.isEnabled = false
            setDrawGridBackground(false)
            axisLeft.isEnabled = false
            axisLeft.spaceTop = 40f
            axisLeft.spaceBottom = 40f
            axisRight.isEnabled = false
            xAxis.isEnabled = false

            animateX(500)
        }

        with(workout_lineChart1){
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            setDrawGridBackground(false)

            description.isEnabled = false
            axisRight.isEnabled = false
            animateX(500)
        }

    }

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
        sleep.add(Entry(1f, 1.2f))
        sleep.add(Entry(2f, 1.3f))
        sleep.add(Entry(3f, 1.4f))
        sleep.add(Entry(4f, 1.5f))
        sleep.add(Entry(5f, 1.6f))
        sleep.add(Entry(6f, 1.8f))
        sleep.add(Entry(7f, 1.9f))
        sleep.add(Entry(8f, 1.8f))
        sleep.add(Entry(9f, 1.7f))
        sleep.add(Entry(10f, 1.6f))
        sleep.add(Entry(11f, 1.4f))
        sleep.add(Entry(12f, 1.3f))
        sleep.add(Entry(13f, 1.4f))
        sleep.add(Entry(14f, 1.5f))
        sleep.add(Entry(15f, 1.4f))
        return sleep
    }

    fun sleep_data_2(): ArrayList<Entry>{
        val sleep = ArrayList<Entry>()
        // float from 0.0 to 2.0
        sleep.add(Entry(0f, 1.6f))
        sleep.add(Entry(1f, 1.4f))
        sleep.add(Entry(2f, 1.3f))
        sleep.add(Entry(3f, 1.5f))
        sleep.add(Entry(4f, 1.4f))
        sleep.add(Entry(5f, 1.2f))
        sleep.add(Entry(6f, 1.1f))
        sleep.add(Entry(7f, 1.2f))
        sleep.add(Entry(8f, 1.3f))
        sleep.add(Entry(9f, 1.4f))
        sleep.add(Entry(10f, 1.5f))
        sleep.add(Entry(11f, 1.6f))
        sleep.add(Entry(12f, 1.4f))
        sleep.add(Entry(13f, 1.3f))
        sleep.add(Entry(14f, 1.2f))
        sleep.add(Entry(15f, 1.2f))
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
        workout.add(Entry(5f, 1.0f))
        return workout
    }


//    fun set_sleep_chart(){
//        sleep_lineChart1
//    }

    fun set_Data_to_Lines(){
        val weekSleepData = LineDataSet(sleep_data_1(), "Sleep 1")
        val dataSet = ArrayList<ILineDataSet>()
        weekSleepData.lineWidth = 2f
        weekSleepData.color = Color.GRAY
        weekSleepData.setDrawValues(false)
        //to make the smooth line
        weekSleepData.mode = LineDataSet.Mode.CUBIC_BEZIER
        //to enable the cubic density : if 1 then it will be sharp curve
        weekSleepData.cubicIntensity = 0.2f
        //to fill the below of smooth line in graph
        weekSleepData.setDrawFilled(true)
        weekSleepData.fillColor= Color.BLUE
        //set the transparency
        weekSleepData.fillAlpha = 70
        //set legend disable or enable to hide {the left down corner name of graph}
        val legend: Legend = sleep_lineChart1.legend
        legend.isEnabled = false
        //to remove the circle from the graph
        weekSleepData.setDrawCircles(false)
        //Display
        dataSet.add(weekSleepData)
        val lineData = LineData(dataSet)
        sleep_lineChart1.data = lineData



        val weekSleepData2 = LineDataSet(sleep_data_2(), "Sleep 2")
        val dataSet2 = ArrayList<ILineDataSet>()
        weekSleepData2.lineWidth = 2f
        weekSleepData2.color = Color.GRAY
        weekSleepData2.setDrawValues(false)
        //to make the smooth line
        weekSleepData2.mode = LineDataSet.Mode.CUBIC_BEZIER
        //to enable the cubic density : if 1 then it will be sharp curve
        weekSleepData2.cubicIntensity = 0.2f
        //to fill the below of smooth line in graph
        weekSleepData2.setDrawFilled(true)
        weekSleepData2.fillColor= Color.RED
        //set the transparency
        weekSleepData2.fillAlpha = 70
        //set legend disable or enable to hide {the left down corner name of graph}
        val legend2: Legend = sleep_lineChart2.legend
        legend2.isEnabled = false
        //to remove the circle from the graph
        weekSleepData2.setDrawCircles(false)
        //Display
        dataSet2.add(weekSleepData2)
        val lineData2 = LineData(dataSet2)
        sleep_lineChart2.data = lineData2



        val weekWorkoutData3 = LineDataSet(workout_data_1(), "Workout")
        val dataSet3 = ArrayList<ILineDataSet>()
        weekWorkoutData3.lineWidth = 2f
        weekWorkoutData3.color = Color.GRAY
        weekWorkoutData3.setDrawValues(false)
        //to make the smooth line
        weekWorkoutData3.mode = LineDataSet.Mode.CUBIC_BEZIER
        //to enable the cubic density : if 1 then it will be sharp curve
        weekWorkoutData3.cubicIntensity = 0.2f
        //to fill the below of smooth line in graph
        weekWorkoutData3.setDrawFilled(true)
        weekWorkoutData3.fillColor= Color.RED
        //set the transparency
        weekWorkoutData3.fillAlpha = 70
        //set legend disable or enable to hide {the left down corner name of graph}
        val legend3: Legend = workout_lineChart1.legend
        legend3.isEnabled = false
        //to remove the circle from the graph
        weekWorkoutData3.setDrawCircles(false)
        //Display
        dataSet3.add(weekWorkoutData3)
        val lineData3 = LineData(dataSet3)
        workout_lineChart1.data = lineData3

    }


}


