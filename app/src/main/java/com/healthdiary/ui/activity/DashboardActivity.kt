package com.healthdiary.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.ToastUtils
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.data.CheckInRecord
import com.healthdiary.databinding.ActivityDashboardBinding
import com.healthdiary.repository.CheckInRecordRepository
import com.healthdiary.viewmodel.CheckInRecordViewModel
import kotlinx.android.synthetic.main.activity_dashboard.*
import org.checkerframework.checker.units.qual.Temperature
import org.jzvd.jzvideo.TAG


class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding

    private val recordViewModel: CheckInRecordViewModel by viewModels {
        CheckInRecordViewModel.Provider(CheckInRecordRepository.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isLogin()) {
            ToastUtils.showShort(getString(R.string.please_login))
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }



        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        set_lineCharts()
        set_Data_to_Lines()


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


        with(temperature_lineChart){
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            setDrawGridBackground(false)
            description.isEnabled = false
            animateX(500)
        }

        with(heartRate_lineChart){
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            setDrawGridBackground(false)
            description.isEnabled = false
            animateX(500)
        }

    }

    fun sleep_data_1(): ArrayList<Entry>{
        val sleep = ArrayList<Entry>()
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

    fun drawTemperatureLineChart(list: ArrayList<Entry>){
        val weekTemperatureData = LineDataSet(list, "temperature")
        val dataSet3 = ArrayList<ILineDataSet>()
        weekTemperatureData.lineWidth = 2f
        weekTemperatureData.color = Color.GRAY
        weekTemperatureData.setDrawValues(false)
        weekTemperatureData.mode = LineDataSet.Mode.CUBIC_BEZIER
        weekTemperatureData.cubicIntensity = 0.2f
        weekTemperatureData.setDrawFilled(true)
        weekTemperatureData.fillColor= Color.RED
        weekTemperatureData.fillAlpha = 70
        val legend3: Legend = temperature_lineChart.legend
        legend3.isEnabled = false
        weekTemperatureData.setDrawCircles(true)
        dataSet3.add(weekTemperatureData)
        val lineData3 = LineData(dataSet3)
        temperature_lineChart.data = lineData3
    }

    fun drawHeartRateLineChart(list: ArrayList<Entry>){
        val weekHeartRateData = LineDataSet(list, "heartRate")
        val dataSet4 = ArrayList<ILineDataSet>()
        weekHeartRateData.lineWidth = 2f
        weekHeartRateData.color = Color.GRAY
        weekHeartRateData.setDrawValues(false)
        weekHeartRateData.mode = LineDataSet.Mode.CUBIC_BEZIER
        weekHeartRateData.cubicIntensity = 0.2f
        weekHeartRateData.setDrawFilled(true)
        weekHeartRateData.fillColor= Color.BLUE
        weekHeartRateData.fillAlpha = 70
        val legend4: Legend = heartRate_lineChart.legend
        legend4.isEnabled = false
        weekHeartRateData.setDrawCircles(true)
        dataSet4.add(weekHeartRateData)
        val lineData4 = LineData(dataSet4)
        heartRate_lineChart.data = lineData4
    }


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
        weekSleepData2.mode = LineDataSet.Mode.CUBIC_BEZIER
        weekSleepData2.cubicIntensity = 0.2f
        weekSleepData2.setDrawFilled(true)
        weekSleepData2.fillColor= Color.RED
        weekSleepData2.fillAlpha = 70
        val legend2: Legend = sleep_lineChart2.legend
        legend2.isEnabled = false
        weekSleepData2.setDrawCircles(false)
        dataSet2.add(weekSleepData2)
        val lineData2 = LineData(dataSet2)
        sleep_lineChart2.data = lineData2



        var temperatureData = ArrayList<Entry>()
        var heartRateData = ArrayList<Entry>()
        var xValue1 = 0.0f
        var xValue2 = 0.0f
        recordViewModel.checkAllRecord(getUserEmail().toString()).observe(this){
            var checkId = it

            if(checkId != "NoCheckInResult"){
                recordViewModel.getRecord(checkId).observe(this){ it1 ->
                    var record = it1
                    if(record.temperature.toString().length>=3){
                        var temp = record.temperature.toString().substring(0,2).toFloat()
                        xValue1 += 1.0f
                        temperatureData.add(Entry(xValue1, temp))
                    }
                    if(record.heartRate.toString().length>=6){
                        var heart = record.heartRate.toString().substring(0,3).toFloat()
                        xValue2 += 1.0f
                        heartRateData.add(Entry(xValue2, heart))
                    }

                    if(xValue1 >= 5.0f)
                    {
                        binding.temperatureLineChart.visibility = View.VISIBLE
                        binding.temperatureRemind.visibility = View.GONE
                        drawTemperatureLineChart(temperatureData)
                    }
                    else
                    {
                        binding.temperatureLineChart.visibility = View.GONE
                        binding.temperatureRemind.visibility = View.VISIBLE
                    }

                    if(xValue2 >= 5.0f)
                    {
                        binding.heartRateLineChart.visibility = View.VISIBLE
                        binding.heartRateRemind.visibility = View.GONE
                        drawHeartRateLineChart(heartRateData)
                    }
                    else
                    {
                        binding.heartRateLineChart.visibility = View.GONE
                        binding.heartRateRemind.visibility = View.VISIBLE
                    }

                }
            }
            else{
                binding.temperatureLineChart.visibility = View.GONE
                binding.temperatureRemind.visibility = View.VISIBLE
                binding.heartRateLineChart.visibility = View.GONE
                binding.heartRateRemind.visibility = View.VISIBLE
            }

        }




    }


}


