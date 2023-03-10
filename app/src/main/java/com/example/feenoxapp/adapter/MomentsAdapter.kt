package com.example.feenoxapp.adapter

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.feenoxapp.R
import com.example.feenoxapp.model.Moment
import com.example.feenoxapp.ui.collect.CollectFeelingsActivity

class MomentsAdapter(private var list : ArrayList<Moment>)
    : RecyclerView.Adapter<MomentsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_item_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pos = list[position]

        holder.apply {
            val cxt = this.view.context

            val id = pos.id
            val imgUri = pos.ImgUri
            val description = pos.description
            val date = pos.date

            ivImg.setImageURI(Uri.parse(imgUri))
            tvDescription.text = description
            tvDate.text = date

            view.setOnClickListener {
                val intent = Intent(cxt,CollectFeelingsActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("imgUri", imgUri)
                intent.putExtra("description", description)
                intent.putExtra("date", date)
                cxt.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvDescription : TextView = itemView.findViewById(R.id.tv_description)
        val tvDate : TextView = itemView.findViewById(R.id.tv_date)
        val ivImg : ImageView = itemView.findViewById(R.id.iv_img)
        val view = itemView
    }
}