package com.example.myapplication.rest.RestMain.SikdangSetting.BookTimeSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
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


        //bookTimeSettingRV.setis
        //bookTimeSettingRVAdapter.setis


        var bts_addTimeBtn: Button = findViewById(R.id.bts_addTimeBtn)
        bts_addTimeBtn.setOnClickListener {
            showAddTimeDialog()
        }



        var bts_cancelBtn:Button = findViewById(R.id.bts_cancelBtn)
        bts_cancelBtn.setOnClickListener { this.dismiss() }
    }

    public fun deleteTime(slectedTime:String){
        tempTimeClass.timeArrayList.remove(slectedTime)
        renewalRV()
    }
    public fun renewalRV(){
        Log.d("확인 리뉴얼", "되나?")
        bookTimeSettingRVAdapter.ButtonALReset()
        bookTimeSettingRVAdapter.vartimePoint=0
        bookTimeSettingRVAdapter.notifyDataSetChanged()
        //bookTimeSettingRVAdapter.setButtonText()
    }
    public fun getTime(){
        tempTimeClass = TempTimeClass(1111)
    }

    public fun addTime(newTime:String):Boolean{
        for (i in 0..tempTimeClass.timeArrayList.size-2){
            var nowTime = tempTimeClass.timeArrayList[i]
            var nextTime = tempTimeClass.timeArrayList[i+1]
            if (newTime.toInt() == nowTime.toInt()){
                val myToast = Toast.makeText(context, "이미 설정되어있는 시간입니다.", Toast.LENGTH_SHORT).show()
                return false
            }
            else if (newTime.toInt() < nextTime.toInt()){
                Log.d("확인 AddTime", "중간에 추가"+i.toString())
                var newTimeAL = ArrayList<String>()
                if(newTime.toInt() < nowTime.toInt()){
                    Log.d("확인 AddTime", newTime+" / "+nextTime+" 처음에 추가"+i.toString())
                    newTimeAL.add(newTime)
                    for (j in i..tempTimeClass.timeArrayList.size-1){
                        newTimeAL.add(tempTimeClass.timeArrayList[j])
                    }
                }
                else{
                    for (j in 0..i){
                        newTimeAL.add(tempTimeClass.timeArrayList[j])
                    }
                    newTimeAL.add(newTime)
                    for (j in i+1..tempTimeClass.timeArrayList.size-1){
                        newTimeAL.add(tempTimeClass.timeArrayList[j])
                    }
                }
                tempTimeClass.timeArrayList=newTimeAL
                Log.d("확인 AddTime", tempTimeClass.timeArrayList.size.toString())
                renewalRV()
                return true
            }

        }
        Log.d("확인 AddTime", "마지막 추가")
        renewalRV()
        tempTimeClass.timeArrayList.add(newTime)

        return true

    }

    public fun showAddTimeDialog(){
        var customDialog = AddTimeDialog(context, this)
        customDialog!!.show()
    }


}
