package com.example.myapplication.rest.Resmain

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.rest.AddRest.AddRestDialog

class ChoiceSikdangPage_res:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_choice_sikdang_page)
        var mySikdangRV:RecyclerView = findViewById(R.id.mySikdangRV)
        var choiceMySikdangRVAdapter = ChoiceMySikdangRVAdapter(this)
        mySikdangRV.adapter = choiceMySikdangRVAdapter

        var RVLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mySikdangRV.layoutManager=RVLayoutManager
        mySikdangRV.setHasFixedSize(true)


        var addMarcket:Button = findViewById(R.id.addMarcket)
        addMarcket.setOnClickListener {
            showAddRestDialog()
        }
    }

    private fun showAddRestDialog(){
        Log.d("확인 showSikdangSettingDialog()", "ㅁㅁ")
        var customDialog = AddRestDialog(this)
        customDialog!!.show()
    }
}