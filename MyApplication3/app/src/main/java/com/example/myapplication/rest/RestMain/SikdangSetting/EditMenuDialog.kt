package com.example.myapplication.rest.RestMain.SikdangSetting

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication._Ingredient
import com.example.myapplication.bookActivity.MenuData
import com.example.myapplication.rest.Resmain.SikdangMain_res
import com.example.myapplication.rest.Table.OrderInfoRVAdapter
import com.google.firebase.database.*

//SikdangSettingDialog 에서 사용
//메뉴 리스트 보여주고 거기서 하나 선택해서 그 메뉴 수정하는 다이얼로그
class EditMenuDialog (context: Context, val sikdangNum:String, var sikdangmainRes: SikdangMain_res): Dialog(context) {

    var menuDataAL = ArrayList<MenuData>()
    var menuKeyAL = ArrayList<String>()
    lateinit var menuRV : RecyclerView
    lateinit var RVAdapter: EditMenuRVAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_editmenu_dialog)

        menuRV = findViewById(R.id.em_rv)
        RVAdapter = EditMenuRVAdapter(context, sikdangNum, sikdangmainRes, this)
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

        getMenuDataOnDB()
    }

    public fun showAddMenuDialog(){
        var customDialog = AddMenuDialog(context, sikdangNum)
        customDialog!!.show()
    }

    //메뉴들의 키 가져온다.
    public fun getMenuDataOnDB(){
        Log.d("확인  getMenuDataOnDB()", "1")
        menuDataAL.clear()
        menuKeyAL.clear()
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Restaurants").child(sikdangmainRes.sikdangType).child(sikdangmainRes.sikdangId).child("menu")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("확인  getMenuDataOnDB()", "2")

                for (menuData in snapshot.children) {
                    Log.d("확인  getMenuDataOnDB()", "3")
                    Log.d("확인  getMenuDataOnDB()", menuData.key.toString())
                    //tableIsBookedAL.add(menuData.value.toString().toInt())
                    menuKeyAL.add(menuData.key.toString())
                }
                Log.d("확인  getMenuDataOnDB()", "4")
                //setTableData()
                getMenuDataByMenuId()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("확인 getTableBookedInfo()", "5 getFromDB : ${error}")
            }
        })

    }


    //가져온 메뉴 키 기반으로 메뉴 데이터 가져온다.
    public fun getMenuDataByMenuId(){
        for (i in 0..menuKeyAL.size-1){
            val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("Restaurants").child(sikdangmainRes.sikdangType).child(sikdangmainRes.sikdangId).child("menu").child(menuKeyAL[i])

            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("확인  getMenuDataByMenuId()", "2")
                    var product:String = ""
                    var image_url : String = ""
                    var price : Int = 0
                    var product_exp: String = ""
                    var ingredients : ArrayList<_Ingredient> = ArrayList()
                    for (menuData in snapshot.children) {
                        if(menuData.key.toString() == "product") product=menuData.value.toString()
                        if(menuData.key.toString() == "image_url") image_url=menuData.value.toString()
                        if(menuData.key.toString() == "price") price=menuData.value.toString().toInt()
                        if(menuData.key.toString() == "product_exp") product_exp=menuData.value.toString()
                        //if(menuData.key.toString() == "ingredients") ingredients=menuData.value.
                        menuKeyAL.add(menuData.key.toString())
                    }
                    //Log.d("확인  getMenuDataByMenuId()", product.toString())
                    //Log.d("확인  getMenuDataByMenuId()", "4")

                    ref.child("ingredients").addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot2: DataSnapshot) {
                            for (menuData2 in snapshot2.children) {
                                ingredients.add(_Ingredient(menuData2.key.toString(), menuData2.value.toString()))
                            }
                            //Log.d("확인 2getMenuDataByMenuId()", "내용확인 : ${ingredients}")
                            //여기서 한번에 MenuData 하나 만들어서 ArrayList에 넣는다.
                            var tempMenuData = MenuData(product, image_url, price, product_exp, ingredients)
                            menuDataAL.add(tempMenuData)
                            Log.d("확인 getMenuDataByMenuId()", "불러오기 완료")
                            renewalMenus()
                        }
                        override fun onCancelled(error: DatabaseError) {
                            Log.d("확인 2getMenuDataByMenuId()", "5 getFromDB : ${error}")
                        }
                    })

                    //setTableData()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("확인 getMenuDataByMenuId()", "5 getFromDB : ${error}")
                }
            })
        }

    }

    public fun renewalMenus() {
        RVAdapter.notifyDataSetChanged()
    }


}