package com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.myapplication.R

class OneTableSettingDialog(context: Context, tableFloor:Int, tableNum:Int, pNum:Int, tableSettingDialog: TableSettingDialog): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_onetablesetting_dialog)
    }
}