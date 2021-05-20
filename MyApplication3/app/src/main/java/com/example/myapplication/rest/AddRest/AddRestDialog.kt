package com.example.myapplication.rest.AddRest

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.myapplication.R
import com.example.myapplication.rest.RestMain.SikdangSetting.BookTimeSetting.BookTimeSettingDialog

//ChoiceSikdangPage에서 사용
class AddRestDialog(context: Context): Dialog(context) {
    lateinit var ar_sikdangNameRT:EditText
    lateinit var ar_addressET:EditText
    lateinit var ar_detailAddressET:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_addrest_dialog)

        ar_sikdangNameRT=findViewById(R.id.ar_sikdangNameRT)
        ar_addressET=findViewById(R.id.ar_addressET)
        ar_detailAddressET=findViewById(R.id.ar_detailAddressET)

        var ar_AddSikdangBtn: Button = findViewById(R.id.ar_AddSikdangBtn)
        ar_AddSikdangBtn.setOnClickListener {
            addRest()
            this.dismiss()
        }

        var ar_cancelBtn:Button = findViewById(R.id.ar_cancelBtn)
        ar_cancelBtn.setOnClickListener { this.dismiss() }
    }

    //데이터 베이스 접속
    public fun addRest(){
        //아래 세개 데이터 베이스로 넘김
        //ar_sikdangNameRT.text.toString()
        //ar_addressET.text.toString()
        //ar_detailAddressET.text.toString()

        //식당에 이름과 주소만 주고 나머지 비운 채로 생성
        //상세 정보는 식당 수정 페이지에서 직접 등록하는 방향으로 진행
        //0층 00 위치에 크기 30테이블 생성한다거나 하는 식으로 심시데이터 하나씩만 넣음
    }


}