package com.example.myapplication.rest.Resmain

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.dataclass.StoreInfo
import com.example.myapplication.rest.AddRest.AddRestDialog
import com.example.myapplication.storeActivity.Review
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_notification.*

class ChoiceSikdangPage_res:AppCompatActivity() {

    var sikdangList = ArrayList<SikdangId>()
    var sikdangInfoList=ArrayList<SikdangInfo>()

    lateinit var mySikdangRV:RecyclerView
    lateinit var choiceMySikdangRVAdapter :ChoiceMySikdangRVAdapter

    lateinit var addRestDialog: AddRestDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_choice_sikdang_page)
        mySikdangRV = findViewById(R.id.mySikdangRV)
        choiceMySikdangRVAdapter = ChoiceMySikdangRVAdapter(this, this)
        mySikdangRV.adapter = choiceMySikdangRVAdapter

        var RVLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mySikdangRV.layoutManager=RVLayoutManager
        mySikdangRV.setHasFixedSize(true)


        setSikdangList()

        var addMarcket:Button = findViewById(R.id.addMarcket)
        addMarcket.setOnClickListener {
            showAddRestDialog()
        }


    }

    public fun setSikdangList(){
        //Log.d("확인 setSikdangList", "1")
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(uid).child("sikdangList")
        //var query = ref.orderByChild("sikdangId")
        //Log.d("확인 setSikdangList", query.toString())
        var a =ref.key
        //Log.d("확인 setSikdangList", a.toString())



        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                sikdangList.clear()
                //Log.d("확인 setSikdangList", "2")
                for (sikdang in snapshot.children) {
                    //var a =snapshot.children
                    //Log.d("확인 setSikdangList", snapshot.children.toString())
                    val sikdangid = sikdang.getValue(SikdangId::class.java)
                    if(sikdangid == null) continue
                    //Log.d("확인 setSikdangList", "3")
                    //Log.d("확인 ReviewFragment", "getFromDB : ${sikdangid}")
                    sikdangList.add(sikdangid)
                }
                //Log.d("확인 ReviewFragment", "getFromDB : ${sikdangList}")
                setSikdangListInfo()
                //choiceMySikdangRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("ReviewFragment", "getFromDB : ${error}")
            }
        })

        /*
        for (i in 0..sikdangList.size-1){
            Log.d("확인 setSikdangList", "반복")
            Log.d("확인 setSikdangList", i.toString()+" "+ sikdangList[i].sikdangId)
        }*/



    }

    public fun setSikdangListInfo(){
        sikdangInfoList.clear()
        //Log.d("확인 setSikdangListInfo()", "1")
        for(k in 0..sikdangList.size-1){
            //Log.d("확인 setSikdangListInfo()", "2"+sikdangList[k].store_type!!+sikdangList[k].sikdangId!!)
            //val uid = FirebaseAuth.getInstance().currentUser.uid
            val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                    .child("Restaurants").child(sikdangList[k].store_type!!).child(sikdangList[k].sikdangId!!)
            //var query = ref.orderByChild("sikdangId")
            //Log.d("확인 setSikdangList", query.toString())
            //var a =ref.key
            //Log.d("확인 setSikdangList", a.toString())

            ref.addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Log.d("확인 setSikdangList", "2")
                    for (sikdangInfo in snapshot.children) {
                        //var a =snapshot.children
                        //Log.d("확인 setSikdangListInfo()", "3"+snapshot.children.toString())
                        val newsikdangInfo = sikdangInfo.getValue(SikdangInfo::class.java)
                        //Log.d("확인 setSikdangListInfo()", "3.5")
                        if(newsikdangInfo!!.store_name == "") continue
                        //Log.d("확인 setSikdangListInfo()", "4")
                        //Log.d("확인 setSikdangListInfo()", "getFromDB : ${newsikdangInfo}")
                        sikdangInfoList.add(newsikdangInfo)
                    }
                    Log.d("확인 setSikdangListInfo()", "getFromDB : ${sikdangInfoList}")
                    //Log.d("확인 setSikdangListInfo()", "6")

                    choiceMySikdangRVAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("확인 setSikdangListInfo()", "5 getFromDB : ${error}")
                }
            })

            /*
            for (i in 0..sikdangList.size-1){
                Log.d("확인 setSikdangList", "반복")
                Log.d("확인 setSikdangList", i.toString()+" "+ sikdangList[i].sikdangId)
            }*/
        }

    }

    private fun showAddRestDialog(){
        Log.d("확인 showSikdangSettingDialog()", "ㅁㅁ")
        var customDialog = AddRestDialog(this, this)
        customDialog!!.show()
    }

    public fun setNewSikdang(){

        var cat =addRestDialog.getCat()
        if (cat == 999){

        }
        else{
            upRes(cat)
        }

    }

    public fun upRes(cat :Int){
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Restaurants").child(addRestDialog.catAL[cat])
        val pushedPostRef: DatabaseReference = ref.push()
        val postId = pushedPostRef.key



        var newRes = hashMapOf<String, Any>(
                "phone_number" to addRestDialog.ar_pnET.text.toString(),
                "store_id" to postId!!,
                "store_name" to addRestDialog.ar_sikdangNameRT.text.toString(),
                "Store_image" to "Null",
                "store_type" to addRestDialog.catAL[cat]
        )

        ref.child(postId!!).child("info").setValue(newRes)
                .addOnSuccessListener {
                    upMenu(ref, postId)
                    upTable(postId)
                    upSikdangOnUser(postId, cat)
                    //finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "식당 업데이트 실패.", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }
        Log.d("확인 ChoiceSikdangPage_res", "식당 업데이트")
    }

    public fun upMenu(ref:DatabaseReference, postId:String){

        val tempMenu = hashMapOf<String, Any>(
                "price" to 100,
                "product" to "임시메뉴",
                "product_exp" to "임시메뉴설명"
        )

        val menuPushedPostRef: DatabaseReference = ref.child(postId!!).child("menu").push()
        val menuPostId = menuPushedPostRef.key

        menuPushedPostRef.setValue(tempMenu)
                .addOnSuccessListener {
                    upIng(ref, postId, menuPostId!!)
                }.addOnFailureListener {
                    Toast.makeText(this, "메뉴 업데이트 실패", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }

        Log.d("확인 ChoiceSikdangPage_res", "메뉴 업데이트")
    }

    public fun upIng(ref:DatabaseReference, postId:String, menuPostId:String ){
        val tempIng = hashMapOf<String, Any>(
                "country" to "임시원산지",
                "ing" to "임시원재료명"
        )

        val ingPushedPostRef: DatabaseReference = ref.child(postId!!)
                .child("menu")
                .child(menuPostId!!)
                .child("ingredients")


        ingPushedPostRef.setValue(tempIng)
                .addOnSuccessListener {
                        //finish()
                    upLoc(postId)
                }.addOnFailureListener {
                    Toast.makeText(this, "재료 업데이트 실패", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }

        Log.d("확인 ChoiceSikdangPage_res", "재료 업데이트")

    }

    public fun upLoc(postId: String){
        val locRef: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Locations")
        val locPushedPostRef: DatabaseReference = locRef.push()
        val locPostId = locPushedPostRef.key

        var newResLoc = hashMapOf<String, Any>(
                "Lat" to 37.535879,
                "Lang" to 126.824997,
                "id" to postId!!,
                "Name" to addRestDialog.ar_sikdangNameRT.text.toString()
        )

        locPushedPostRef.setValue(newResLoc)
                .addOnSuccessListener {
                    //finish()
                    //upSikdangOnUser(postId)
                }.addOnFailureListener {
                    Toast.makeText(this, "위치 업데이트 실패.", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }

        Log.d("확인 ChoiceSikdangPage_res", "위치 업데이트")
    }

    public fun upSikdangOnUser(postId:String, cat:Int){
        //val userid = firebaseAuth.currentUser!!.uid
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(uid).child("sikdangList").child(postId)

        var newSikdang = hashMapOf<String, Any>(
                "sikdangId" to postId,
                "store_type" to addRestDialog.catAL[cat]
        )

        ref.setValue(newSikdang)
                .addOnSuccessListener {
                    //finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "유저에 추가 실패.", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }

        Log.d("확인 ChoiceSikdangPage_res", "유저에 추가")
    }

    public fun upTable(postId:String){
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Tables").child(postId)


        var tempTable = hashMapOf<String, Any>(
                "capacity" to 2,
                "height" to 30,
                "width" to 30,
                "x" to 0.5,
                "y" to 0.5
        )

        ref.child("TableInfo").child("floor_1").setValue(tempTable)
                .addOnSuccessListener {
                    //finish()
                    //upSikdangOnUser(postId)
                }.addOnFailureListener {
                    Toast.makeText(this, "위치 업데이트 실패.", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }

        var tempBookInfo = hashMapOf<String, Any>(
                "cerrent" to 1,
                "max" to 1
        )
        var temptableMutex = hashMapOf<String, Any>(
                "mutex" to 1
        )

        ref.child("Booked").child("floor_1").child("09:00 오전" ).child("BookInfo").setValue(tempBookInfo)
                .addOnSuccessListener {
                    //finish()
                    //upSikdangOnUser(postId)

                }.addOnFailureListener {
                    Toast.makeText(this, "bookinfo 업데이트 실패.", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }

        ref.child("Booked").child("floor_1").child("09:00 오전" ).child("table1").setValue(temptableMutex)
                .addOnSuccessListener {
                    //finish()
                    //upSikdangOnUser(postId)
                }.addOnFailureListener {
                    Toast.makeText(this, "bookinfo 업데이트 실패.", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }




        Log.d("확인 ChoiceSikdangPage_res", "위치 업데이트")
    }
}