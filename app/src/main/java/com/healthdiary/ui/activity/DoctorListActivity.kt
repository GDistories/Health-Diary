package com.healthdiary.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityDoctorListBinding
import com.healthdiary.ui.fragment.DoctorList
import com.healthdiary.ui.fragment.DoctorListAdapter
import kotlinx.android.synthetic.main.doctor_item_layout.view.*

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


        var locations: ArrayList<DoctorList> = arrayListOf(
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

//        locations.add(
//            DoctorList(
//                "Airport",
//                "07/05/2022",
//                R.drawable.ic_home
//            )
//        )


        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.doctor_list_recylerview)

        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )


        // Adapt the data to the recyclerview using Adapter
        recyclerView.adapter = DoctorListAdapter(locations)


    }

    private fun onListItemClick(position: Int) {
        startActivity(Intent(this, DoctorInfoActivity::class.java))
    }
}