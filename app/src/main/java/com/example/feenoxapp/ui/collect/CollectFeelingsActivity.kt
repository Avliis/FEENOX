package com.example.feenoxapp.ui.collect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.feenoxapp.R
import com.example.feenoxapp.ui.MainActivity
import com.example.feenoxapp.ui.moments.MomentsActivity
import com.example.feenoxapp.utils.Constant.Companion.helper
import com.example.foodapp.utils.showLog
import com.example.foodapp.utils.showToast
import com.example.foodapp.utils.startActivity
import kotlinx.android.synthetic.main.activity_collect_feelings.*
import kotlinx.android.synthetic.main.custom_item_layout.*
import java.text.SimpleDateFormat
import java.util.*

class CollectFeelingsActivity : AppCompatActivity() {

    private val pickImage = 100
    private var imageUri: Uri? = null

    private lateinit var mId : String
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collect_feelings)

        supportActionBar?.title = "Collect Feelings"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val id = intent.getStringExtra("id")
        if(id!=null){
            imageUri = Uri.parse(intent.getStringExtra("imgUri"))
            iv_collectImg.setImageURI(imageUri)
            et_description.setText(intent.getStringExtra("description"))
            mId = id
            isUpdate = true

            supportActionBar?.title = "Update Feelings"
            btn_saveMoments.text = getString(R.string.update_now)
        }

        iv_collectImg.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        btn_saveMoments.setOnClickListener {
            collectMoments()
        }
    }

    private fun collectMoments() {
        val description = et_description.text.toString().trim()
        if(isUriEmpty(imageUri))
            showToast("Please select Image first")
        else if(description.isEmpty())
            showToast("Enter description first")
        else {
            val date: String = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
            if(isUpdate) {
                helper.deleteMoment(mId)
                helper.saveMoments(imageUri.toString(), description, date)
                showToast("Moment successfully updated")
                startActivity<MomentsActivity>(finish = true)
            } else {
                helper.saveMoments(imageUri.toString(), description, date)
                showToast("Moment successfully saved")
                startActivity<MainActivity>(finish = true)
            }
        }
    }

    private fun isUriEmpty(uri: Uri?):Boolean{
        return uri == null || uri == Uri.EMPTY
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            iv_collectImg.setImageURI(imageUri)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        if(isUpdate)
            startActivity<MomentsActivity>(finish = true)
        else
            startActivity<MainActivity>(finish = true)
        return true
    }
}