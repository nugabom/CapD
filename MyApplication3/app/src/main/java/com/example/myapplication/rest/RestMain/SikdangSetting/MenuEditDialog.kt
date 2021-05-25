package com.example.myapplication.rest.RestMain.SikdangSetting

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication._Ingredient
import com.example.myapplication.bookActivity.MenuData
import com.example.myapplication.rest.Resmain.SikdangMain_res


//EditMenuRVAdapter 에서 사용
//선택된 메뉴의 상세 내용 수정하는 다이얼로그

class MenuEditDialog(context: Context, val sikdangNum: String, val menuNum: Int, val menuData: MenuData, var editMenuDialog: EditMenuDialog, var sikdangmainRes: SikdangMain_res): Dialog(context) {

    private final var REQUEST_CODE = 0

    var afterIngAL=ArrayList<_Ingredient>()

    lateinit var RVAdapter2:AfterIngRVAdapter

    lateinit var afterChangeImage:ImageView

    init{
        afterIngAL=menuData.ingredients
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.res_menuedit_dialog)

        var entLayout=findViewById<LinearLayout>(R.id.entLayout)
        entLayout.setOnClickListener {
            closeKeyBoard()
        }
        sikdangmainRes.menuEditDialog=this

        setCanceledOnTouchOutside(false)


        var beforeNameTV = findViewById<TextView>(R.id.beforeNameTV)
        beforeNameTV.setText(menuData.product)

        var beforePriceTV:TextView = findViewById(R.id.beforePriceTV)
        beforePriceTV.setText(menuData.price.toString())

        var beforeExpTV:TextView = findViewById(R.id.beforeExpTV)
        beforeExpTV.setText(menuData.product_exp)


        var beforeIngRV:RecyclerView = findViewById(R.id.beforeIngRV)


        var RVAdapter1 = BeforeIngRVAdapter(context, menuData)
        beforeIngRV.adapter = RVAdapter1

        var LM1 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        beforeIngRV.layoutManager=LM1
        beforeIngRV.setHasFixedSize(true)

        beforeIngRV.setOnClickListener {
            //Log.d("확인 MenuEditDialog","리사이클러뷰 클릭시")
        }

        var beforeChangeImage : ImageView=findViewById(R.id.beforeChangeImage)
        var beforeImgRes = R.drawable.foodimage
        beforeChangeImage.setImageResource(beforeImgRes)



        afterChangeImage =findViewById(R.id.afterChangeImage)
        //afterChangeImage.setImageResource()

        var imageRes:Int=beforeImgRes
        var imgUrl=""
        afterChangeImage.setImageResource(imageRes)
        afterChangeImage.setOnClickListener {
            //갤러리에서 이미지 불러오는 코드
            //imageRes에 변경할 이미지 저장
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            sikdangmainRes.startActivityForResult(intent, 1)
            //getContext().startActivity(intent);
            //getActivity().startActivityForResult(intent, 1)
            //(getContext() as Activity).startActivityForResult(intent, 1)
            //getContext().startActivityForResult(intent, 1)
            //startActivityForResult(intent, 1)
            /*
            while(sikdangmainRes.sikdangimgCheckNum==0){
                Log.d("확인 대기", sikdangmainRes.sikdangimgCheckNum.toString())
                Thread.sleep(1_000)
            }*/



            //imgUrl 에 정보 집어넣음
            //setNewImg() 에서 데이터 베이스 접근하도록 수정
        }



        var afterIngRV :RecyclerView = findViewById(R.id.afterIngRV)

        RVAdapter2 = AfterIngRVAdapter(context, this)
        afterIngRV.adapter = RVAdapter2

        var LM2 = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        afterIngRV.layoutManager=LM2
        afterIngRV.setHasFixedSize(true)



        var meEditIngBtn:Button = findViewById(R.id.meEditIngBtn)
        meEditIngBtn.setOnClickListener {
            showIngEditDialog()
        }

        afterIngRV.setOnClickListener {
            //재료 다이얼로그 출력
            //Log.d("확인 MenuEditDialog","리사이클러뷰 클릭시")
            showIngEditDialog()
        }


        var afterNameET:EditText=findViewById(R.id.afterNameET)
        afterNameET.setText(menuData.product)

        var afterPriceET:EditText=findViewById(R.id.afterPriceET)
        afterPriceET.setText(menuData.price.toString())

        var afterExpET:EditText=findViewById(R.id.afterExpET)
        afterExpET.setText(menuData.product_exp)


        var meChangeBtn:Button = findViewById(R.id.meChangeBtn)
        meChangeBtn.setOnClickListener {
            //afterNameET.text : 변경된 이름
            //afterPriceET.test : 변경된 가격
            //afterExpET : 변경된 메뉴 설명
            //afterIngAL _ingredient 의 ArrayList : 변경된 재료
            //imageRes : 변경할 메뉴 이미지
            menuChange(afterNameET.text.toString(), imgUrl, afterPriceET.text.toString().toInt(), afterExpET.text.toString(), afterIngAL)
            this.dismiss()
        }

        var meDeleteBtn:Button = findViewById(R.id.meDeleteBtn)
        meDeleteBtn.setOnClickListener {
            //데이터베이스에서 이 메뉴 삭제
            deleteMenu()
        }



        var meCancelBtn: Button = findViewById(R.id.meCancelBtn)
        meCancelBtn.setOnClickListener { this.dismiss() }




    }



    //override fun onActivity





    /*
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
            }
            MotionEvent.ACTION_CANCEL -> {
            }
            else -> {
            }
        }
        return true
    }*/


    public fun setNewImg(){

        if(sikdangmainRes.sikdangimgCheckNum==1){
            afterChangeImage.setImageBitmap(sikdangmainRes.sikdangimg)
            var imageRes=afterChangeImage.imageAlpha

            sikdangmainRes.sikdangimgCheckNum=0

            //afterChangeImage.setImageResource(imageRes)
        }

    }

    fun closeKeyBoard(){
        var view = this.currentFocus
        if (view != null){
            //var act = activity as SikdangMain_res
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
        }
    }

    private fun showIngEditDialog(){
        var customDialog = IngEditDialog(context, sikdangNum, this)
        customDialog!!.show()

    }

    public fun renewalAfterIngRV(){
        RVAdapter2.notifyDataSetChanged()
    }


    private fun menuChange(newName: String, imgUrl: String, newPrice: Int, newEXP: String, newIng: ArrayList<_Ingredient>){
        var newMenu = MenuData(newName, imgUrl, newPrice, newEXP, newIng)


        //newData를 데이터베이스로 보내서 수정
    }

    private fun deleteMenu(){
        //이 메뉴 삭제
    }
}