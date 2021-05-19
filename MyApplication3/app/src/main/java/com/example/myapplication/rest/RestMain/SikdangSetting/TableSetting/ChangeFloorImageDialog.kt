package com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.myapplication.R

//TableSettingDialog에서 사용
//층 단면도 변경 다이얼로그
//
class ChangeFloorImageDialog(context: Context, val sikdangNum: String, val floor: Int, var tableSettingDialog: TableSettingDialog): Dialog(context) {
    //var newUrl:String=""
    var newImg:Int=0
    var isImeSetted = false
    lateinit var cfi_newIV:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_changefloorimage_dialog)

        cfi_newIV=findViewById(R.id.cfi_newIV)

        var cfi_getImgBtn:Button=findViewById(R.id.cfi_getImgBtn)
        cfi_getImgBtn.setOnClickListener {
            setNewImg()
        }

        var cfi_changeBtn:Button = findViewById(R.id.cfi_changeBtn)
        cfi_changeBtn.setOnClickListener {
            if (isImeSetted == true){//이미 새 이미지 갤러리에서 가져온 경우
                if(setNewImgOnDB()){//데이터 올리기 성공한 경우
                    tableSettingDialog.setNewFloorImg(newImg)
                    this.dismiss()
                }
                else{//데이터베이스에 올리기 실패
                    val myToast = Toast.makeText(context, "서버 접속 실패", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                val myToast = Toast.makeText(context, "새 이미지를 불러오십시오", Toast.LENGTH_SHORT).show()
            }

        }

        var cfi_cancelBtn:Button = findViewById(R.id.cfi_cancelBtn)
        cfi_cancelBtn.setOnClickListener { this.dismiss() }


    }

    //이미지 갤러리에서 찾아와서 newImg에 넣는다.
    private fun setNewImg(){
        //아래는 임시로 넣음
        newImg = R.drawable.store_layout_example
        cfi_newIV.setImageResource(newImg)

        if(true){//갤러리에서 가져오기 성공시
            isImeSetted = true
        }
    }

    //데이터베이스에 이미지 올림
    private fun setNewImgOnDB():Boolean{
        if(false){//올리기 실패시
            return false
        }
        return true
    }
}