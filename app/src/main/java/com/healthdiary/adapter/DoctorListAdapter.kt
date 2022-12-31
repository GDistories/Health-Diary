package com.healthdiary.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.healthdiary.R
import com.healthdiary.codepalace.chatbot.ui.ChatActivity
import com.healthdiary.data.DoctorList
import com.healthdiary.ui.activity.DoctorInfoActivity

private var context: Context? = null

class DoctorListAdapter(
    var locations: ArrayList<DoctorList>
):
    RecyclerView.Adapter<DoctorListAdapter.DoctorListViewHolder>()  //interface to implement
{

    constructor(contexts: Context, locations: ArrayList<DoctorList>) : this(locations) {
        context = contexts
    }

    // Constructor for the viewholder
    // second called
    class DoctorListViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)//interface to implement
    {
        val doctor_name = itemView.findViewById<TextView>(R.id.doctor_name)
        val doctor_symptons = itemView.findViewById<TextView>(R.id.doctor_symptons)
        val doctor_img = itemView.findViewById<ImageView>(R.id.doctor_img)
        val doctor_personal_info = itemView.findViewById<Button>(R.id.doctor_personal_info_btn)
        val doctor_chat = itemView.findViewById<CardView>(R.id.doctor_chat)
//        init {
//            itemView.setOnClickListener(this)
//        }
//
//        override fun onClick(v: View?) {
//            val position=adapterPosition
//            if(position!=RecyclerView.NO_POSITION){
//                listener.onItemClick(position)
//            }
//        }

        fun bind(location: DoctorList) {
            doctor_name.text = location.doctorName
            doctor_symptons.text = location.doctorSymptons
            doctor_img.setImageResource(location.doctorimg)
        }

    }

//    interface OnItemClickListener{
//        fun onItemClick(position: Int)
//    }

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
        holder.doctor_personal_info.setOnClickListener {
            val intent = Intent(holder.itemView.context, DoctorInfoActivity::class.java)
            startActivity(context!!, intent, null)
        }
        holder.doctor_chat.setOnClickListener {
            val intent = Intent(holder.itemView.context, ChatActivity::class.java)
            startActivity(context!!, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return locations.size
    }
}
