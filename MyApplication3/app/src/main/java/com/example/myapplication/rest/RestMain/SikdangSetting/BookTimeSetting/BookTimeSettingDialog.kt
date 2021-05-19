package com.example.myapplication.rest.RestMain.SikdangSetting.BookTimeSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.rest.Time.TempTimeClass
import com.example.sikdangbook_rest.Time.TimeLineAdapter

class BookTimeSettingDialog(context: Context, val sikdangNum:String): Dialog(context) {

    lateinit var bookTimeSettingRV : RecyclerView
    lateinit var bookTimeSettingRVAdapter:BookTimeSettingRVAdapter

    lateinit var tempTimeClass:TempTimeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_booktimesetting_dialog)

        getTime()


        bookTimeSettingRV  = findViewById(R.id.bookTimeSettingRV)
        bookTimeSettingRVAdapter = BookTimeSettingRVAdapter(context, this)
        bookTimeSettingRV.adapter = bookTimeSettingRVAdapter



        var timeLM = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        bookTimeSettingRV.layoutManager=timeLM
        bookTimeSettingRV.setHasFixedSize(true)

        var bts_addTimeBtn: Button = findViewById(R.id.bts_addTimeBtn)
        bts_addTimeBtn.setOnClickListener {
            showAddTimeDialog()
        }
    }

    public fun deleteTime(slectedTime:String){

    }
    public fun renewalRV(){
        bookTimeSettingRVAdapter.notifyDataSetChanged()
    }
    public fun getTime(){
        tempTimeClass = TempTimeClass(1111)
    }

    public fun addTime(newTime:String){

    }

    public fun showAddTimeDialog(){
        var customDialog = AddTimeDialog(context)
        customDialog!!.show()
    }
}
