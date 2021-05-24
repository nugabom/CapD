package com.example.myapplication.rest.Resmain

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.rest.AddRest.AddRestDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_notification.*

class ChoiceSikdangPage_res:AppCompatActivity() {

    lateinit var addRestDialog: AddRestDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_choice_sikdang_page)
        var mySikdangRV:RecyclerView = findViewById(R.id.mySikdangRV)
        var choiceMySikdangRVAdapter = ChoiceMySikdangRVAdapter(this)
        mySikdangRV.adapter = choiceMySikdangRVAdapter

        var RVLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mySikdangRV.layoutManager=RVLayoutManager
        mySikdangRV.setHasFixedSize(true)


        var addMarcket:Button = findViewById(R.id.addMarcket)
        addMarcket.setOnClickListener {
            showAddRestDialog()
        }
    }

    private fun showAddRestDialog(){
        Log.d("확인 showSikdangSettingDialog()", "ㅁㅁ")
        var customDialog = AddRestDialog(this, this)
        customDialog!!.show()
    }

    public fun setNewSikdang(){
        /*
        FirebaseDatabase.getInstance().getReference("Users")
                .child(FirebaseAuth.getInstance().uid!!)
                .setValue(addRestDialog.newRes)
                .addOnSuccessListener {
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this, "죄송합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }*/

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
                    upSikdangOnUser(postId)
                }.addOnFailureListener {
                    Toast.makeText(this, "위치 업데이트 실패.", Toast.LENGTH_SHORT).show()
                    runOnUiThread{
                        loading.visibility = View.INVISIBLE
                    }
                }

        Log.d("확인 ChoiceSikdangPage_res", "위치 업데이트")
    }

    public fun upSikdangOnUser(postId:String){
        //val userid = firebaseAuth.currentUser!!.uid
        val uid = FirebaseAuth.getInstance().currentUser.uid
        val ref: DatabaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(uid).child("sikdangList").child(postId)

        var newSikdang = hashMapOf<String, Any>(
                "sikdangId" to postId
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