package com.example.myapplication.rest.Resmain

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.rest.ResInfo.ResInfoActivity
import com.example.myapplication.rest.RestMain.SikdangSetting.EditSikdangImageDialog
import com.example.myapplication.rest.RestMain.SikdangSetting.MenuEditDialog
import com.example.myapplication.rest.RestMain.SikdangSetting.SikdangSettingDialog
import com.example.myapplication.rest.RestMain.SikdangSetting.TableSetting.ChangeFloorImageDialog
import com.example.sikdangbook_rest.Table.TableFloorVPAdapter_res
import com.example.sikdangbook_rest.Time.TimeSelectDialog
import kotlinx.android.synthetic.main.res_sikdangmain.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class SikdangMain_res:AppCompatActivity() {
    lateinit var sm_drawerLayout: DrawerLayout
    lateinit var tableFloorVP: ViewPager2
    lateinit var vpAdapter: TableFloorVPAdapter_res
    lateinit var nowBtn: ToggleButton
    lateinit var selectedTimeTV: TextView
    lateinit var sm_beforeCheckBtn: ToggleButton
    lateinit var sm_afterCheckBtn: ToggleButton

    lateinit var sm_messageRV: RecyclerView
    lateinit var messageRVAdapter: ResMainMessageRVAdapter

    lateinit var sikdangimg:Bitmap
    var sikdangimgCheckNum = 0
    lateinit var menuEditDialog:MenuEditDialog
    lateinit var changeFloorImageDialog:ChangeFloorImageDialog
    lateinit var editSikdangImageDialog:EditSikdangImageDialog


    private var timeNum = ""
    var sikdangName = "식다아아아앙이름"
    var sikdangNum = "10987654321"

    var messages = ArrayList<MessageData>()

    //전체 알림인지 처리전 알림인지 체크크
    var isAll: Boolean = true

    lateinit var imageView4:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Log.d("확인 CSikdangMain_res", "1")
        setContentView(R.layout.res_sikdangmain)
        Log.d("확인 CSikdangMain_res", "2")

        sm_drawerLayout = findViewById(R.id.sm_drawerLayout)
        Log.d("확인 CSikdangMain_res", "3")
        val time = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("kk:mm")
        val curTime = dateFormat.format(Date(time))
        //Log.d("확인 시간 정상 확인", curTime.toString())
        //val myToast = Toast.makeText(this, curTime.toString(), Toast.LENGTH_SHORT).show()

        Log.d("확인 CSikdangMain_res", "4")
        timeNum = timeNum + curTime[0] + curTime[1] + curTime[3] + curTime[4]

        Log.d("확인 CSikdangMain_res", "5")

        setSikdangInfo()
        setTable()

        setMessage()


        imageView4=findViewById(R.id.imageView4)


        var timeselectBtn: Button = findViewById(R.id.timeselectBtn)
        timeselectBtn.setOnClickListener {
            var customDialog = TimeSelectDialog(this, this)
            customDialog!!.show()
        }

        Log.d("확인 CSikdangMain_res", "6")
        nowBtn = findViewById<ToggleButton>(R.id.nowBtn)
        nowBtn.isChecked = true
        selectedTimeTV = findViewById<TextView>(R.id.selectedTimeTV)
        selectedTimeTV.setText(curTime.toString())
        nowBtn.setOnClickListener {
            nowBtn.isChecked = true
            val time = System.currentTimeMillis()
            val dateFormat = SimpleDateFormat("kk:mm")
            val curTime = dateFormat.format(Date(time))
            selectedTimeTV.setText(curTime.toString())

        }

        var sm_infoBtn: Button = findViewById(R.id.sm_infoBtn)
        sm_infoBtn.setOnClickListener {
            val intent = Intent(this, ResInfoActivity::class.java)
            startActivity(intent)
        }

        //식당 설정 버튼
        var sikdangSettingbtn: Button = findViewById(R.id.sikdangSettingbtn)
        sikdangSettingbtn.setOnClickListener {
            showSikdangSettingDialog()
        }

        //식당 선택 버튼
        var sm_choiceSikdangBtn: Button = findViewById(R.id.sm_choiceSikdangBtn)
        sm_choiceSikdangBtn.setOnClickListener {
            this.finish()
        }

        var sm_messageTV: Button = findViewById(R.id.sm_messageTV)
        sm_messageTV.setOnClickListener {
            sm_drawerLayout.openDrawer(GravityCompat.END)
        }

        sm_beforeCheckBtn = findViewById(R.id.sm_beforeCheckBtn)
        sm_beforeCheckBtn.text = "처리전 알림"
        sm_beforeCheckBtn.textOn = "처리전 알림"
        sm_beforeCheckBtn.textOff = "처리전 알림"

        sm_afterCheckBtn = findViewById(R.id.sm_afterCheckBtn)
        sm_afterCheckBtn.text = "전체 알림"
        sm_afterCheckBtn.textOn = "전체 알림"
        sm_afterCheckBtn.textOff = "전체 알림"


        sm_messageRV = findViewById(R.id.sm_messageRV)
        messageRVAdapter = ResMainMessageRVAdapter(this, this)
        sm_messageRV.adapter = messageRVAdapter

        var layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        sm_messageRV.layoutManager = layoutManager
        sm_messageRV.setHasFixedSize(true)



    }

    var backPressed = false
    var pressedTime = System.currentTimeMillis()
    override fun onBackPressed() {
        //super.onBackPressed()
        if (backPressed == false) {
            Toast.makeText(this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis()
            backPressed = true
        } else {
            var seconds = (System.currentTimeMillis() - pressedTime).toInt();
            if (seconds > 2000) {
                Log.d("확인 종료 안됨", seconds.toString())
                Toast.makeText(this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                backPressed = true;
            } else {

                //Log.d("확인 종료@@@@@@@@@@@@@@@@@@@", "1")
                //Toast.makeText(this, " 종료@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@." , Toast.LENGTH_LONG).show();
                super.onBackPressed()
                //finish(); // app 종료 시키기
            }
        }

    }

    //데이터베이스에서 식당이름 불러온다
    public fun setSikdangInfo() {
        sikdangName = "불러온식당이름"
        sikdangNum = "109876543210"
    }

    public fun setTable() {
        //각 층 들어가는 뷰페이저
        tableFloorVP = findViewById(R.id.tableFloorVP)
        vpAdapter = TableFloorVPAdapter_res(this, this)
        tableFloorVP.adapter = vpAdapter
    }

    public fun setTimeNum(timeNum_: String) {
        timeNum = timeNum_
        selectedTimeTV.setText(timeNum)
        renewalTable()
        nowBtn.isChecked = false
    }

    public fun getTimeNum(): String {
        return timeNum
    }

    public fun renewalTable() {
        vpAdapter.notifyDataSetChanged()
    }

    public fun renewalOrder() {
        messageRVAdapter.notifyDataSetChanged()
    }


    private fun showSikdangSettingDialog() {
        Log.d("확인 showSikdangSettingDialog()", "ㅁㅁ")
        var customDialog = SikdangSettingDialog(this, sikdangNum, this)
        customDialog!!.show()
    }

    //데이터베이스에서 현재 예약 신청 목록을 불러온다.
    public fun setMessage() {
        messages = ArrayList<MessageData>()
        var tempMenu = MessageMenuData("맥콜", 3)
        var tempMenu2 = MessageMenuData("파인애플피자", 5)
        var tempMenu3 = MessageMenuData("민트초코라떼", 1)
        var tempMenuAL1 = ArrayList<MessageMenuData>()
        tempMenuAL1.add(tempMenu)
        tempMenuAL1.add(tempMenu2)
        tempMenuAL1.add(tempMenu3)

        var tempMenu4 = MessageMenuData("맥콜", 3)
        var tempMenu5 = MessageMenuData("파인애플피자", 5)
        var tempMenuAL2 = ArrayList<MessageMenuData>()
        tempMenuAL2.add(tempMenu4)
        tempMenuAL2.add(tempMenu5)

        var tempMenu6 = MessageMenuData("맥콜", 3)
        var tempMenu7 = MessageMenuData("파인애플피자", 3)
        var tempMenu8 = MessageMenuData("오이피자", 1)
        var tempMenu9 = MessageMenuData("초코치킨", 12)
        var tempMenuAL3 = ArrayList<MessageMenuData>()
        tempMenuAL3.add(tempMenu6)
        tempMenuAL3.add(tempMenu7)
        tempMenuAL3.add(tempMenu8)
        tempMenuAL3.add(tempMenu9)

        var tempTableAL1 = ArrayList<MessageTableData>()
        tempTableAL1.add(MessageTableData(2, 8, tempMenuAL1))
        tempTableAL1.add(MessageTableData(2, 9, tempMenuAL2))
        tempTableAL1.add(MessageTableData(3, 11, tempMenuAL3))


        messages.add(MessageData("임꺽정", "010-1234-5678", 80000, 2021, 5, 21, 9, 34, 10, 9, 30, 10, 30, tempTableAL1, "987654321"))

        var temp2Menu = MessageMenuData("맥콜", 3)
        var temp2Menu2 = MessageMenuData("파인애플피자", 5)
        var temp2Menu3 = MessageMenuData("민트초코라떼", 1)
        var temp2MenuAL1 = ArrayList<MessageMenuData>()
        temp2MenuAL1.add(temp2Menu)
        temp2MenuAL1.add(temp2Menu2)
        temp2MenuAL1.add(temp2Menu3)

        var temp2Menu4 = MessageMenuData("맥콜", 3)
        var temp2Menu5 = MessageMenuData("파인애플피자", 5)
        var temp2MenuAL2 = ArrayList<MessageMenuData>()
        temp2MenuAL2.add(temp2Menu4)
        temp2MenuAL2.add(temp2Menu5)

        var temp2Menu6 = MessageMenuData("맥콜", 3)
        var temp2Menu7 = MessageMenuData("파인애플피자", 3)
        var temp2Menu8 = MessageMenuData("오이피자", 1)
        var temp2Menu9 = MessageMenuData("초코치킨", 12)
        var temp2MenuAL3 = ArrayList<MessageMenuData>()
        temp2MenuAL3.add(temp2Menu6)
        temp2MenuAL3.add(temp2Menu7)
        temp2MenuAL3.add(temp2Menu8)
        temp2MenuAL3.add(temp2Menu9)

        var temp2TableAL1 = ArrayList<MessageTableData>()
        temp2TableAL1.add(MessageTableData(1, 1, temp2MenuAL1))
        temp2TableAL1.add(MessageTableData(1, 3, temp2MenuAL2))
        temp2TableAL1.add(MessageTableData(1, 4, temp2MenuAL3))


        messages.add(MessageData("장길산", "010-1234-5678", 90000, 2021, 5, 21, 9, 34, 10, 9, 30, 10, 30, temp2TableAL1, "123456789"))


    }

    //하나의 예약 신청에서 식당 주인이 확인할 정보
    //conName 소비자 이름 pn 소비자 번호 price 가격 y, m, day, h, min, sec, 예약 수락 or 예약 신청 시간 sh, sm 예약 시작시간 eh em 예약 끝 시간
    //orderId 는 주문 고유번호
    inner class MessageData(
            var conName: String, var pn: String, var price: Int,
            var y: Int, var m: Int, var day: Int, var h: Int, var min: Int, var sec: Int,
            var sh: Int, var sm: Int, var eh: Int, var em: Int, var tables: ArrayList<MessageTableData>, var orderId: String
    )


    //테이블별 메뉴 내용
    inner class MessageTableData(var tableFloor: Int, var tableNum: Int, var menus: ArrayList<MessageMenuData>)

    inner class MessageMenuData(var menuName: String, var menuNum: Int)



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    val ins: InputStream? = contentResolver.openInputStream(data?.data!!)
                    sikdangimg = BitmapFactory.decodeStream(ins)
                    ins?.close()
                    Log.d("확인 onActivityResult1", sikdangimg.toString())
                    sikdangimgCheckNum=1
                    //saveBitmap(img)
                    //imageView3.setImageBitmap(img)
                    menuEditDialog.setNewImg()
                } catch (e: Exception) {
                    Log.d("확인 onActivityResult1.e", sikdangimg.toString())
                    sikdangimgCheckNum=1
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show()
                sikdangimgCheckNum=2
            }
        }
        else if (requestCode == 2){
            if (resultCode == RESULT_OK) {
                try {
                    val ins: InputStream? = contentResolver.openInputStream(data?.data!!)
                    sikdangimg = BitmapFactory.decodeStream(ins)
                    ins?.close()
                    Log.d("확인 onActivityResult2", sikdangimg.toString())
                    sikdangimgCheckNum=1
                    //saveBitmap(img)
                    //imageView4.setImageBitmap(sikdangimg)
                    changeFloorImageDialog.setNewImg()

                } catch (e: Exception) {
                    Log.d("확인 onActivityResult2.e", sikdangimg.toString())
                    sikdangimgCheckNum=1
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show()
                sikdangimgCheckNum=2
            }
        }
        else if (requestCode == 3){
            if (resultCode == RESULT_OK) {
                try {
                    val ins: InputStream? = contentResolver.openInputStream(data?.data!!)
                    sikdangimg = BitmapFactory.decodeStream(ins)
                    ins?.close()
                    Log.d("확인 onActivityResult3", sikdangimg.toString())
                    sikdangimgCheckNum=1
                    //saveBitmap(img)
                    //imageView4.setImageBitmap(sikdangimg)
                    editSikdangImageDialog.setNewImg()

                } catch (e: Exception) {
                    Log.d("확인 onActivityResult3.e", sikdangimg.toString())
                    sikdangimgCheckNum=1
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show()
                sikdangimgCheckNum=2
            }
        }

    }



    private fun saveBitmap(bitmap: Bitmap): String
    {
        var folderPath = Environment.getExternalStorageDirectory().absolutePath + "/path/"
        Log.d("확인 onActivityResult", Environment.getExternalStorageDirectory().absolutePath.toString() + "/path/")
        var fileName = "comment.jpeg"
        var imagePath = folderPath + fileName
        var folder = File(folderPath)
        if (!folder.isDirectory)
            folder.mkdirs()
        var out = FileOutputStream(folderPath + fileName)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
        return imagePath
    }








}