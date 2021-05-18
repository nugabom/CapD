package com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.myapplication.R
import kotlinx.android.synthetic.main.res_onetablesetting_dialog.*

class OneTableSettingDialog(context: Context, val tableFloor:Int, val tableNum:Int, val pNum:Int, val alNum:Int, var tableSettingDialog: TableSettingDialog): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_onetablesetting_dialog)

        var ots_tableTV:TextView = findViewById(R.id.ots_tableTV)
        ots_tableTV.setText(tableFloor.toString()+"층 "+tableNum.toString()+"번 테이블")

        var ots_ET:EditText = findViewById(R.id.ots_ET)
        ots_ET.setText(pNum.toString())
        ots_ET.addTextChangedListener{
            if (ots_ET.text.toString() == "0"){
                val myToast = Toast.makeText(context, "최소 1명 이상이어야 함니다.", Toast.LENGTH_SHORT).show()
                ots_ET.setText("1")
            }
        }

        var otsCancelBtn: Button = findViewById(R.id.otsCancelBtn)
        otsCancelBtn.setOnClickListener { this.dismiss() }

        var ots_deleteBtn:Button=findViewById(R.id.ots_deleteBtn)
        ots_deleteBtn.setOnClickListener {
            tableSettingDialog.deleteTable(alNum)
            this.dismiss()

        }

        var ots_changeBtn:Button = findViewById(R.id.ots_changeBtn)
        ots_changeBtn.setOnClickListener {
            tableSettingDialog.changePnum(alNum, ots_ET.text.toString().toInt())
            this.dismiss()
        }


    }
}