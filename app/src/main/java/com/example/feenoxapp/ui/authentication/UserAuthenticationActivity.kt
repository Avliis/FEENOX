package com.example.feenoxapp.ui.authentication

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.feenoxapp.R
import com.example.feenoxapp.database.DatabaseHelper
import com.example.feenoxapp.ui.MainActivity
import com.example.feenoxapp.utils.Constant.Companion.IS_LOGGED_KEY
import com.example.feenoxapp.utils.Constant.Companion.helper
import com.example.feenoxapp.utils.Constant.Companion.sharedPreferences
import com.example.feenoxapp.utils.ProgressDialogUtils
import com.example.foodapp.utils.showToast
import com.example.foodapp.utils.startActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_authentication.*

class UserAuthenticationActivity : AppCompatActivity() {

    private var isRegister = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_authentication)

        supportActionBar?.hide()

        btn_login.setOnClickListener {
            val email = et_email.text.toString().trim()
            val psw = et_psw.text.toString().trim()

            if(email.isEmpty()){
                et_email.error = "Enter Email"
                et_email.requestFocus()
                et_email.performClick()
            } else if(psw.isEmpty()){
                et_psw.error = "Enter Password"
                et_psw.requestFocus()
                et_psw.performClick()
            } else if(psw.length<6){
                et_psw.error = "Enter Password at least 6 characters"
                et_psw.requestFocus()
                et_psw.performClick()
            } else {
                val editor: SharedPreferences.Editor =  sharedPreferences.edit()
                ProgressDialogUtils.showProgressDialog(this,"Loading, please wait...", false)
                val res = getUserValid(email,psw)
                if(isRegister){
                    if(!res){
                        helper.registerUser(email,psw)
                        showToast("Successfully register")
                        startActivity<MainActivity>(finish = true)
                        editor.putBoolean(IS_LOGGED_KEY, true)
                        editor.apply()
                        ProgressDialogUtils.dismissProgress()
                    }
                } else {
                    if(res){
                        showToast("Successfully Logged")
                        startActivity<MainActivity>(finish = true)
                        editor.putBoolean(IS_LOGGED_KEY, true)
                        editor.apply()
                        ProgressDialogUtils.dismissProgress()
                    } else {
                        showToast("Wrong Email/Password")
                        ProgressDialogUtils.dismissProgress()
                    }
                }
            }
        }

        tv_changeScreenStatus.setOnClickListener {
            if(isRegister){
                isRegister = false
                btn_login.text = getString(R.string.login)
                tv_statusLabel.text = getString(R.string.don_t_have_account)
            } else {
                isRegister = true
                btn_login.text = getString(R.string.register)
                tv_statusLabel.text = getString(R.string.have_account)
            }

            showToast(isRegister)
        }
    }

    @SuppressLint("Range", "Recycle")
    fun getUserValid(email: String, psw: String) : Boolean{
        var isUser = false
        val selectQuery = "SELECT * FROM ${DatabaseHelper.TABLE_USERS} WHERE ${DatabaseHelper.USER_EMAIL}=? AND ${DatabaseHelper.USER_PSW}=?"
        val db : SQLiteDatabase = helper.readableDatabase
        val cursor = db.rawQuery(selectQuery,arrayOf(email,psw))
        if(cursor.count>0){
            isUser = true
        }
        return isUser
    }
}