package com.example.myapplication.bookActivity

import android.content.Intent
import android.net.TrafficStats
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IsFull
import com.example.myapplication.R
import com.example.myapplication.dataclass.StoreInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.core.view.View
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Semaphore
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class PayActivity : AppCompatActivity() {
    companion object {
        val NotSelected = -1
    }
    lateinit var store_name : TextView
    lateinit var requset : TextView
    lateinit var face_price : TextView
    lateinit var coupon_directory : TextView
    lateinit var real_price : TextView
    lateinit var table_directory : RecyclerView
    lateinit var check_select_button : RadioGroup
    lateinit var pay_button : Button
    lateinit var recycler_view_usedCoupon : RecyclerView

    lateinit var stocks : HashMap<String, ChoiceItem>
    lateinit var storeInfo : StoreInfo
    lateinit var coupon_list : ArrayList<Coupon>
    lateinit var selected_time : String
    private lateinit var used_coupons : ArrayList<Coupon>
    var price : Int = 0

    private lateinit var firebaseUser : FirebaseUser
    private var isCouponSet : Boolean = false
    var duplicate : Coupon? = null
    var isFail : Boolean = false
    var isSuccess = false
    var semaphore = Semaphore(1)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("payActivity", "not uploaded ${isCouponSet}")
        setContentView(R.layout.activity_pay)
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        getCouponFromDB()
        stocks = intent.getSerializableExtra("stocks") as HashMap<String, ChoiceItem>
        price = intent.getIntExtra("price", 0)
        storeInfo = intent.getSerializableExtra("store_info") as StoreInfo
        selected_time = intent.getStringExtra("bookTime")!!
        used_coupons = arrayListOf()

        if(price == 0) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            finish()
        }
        val complete_intent = Intent(this, PayComplete::class.java)
        init_UI()
    }

/* Listener */
    fun updatePrice() {
        var discout = 0
        used_coupons.clear()
        for(data in coupon_list) {
            if(data.used == true) {
                discout += data.discount.toInt()
                used_coupons.add(data)
            }
        }
        real_price.text =  (price - discout).toString()
        recycler_view_usedCoupon.adapter!!.notifyDataSetChanged()
    }

    /* Private Function */
    private fun init_UI() {
        store_name = findViewById(R.id.payPageSikdangName)
        store_name.text = storeInfo.store_name
        requset = findViewById(R.id.reqEditText)
        face_price = findViewById(R.id.payPageOriginalPrice)
        face_price.text = "?????? ?????? : ${price}"
        coupon_directory = findViewById(R.id.couponNumTV)
        table_directory = findViewById(R.id.couponRV)
        real_price = findViewById(R.id.totalPriceTV)
        pay_button = findViewById(R.id.payButton)
        check_select_button = findViewById(R.id.check_select_button)
        recycler_view_usedCoupon = findViewById(R.id.recycler_view_used_coupon)

        pay_button.setOnClickListener {
            val radioId = check_select_button.checkedRadioButtonId
            when (radioId) {
                NotSelected->{
                    Toast.makeText(this, "???????????? ????????? ????????? ?????????", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                R.id.kakaoPayCB-> {kakaoPayProcess(price, firebaseUser.uid, storeInfo.store_name!!)}
                R.id.secondPayCB->{bookComplete()}
            }
        }

        coupon_directory.setOnClickListener {
            if(isCouponSet) {
                showDiaglog()
            } else { Toast.makeText(this, "?????? ??????????????? ?????? ???????????? ???????????????.", Toast.LENGTH_SHORT).show()}
        }
        init_table_and_coupons()
    }

    private fun init_table_and_coupons() {
        table_directory.adapter = TableDirectoryAdapter(applicationContext, stocks)
        table_directory.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        table_directory.setHasFixedSize(true)

        recycler_view_usedCoupon.adapter = UsedCouponAdapter(applicationContext, used_coupons)
        recycler_view_usedCoupon.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recycler_view_usedCoupon.setHasFixedSize(true)
    }

    private fun getCouponFromDB() {
        FirebaseDatabase.getInstance().getReference("Coupon")
                .child(firebaseUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        coupon_list  = arrayListOf()
                        getCoupon(snapshot)
                        isCouponSet = true
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.d("PayActivity", "getCouponFromDB: ${error.message}")
                    }
                })
    }

    private fun getCoupon(snapshot: DataSnapshot) {
        for (data in snapshot.children) {
            val proto_coupon = data.getValue(_coupon::class.java)
            if(proto_coupon == null) continue
            if(!Coupon.IsValidCoupon(proto_coupon)) {
                Log.d("PayActivity", "IsValidCoupon : ${proto_coupon.coupon_id} is expired")
                FirebaseDatabase.getInstance().getReference("Coupon")
                        .child(firebaseUser.uid)
                        .child(data.key!!).removeValue()
                continue
            }
            coupon_list.add(Coupon(
                    proto_coupon.coupon_id!!,
                    proto_coupon.min_price!!,
                    proto_coupon.discount!!,
                    proto_coupon.coupon_exp!!,
                    proto_coupon.type!!,
                    proto_coupon.expire!!,
                    false
            ))
        }
        for (data in coupon_list) {
            Log.d("coupon_list", "${data.coupon_id}")
        }
    }

    private fun showDiaglog() {
        CouponDialog(this, coupon_list, this).show()
    }

    private fun kakaoPayProcess(price : Int, user_id : String, store_id : String) {
        val requestBody = FormBody.Builder()
            .add("cid", "TC0ONETIME")
            .add("partner_order_id", user_id)
            .add("partner_user_id", store_id)
            .add("item_name", "??????")
            .add("quantity", "1")
            .add("total_amount", price.toString())
            .add("vat_amount", "200")
            .add("tax_free_amount", "0")
            .add("approval_url", "http://192.168.147.1:3000/kakaopayment")
            .add("fail_url", "http://192.168.147.1:3000/kakaopayment")
            .add("cancel_url", "http://192.168.147.1:3000/kakaopayment")
            .build()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        var client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        var request = okhttp3.Request.Builder()
            .url("https://kapi.kakao.com/v1/payment/ready")
            .addHeader("Authorization", "KakaoAK 697a966c0fe76c2e80470060ab00fe30")
            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
            //.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8")
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d("fail 1", request.headers.toString())
                Log.d("fail 2", request.body.toString())
                Log.d("fail 2", requestBody.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                var buffer = Buffer()
                request.body!!.writeTo(buffer)
                Log.d("sucess 1", request.headers.toString())
                Log.d("sucess 2", buffer.readUtf8())
                val jsonData = response.body!!.string()
                val jobject = JSONObject(jsonData)

                val tid = jobject.getString("tid")
                val popup = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(jobject.getString("next_redirect_pc_url"))
                )
                startActivity(popup)
                var is_sign_up = false
                while (!is_sign_up) {
                    Thread.sleep(4000)
                    client = OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build()

                    val requestBody = FormBody.Builder()
                        .add("cid", "TC0ONETIME")
                        .add("tid", tid)
                        .add("partner_order_id", "partner_order_id")
                        .add("partner_user_id", "partner_user_id")
                        .add("pg_token", "pg_token=xxxxxxxxxxxxxxxxxxxx")
                        .build()

                    var request = okhttp3.Request.Builder()
                        .url("https://kapi.kakao.com/v1/payment/approve")
                        .addHeader("Authorization", "KakaoAK f16d4e8048a301aa905a42c285e88b9d")
                        .addHeader(
                            "Content-Type",
                            "application/x-www-form-urlencoded;charset=utf-8"
                        )
                        //.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8")
                        .post(requestBody)
                        .build()


                    client.newCall(request).enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                            Log.d("fail_after 1", request.headers.toString())
                            Log.d("fail_after 2", request.body.toString())
                            Log.d("fail_after 3", requestBody.toString())
                        }

                        override fun onResponse(call: Call, response: Response) {
                            Log.d("sucess_after 1", request.headers.toString())
                            Log.d("sucess_after 2", buffer.readUtf8())
                            Log.d("sucess_after 3", response.code.toString())
                            val jsonData = response.body!!.string()
                            val jobject = JSONObject(jsonData)
                            if (jobject.getString("code") == "-708") {
                                Log.d("success", "????????? ??????")

                                semaphore.acquire()
                                isSuccess = is_sign_up
                                if(!isSuccess) {
                                    Log.d("sucess", "????????? ????????? ??????~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`")
                                    bookComplete()
                                }
                                is_sign_up = true
                                semaphore.release()
                                return
                            }
                        }
                    })

                }
            }
        })
    }

    private fun bookComplete() {
        var bookHere = ArrayList(stocks.keys)
        var tryToBook: ArrayList<Pair<String, String>> = arrayListOf()
        var cnt = 0
        for (floor_table in bookHere) {
            tryToBook.add(splitTable(floor_table))
            cnt += 1
        }
        var isCached = false
        var complete = false
        while (!isCached) {
            var store_ref = FirebaseDatabase.getInstance().getReference("Tables")
                .child(storeInfo.store_id!!)
                .child("Booked")
                .runTransaction(object :Transaction.Handler{
                    override fun doTransaction(currentData: MutableData): Transaction.Result {
                        for (location in tryToBook) {
                            var ref = currentData.child(location.first)
                                .child(selected_time)
                                .child(location.second)

                            val mutex = ref.getValue(IsTableBooKed::class.java)

                            if (mutex == null) {
                                Log.d("in transaction", "Not cached")
                                return Transaction.abort()
                            }
                            isCached = true
                            if (mutex!!.mutex == 0) {
                                return Transaction.abort()
                            }
                            ref.value = hashMapOf(
                                "mutex" to 0
                            )

                            var bookInfoRef = currentData.child(location.first)
                                .child(selected_time)
                                .child("BookInfo")

                            var bookInfo = bookInfoRef.getValue(BookInfo::class.java)
                            if(bookInfo == null) Transaction.abort()
                            if(!complete) {
                                bookInfoRef.value = hashMapOf(
                                    "current" to (bookInfo!!.current!! - cnt),
                                    "max" to bookInfo!!.max
                                )
                            }
                            complete = true
                        }

                        reservate()
                        val _intent = Intent(this@PayActivity, PayComplete::class.java)
                        _intent.putExtra("request", requset.text.toString())
                        _intent.putExtra("stocks", stocks)
                        startActivity(_intent)
                        return Transaction.success(currentData)
                    }

                    override fun onComplete(
                        error: DatabaseError?,
                        committed: Boolean,
                        currentData: DataSnapshot?
                    ) {
                    }
                })
        }
    }


    private fun splitTable(name : String) : Pair<String, String> {
        var temp = name.split( ":")
        return Pair(temp[0], temp[1])
    }

    private fun reservate() {
        val current_date = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
        val pay_time = SimpleDateFormat("HH:mm a", Locale.KOREA).format(Calendar.getInstance().time)
        var request = requset.text.toString()

        if(TextUtils.isEmpty(request)) request == "NULL"

        var reservation = hashMapOf<String, Any>(
            "userId" to firebaseUser.uid,
            "sikdangId" to storeInfo.store_id.toString(),
            "totalPay" to price,
            "bookTime" to selected_time,
            "payTime" to pay_time,
            "store_type" to storeInfo.store_type!!
        )

        var floor_table_list = hashMapOf<String, String>()
        for (table_id in stocks.keys) {
            floor_table_list.put(table_id, stocks[table_id].toString())
        }

        reservation.put("tables", floor_table_list)

        // Reservation for user
        FirebaseDatabase.getInstance().getReference()
            .child("Reservations")
            .child(firebaseUser.uid)
            .child(current_date)
            .push()
            .setValue(reservation)

        // Reservation for Sikdang
        reservation.put("request", request)

        FirebaseDatabase.getInstance().getReference()
            .child("Reservations")
            .child(storeInfo.store_id!!)
            .child(current_date)
            .push()
            .setValue(reservation)
    }
}

data class BookInfo(val current : Int? = null, val max : Int? = null)