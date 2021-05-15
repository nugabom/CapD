package com.example.myapplication.rest.RestMain.SikdangSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import com.example.myapplication.R
import com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting.TableSettingDialog

//SikdangMain_res 에서 사용
//식당 정보 수정 - 어떤거 수정할기 고르는 다이얼로그
class SikdangSettingDialog(context: Context, val sikdangNum:String): Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_sikdangsetting_dialog)


        var changeSikdangNameBtn: Button =findViewById(R.id.changeSikdangNameBtn)
        changeSikdangNameBtn.setOnClickListener {
            showChangeSikdangNameDialog()
        }


        var editSikdangExpBntn:Button = findViewById(R.id.editSikdangExpBntn)
        editSikdangExpBntn.setOnClickListener {
            showEditSikdangExpDialog()
        }

        var editMenuBtn:Button = findViewById(R.id.editMenuBtn)
        editMenuBtn.setOnClickListener {
            showEditMenuDialog()
        }

        var ss_editTableBtn:Button = findViewById(R.id.ss_editTableBtn)
        ss_editTableBtn.setOnClickListener {
            showTableSettingDialog()
        }




    }


    private fun showChangeSikdangNameDialog(){
        var customDialog = ChangeSikdangNameDialog(context,sikdangNum)
        customDialog!!.show()
    }

    private fun showEditSikdangExpDialog(){
        var customDialog = EditSikdangExpDialog(context,sikdangNum)
        customDialog!!.show()
    }

    private fun showEditMenuDialog(){
        var customDialog = EditMenuDialog(context,sikdangNum)
        customDialog!!.show()
    }

    private fun showTableSettingDialog(){
        var customDialog = TableSettingDialog(context,sikdangNum, 1)
        customDialog!!.show()
    }


}