package com.example.myapplication.rest.RestMain.SikdangSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication._Ingredient
import com.example.myapplication.bookActivity.MenuData

//EditMenuDialog 에서 사용
//메뉴 추가하는 다이얼로그
//메뉴 이름 가격 설명 재료

class AddMenuDialog(context: Context, val sikdangNum: String): Dialog(context) {

    var addIngAL = ArrayList<_Ingredient>()

    lateinit var RVAdapter:AddMenuIngRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_addmenu_dialog)

        var am_setIngBtn = findViewById<Button>(R.id.am_setIngBtn)
        am_setIngBtn.setOnClickListener {
            showIngAddDialog()
        }

        var am_RV : RecyclerView = findViewById(R.id.am_RV)

        RVAdapter = AddMenuIngRVAdapter(context, this)
        am_RV.adapter = RVAdapter

        var LM2 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        am_RV.layoutManager=LM2
        am_RV.setHasFixedSize(true)

        var am_imageView:ImageView=findViewById(R.id.am_imageView)
        var imgUrl=""
        am_imageView.setOnClickListener {
            //이미지 물러오는 코드
            //imgUrl에 이미지 정보 넣음
        }

        var am_menuName:EditText=findViewById(R.id.am_menuName)
        am_menuName.setText("")
        var am_price:EditText = findViewById(R.id.am_price)
        am_price.setText("0")
        var am_menuEXP:EditText=findViewById(R.id.am_menuEXP)



        var am_addBtn = findViewById<Button>(R.id.am_addBtn)
        am_addBtn.setOnClickListener {
            if (am_price.text.toString()==""){
                this.dismiss()
            }
            else{
                addMenu(am_menuName.text.toString(), imgUrl,am_price.text.toString().toInt(), am_menuEXP.text.toString(), addIngAL)
                this.dismiss()
            }
        }

        var am_calcelBtn:Button = findViewById(R.id.am_calcelBtn)
        am_calcelBtn.setOnClickListener {
            this.dismiss()
        }
    }

    public fun showIngAddDialog(){
        var customDialog = IngAddDialog(context, sikdangNum, this)
        customDialog!!.show()
    }

    public fun renewalRV(){
        RVAdapter.notifyDataSetChanged()
    }

    //데이터 베이스 접속헤 MenuData를 새 메뉴로 집어넣음
    private fun addMenu(menuName:String, menuImg:String, menuPrice:Int, menuExp:String, addIngAL:ArrayList<_Ingredient>){
        var newMenu= MenuData(menuName, menuImg, menuPrice, menuExp, addIngAL)


    }

}