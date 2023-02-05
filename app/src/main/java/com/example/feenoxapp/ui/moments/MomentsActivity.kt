package com.example.feenoxapp.ui.moments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.feenoxapp.R
import com.example.feenoxapp.adapter.MomentsAdapter
import com.example.feenoxapp.utils.Constant.Companion.helper
import kotlinx.android.synthetic.main.activity_moments.*

class MomentsActivity : AppCompatActivity() {

    private lateinit var adapter: MomentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moments)

        supportActionBar?.title = "Moments"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = MomentsAdapter(helper.getMoments())
        if(adapter.itemCount>0) {
            rv_list.layoutManager = LinearLayoutManager(this@MomentsActivity)
            rv_list.visibility = View.VISIBLE
            rv_list.adapter = adapter
            tv_list.visibility = View.GONE
        } else{
            rv_list.visibility = View.GONE
            tv_list.visibility = View.VISIBLE
            tv_list.text = getString(R.string.empty_list)
        }
    }
}