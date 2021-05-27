package com.example.myapplication.rest.RestMain.SikdangSetting

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import com.example.myapplication.R
import com.example.myapplication.rest.Resmain.SikdangMain_res

class EditSikdangImageDialog(context: Context, val sikdangNum:String, var sikdangmainRes: SikdangMain_res): Dialog(context) {

    var isImgAdded = false

    lateinit var esi_newSikdangImgIV:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_editsikdangimage_dialog)

        esi_newSikdangImgIV=findViewById(R.id.esi_newSikdangImgIV)
        esi_newSikdangImgIV.setOnClickListener {
            sikdangmainRes.editSikdangImageDialog=this
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            sikdangmainRes.startActivityForResult(intent, 3)
        }

        var esi_cancelBtn: Button = findViewById(R.id.esi_cancelBtn)
        esi_cancelBtn.setOnClickListener {
            this.dismiss()
        }

        var esi_editBtn:Button = findViewById(R.id.esi_editBtn)
        esi_editBtn.setOnClickListener {
            setNewImgOnDB()
            this.dismiss()
        }
    }

    public fun setNewImg(){
        Log.d("확인 EditSikdangImageDialog.setNewImage", sikdangmainRes.sikdangimgCheckNum.toString())
        if(sikdangmainRes.sikdangimgCheckNum==1){

            esi_newSikdangImgIV.setImageBitmap(sikdangmainRes.sikdangimg)
            sikdangmainRes.sikdangimgCheckNum=0

            isImgAdded = true
            //afterChangeImage.setImageResource(imageRes)
        }


    }

    public fun setNewImgOnDB(){
        //sikdangmainRes.sikdangimg 데이터베이스에 올린다
    }




}