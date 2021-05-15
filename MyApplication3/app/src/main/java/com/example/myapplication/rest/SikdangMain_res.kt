package com.example.sikdangbook_rest

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.rest.RestMain.SikdangSetting.SikdangSettingDialog
import com.example.sikdangbook_rest.Table.TableFloorVPAdapter_res
import com.example.sikdangbook_rest.Time.TimeSelectDialog
import java.text.SimpleDateFormat
import java.util.*

class SikdangMain_res:AppCompatActivity() {
    lateinit var tableFloorVP:ViewPager2
    lateinit var vpAdapter: TableFloorVPAdapter_res
    lateinit var nowBtn:ToggleButton
    lateinit var selectedTimeTV:TextView
    private var timeNum =""
    var sikdangName = "식다아아아앙이름"
    var sikdangNum = "10987654321"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.res_sikdangmain)
        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("kk:mm")
        val curTime = dateFormat.format(Date(time))
        //Log.d("확인 시간 정상 확인", curTime.toString())
        //val myToast = Toast.makeText(this, curTime.toString(), Toast.LENGTH_SHORT).show()

        timeNum=timeNum+curTime[0]+curTime[1]+curTime[3]+curTime[4]



        setSikdangInfo()
        setTable()



        var timeselectBtn: Button = findViewById(R.id.timeselectBtn)
        timeselectBtn.setOnClickListener {
            var customDialog = TimeSelectDialog(this, this)
            customDialog!!.show()
        }

        nowBtn = findViewById<ToggleButton>(R.id.nowBtn)
        nowBtn.isChecked=true
        selectedTimeTV = findViewById<TextView>(R.id.selectedTimeTV)
        selectedTimeTV.setText(curTime.toString())
        nowBtn.setOnClickListener {
            nowBtn.isChecked=true
            val time = System.currentTimeMillis()
            val dateFormat = SimpleDateFormat("kk:mm")
            val curTime = dateFormat.format(Date(time))
            selectedTimeTV.setText(curTime.toString())

        }

        //식당 설정 버튼
        var sikdangSettingbtn:Button = findViewById(R.id.sikdangSettingbtn)
        sikdangSettingbtn.setOnClickListener {
            showSikdangSettingDialog()
        }






    }

    //데이터베이스에서 식당이름 불러온다
    public fun setSikdangInfo(){
        sikdangName="불러온식당이름"
        sikdangNum="109876543210"
    }

    public fun setTable(){
        //각 층 들어가는 뷰페이저
        tableFloorVP= findViewById(R.id.tableFloorVP)
        vpAdapter = TableFloorVPAdapter_res(this, this)
        tableFloorVP.adapter = vpAdapter
    }

    public fun setTimeNum(timeNum_:String){
        timeNum=timeNum_
        selectedTimeTV.setText(timeNum)
        renewalTable()
        nowBtn.isChecked=false
    }
    public fun getTimeNum():String{
        return timeNum
    }

    public fun renewalTable(){
        vpAdapter.notifyDataSetChanged()
    }


    private fun showSikdangSettingDialog(){
        var customDialog = SikdangSettingDialog(this, sikdangNum)
        customDialog!!.show()
    }



}