package com.example.myapplication.rest.ResInfo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

//ResInfoActivity 에서 사용
class SellInfoActivity: AppCompatActivity() {
    var nowPage = 1
    var nowPageSet = 1

    var sellDataAL=ArrayList<SellData>()

    lateinit var si_nBtn1:TextView
    lateinit var si_nBtn2:TextView
    lateinit var si_nBtn3:TextView
    lateinit var si_nBtn4:TextView
    lateinit var si_nBtn5:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_selliffo_activity)

        var si_numBefBtn: Button = findViewById(R.id.si_numBefBtn)
        si_numBefBtn.setOnClickListener {
            if(nowPageSet>1){
                nowPageSet-=1
            }
            pageSet()

        }

        var si_numNextBtn:Button = findViewById(R.id.si_numNextBtn)
        si_numNextBtn.setOnClickListener {
            nowPageSet+=1
            pageSet()
        }

        si_nBtn1=findViewById(R.id.si_nBtn1)
        si_nBtn1.setOnClickListener {
            nowPage = nowPageSet*5-4
            setNowPage()
        }
        si_nBtn2=findViewById(R.id.si_nBtn2)
        si_nBtn2.setOnClickListener {
            nowPage = nowPageSet*5-3
            setNowPage()
        }
        si_nBtn3=findViewById(R.id.si_nBtn3)
        si_nBtn3.setOnClickListener {
            nowPage = nowPageSet*5-2
            setNowPage()
        }
        si_nBtn4=findViewById(R.id.si_nBtn4)
        si_nBtn4.setOnClickListener {
            nowPage = nowPageSet*5-1
            setNowPage()
        }
        si_nBtn5=findViewById(R.id.si_nBtn5)
        si_nBtn5.setOnClickListener {
            nowPage = nowPageSet*5
            setNowPage()
        }

    }

    //nowPageSet 바꿈
    private fun pageSet(){
        si_nBtn1.setText((nowPageSet*5-4).toString())
        si_nBtn2.setText((nowPageSet*5-3).toString())
        si_nBtn3.setText((nowPageSet*5-2).toString())
        si_nBtn4.setText((nowPageSet*5-1).toString())
        si_nBtn5.setText((nowPageSet*5).toString())
    }

    private fun setNowPage(){
        setSellData()
    }
    public fun renewalRV(){
        Log.d("확인 리뉴얼", "되나?")
        //bookTimeSettingRVAdapter.ButtonALReset()
        //bookTimeSettingRVAdapter.vartimePoint=0
        //bookTimeSettingRVAdapter.notifyDataSetChanged()
        //bookTimeSettingRVAdapter.setButtonText()
    }

    public fun setSellData(){

    }

    inner class SellData(){

    }


}