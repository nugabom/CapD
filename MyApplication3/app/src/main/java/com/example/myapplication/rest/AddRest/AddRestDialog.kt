package com.example.myapplication.rest.AddRest

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.rest.RestMain.SikdangSetting.BookTimeSetting.BookTimeSettingDialog

class AddRestDialog(context: Context, var nowTerm:String, var bookTimeSettingDialog: BookTimeSettingDialog): Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_addtime_dialog)
    }
}