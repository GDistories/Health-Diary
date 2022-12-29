package com.healthdiary.ui.activity

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.healthdiary.R
import com.healthdiary.adapter.DoctorListAdapter
import com.healthdiary.base.BaseActivity
import com.healthdiary.data.DoctorList
import com.healthdiary.databinding.ActivityDoctorListBinding

class DoctorListActivity : BaseActivity() {
    private lateinit var binding:ActivityDoctorListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDoctorListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }

        val locations: ArrayList<DoctorList> = arrayListOf(
            DoctorList(
                "Dr. Rodger Struck",
                "Heart Surgeon, London, England",
                R.drawable.doctor_img1
            ),
            DoctorList(
                "Dr. Kathy Pacheco",
                "Heart Surgeon, London, England",
                R.drawable.doctor_img2
            ),
            DoctorList(
                "Dr. Lorri Warf",
                "General Dentist",
                R.drawable.doctor_img3
            ),
            DoctorList(
                "Dr. Chris Glasser",
                "Heart Surgeon, London, England",
                R.drawable.doctor_img4
            ),
            DoctorList(
                "Dr. Maria Waston",
                "Heart Surgeon, London Bridge Hospital",
                R.drawable.doctor_img5
            )
        )

        var listDoctor = ArrayList<DoctorList>()
        listDoctor.addAll(locations)
        val recyclerView = findViewById<RecyclerView>(R.id.doctor_list_recylerview)
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.adapter = DoctorListAdapter(listDoctor)
        // Adapt the data to the recyclerview using Adapter
        recyclerView.adapter = DoctorListAdapter(locations)
        binding.searchDoctors.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                listDoctor = ArrayList<DoctorList>()
                for (doctor in locations) {
                    if (doctor.doctorName.contains(newText, true)) {
                        listDoctor.add(doctor)
                    }
                }
                recyclerView.adapter = DoctorListAdapter(listDoctor)
                return false
            }
        })
    }

//    override fun onItemClick(position: Int) {
//        val intent = Intent(this,DoctorInfoActivity::class.java)
//        startActivity(intent)
//    }

//    private fun onListItemClick(position: Int) {
//        startActivity(Intent(this, DoctorInfoActivity::class.java))
//    }
}