package com.healthdiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.R
import com.healthdiary.data.DoctorList

class DoctorListAdapter(var locations: ArrayList<DoctorList>):
    RecyclerView.Adapter<DoctorListAdapter.DoctorListViewHolder>()  //interface to implement
{
    // Constructor for the viewholder
    // second called
    class DoctorListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) //interface to implement
    {
        val doctor_name = itemView.findViewById<TextView>(R.id.doctor_name)
        val doctor_symptons = itemView.findViewById<TextView>(R.id.doctor_symptons)
        val doctor_img = itemView.findViewById<ImageView>(R.id.doctor_img)

        fun bind(location: DoctorList) {
            doctor_name.text = location.doctorName
            doctor_symptons.text = location.doctorSymptons
            doctor_img.setImageResource(location.doctorimg)

        }

    }

    // First function called
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.doctor_item_layout, parent, false)
        /*
        when you put attachToRoot
        true : add the child view to parent RIGHT NOW
        false: add the child view to parent NOT NOW.
         */

        return DoctorListViewHolder(view)
    }

    // Third called
    override fun onBindViewHolder(holder: DoctorListViewHolder, position: Int) {
        holder.bind(locations[position])
    }

    override fun getItemCount(): Int {
        return locations.size
    }
}
