package com.example.myapplication.rest.RestMain.SikdangSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.rest.Table.OrderInfoRVAdapter

//SikdangSettingDialog 에서 사용
//메뉴 리스트 보여주고 거기서 하나 선택해서 그 메뉴 수정하는 다이얼로그
class EditMenuDialog (context: Context, val sikdangNum:String): Dialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_editmenu_dialog)

        var menuRV : RecyclerView = findViewById(R.id.em_rv)
        var RVAdapter = EditMenuRVAdapter(context, sikdangNum)
        menuRV.adapter = RVAdapter


        var LM = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        menuRV.layoutManager=LM
        menuRV.setHasFixedSize(true)

        var em_addMenuBtn:Button = findViewById(R.id.em_addMenuBtn)
        em_addMenuBtn.setOnClickListener {
            showAddMenuDialog()
        }

        var emCloseBtn: Button = findViewById(R.id.emCloseBtn)
        emCloseBtn.setOnClickListener { this.dismiss() }

    }

    public fun showAddMenuDialog(){
        var customDialog = AddMenuDialog(context, sikdangNum)
        customDialog!!.show()
    }
}