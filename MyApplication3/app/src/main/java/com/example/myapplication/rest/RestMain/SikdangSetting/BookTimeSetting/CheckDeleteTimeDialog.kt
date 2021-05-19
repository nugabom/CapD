package com.example.myapplication.rest.RestMain.SikdangSetting.BookTimeSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.R

class CheckDeleteTimeDialog(context: Context, val deleteTime:String, var bookTimeSettingDialog: BookTimeSettingDialog): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_checking_dialog)
        var text: TextView = findViewById(R.id.checkingText)
        text.setText(deleteTime+" 삭제하시겠습니까?")

        var checkButton: Button = findViewById(R.id.checkButton)
        checkButton.setOnClickListener {
            bookTimeSettingDialog.deleteTime(deleteTime)
            this.dismiss()
        }

        var notCheckButton: Button = findViewById(R.id.notCheckButton)
        notCheckButton.setOnClickListener { this.dismiss() }

    }
}